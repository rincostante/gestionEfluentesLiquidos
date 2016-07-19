/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.util.CriptPass;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.util.JsfUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.omnifaces.util.Faces;

/**
 *
 * @author rincostante
 */
public class MbLogin implements Serializable{

    private boolean logeado = false;   
    private String ambito;
    private Usuario usLogeado;
    private Long totalEst;
    private Long totalDec;
    private Long totalFirm;
    private Long totalUsExt;
    private Long totalInmGeo;

    @EJB
    private BackendSrv backendSrv;
    private boolean iniciado;
    
    /**
     * Creates a new instance of MbLogin
     */
    public MbLogin() {
    }

    public Long getTotalEst() {
        return totalEst;
    }

    public void setTotalEst(Long totalEst) {
        this.totalEst = totalEst;
    }

    public Long getTotalDec() {
        return totalDec;
    }

    public void setTotalDec(Long totalDec) {
        this.totalDec = totalDec;
    }

    public Long getTotalFirm() {
        return totalFirm;
    }

    public void setTotalFirm(Long totalFirm) {
        this.totalFirm = totalFirm;
    }

    public Long getTotalUsExt() {
        return totalUsExt;
    }

    public void setTotalUsExt(Long totalUsExt) {
        this.totalUsExt = totalUsExt;
    }

    public Long getTotalInmGeo() {
        return totalInmGeo;
    }

    public void setTotalInmGeo(Long totalInmGeo) {
        this.totalInmGeo = totalInmGeo;
    }
    
    public boolean isLogeado() {
        return logeado;
    }

    public void setLogeado(boolean logeado) {
        this.logeado = logeado;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public Usuario getUsLogeado() {
        return usLogeado;
    }

    public void setUsLogeado(Usuario usLogeado) {
        this.usLogeado = usLogeado;
    }

    public boolean isIniciado() {
        return iniciado;
    }

    public void setIniciado(boolean iniciado) {
        this.iniciado = iniciado;
    }       
    
    /**
     * Método para inicializar el listado de los Mb activos
     */
    @PostConstruct
    public void init(){
        iniciado = false;
    }
    
    /**
     * Método que borra de la memoria los MB innecesarios al cargar el listado 
     */
    public void iniciar() throws IOException{
        try{
            totalEst = backendSrv.getTotalEstab();
            totalDec = backendSrv.getTotalDjReg();
            totalFirm = backendSrv.getTotalFirm();
            totalUsExt = backendSrv.getTotalUsExt();
            totalInmGeo = backendSrv.getTotalInmGeo();
        }catch(Exception ex){
            JsfUtil.addErrorMessage("Hubo un error obteniendo los totales. " + ex.getMessage());
        }
        if(iniciado){
            String s;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
            .getExternalContext().getSession(true);
            Enumeration enume = session.getAttributeNames();
            while(enume.hasMoreElements()){
                s = (String)enume.nextElement();
                if(s.substring(0, 2).equals("mb")){
                    if(!s.equals("mbLogin")){
                        session.removeAttribute(s);
                    }
                }
            }
        }else{
            // obtengo el nombre del usuario logeado de la cookie correspondiente
            String nomUsuario;
            String valueEnc = Faces.getRequestCookie(ResourceBundle.getBundle("/Bundle").getString("nameCookieUser"));
            
            try {
                // desencripto el nombre
                nomUsuario = CriptPass.desencriptar(valueEnc);
                
                // obtebtengo el usuario correspondiente al nombre desencriptado
                if(backendSrv.getUsuario(nomUsuario) != null){
                    usLogeado = backendSrv.getUsuario(nomUsuario);
                    ambito = usLogeado.getRol().getNombre();
                    iniciado = true;
                }else{
                    // si no tengo un usuario registrado en la aplicación para este nombre, redirecciono a la vista de error de acceso
                    FacesContext fc=FacesContext.getCurrentInstance();
                    fc.getExternalContext().redirect(ResourceBundle.getBundle("/Bundle").getString("logError"));
                }
            } catch (Exception ex) {
                Logger.getLogger(MbLogin.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
    }      
    
    public void logout(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        iniciado = false;
    }      
}
