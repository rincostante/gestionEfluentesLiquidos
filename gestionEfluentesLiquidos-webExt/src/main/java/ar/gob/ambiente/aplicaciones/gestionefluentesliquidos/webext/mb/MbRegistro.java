/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.modelo.SolicitudCuenta;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.JsfUtil;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rincostante
 */
public class MbRegistro implements Serializable{
    
    private boolean solicitado;
    private boolean resultado;
    private boolean mostrarResult;
    private SolicitudCuenta solicitud;   
    private String mensajeError;

    /****************
     * Constructor **
     ****************/
    public MbRegistro() {
    }
    
    @PostConstruct
    public void init(){
        String s;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
        .getExternalContext().getSession(true);
        Enumeration enume = session.getAttributeNames();
        while(enume.hasMoreElements()){
            s = (String)enume.nextElement();
            if(s.substring(0, 2).equals("mb")){
                if(!s.equals("mbRegistro")){
                    session.removeAttribute(s);
                }
            }
        }
    }
    
    public void iniciar(){
        if(solicitado){
            solicitado = false;
        }else{
            resultado = false;
            mostrarResult = false;
            solicitud = new SolicitudCuenta();
        }
    }    

    /**********************
     * Métodos de acceso **
     **********************/
    
    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

    public boolean isSolicitado() {
        return solicitado;
    }

    public void setSolicitado(boolean solicitado) {
        this.solicitado = solicitado;
    }

    public boolean isMostrarResult() {
        return mostrarResult;
    }

    public void setMostrarResult(boolean mostrarResult) {
        this.mostrarResult = mostrarResult;
    }

    public SolicitudCuenta getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudCuenta solicitud) {
        this.solicitud = solicitud;
    }
    
  
    /*****************************
     * Métodos de procesamiento **
     *****************************/
    
    public void solicitar(){
        if(solicitud.getCuitEstablecimiento().equals(solicitud.getCuitFirmante())){
            resultado = true;
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("validateRegExito"));
        }else{
            // supondremos que el correo es inválido y que el cude también
            resultado = false;
            mensajeError = "Su correo electrónico es iválido. Su CUDE es inválido.";
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("panelRegIvalidoMessage_1") );
        }
        solicitado = true;
        mostrarResult = true;
        solicitud = null;
    }
    
    public void limpiar(){
        solicitud = new SolicitudCuenta();
        solicitado = false;
        resultado = false;
    }    

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
