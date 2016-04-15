

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.modelo.ConsultaCude;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.JsfUtil;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.ResourceBundle;
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
    private String cude;
    
    // botonos
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
        }
    }

    /**********************
     * Métodos de acceso **
     **********************/
    
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

    
    public String getCude() {
        return cude;
    }

    public void setCude(String cude) {
        this.cude = cude;
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
        cude = "";
        if(consulta.getCuitEstablecimiento().equals(consulta.getCuitFirmante())){
            resultado = true;
            cude = "990-19002-01";
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("validateCudeExito"));
        }else{
            resultado = false;
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("validateCudeError"));
        }
        validado = true;
        mostrarResult = true;
        consulta = null;
    }
    
    public void limpiar(){
        consulta = new ConsultaCude();
        validado = false;
        resultado = false;
    }
}
