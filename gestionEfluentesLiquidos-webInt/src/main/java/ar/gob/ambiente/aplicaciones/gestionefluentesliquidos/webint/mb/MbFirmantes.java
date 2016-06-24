

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Firmante;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import java.io.Serializable;
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
    
    @EJB
    private BackendSrv backendSrv;    
    
    public MbFirmantes() {
    }

    /**********************
     * Métodos de acceso **
     **********************/
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

    public Firmante getFirmanteSelected() {
        return firmanteSelected;
    }

    public void setFirmanteSelected(Firmante firmanteSelected) {
        this.firmanteSelected = firmanteSelected;
    }

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
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
                    if(!s.equals("mbUsuario") && !s.equals("mbLogin")){
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
        //recreateModel();
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
    
    /**
     * Método que verifica que el Firmante que se quiere eliminar no esté siento utilizado por otra entidad
     * @return 
     */
    public String prepareDestroy(){
        return "";
        /*
        boolean libre = getFacade().noTieneDependencias(current.getId());

        if (libre){
            // Elimina
            performDestroy();
            recreateModel();
        }else{
            //No Elimina 
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("TipoEstablecimientoNonDeletable"));
        }
        return "view";
        */
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
        //update();        
        recreateModel();
    }  
     /**
     */    
    public void deshabilitar() {
        /*
        if (getFacade().rolNoTieneDependencias(current.getId())){
            update = 1;
            update();      
        }
        */
        recreateModel();
    }    
    
    /**
     * Restea la entidad
     */
    private void recreateModel() {
        listado = null;
    }    
    
    
    /*********************
    ** Métodos privados **
    **********************/
 

    
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
