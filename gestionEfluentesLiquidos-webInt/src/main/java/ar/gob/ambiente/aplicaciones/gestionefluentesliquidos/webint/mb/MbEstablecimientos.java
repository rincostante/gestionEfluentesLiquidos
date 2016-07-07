

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Firmante;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.HistorialFirmantes;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Inmueble;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Partido;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.servicio.PersonasServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 * Bean de respaldo para la gestión de Establecimientos
 * @author rincostante
 */
public class MbEstablecimientos implements Serializable{

    private Establecimiento current;
    private ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento estRup;
    private MbLogin login;
    private Usuario usLogeado;
    private boolean iniciado;
    private int update; // 0=updateNormal | 1=deshabiliar | 2=habilitar
    private static final Logger logger = Logger.getLogger(Establecimiento.class.getName());
    private Long cuit;
    private List<Establecimiento> listado = null;
    private List<Establecimiento> listaFilter;   
    private List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento> listEstRup;
    private List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento> listEstRupFilter;
    private List<HistorialFirmantes> listHistFirmFilter;
    private List<Firmante> listFirmantes;
    private List<Partido> listPartidos;
    private Inmueble inmueble;
    
    @EJB
    private BackendSrv backendSrv;    
    @EJB
    private PersonasServicio rupBachendSrv;
    
    public MbEstablecimientos() {
    }
    
    
   /**********************
     * Métodos de acceso **
     **********************/
    /**
     * @return el Establecimiento gestionado
     */
    public Establecimiento getSelected() {
        if (current == null) {
            current = new Establecimiento();
        }
        return current;
    }  

    public ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento getEstRup() {
        return estRup;
    }

    public void setEstRup(ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento estRup) {
        this.estRup = estRup;
    }

    public List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento> getListEstRup() {
        return listEstRup;
    }

    public void setListEstRup(List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento> listEstRup) {
        this.listEstRup = listEstRup;
    }

    public List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento> getListEstRupFilter() {
        return listEstRupFilter;
    }

    public void setListEstRupFilter(List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento> listEstRupFilter) {
        this.listEstRupFilter = listEstRupFilter;
    }
    
    public Establecimiento getCurrent() {
        return current;
    }

    public void setCurrent(Establecimiento current) {
        this.current = current;
    }

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    public List<Establecimiento> getListado() {
        if (listado == null || listado.isEmpty()) {
            listado = backendSrv.getEstablecimientos();
        }        
        return listado;
    }

    public void setListado(List<Establecimiento> listado) {
        this.listado = listado;
    }

    public List<Establecimiento> getListaFilter() {
        return listaFilter;
    }

    public void setListaFilter(List<Establecimiento> listaFilter) {
        this.listaFilter = listaFilter;
    }

    public List<HistorialFirmantes> getListHistFirmFilter() {
        return listHistFirmFilter;
    }

    public void setListHistFirmFilter(List<HistorialFirmantes> listHistFirmFilter) {
        this.listHistFirmFilter = listHistFirmFilter;
    }

    public List<Firmante> getListFirmantes() {
        return listFirmantes;
    }

    public void setListFirmantes(List<Firmante> listFirmantes) {
        this.listFirmantes = listFirmantes;
    }

    public List<Partido> getListPartidos() {
        return listPartidos;
    }

    public void setListPartidos(List<Partido> listPartidos) {
        this.listPartidos = listPartidos;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
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
        inmueble = new Inmueble();
        current = new Establecimiento();
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
    public Establecimiento getEstablecimiento(java.lang.Long id) {
        return backendSrv.getEstablecimientoById(id);
    }  
    
    /**
     * Método para abrir el diálogo para buscar Establecimientos según el CUIT
     */
    public void prepareBuscarXCuit(){
        cuit = Long.valueOf(0);
        
        Map<String,Object> options = new HashMap<>();
        options.put("contentWidth", 900);
        options.put("contentHeight", 250);
        RequestContext.getCurrentInstance().openDialog("dlgBuscarXCuit", options, null);
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
        update = 1;
        update();
        recreateModel();
    }      
    
    /**
     * Método para buscar los establecimientos RUP vinculados al cuit
     * Acá habrá que implementar el método del servicio RUP (27056924472)
     */
    public void buscarEst(){
        Long c = cuit;
        
        // instancio el listado de estRup
        listEstRup = rupBachendSrv.getEstablecimientosPorCUIT(cuit);
        /*
        // instancio el listado de estRup
        estRup = new ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.wsExt.Establecimiento();
        estRup.setId(Long.valueOf(2634));
        estRup.setCorreoElectronico("miguel-luk@hotmail.com");
        estRup.setTelefono("4543-8884");
        
        domRup = new ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.wsExt.Domicilio();
        domRup.setId(Long.valueOf(2655));
        domRup.setCalle("MONROE");
        domRup.setDepartamento("CAPITAL FEDERAL");
        domRup.setLocalidad("CAPITAL FEDERAL");
        domRup.setNumero("2855");
        estRup.setDomicilio(domRup);

        perJurRup = new ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.wsExt.PerJuridica();
        perJurRup.setId(Long.valueOf(4683));
        perJurRup.setRazonSocial("MARGARITA FLAQUER");
        perJurRup.setCuit(Long.valueOf("27056924472"));
        estRup.setPerJuridica(perJurRup);
                
        listEstRup = new ArrayList<>();
        listEstRup.add(estRup);
        */
    }
    
    public void limpiarEst(){
        listEstRup = new ArrayList<>();
        cuit = Long.valueOf(0);
    }
    
    public void selecRupEst(){
        inmueble.setIdRupDom(estRup.getDomicilio().getId());
        // campos cacheados de domRup
        inmueble.setCalle(estRup.getDomicilio().getCalle());
        inmueble.setNumero(estRup.getDomicilio().getNumero());
        inmueble.setLocalidad(estRup.getDomicilio().getLocalidad());
        inmueble.setDepartamento(estRup.getDomicilio().getDepartamento());
        
        // agrego el inmueble en el Establecimiento
        current.setInmueble(inmueble);
        
        // agrego los datos del rup
        current.setIdRupEst(estRup.getId());
        current.setIdRupRaz(estRup.getPerJuridica().getId());
        current.setRzJuridica(true);
        // campos cacheados
        current.setRazonSocial(estRup.getPerJuridica().getRazonSocial());
        current.setCuit(estRup.getPerJuridica().getCuit());
        current.setCrs(estRup.getRazonesSocialesAnt().size());
    }
    
    public void pruebaActualizar(){
        Establecimiento est = current;
    }
    
    /**
     * Método para crear un Establecimiento
     * @return 
     */
    public String create() {  
        return null;
    }    
    
    /**
     * Método para actualizar un Establecimiento existente
     * @return
     */
    public String update() {
        return null;
    }    
    
    /**
     * Restea la entidad
     */
    private void recreateModel() {
        listado = null;
    }      
    

   /********************************************************************
   ** Converter. Se debe actualizar la entidad y el facade respectivo **
   *********************************************************************/
    @FacesConverter(forClass = Establecimiento.class)
    public static class EstablecimientoControllerConverter implements Converter {

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
            MbEstablecimientos controller = (MbEstablecimientos) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbEstablecimientos");
            return controller.getEstablecimiento(getKey(value));
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
            if (object instanceof Establecimiento) {
                Establecimiento o = (Establecimiento) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Establecimiento.class.getName());
            }
        }
}     
}
