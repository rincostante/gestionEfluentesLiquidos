

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.modelo.ConsultaCude;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Bean de respaldo para el formulario de solicitud de CUDE
 * @author rincostante
 */
public class MbCude implements Serializable{
    
    private boolean validado;
    private boolean resultado;
    private boolean mostrarResult;
    private ConsultaCude consulta;
    private Establecimiento est;
    private List<Establecimiento> lstEst;
    
    @EJB
    private BackendSrv backendSrv;    
    
    // botones
    private String cmbValidar;
    private String cmbLimpiar;
    
    /**********************
     * Métodos de inicio **
     **********************/
    
    public MbCude() {

    }

    public void iniciar(){
        if(validado){
            validado = false;
        }else{
            String s;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
            .getExternalContext().getSession(true);
            Enumeration enume = session.getAttributeNames();
            while(enume.hasMoreElements()){
                s = (String)enume.nextElement();
                if(s.substring(0, 2).equals("mb")){
                    if(!s.equals("mbCude") && !s.equals("mbSesion")){
                        session.removeAttribute(s);
                    }
                }
            }
            cmbValidar = "Validar";
            cmbLimpiar = "Limpiar";
            
            resultado = false;
            mostrarResult = false;
            consulta = new ConsultaCude();
            lstEst = new ArrayList<>();
        }
    }

    /**********************
     * Métodos de acceso **
     **********************/   
    public Establecimiento getEst() {
        return est;
    }

    public void setEst(Establecimiento est) {
        this.est = est;
    }

    public List<Establecimiento> getLstEst() {
        return lstEst;
    }

    public void setLstEst(List<Establecimiento> lstEst) {
        this.lstEst = lstEst;
    }
   
    public String getCmbValidar() {
        return cmbValidar;
    }

    public void setCmbValidar(String cmbValidar) {
        this.cmbValidar = cmbValidar;
    }

    public String getCmbLimpiar() {
        return cmbLimpiar;
    }

    public void setCmbLimpiar(String cmbLimpiar) {
        this.cmbLimpiar = cmbLimpiar;
    }

    
    public boolean isMostrarResult() {
        return mostrarResult;
    }

    public void setMostrarResult(boolean mostrarResult) {
        this.mostrarResult = mostrarResult;
    }
    
    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }
    
    public ConsultaCude getConsulta() {
        return consulta;
    }

    public void setConsulta(ConsultaCude consulta) {
        this.consulta = consulta;
    }
    
    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }
    
    
    /*****************************
     * Métodos de procesamiento **
     *****************************/
    
    public void validar(){
        boolean valida = true;
        String msgError = "";
        
        if(consulta.getDniLetra() != null){
            if(consulta.getDniLetra().length() > 1){
                valida = false;
                msgError = msgError + "El DNI solo puede contener una letra.";
            }else{
                if(!consulta.getDniLetra().equals("M") && !consulta.getDniLetra().equals("m") 
                        && !consulta.getDniLetra().equals("F") && !consulta.getDniLetra().equals("f")){
                    valida = false;
                    msgError = msgError + "La letra del DNI solo puede ser una 'F' o una 'M'.";
                }
            }
        }
        
        if(valida){
            lstEst = backendSrv.getEstablecimientosByCuit(consulta.getCuitEstablecimiento());

            if(lstEst.isEmpty()){
                resultado = false;
                JsfUtil.addErrorMessage("Su consulta no devolvió ningún Establecimiento, esto puede ser por diferentes motivos, "
                        + "le solicitamos acercarse a nuestras oficinas para realizar el trámtite personalmente. ¡Muchas gracias!");
            }else{
                resultado = true;
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("validateCudeExito"));
            }
            validado = true;
            mostrarResult = true;
            consulta = new ConsultaCude();
        }else{
            JsfUtil.addErrorMessage("No se pudieron validar los datos de entrada. " + msgError);
        }
    }
    
    public void limpiar(){
        consulta = new ConsultaCude();
        validado = false;
        resultado = false;
    }
}
