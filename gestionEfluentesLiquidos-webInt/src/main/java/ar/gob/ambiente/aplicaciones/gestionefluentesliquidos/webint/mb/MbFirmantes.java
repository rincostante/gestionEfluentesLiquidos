

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.AdminEntidad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Firmante;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.HistorialFirmantes;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.util.JsfUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

/**
 * Clase que encapsula los datos correspondientes a los Firmantes representantes de cada Establecimiento
 * @author rincostante
 */
public class MbFirmantes implements Serializable{

    private Firmante current;
    private Firmante firmanteSelected;
    private MbLogin login;
    private Usuario usLogeado;
    private boolean iniciado;
    private int update; // 0=updateNormal | 1=deshabiliar | 2=habilitar
    private static final Logger logger = Logger.getLogger(Firmante.class.getName());
    private Long cuit;    
    private List<Firmante> listado = null;
    private List<Firmante> listaFilter;       
    private List<Establecimiento> listEstFilter;
    private List<HistorialFirmantes> listHistFirmFilter;
    
    @EJB
    private BackendSrv backendSrv;    
    
    public MbFirmantes() {
    }

    /**********************
     * Métodos de acceso **
     **********************/
    public List<HistorialFirmantes> getListHistFirmFilter() {
        return listHistFirmFilter;
    }
    
    public void setListHistFirmFilter(List<HistorialFirmantes> listHistFirmFilter) {
        this.listHistFirmFilter = listHistFirmFilter;
    }

    public List<Establecimiento> getListEstFilter() {
        return listEstFilter;
    }

    public void setListEstFilter(List<Establecimiento> listEstFilter) {
        this.listEstFilter = listEstFilter;
    }

    public List<Firmante> getListado() {
        if (listado == null || listado.isEmpty()) {
            listado = backendSrv.getFirmantesAll();
        }        
        return listado;
    }

    public void setListado(List<Firmante> listado) {
        this.listado = listado;
    }

    public List<Firmante> getListaFilter() {
        return listaFilter;
    }

    public void setListaFilter(List<Firmante> listaFilter) {
        this.listaFilter = listaFilter;
    }

    public Firmante getCurrent() {
        return current;
    }

    public void setCurrent(Firmante current) {
        this.current = current;
    }

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }
    
    /**
     * @return La entidad gestionada
     */
    public Firmante getSelected() {
        if (current == null) {
            current = new Firmante();
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
        current = new Firmante();
        return "new";
    }    
    
    /**
     * @return acción para la edición de la entidad
     */
    public String prepareEdit() {
        return "edit";
    }          

 
    /*************************
     ** Métodos de selección **
     **************************/ 
    /**
     * @param id equivalente al id de la entidad persistida
     * @return la entidad correspondiente
     */
    public Firmante getFirmante(java.lang.Long id) {
        return backendSrv.getFirmanteByID(id);
    }      
    
    /*************************
     * Métodos de operación **
     *************************/
    
    /**
     */    
    public void habilitar() {
        update = 2;
        update();        
        recreateModel();
    }  
     /**
     */    
    public void deshabilitar() {
        if(current.getEstablecimientos().isEmpty()){
            update = 1;
            update();
        }
        recreateModel();
    }    
    
    /**
     * Restea la entidad
     */
    private void recreateModel() {
        listado = null;
    }    
    
    /**
     * Método para crear un Firmante
     * @return 
     */
    public String create() {       
        try {
            Date date = new Date(System.currentTimeMillis());
            AdminEntidad admin = new AdminEntidad();
            admin.setFechaAlta(date);
            admin.setUsAlta(usLogeado);
            admin.setHabilitado(true);
            current.setAdmin(admin);
            String tempNombre = current.getNombreYApellido();
            current.setNombreYApellido(tempNombre.toUpperCase());
            backendSrv.createFirmante(current);
            JsfUtil.addSuccessMessage("El Firmante se ha registrado con éxito.");
            return "view";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Hubo un error registrando el Firmante: " + e.getMessage());
            return null;
        }
    }    
    
    /**
     * Método para actualizar un Firmante existente
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
            String tempNombre = current.getNombreYApellido();
            current.setNombreYApellido(tempNombre.toUpperCase());
            backendSrv.editFirmante(current);
            switch (update) {
                case 0:
                    JsfUtil.addSuccessMessage("El Firmante fue actualizado con éxito");
                    return "view";
                case 1:
                    JsfUtil.addSuccessMessage("El Firmante fue deshabilitado con éxito");
                    return "view";
                default:
                    JsfUtil.addSuccessMessage("El Firmante fue habilitado con éxito");
                    return "view";
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Hubo un error actualizando el Firmante: " + e.getMessage());
            return null;
        }
    }    
    
    /*****************
     * Validaciones **
     *****************/
    /**
     * Método para validar que no exista ya una entidad con este nombre al momento de crearla
     * @param arg0: vista jsf que llama al validador
     * @param arg1: objeto de la vista que hace el llamado
     * @param arg2: contenido del campo de texto a validar 
     */
    public void validarInsert(FacesContext arg0, UIComponent arg1, Object arg2){
        validarExistente(arg2);
    }
    
    /**
     * Método para validar que no exista una entidad con este nombre, siempre que dicho nombre no sea el que tenía originalmente
     * @param arg0: vista jsf que llama al validador
     * @param arg1: objeto de la vista que hace el llamado
     * @param arg2: contenido del campo de texto a validar 
     * @throws ValidatorException 
     */
    public void validarUpdate(FacesContext arg0, UIComponent arg1, Object arg2){
        if(current.getDni() != (Long)arg2){
            validarExistente(arg2);
        }
} 
    
    
    /*********************
    ** Métodos privados **
    **********************/

    private void validarExistente(Object arg2) throws ValidatorException{
        if(!backendSrv.firXDniNoExiste((Long)arg2)){
            throw new ValidatorException(new FacesMessage("Ya existe un Firmante con el DNI que está ingresando, por favor, verifique los datos."));
        }
}
    
  /********************************************************************
   ** Converter. Se debe actualizar la entidad y el facade respectivo **
   *********************************************************************/
    @FacesConverter(forClass = Firmante.class)
    public static class FirmanteControllerConverter implements Converter {

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
            MbFirmantes controller = (MbFirmantes) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbFirmantes");
            return controller.getFirmante(getKey(value));
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
            if (object instanceof Firmante) {
                Firmante o = (Firmante) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Firmante.class.getName());
            }
        }
    }  
}
