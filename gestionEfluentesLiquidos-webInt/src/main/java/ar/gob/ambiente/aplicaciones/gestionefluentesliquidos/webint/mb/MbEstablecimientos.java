

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.AdminEntidad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Firmante;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.HistorialFirmantes;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Inmueble;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Partido;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.util.EntidadServicio;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.util.JsfUtil;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.wsClient.CentroPoblado;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.wsClient.CentrosPobladosWebService;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.wsClient.CentrosPobladosWebService_Service;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.wsClient.Departamento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.wsClient.Provincia;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Actividad;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Domicilio;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Especialidad;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.PerJuridica;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.ReasignaRazonSocial;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.TipoPersonaJuridica;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.servicio.PersonasServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;
import org.primefaces.context.RequestContext;

/**
 * Bean de respaldo para la gestión de Establecimientos
 * @author rincostante
 */
public class MbEstablecimientos implements Serializable{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/CentrosPobladosWebService/CentrosPobladosWebService.wsdl")
    private CentrosPobladosWebService_Service service;

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
    private PerJuridica perJuridica;
    private PerJuridica tempPerJuridica;
    private Firmante tempFirmante;
    private List<PerJuridica> listPerJur;
    private List<TipoPersonaJuridica> listTipoPerJur;
    private List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Actividad> listActividades;
    private ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Actividad actRup;
    private Domicilio domicilio;
    
    // listados provistos por el servicio de centros poblados de Establecimientos
    
    private List<EntidadServicio> listProvincias;
    
    private EntidadServicio provSelected;
    
    private List<EntidadServicio> listDepartamentos;
    
    private EntidadServicio deptoSelected;

    private List<EntidadServicio> listLocalidades;
    private EntidadServicio localSelected;        

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

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Actividad getActRup() {
        return actRup;
    }

    public void setActRup(Actividad actRup) {
        this.actRup = actRup;
    }

    public EntidadServicio getProvSelected() {
        return provSelected;
    }

    public void setProvSelected(EntidadServicio provSelected) {
        this.provSelected = provSelected;
    }

    public EntidadServicio getDeptoSelected() {
        return deptoSelected;
    }

    public void setDeptoSelected(EntidadServicio deptoSelected) {
        this.deptoSelected = deptoSelected;
    }

    public EntidadServicio getLocalSelected() {
        return localSelected;
    }

    public void setLocalSelected(EntidadServicio localSelected) {
        this.localSelected = localSelected;
    }

    public List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Actividad> getListActividades() {
        listActividades = rupBachendSrv.getActividadesAll();
        return listActividades;
    }

    public void setListActividades(List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Actividad> listActividades) {
        this.listActividades = listActividades;
    }

    public List<EntidadServicio> getListProvincias() {
        return listProvincias;
    }

    public void setListProvincias(List<EntidadServicio> listProvincias) {
        this.listProvincias = listProvincias;
    }

    public List<EntidadServicio> getListDepartamentos() {
        return listDepartamentos;
    }

    public void setListDepartamentos(List<EntidadServicio> listDepartamentos) {
        this.listDepartamentos = listDepartamentos;
    }

    public List<EntidadServicio> getListLocalidades() {
        return listLocalidades;
    }

    public void setListLocalidades(List<EntidadServicio> listLocalidades) {
        this.listLocalidades = listLocalidades;
    }

    public List<PerJuridica> getListPerJur() {
        listPerJur = rupBachendSrv.getPerJuridicasHabilitadas();
        return listPerJur;
    }

    public void setListPerJur(List<PerJuridica> listPerJur) {
        this.listPerJur = listPerJur;
    }

    public List<TipoPersonaJuridica> getListTipoPerJur() {
        listTipoPerJur = rupBachendSrv.getTipoPersonaJuridicaAll();
        return listTipoPerJur;
    }

    public void setListTipoPerJur(List<TipoPersonaJuridica> listTipoPerJur) {
        this.listTipoPerJur = listTipoPerJur;
    }

    public PerJuridica getPerJuridica() {
        return perJuridica;
    }

    public void setPerJuridica(PerJuridica perJuridica) {
        this.perJuridica = perJuridica;
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
        listFirmantes = backendSrv.getFirmantesAll();
        return listFirmantes;
    }

    public void setListFirmantes(List<Firmante> listFirmantes) {
        this.listFirmantes = listFirmantes;
    }

    public List<Partido> getListPartidos() {
        listPartidos = backendSrv.getPartidosAll();
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
        domicilio = new Domicilio();
        return "new";
    }    
    
    /**
     * @return acción para la edición de la entidad
     */
    public String prepareEdit() {
        tempFirmante = current.getFirmante();
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
    
    /**
     * Método para preparar el formulario de registro de nueva persona jurídica
     */
    public void prepareNewRazonSocial(){
        perJuridica = new PerJuridica();
        
        Map<String,Object> options = new HashMap<>();
        options.put("contentWidth", 900);
        RequestContext.getCurrentInstance().openDialog("dlgNewPerJuridica", options, null);
    }
    
    public void prepareEditRazonSocial(){
        perJuridica = rupBachendSrv.getPerJuridicaPorId(current.getIdRupRaz());
        
        Map<String,Object> options = new HashMap<>();
        options.put("contentWidth", 900);
        RequestContext.getCurrentInstance().openDialog("dlgEditPerJuridica", options, null);        
    }
    
    public void prepareNewEstRup(){
        estRup = new ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento();
        actRup = new ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Actividad();
        getProvinciasSrv();
        Map<String,Object> options = new HashMap<>();
        options.put("contentWidth", 900);
        RequestContext.getCurrentInstance().openDialog("dlgNewEstabRup", options, null);  
    }
    
    public void prepareEditEstRup(){
        estRup = rupBachendSrv.getEstablecimientoPorId(current.getIdRupEst());
        actRup = estRup.getActividades().get(0);
        domicilio = estRup.getDomicilio();
        cargarLocalidadesEdit();
        Map<String,Object> options = new HashMap<>();
        options.put("contentWidth", 900);
        RequestContext.getCurrentInstance().openDialog("dlgEditEstabRup", options, null);  
    }
    
    public void prepareCambioRazSoc(){
        estRup = rupBachendSrv.getEstablecimientoPorId(current.getIdRupEst());
        perJuridica = estRup.getPerJuridica();
        Map<String,Object> options = new HashMap<>();
        options.put("contentWidth", 900);
        RequestContext.getCurrentInstance().openDialog("dlgCambioRazSoc", options, null);
    }
    
    /**
     * Método para actualizar el listado de departamentos según la provincia seleccionada
     */    
    public void provinciaChangeListener(){  
        getDepartamentosSrv(provSelected.getId());
    }   
    
    /**
     * Método para actualizar el listado de localidades según el departamento seleccionado
     */    
    public void deptoChangeListener(){
        getLocalidadesSrv(deptoSelected.getId());
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
        // instancio el listado de estRup
        listEstRup = new ArrayList<>();
        List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento> listEstRupTemp;
        listEstRupTemp = rupBachendSrv.getEstablecimientosPorCUIT(cuit);
        // por cada uno de la lista solo muestro los que no estén registrados ya en el GEL
        for(ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento estRup : listEstRupTemp){
            if(backendSrv.existeEstByIdRupEst(estRup.getId())){
                listEstRup.add(estRup);
            }
        }
        if(listEstRup.isEmpty()){
            JsfUtil.addErrorMessage("No hay Establecimientos asociados a este CUIT disponibles para seleccionar y registrar. "
                    + "Para registrar un nuevo Establecimiento asociado a este CUIT deberá registrarlo primero en el RUP.");
        }
    }
    
    public void pruebaActualizar(){
        Establecimiento est = current;
    }
    
    public void actualizarEdit(){
        Establecimiento est = current;
    }
    
    public void limpiarNew(){
        limpiarEst();
        current = new Establecimiento();
    }
    
    public void limpiarEdit(){
        Establecimiento est = current;
    }
    
    public void limpiarEst(){
        listEstRup = new ArrayList<>();
        cuit = Long.valueOf(0);
    }
    
    public void limpiarPerJuridica(){
        perJuridica = new PerJuridica();
    }
    
    public void limpiarEstRup(){
        estRup = new ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento();
        domicilio = new Domicilio();
        limpiarEntitadesSrv();
    }
    
    public void limpiarRazSoc(){
        perJuridica = estRup.getPerJuridica();
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
        
        JsfUtil.addSuccessMessage("El Establecimiento seleccionado del RUP se agregó al formulario, "
                + "por favor, cierre esta ventana y actualice el formulario mediante el botón correspondiente.");
    }
    
    /**
     * Método para la creación de una Razón social en el RUP (perJuridica)
     */
    public void createPerJuridica(){
        // valido que el cuit no esté registrado ya
        boolean valida = true;
        if(rupBachendSrv.getPerJuridicasPorCuit(cuit) != null){
            valida = false;
            JsfUtil.addErrorMessage("Ya existe una Razón social con el CUIT que está ingresando");
        }
        
        // obtengo el usuario homonimo del RUP
        ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Usuario usRup;
        usRup = rupBachendSrv.getUsuarioByNombre(usLogeado.getNombre());
        if(usRup == null){
            valida = false;
            JsfUtil.addErrorMessage("El usuario logeado no está registrado en el RUP, por favor contactar al adminisntrador para gestionar tal registro.");
        }
        if(valida){
            // actualizo el nombre en mayúsculas
            String rz = perJuridica.getRazonSocial();
            perJuridica.setRazonSocial(rz.toUpperCase());
            // asigno la aplicación (el id correspondiente a esta aplicación)
            perJuridica.setIdAplicacion(Long.valueOf(8));
            // asigno el estado
            perJuridica.setEstado(rupBachendSrv.getEstadoByNombre("Validado"));
           
            try{
                // instancio los datos administrativos
                Date date = new Date(System.currentTimeMillis());
                ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.AdminEntidad admin = new ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.AdminEntidad();
                admin.setFechaAlta(date);
                admin.setUsAlta(usRup);
                admin.setHabilitado(true);
                perJuridica.setAdmin(admin);
                
                // persisto
                rupBachendSrv.createPerJuridica(perJuridica);
                
                JsfUtil.addSuccessMessage("La Razón social se registró en el RUP. Por favor, cierre este formulario y continúe con el registro del Establecimiento.");
            }catch(Exception ex){
                JsfUtil.addErrorMessage("Hubo un error registrando la Razón Social. " + ex.getMessage());
            }
        }
    }
    
    public void editPerJuridica(){
        // valido que el cuit no esté registrado ya
        boolean valida = true;
        PerJuridica pj = rupBachendSrv.getPerJuridicasPorCuit(perJuridica.getCuit());
        if(!pj.equals(perJuridica)){
            valida = false;
            JsfUtil.addErrorMessage("Ya existe una Razón social con el CUIT que ha actualizado");
        }
        
        // obtengo el usuario homonimo del RUP
        ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Usuario usRup;
        usRup = rupBachendSrv.getUsuarioByNombre(usLogeado.getNombre());
        if(usRup == null){
            valida = false;
            JsfUtil.addErrorMessage("El usuario logeado no está registrado en el RUP, por favor contactar al adminisntrador para gestionar tal registro.");
        }
        
        if(valida){
            // actualizo el nombre en mayúsculas
            String rz = perJuridica.getRazonSocial();
            perJuridica.setRazonSocial(rz.toUpperCase());
            
            try{
                // instancio los datos administrativos
                Date date = new Date(System.currentTimeMillis());
                perJuridica.getAdmin().setFechaModif(date);
                perJuridica.getAdmin().setUsModif(usRup);
                
                // persisto
                rupBachendSrv.editPerJuridica(perJuridica);
                
                // actualizo el/los Establecimiento/s del GEL
                List<Establecimiento> listEstTemp = backendSrv.getEstablecimientosByCuit(current.getCuit());
                for(Establecimiento estTemp : listEstTemp){
                    if(!estTemp.equals(current)){
                        estTemp.setRazonSocial(perJuridica.getRazonSocial());
                        estTemp.setCuit(perJuridica.getCuit());
                        backendSrv.editEstablecimiento(estTemp);
                    }else{
                        current.setRazonSocial(perJuridica.getRazonSocial());
                        current.setCuit(perJuridica.getCuit());
                        backendSrv.editEstablecimiento(current);
                    }
                }
                
                JsfUtil.addSuccessMessage("La Razón social se actualizó en el RUP. Por favor, cierre este formulario y continúe con el registro del Establecimiento.");
            }catch(Exception ex){
                JsfUtil.addErrorMessage("Hubo un error actualizando la Razón Social. " + ex.getMessage());
            }
        }
    }
    
    /**
     * Método para la creación de un Establecimiento en el RUP
     */
    public void createEstRup(){
        boolean valida = true;
        // obtengo el usuario homonimo del RUP
        ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Usuario usRup;
        usRup = rupBachendSrv.getUsuarioByNombre(usLogeado.getNombre());
        if(usRup == null){
            valida = false;
            JsfUtil.addErrorMessage("El usuario logeado no está registrado en el RUP, por favor contactar al administrador para gestionar tal registro.");
        }      
        
        if(valida){
            try{
                // instancio los datos administrativos
                Date date = new Date(System.currentTimeMillis());
                ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.AdminEntidad admin = new ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.AdminEntidad();
                admin.setFechaAlta(date);
                admin.setUsAlta(usRup);
                admin.setHabilitado(true);
                estRup.setAdmin(admin);

                // completo los datos hardcodeados (Estado, Tipo Establecimiento, Especialidad y Expediente)
                estRup.setEstado(rupBachendSrv.getEstadoByNombre("Validado"));
                estRup.setTipo(rupBachendSrv.getTipoEstByNombre("Planta Productora"));
                List<Especialidad> listEsp = new ArrayList<>();
                Especialidad esp = rupBachendSrv.getEspecialidadByNombre("Vertido de Efluentes");
                listEsp.add(esp);
                estRup.setEspecialidades(listEsp);
                estRup.setExpediente(rupBachendSrv.getExpedienteById(Long.valueOf(66)));

                // agrego la Actividad
                List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Actividad> listActTemp = new ArrayList<>();
                listActTemp.add(actRup);
                estRup.setActividades(listActTemp);

                // completo el domicilio
                domicilio.setIdLocalidad(localSelected.getId());
                domicilio.setLocalidad(localSelected.getNombre());
                domicilio.setDepartamento(deptoSelected.getNombre());
                domicilio.setProvincia(provSelected.getNombre());
                estRup.setDomicilio(domicilio);

                // verifico si hay otros Establecimientos del mismo tipo en el mismo domicilio. En cuyo caso seteo un alerta
                List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento> listEstTemp;
                listEstTemp = rupBachendSrv.getEstablecimientosExistentes(domicilio.getCalle(), domicilio.getNumero(), domicilio.getIdLocalidad(), estRup.getTipo());
                if(listEstTemp.isEmpty()){
                    estRup.setAlertaDomicilio(false);
                }else if(listEstTemp.size() == 1){
                    estRup.setAlertaDomicilio(!listEstTemp.get(0).getId().equals(current.getId()));
                }else{
                    estRup.setAlertaDomicilio(true);
                }

                // seteo mayúsculas en la Calle
                String calle = estRup.getDomicilio().getCalle();
                estRup.getDomicilio().setCalle(calle.toUpperCase());

                // persisto
                rupBachendSrv.createEstablecimiento(estRup);

                JsfUtil.addSuccessMessage("El Establecimiento se registró en el RUP. Por favor, cierre este formulario y continúe con el registro del Establecimiento.");
            }catch(Exception ex){
                JsfUtil.addErrorMessage("Hubo un error registrando el Establecimiento en el RUP. " + ex.getMessage());
            }   
        }else{
            JsfUtil.addErrorMessage("No se pudo registrar el Establecimiento en el RUP.");
        }
    }
    
    public void editEstRup(){
        boolean valida = true;
        // obtengo el usuario homonimo del RUP
        ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Usuario usRup;
        usRup = rupBachendSrv.getUsuarioByNombre(usLogeado.getNombre());
        if(usRup == null){
            valida = false;
            JsfUtil.addErrorMessage("El usuario logeado no está registrado en el RUP, por favor contactar al administrador para gestionar tal registro.");
        }     
        
        if(valida){
            try{
                // instancio los datos administrativos
                Date date = new Date(System.currentTimeMillis());
                estRup.getAdmin().setFechaModif(date);
                estRup.getAdmin().setUsModif(usRup);
                
                // agrego la Actividad
                List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Actividad> listActTemp = new ArrayList<>();
                listActTemp.add(actRup);
                estRup.setActividades(listActTemp);
                
                // actualizo el domicilio
                estRup.setDomicilio(domicilio); 
                
                // verifico si hay otros Establecimientos del mismo tipo en el mismo domicilio. En cuyo caso seteo un alerta
                List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento> listEstTemp;
                listEstTemp = rupBachendSrv.getEstablecimientosExistentes(domicilio.getCalle(), domicilio.getNumero(), domicilio.getIdLocalidad(), estRup.getTipo());
                if(listEstTemp.isEmpty()){
                    estRup.setAlertaDomicilio(false);
                }else if(listEstTemp.size() == 1){
                    estRup.setAlertaDomicilio(!listEstTemp.get(0).getId().equals(current.getId()));
                }else{
                    estRup.setAlertaDomicilio(true);
                }    
                
                // seteo mayúsculas en la Calle
                String calle = estRup.getDomicilio().getCalle();
                estRup.getDomicilio().setCalle(calle.toUpperCase());

                // persisto
                rupBachendSrv.editEstablecimiento(estRup);
                
                // actualizo los datos del inmueble en el GEL
                Inmueble inm = current.getInmueble();
                inm.setCalle(estRup.getDomicilio().getCalle());
                inm.setNumero(estRup.getDomicilio().getNumero());
                inm.setLocalidad(estRup.getDomicilio().getLocalidad());
                backendSrv.editInmueble(inm);
                
                JsfUtil.addSuccessMessage("Los datos del Establecimiento se actualizaron en el RUP. Por favor, cierre este formulario y continúe con el registro del Establecimiento.");
                
            }catch(Exception ex){
                JsfUtil.addErrorMessage("Hubo un error actualizando el Establecimiento en el RUP. " + ex.getMessage());
            }   
        }else{
            JsfUtil.addErrorMessage("No se pudo actualizar el Establecimiento en el RUP.");
        }
    }
    
    public void cambiarRazSoc(){
        boolean valida = true, actualizoRs = false, insertoNuevaRs = false, actualizoEstRup = false, actualizoEst = false;
        ReasignaRazonSocial rsAnterior = new ReasignaRazonSocial();
        ReasignaRazonSocial rsNueva = new ReasignaRazonSocial();
        Date dt = new Date(System.currentTimeMillis());
        tempPerJuridica = estRup.getPerJuridica();
        Establecimiento newEst;
        
        // obtengo el usuario homonimo del RUP
        ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Usuario usRup;
        usRup = rupBachendSrv.getUsuarioByNombre(usLogeado.getNombre());
        if(usRup == null){
            valida = false;
            JsfUtil.addErrorMessage("El usuario logeado no está registrado en el RUP, por favor contactar al administrador para gestionar tal registro.");
        }     
        
        if(estRup.getPerJuridica().equals(perJuridica)){
            valida = false;
            JsfUtil.addErrorMessage("La Razón Social que está guardando es la misma que tenía asignada el Establecimiento.");
        }
        
        if(valida){
            // leo el último cambio de rs si lo hubiera
            if(rupBachendSrv.getUltimaRazSocActiva(estRup) != null){
                rsAnterior = rupBachendSrv.getUltimaRazSocActiva(estRup);
            }

            rsNueva.setPerJuridica(estRup.getPerJuridica());
            rsNueva.setEstablecimiento(estRup);
            rsNueva.setFecha(dt);
            rsNueva.setActiva(true);
            rsNueva.setUsuario(usRup);

            try{
                /************************************************
                 * Ejecuto el cambio de razón social en el RUP
                 * con la correspondiente generación de historial
                 ************************************************/
                // si hubo una anterior la apago y seteo la razón social anterior
                if(rsAnterior.getId() != null){
                    rsNueva.setExPerJuridica(rsAnterior.getPerJuridica());
                    rsNueva.setExPerFisica(rsAnterior.getPerFisica());
                    rsAnterior.setActiva(false);
                    rupBachendSrv.editRazSoc(rsAnterior);
                    actualizoRs = true;
                }else{
                    // si no hubo asignación anterior seteo las ex con los valores temporales
                    rsNueva.setExPerJuridica(estRup.getPerJuridica());
                }
                // agrego la nueva como activa
                rupBachendSrv.createRazSoc(rsNueva);
                insertoNuevaRs = true;
                
                // actualizo la perJuridica del Establecimento en el RUP
                estRup.setPerJuridica(perJuridica);
                rupBachendSrv.editEstablecimiento(estRup);
                actualizoEstRup = true;
                
                /**********************************************************
                 * Creo un nuevo Establecimiento con los datos actualizados
                 * y el domicilio del original
                 **********************************************************/
                // actualizo histórico
                current.setHistorico(true);
                backendSrv.editEstablecimiento(current);
                actualizoEst = true;
                
                newEst = new Establecimiento();
                newEst.setCrs(current.getCrs() + 1);
                newEst.setNumero(current.getNumero());
                newEst.setPartidoGel(current.getPartidoGel());
                newEst.setInmueble(current.getInmueble());
                // tomo el firmante existente para que lo cambien después
                newEst.setFirmante(current.getFirmante());
                newEst.setIdRupRaz(perJuridica.getId());
                newEst.setIdRupEst(estRup.getId());
                newEst.setRzJuridica(true);
                newEst.setCuit(perJuridica.getCuit());
                newEst.setRazonSocial(perJuridica.getRazonSocial());
                // Seteo los datos administrativos
                AdminEntidad admin = new AdminEntidad();
                admin.setFechaAlta(dt);
                admin.setUsAlta(usLogeado);
                admin.setHabilitado(true);
                newEst.setAdmin(admin);
                
                // persisto
                backendSrv.createEstablecimiento(newEst);
                // seteo el current con el Establecimiento creado para verlo en el formulario de edición
                current = newEst;
                
                JsfUtil.addSuccessMessage("Se ha registrado el cambio de Razón Social, tanto en el RUP como en GEL. "
                        + "Por favor, cierre el formulario y actuelice el de edición para continuar con el proceso de actualización.");

            }catch(Exception ex){
                if(actualizoRs){
                    rsAnterior.setActiva(true);
                    rupBachendSrv.editRazSoc(rsAnterior);
                }
                if(insertoNuevaRs){
                    rupBachendSrv.deleteRazSoc(rsNueva);
                }
                if(actualizoEstRup){
                    estRup.setPerJuridica(tempPerJuridica);
                    rupBachendSrv.editEstablecimiento(estRup); 
                }
                if(actualizoEst){
                    current.setHistorico(true);
                    backendSrv.editEstablecimiento(current);
                }
                JsfUtil.addErrorMessage("Hubo un error cambiando la Razón Social del Establecimiento. " + ex.getMessage());
            }
        }else{
            JsfUtil.addErrorMessage("No se registró cambio de Razón Social alguna.");
        }
    }
    
    /**
     * Método para crear un Establecimiento
     * @return 
     */
    public String create() {  
        boolean inmuebleCreado = false;
        Partido part;
        boolean valida = true;
        String mensaje = "";
        // solo opero si ya tengo un establecimiento del RUP seleccionado
        if(current.getIdRupEst() == null){
            valida = false;
            mensaje = "Debe seleccionar un Establecimiento proveniente del Registro Unico de Personas (RUP)";
        }
        
        // obtengo el departamento correspondiente a la localidad
        CentroPoblado loc = obtenerLocalidad(estRup.getDomicilio().getIdLocalidad());
        if(loc == null){
            valida = false;
            mensaje = "Hubo un error al obtener la Localidad.";
        }else{
            /**
             * Obtento el partido. Por cuestiones de incompatibilidad con el modelo territorial, 
             * doy un tratamiento distinto para los casos de Establecimientos emplazados en la CABA (idProvincia = 1).
             * En cuyo caso obtengo el partido GEL cuyo id es 18.
             */
            if(loc.getDepartamento().getProvincia().getId() == Long.valueOf(1)){
                part = backendSrv.getPartidoByID(Long.valueOf(18));
            }else{
                Long idDepto = loc.getDepartamento().getId();
                part = backendSrv.getPartidoByIdRt(idDepto);
            }
            
            // valido que haya obtenido un partido
            if(part == null){
                valida = false;
                mensaje = "Hubo un error al obtener el Partido.";
            }else{
                current.setPartidoGel(part.getIdRt().intValue());
            }
        }
        
        if(!validarExistente(Long.valueOf(current.getPartidoGel()), current.getNumero(), current.getCrs(), true)){
            valida = false;
            mensaje = "Ya existe un Establecimient para el Partido que está ingresando con el número y el crs asignados.";
        }
        if(valida){
            try{
                // instancio los datos administrativos
                Date date = new Date(System.currentTimeMillis());
                AdminEntidad admin = new AdminEntidad();
                admin.setFechaAlta(date);
                admin.setUsAlta(usLogeado);
                admin.setHabilitado(true);
                current.setAdmin(admin);
                current.setRzJuridica(true);
                
                // asigno el número
                Long numEst = backendSrv.getMaxNumEstablecimiento() + 1;
                current.setNumero(numEst);
                
                // agrego el prefijo al código de recibo
                String tempCodRec = current.getCodRecibo();
                tempCodRec = "TMP" + tempCodRec;
                current.setCodRecibo(tempCodRec);
                
                // inserto el inmueble
                backendSrv.createInmueble(inmueble);
                inmuebleCreado = true;
                
                // inserto
                backendSrv.createEstablecimiento(current);
                JsfUtil.addSuccessMessage("El Establecimiento se ha registrado correctamente.");
                return "view";
            }catch(Exception ex){
                if(inmuebleCreado) backendSrv.deleteInmueble(inmueble);
                JsfUtil.addErrorMessage("Hubo un error registrando el Establecimiento. " + ex.getMessage());
                return null;
            }
        }else{
            JsfUtil.addErrorMessage(mensaje);
            return null;
        }
    }    
    
    /**
     * Método para actualizar los datos de un Establecimiento
     * @return 
     */
    public String edit(){
        boolean valida = true, actualizoFirm = false, insertoNuevoFirm = false;
        String mensaje = "";
        HistorialFirmantes hisFirUltimo = new HistorialFirmantes();
        HistorialFirmantes hisFirNuevo = new HistorialFirmantes();

        if(!validarExistente(Long.valueOf(current.getPartidoGel()), current.getNumero(), current.getCrs(), true)){
            valida = false;
            mensaje = "Ya existe un Establecimiento para el Partido que está ingresando con el número y el crs asignados.";
        }
        
        if(valida){
            try{
                // instancio los datos administrativos
                Date date = new Date(System.currentTimeMillis());
                current.getAdmin().setFechaModif(date);
                current.getAdmin().setUsModif(usLogeado);

                // si cambió firmante, registro en el historial
                if(!current.getFirmante().equals(tempFirmante)){
                    // leo el último cambio de Firmante si lo hubiera
                    if(backendSrv.getUltimoFirmante(current) != null){
                        hisFirUltimo = backendSrv.getUltimoFirmante(current);
                    }
                    // seteo el nuevo historial
                    hisFirNuevo.setFirmanteActual(current.getFirmante());
                    hisFirNuevo.setActiva(true);
                    hisFirNuevo.setEstablecimiento(current);
                    hisFirNuevo.setFecha(date);
                    hisFirNuevo.setMotivo("Actualización administrativa.");
                    hisFirNuevo.setUsuario(usLogeado);
                    
                    // si hubo una anterior la apago y seteo la razón social anterior
                    if(hisFirUltimo.getId() != null){
                        hisFirNuevo.setFirmanteAnterior(hisFirUltimo.getFirmanteActual());
                        hisFirUltimo.setActiva(false);
                        backendSrv.editHisFirmante(hisFirUltimo);
                        actualizoFirm = true;
                    }else{
                        // si no había un registro de historial anterior, pongo como anterior al guardado como saliente
                        hisFirNuevo.setFirmanteAnterior(tempFirmante);
                    }
                    // creo el historial del nuevo Firmante
                    backendSrv.createHisFirmante(hisFirNuevo);
                    insertoNuevoFirm = true;
                }
                
                // actualizo
                backendSrv.editEstablecimiento(current);
                JsfUtil.addSuccessMessage("El Establecimiento se ha actualizado correctamente.");
                return "view";
            }catch(Exception ex){
                if(actualizoFirm){
                    hisFirUltimo.setActiva(true);
                    backendSrv.editHisFirmante(hisFirUltimo);
                }
                if(insertoNuevoFirm) backendSrv.deleteHisFirmante(hisFirNuevo);

                JsfUtil.addErrorMessage("Hubo un error actualizando el Establecimiento. " + ex.getMessage());
                return null;
            }
        }else{
            JsfUtil.addErrorMessage(mensaje);
            return null;
        }        
    }
    
    /**
     * Método para actualizar un Establecimiento existente
     * @return
     */
    public String update() {
        return null;
    }    
    
    /**********************
     ** Métodos privados **
     **********************/
    
    /**
     * Restea la entidad
     */
    private void recreateModel() {
        listado = null;
    }      
    /**
     * Método para validar la Existencia de un Establecimiento con el mismo cude que el que se está insertando
     * @param idPartido
     * @param numEst
     * @param crs
     * @return 
     */
    private boolean validarExistente(Long idPartido, Long numEst, int crs, boolean edita){
        Establecimiento est = backendSrv.getEstablecimientoByCude(idPartido, numEst, crs);
        if(est != null && edita){
            return est.equals(current);
        }else return est == null || edita;
    }
    
    /**
     * Método que obtiene la Localidad según la id del Domicilio.
     * @param idLoc
     * @return 
     */
    private CentroPoblado obtenerLocalidad(Long idLoc){
        try {
            CentrosPobladosWebService port = service.getCentrosPobladosWebServicePort();
            CentroPoblado result = port.buscarCentroPoblado(idLoc);
            System.out.println("Result = "+result);
            return result;
        } catch (Exception ex) {
            // muestro un mensaje al usuario
            JsfUtil.addErrorMessage("Hubo un error obteniendo el Centro Poblado desde el ws. " + ex.getMessage());
            // lo escribo en el log del server
            logger.log(Level.SEVERE, "{0} - {1}", new Object[]{"Hubo un error con el método buscarCentroPoblado() del servicio web", ex.getMessage()});
            return null;
        }
    }
    
    /**
     * Método para el converter
     * @param id
     * @return 
     */
    public TipoPersonaJuridica getTipoPersonaJuridica(java.lang.Long id) {
        return rupBachendSrv.getTipoPerJuridica(id);
    }  
    
    /**
     * Método para el converter
     * @param id
     * @return 
     */
    public Actividad getActividad(java.lang.Long id){
        return rupBachendSrv.getActividad(id);
    } 
    
    public PerJuridica getPerJuridica(java.lang.Long id){
        return rupBachendSrv.getPerJuridicaPorId(id);
    }
    
    /**
     * Método para poblar el listado de provincias del servicio de centros poblados
     */

    private void getProvinciasSrv(){
        EntidadServicio provincia;
        List<Provincia> listSrv;
        try {
            CentrosPobladosWebService port = service.getCentrosPobladosWebServicePort();
            
            // lleno el listado de provincias
            listSrv = port.verProvincias();
            
            // lleno el list con las provincias como un objeto Entidad Servicio
            listProvincias = new ArrayList<>();

            for(Provincia prov : listSrv){
                provincia = new EntidadServicio(prov.getId(), prov.getNombre());
                listProvincias.add(provincia);
                //provincia = null;
            }
        } catch (Exception ex) {
            // muestro un mensaje al usuario
            JsfUtil.addErrorMessage("Hubo un error leyendo las provincias del servicio de centros poblados. " + ex.getMessage());
            // lo escribo en el log del server
            logger.log(Level.SEVERE, "{0} - {1}", new Object[]{"Hubo un error leyendo las provincias del servicio de centros poblados.", ex.getMessage()});
        }
    }
    
    /**
     * Método para cargar las localidades vinculadas al departamento seleccionado
     * para editar el establecimiento RUP
     */
    public void cargarLocalidadesEdit(){
        CentrosPobladosWebService port = service.getCentrosPobladosWebServicePort();
        CentroPoblado cp;
        List<CentroPoblado> listLocal;
        EntidadServicio local;
        
        try{
            // obtengo el CentroPoblado a partir del idLocalidad del domicilio del Establecimiento y seteo la EntidadServicio correspondiente
            cp = port.buscarCentroPoblado(estRup.getDomicilio().getIdLocalidad());
            localSelected = new EntidadServicio(cp.getId(), cp.getNombre());
            // instancio el listado de departamentos como un list vacío
            listDepartamentos = new ArrayList<>();
            // obtengo el departamento (Partido) Si es CABA, la comuna
            deptoSelected = new EntidadServicio(cp.getDepartamento().getId(), cp.getDepartamento().getNombre());
            // lleno las Localidades según el Departamento (Partido)
            listLocal = port.buscarCentrosPorDepto(deptoSelected.getId());
            listLocalidades = new ArrayList<>();
            for(CentroPoblado loc : listLocal){
                local = new EntidadServicio(loc.getId(), loc.getNombre());
                listLocalidades.add(local);
            }
            
        }catch(Exception ex){
            // muestro un mensaje al usuario
            JsfUtil.addErrorMessage("Hubo un error obteniendo las Localidades del Partido del Establecimiento para la edición del domicilio.");
            // lo escribo en el log del server
            logger.log(Level.SEVERE, "{0} - {1}", new Object[]{"Hubo un error obteniendo las Localidades del Partido del Establecimiento para la edición del domicilio..", ex.getMessage()});
        }
    }

    /**
     * Método para cargar entidades de servicio y los listados, para actualizar el Establecimiento RUP
     */
    private void cargarEntidadesSrv(){
        CentrosPobladosWebService port = service.getCentrosPobladosWebServicePort();
        CentroPoblado cp;
        List<Provincia> listProv;
        List<Departamento> listDeptos;
        List<CentroPoblado> listLocal;
        EntidadServicio provincia;
        EntidadServicio depto;
        EntidadServicio local;
        
        try{
            // obtengo el CentroPoblado a partir del idLocalidad del domicilio del Establecimiento y seteo la EntidadServicio correspondiente
            cp = port.buscarCentroPoblado(estRup.getDomicilio().getIdLocalidad());
            localSelected = new EntidadServicio(cp.getId(), cp.getNombre());

            // del CentroPoblado obtengo el Departamento y seteo la EntidadServicio correspondiente
            deptoSelected = new EntidadServicio(cp.getDepartamento().getId(), cp.getDepartamento().getNombre());

            // del Departamento del CentroPoblado obtengo la Provincia y seteo la EntidadServicio correspondiente
            provSelected = new EntidadServicio(cp.getDepartamento().getProvincia().getId(), cp.getDepartamento().getProvincia().getNombre());

            // cargo el listado de Provincias
            listProv = port.verProvincias();
            listProvincias = new ArrayList<>();
            for(Provincia prov : listProv){
                provincia = new EntidadServicio(prov.getId(), prov.getNombre());
                listProvincias.add(provincia);                    
            }
            
            // lleno los Departamentos según la provincia que tiene asignada la persona
            listDeptos = port.buscarDeptosPorProvincia(provSelected.getId());
            listDepartamentos = new ArrayList<>();
            for(Departamento dpt : listDeptos){
                depto = new EntidadServicio(dpt.getId(), dpt.getNombre());
                listDepartamentos.add(depto);
            }
            
            // lleno las Localidades según el Departamento que tiene asignado la persona
            listLocal = port.buscarCentrosPorDepto(deptoSelected.getId());
            listLocalidades = new ArrayList<>();
            for(CentroPoblado loc : listLocal){
                local = new EntidadServicio(loc.getId(), loc.getNombre());
                listLocalidades.add(local);
            }
            
        }catch(Exception ex){
            // muestro un mensaje al usuario
            JsfUtil.addErrorMessage("Hubo un error obteniendo los datos de emplazamiento del Establecimiento.");
            // lo escribo en el log del server
            logger.log(Level.SEVERE, "{0} - {1}", new Object[]{"Hubo un error obteniendo los datos de emplazamiento del Establecimiento.", ex.getMessage()});
        }
    }
    
    
    /**
     * Método para poblar el listado de departamentos del servicio de centros poblados
     */

    private void getDepartamentosSrv(Long idProv){
        EntidadServicio depto;
        List<Departamento> listSrv;
        try { 
            CentrosPobladosWebService port = service.getCentrosPobladosWebServicePort();
            listSrv = port.buscarDeptosPorProvincia(idProv);

            listDepartamentos = new ArrayList<>();
            for(Departamento dpt : listSrv){
                depto = new EntidadServicio(dpt.getId(), dpt.getNombre());
                listDepartamentos.add(depto);
            }
        } catch (Exception ex) {
            // muestro un mensaje al usuario
            JsfUtil.addErrorMessage("Hubo un error obteniendo los departamentos del servicio de centros poblados. " + ex.getMessage());
            // lo escribo en el log del server
            logger.log(Level.SEVERE, "{0} - {1}", new Object[]{"Hubo un error obteniendo los departamentos del servicio de centros poblados", ex.getMessage()});
        }
    }

    /**
     * Método para poblar el listado de Localidades del servicio de centros poblados
     */

    private void getLocalidadesSrv(Long idDepto){
        EntidadServicio local;
        List<CentroPoblado> listSrv;
        try { 
            CentrosPobladosWebService port = service.getCentrosPobladosWebServicePort();
            listSrv = port.buscarCentrosPorDepto(idDepto);

            listLocalidades = new ArrayList<>();
            for(CentroPoblado loc : listSrv){
                local = new EntidadServicio(loc.getId(), loc.getNombre());
                listLocalidades.add(local);
            }
        } catch (Exception ex) {
            // muestro un mensaje al usuario
            JsfUtil.addErrorMessage("Hubo un error obteniendo las localidades del servicio de centros poblados. " + ex.getMessage());
            // lo escribo en el log del server
            logger.log(Level.SEVERE, "{0} - {1}", new Object[]{"Hubo un error obteniendo las localidades del servicio de centros poblados.", ex.getMessage()});
        }
    }       

    /**
     * Método para limpiar las entidades del servicio
     */

    private void limpiarEntitadesSrv(){
        if(provSelected != null){
            provSelected = null;
        }
        
        if(deptoSelected != null){
            deptoSelected = null;
        }

        if(localSelected != null){
            localSelected = null;
        }
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
    
    /***********************************************
    ** Converter para el tipo de Persona Jurídica **
    ************************************************/
    @FacesConverter(forClass = TipoPersonaJuridica.class)
    public static class TipoPersonaJuridicaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbEstablecimientos controller = (MbEstablecimientos) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbEstablecimientos");
            return controller.getTipoPersonaJuridica(getKey(value));
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
            if (object instanceof TipoPersonaJuridica) {
                TipoPersonaJuridica o = (TipoPersonaJuridica) object;
                return getStringKey(o.getId());
           } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TipoPersonaJuridica.class.getName());
            }
        }
    }           
    
    /************************************
    ** Converter para la Actividad RUP **
    *************************************/
    @FacesConverter(forClass = Actividad.class)
    public static class ActividadConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbEstablecimientos controller = (MbEstablecimientos) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbEstablecimientos");
            return controller.getActividad(getKey(value));
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
            if (object instanceof Actividad) {
                Actividad o = (Actividad) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Actividad.class.getName());
            }
        }
    }      
    
  /************************************
   ** Converter para la Razón Social **
   ************************************/
    @FacesConverter(forClass = PerJuridica.class)
    public static class PerJuridicaConverter implements Converter {

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
            return controller.getPerJuridica(getKey(value));
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
            if (object instanceof PerJuridica) {
                PerJuridica o = (PerJuridica) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + PerJuridica.class.getName());
            }
        }
    }            
}
