

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.mb;


import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.UsuarioExterno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.modelo.Sesion;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.JsfUtil;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Bean que gestiona el login y toda la sesión del usuario registrado
 * @author rincostante
 */
public class MbSesion implements Serializable{

    private String cude;
    private String contrasenia;
    private boolean logeado = false; 
    private UsuarioExterno usuario;  
    private Sesion sesion;
    private boolean recupEnviado;
    
    @EJB
    private BackendSrv backendSrv;    

    public MbSesion(){
    }
    
    public void iniciar(){
        cude = "";
        contrasenia = "";
        sesion = new Sesion();
    }    
    
    /**********************
     * Métodos de acceso **
     **********************/
    public boolean isRecupEnviado() {
        return recupEnviado;
    }

    public void setRecupEnviado(boolean recupEnviado) {
        this.recupEnviado = recupEnviado;
    }

    public Sesion getSesion() {
        return sesion;
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    public UsuarioExterno getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioExterno usuario) {
        this.usuario = usuario;
    }

    public String getCude() {
        return cude;
    }

    public void setCude(String cude) {
        this.cude = cude;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean isLogeado() {
        return logeado;
    }

    public void setLogeado(boolean logeado) {
        this.logeado = logeado;
    }

    public UsuarioExterno getUsuarioExterno(java.lang.Long id) {
        return getFacade().getUsrExtByID(id);
    }     
   
    
    /***********************
     * Métodos operativos **
     ***********************/
    public void login(){
        JsfUtil.addSuccessMessage("Bienvenid@");
    }
    
    public void recuperarContrasenia(){
        JsfUtil.addErrorMessage("Su nueva contraseña ha sido enviada a su cuenta de correo electrónico.");
        recupEnviado = true;
    }
    

    /*********************
    ** Métodos privados **
    **********************/
    
    private BackendSrv getFacade() {
        return backendSrv;
    }   

    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = UsuarioExterno.class)
    public static class EstablecimientoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbSesion controller = (MbSesion) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbSesion");
            return controller.getUsuarioExterno(getKey(value));
        }

        
        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }
       
        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof UsuarioExterno) {
                UsuarioExterno o = (UsuarioExterno) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UsuarioExterno.class.getName());
            }
        }
    }       
}
