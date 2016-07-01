/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.AdminEntidad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.DeclaracionJurada;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.HistorialDeclaraciones;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Recibo;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.UsuarioExterno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.JsfUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author rincostante
 */
public class MbRecibo implements Serializable{

    private HistorialDeclaraciones historial;
    private List<HistorialDeclaraciones> listHistorial;
    private MbSesion sesion;
    private UsuarioExterno usLogueado;
    private Establecimiento est;
    private DeclaracionJurada decJurada;
    JasperPrint jasperPrint;
    
    @EJB
    private BackendSrv backendSrv;

    public MbRecibo() {
    }
    
    @PostConstruct
    public void init(){
        // obtento el usuario
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        sesion = (MbSesion)ctx.getSessionMap().get("mbSesion");
        usLogueado = sesion.getUsuario();   
        
        listHistorial = new ArrayList<>();
        decJurada = backendSrv.getDeclaracionByCude(usLogueado.getCude());
        
        historial = backendSrv.getHistorialDecByDec(decJurada);
    }
    
    public void iniciar() throws JRException, IOException{
        // instancio el recibo si no lo tiene
        String codRecibo;
        if(decJurada.getRecibo() == null){
            Recibo recibo = new Recibo();
            Date date = new Date(System.currentTimeMillis());
            recibo.setFecha(date);
            Integer idUltimo = backendSrv.getUltimoIdRecibo();
            if(idUltimo == null){
                codRecibo = "1-15"; 
            }else{
                codRecibo = String.valueOf(idUltimo + 1) + "-16";
            }
            recibo.setCodigo(codRecibo);
            
            //seteo admin
            AdminEntidad admEnt = new AdminEntidad();
            admEnt.setFechaAlta(date);
            admEnt.setHabilitado(true);
            admEnt.setUsExtAlta(usLogueado);
            recibo.setAdmin(admEnt);
            
            try{
                // inserto
                backendSrv.createRecibo(recibo);
                decJurada.setRecibo(recibo);
                // actualizo declaración
                backendSrv.editDeclaracion(decJurada);
            }catch(Exception ex){
                JsfUtil.addErrorMessage("Hubo un error al crear el Recibo de la Declaración. " + ex.getMessage());
            }
        }
 
        listHistorial.add(historial);
        
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listHistorial);
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Recibo.jasper");
        jasperPrint =  JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=reciboDeclaracion.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarReporte() throws JRException, IOException{
        /*
        listHistorial.add(historial);
        
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listHistorial);
        String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/Recibo.jasper");
        jasperPrint =  JasperFillManager.fillReport(reportPath, new HashMap(), beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=reciboDeclaracion.pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
        */
    }
}
