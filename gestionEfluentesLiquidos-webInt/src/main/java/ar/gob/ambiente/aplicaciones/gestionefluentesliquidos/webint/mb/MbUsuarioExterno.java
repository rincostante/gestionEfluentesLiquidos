

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.UsuarioExterno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.util.JsfUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;

/**
 * Bean para la gestión de usuarios externos
 * @author rincostante
 */
public class MbUsuarioExterno implements Serializable{
    
    private UsuarioExterno current;
    private UsuarioExterno firmanteSelected;
    private MbLogin login;
    private Usuario usLogeado;
    private boolean iniciado;
    private int update; // 0=updateNormal | 1=deshabiliar | 2=habilitar
    private static final Logger logger = Logger.getLogger(UsuarioExterno.class.getName());   
    private List<UsuarioExterno> listado = null;
    private List<UsuarioExterno> listaFilter;       

    
    @EJB
    private BackendSrv backendSrv;       

    public MbUsuarioExterno() {
    }
    
    /**********************
     * Métodos de acceso **
     **********************/
    public UsuarioExterno getCurrent() {
        return current;
    }

    public void setCurrent(UsuarioExterno current) {
        this.current = current;
    }

    public UsuarioExterno getFirmanteSelected() {
        return firmanteSelected;
    }

    public void setFirmanteSelected(UsuarioExterno firmanteSelected) {
        this.firmanteSelected = firmanteSelected;
    }

    public List<UsuarioExterno> getListado() {
        if (listado == null || listado.isEmpty()) {
            listado = backendSrv.getUsrExtAll();
        }  
        
        return listado;
    }

    public void setListado(List<UsuarioExterno> listado) {
        this.listado = listado;
    }

    public List<UsuarioExterno> getListaFilter() {
        return listaFilter;
    }

    public void setListaFilter(List<UsuarioExterno> listaFilter) {
        this.listaFilter = listaFilter;
    }
    
    /**
     * @return La entidad gestionada
     */
    public UsuarioExterno getSelected() {
        if (current == null) {
            current = new UsuarioExterno();
        }
        return current;
    }   
    
 
    /****************************
     * Métodos de inicialización
     ****************************/
    /**
     * Método que se ejecuta luego de instanciada la clase e inicializa los datos del usuario
     */
    @PostConstruct
    public void init(){
        iniciado = false;
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        login = (MbLogin)ctx.getSessionMap().get("mbLogin");
        usLogeado = login.getUsLogeado();  
    }
    
    /**
     * Método que borra de la memoria los MB innecesarios al cargar el listado 
     */
    public void iniciar(){
        if(!iniciado){
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
        }
    }       
    
    /**
     * @return acción para el listado de entidades a mostrar en el list
     */
    public String prepareList() {
        recreateModel();
        return "list";
    }    
    
    /**
     * @return acción para el detalle de la entidad
     */
    public String prepareView() {
        return "view";
    }
    
    /** (Probablemente haya que embeberlo con el listado para una misma vista)
     * @return acción para el formulario de nuevo
     */
    public String prepareCreate() {
        current = new UsuarioExterno();
        return "new";
    }    
    
    /**
     * @return acción para la edición de la entidad
     */
    public String prepareEdit() {
        update = 0;
        return "edit";
    }       

    
    /*************************
     * Métodos de operación **
     *************************/
    
    /**
     * Restea la entidad
     */
    private void recreateModel() {
        listado = null;
    }      
    
    /**
     * Método para actualizar un Usurio Externo existente
     * @return
     */
    public String update() {
        Date date = new Date(System.currentTimeMillis()); 
        // actualizamos según el valor de update
        if(update == 1){
            current.getAdmin().setFechaBaja(date);
            current.getAdmin().setUsBaja(usLogeado);
            current.getAdmin().setHabilitado(false);
        }
        if(update == 2){
            current.getAdmin().setFechaModif(date);
            current.getAdmin().setUsModif(usLogeado);
            current.getAdmin().setHabilitado(true);
            current.getAdmin().setFechaBaja(null);
            current.getAdmin().setUsBaja(usLogeado);
        }
        if(update == 0){
            current.getAdmin().setFechaModif(date);
            current.getAdmin().setUsModif(usLogeado);
        }

        // acualizo
        try {
            backendSrv.editUsuarioExterno(current);
            switch (update) {
                case 0:
                    JsfUtil.addSuccessMessage("El Usuario Externo fue actualizado con éxito");
                    return "view";
                case 1:
                    JsfUtil.addSuccessMessage("El Usuario Externo fue deshabilitado con éxito");
                    return "view";
                default:
                    JsfUtil.addSuccessMessage("El Usuario Externo fue habilitado con éxito");
                    return "view";
            }
        }catch (Exception e) {
            JsfUtil.addErrorMessage("Hubo un error actualizando el Usuario Externo: " + e.getMessage());
            return null;
        }
    }    
    
    
    /********************************************
     ** Métodos de selección para el converter **
     ********************************************/
    
    /**
     * @param id equivalente al id de la entidad persistida
     * @return la entidad correspondiente
     */
    public UsuarioExterno getUsuarioExt(java.lang.Long id) {
        return backendSrv.getUsrExtByID(id);
    }      
    
    /********************************************************************
     ** Converter. Se debe actualizar la entidad y el facade respectivo **
     *********************************************************************/
    @FacesConverter(forClass = UsuarioExterno.class)
    public static class UsuarioExtControllerConverter implements Converter {

         /**
         *
         * @param facesContext
         * @param component
         * @param value
         * @return
         */
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbUsuarioExterno controller = (MbUsuarioExterno) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbUsuarioExterno");
            return controller.getUsuarioExt(getKey(value));
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

        /**
         *
         * @param facesContext
         * @param component
         * @param object
         * @return
         */
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
