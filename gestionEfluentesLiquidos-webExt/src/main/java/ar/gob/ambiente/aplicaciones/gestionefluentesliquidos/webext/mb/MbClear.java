

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.mb;

import java.io.Serializable;
import java.util.Enumeration;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Bean para la limpieza de Mb residuales y cierre de sesión
 * @author rincostante
 */
public class MbClear implements Serializable{

    public MbClear() {
    }
    
    /**
     * Método para limpiar los Mb que no se usan durante la sesión
     */
    public void limpiarConSesion(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
        .getExternalContext().getSession(true);
        //Enumeration enume = session.getAttributeNames();
        session.removeAttribute("mbDeclaraciones");
        session.removeAttribute("mbEstablecimiento");
        session.removeAttribute("mbRecibo");
    }    
    
    /**
     * Método para limpiar los Mb que no se usan durante la navegación sin sesión
     */
    public void limpiarSinSesion(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
        .getExternalContext().getSession(true);
        //Enumeration enume = session.getAttributeNames();
        session.removeAttribute("mbCude");
        session.removeAttribute("mbRegistro");
        session.removeAttribute("mbSesion");
    }
}
