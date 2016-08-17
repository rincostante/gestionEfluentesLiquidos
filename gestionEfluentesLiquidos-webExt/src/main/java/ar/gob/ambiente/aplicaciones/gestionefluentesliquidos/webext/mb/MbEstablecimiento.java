

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.UsuarioExterno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.JsfUtil;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 * Bean de respaldo para la gestión de las consultas y actualización de Establecimientos
 * @author rincostante
 */
public class MbEstablecimiento implements Serializable{
    // campos para el mapa
    private MapModel geoModel;
    private MapModel actualizadoModel;
    private String centerGeoMap;
    private final double latDpyra = -34.796642;
    private final double lngDpyra = -58.515071;
    private double latSelected;
    private double lngSelected;
    private String titulo;
    private boolean buscado;
    private boolean marcado;
    private boolean actualizado;
    private MbSesion sesion;
    private UsuarioExterno usLogueado;
    
    // botonos
    private String cmbGuardar;
    private String cmbCancelar;    
    
    @EJB
    private BackendSrv backendSrv;
    
    private Establecimiento est;
            
    public MbEstablecimiento() {
    }
    
    @PostConstruct
    public void init(){
        geoModel = new DefaultMapModel();
        buscado = false;
        marcado = false;

        // obtengo el usuario
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        sesion = (MbSesion)ctx.getSessionMap().get("mbSesion");
        usLogueado = sesion.getUsuario();
        est = sesion.getEstablecimiento();
    }    
    
    public void iniciar(){

        if(!marcado){
            String s;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
            .getExternalContext().getSession(true);
            //Enumeration enume = session.getAttributeNames();
            session.removeAttribute("mbDeclaraciones");
            session.removeAttribute("mbRecibo");
        }
        LatLng coord;
        
        cmbGuardar = "Guardar";
        cmbCancelar = "Cancelar";        
            
        if(est != null){
            if(est.getInmueble().getLatitud() != 0 && est.getInmueble().getLongitud() != 0){
                actualizado = true;
                actualizadoModel = new DefaultMapModel();
                coord = new LatLng((double)est.getInmueble().getLatitud(), (double)est.getInmueble().getLongitud());
                actualizadoModel.addOverlay(new Marker(coord, usLogueado.getDomCalle() + " " + usLogueado.getNumero() + ", " + usLogueado.getLocalidad()));
                centerGeoMap = String.valueOf(est.getInmueble().getLatitud()) + "," + String.valueOf(est.getInmueble().getLongitud());
            }else{
                coord = new LatLng(latDpyra, lngDpyra);
                geoModel.addOverlay(new Marker(coord, ResourceBundle.getBundle("/Bundle").getString("orgNombre") + " "
                        + ResourceBundle.getBundle("/Bundle").getString("orgDireccion_1") + " "
                        + ResourceBundle.getBundle("/Bundle").getString("orgDireccion_2") + " "
                        + ResourceBundle.getBundle("/Bundle").getString("orgTelefono") + " "
                        + ResourceBundle.getBundle("/Bundle").getString("orgMail")));
            }
        }

        if(buscado){
            buscado = false;
        }else{
            if(!actualizado){
                centerGeoMap = "-34.809011, -58.401947";
            }
        }

        if(marcado){
            marcado = false;
            actualizado = true;
        }
    }

    /**********************
     * métodos de acceso **
     **********************/
    public String getCmbGuardar() {
        return cmbGuardar;
    }

    public void setCmbGuardar(String cmbGuardar) {
        this.cmbGuardar = cmbGuardar;
    }

    public String getCmbCancelar() {
        return cmbCancelar;
    }

    public void setCmbCancelar(String cmbCancelar) {
        this.cmbCancelar = cmbCancelar;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getLatSelected() {
        return latSelected;
    }

    public void setLatSelected(double latSelected) {
        this.latSelected = latSelected;
    }

    public double getLngSelected() {
        return lngSelected;
    }

    public void setLngSelected(double lngSelected) {
        this.lngSelected = lngSelected;
    }

    public MapModel getActualizadoModel() {
        return actualizadoModel;
    }

    public void setActualizadoModel(MapModel actualizadoModel) {
        this.actualizadoModel = actualizadoModel;
    }

    public boolean isActualizado() {
        return actualizado;
    }

    public void setActualizado(boolean actualizado) {
        this.actualizado = actualizado;
    }

    public MapModel getGeoModel() {
        return geoModel;
    }

    public void setGeoModel(MapModel geoModel) {
        this.geoModel = geoModel;
    }

    public String getCenterGeoMap() {
        return centerGeoMap;
    }

    public void setCenterGeoMap(String centerGeoMap) {
        this.centerGeoMap = centerGeoMap;
    }

    /**
     * Método para localizar la dirección o referencia ingresada. 
     * @param event 
     */
    public void onGeocode(GeocodeEvent event) {
        buscado = true;
        List<GeocodeResult> results = event.getResults();
         
        if (results != null && !results.isEmpty()) {
            LatLng center = results.get(0).getLatLng();
            centerGeoMap = center.getLat() + "," + center.getLng();   
        }
    }
    
    /**
     * Método que guarda las coordenadas seleccionadas y agrega un título al punto
     */
    public void addMarker(){
        if(!actualizado){
            Marker marker = new Marker(new LatLng(latSelected, lngSelected), titulo);
            geoModel.addOverlay(marker);
            
            // actualizo el establecimiento
            try{
                est.getInmueble().setLatitud((float)latSelected);
                est.getInmueble().setLongitud((float)lngSelected);
                backendSrv.editInmueble(est.getInmueble());
                marcado = true;
                JsfUtil.addSuccessMessage("Ubicación agregada: Lat: " + latSelected + ", Long: " + lngSelected);
                
            }catch(Exception ex){
                JsfUtil.addErrorMessage("Hubo un error actualizando las coordenadas del Estabecimiento. " + ex.getMessage());
            }
        }else{
            JsfUtil.addErrorMessage("El Establecimiento ya tiene una ubicación seleccionada");
        }
    }
}
