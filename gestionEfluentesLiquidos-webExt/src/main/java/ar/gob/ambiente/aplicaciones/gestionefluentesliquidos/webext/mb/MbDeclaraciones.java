

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Abastecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Abasto;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Actividad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.ActividadDec;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.AdminEntidad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Aforo;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.CantPersonalDec;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Curso;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.DeclaracionJurada;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Descarga;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Dia;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.DocDec;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.FechaDec;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Firmante;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Horario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Pozo;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.SuperficieDec;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.TipoAbasto;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.TipoCaudal;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Tratamiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Turno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.UsuarioExterno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Vuelco;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.JsfUtil;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.wsExt.CuitAfipWs;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.wsExt.CuitAfipWs_Service;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.wsExt.ExpedienteDrp;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceRef;

/**
 * Bean de respaldo para el registro de Declaraciones Juradas
 * @author rincostante
 */
public class MbDeclaraciones implements Serializable{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/vmdeswebjava.medioambiente.gov.ar_8080/CuitAfipWs/CuitAfipWs.wsdl")
    private CuitAfipWs_Service service;
    
    // Actividades
    private Actividad actividad;
    private List<Actividad> lstActividades;
    private ActividadDec actDec;
    private ActividadDec actDecSelected;
    private List<ActividadDec> lstActDec;
    
    // documentación
    private DocDec docDec;
    private boolean presentoDoc;
    private Map<Integer, String> mapTipoVisado;
    private int numero;

    // Fechas
    private FechaDec fechaDec;
    private FechaDec fechaDecSelected;
    private List<FechaDec> lstFechaDec;
    private Map<Integer, String> mapTipoFecha;
    
    // Personal
    private CantPersonalDec canPersDec;
    private CantPersonalDec canPersDecSelected;
    private List<CantPersonalDec> lstCantPers;
    private Map<Integer, String> mapTipoPersonal;
    
    // Superficie
    private SuperficieDec supDec;
    private SuperficieDec supDecSelected;
    private List<SuperficieDec> lstSupDec;    
    private Map<Integer, String> mapTipoSuperficie;
    
    // vuelco
    private Vuelco vuelco;
    private String usDrp;
    private ExpedienteDrp expediente;
    
    private DeclaracionJurada declaracion;
    private List<String> lstVisados;
    
    // Descargas
    private Descarga descarga;
    private Descarga descSwap;
    private Tratamiento tratamiento;
    private Tratamiento tratamientoSelected;
    private Tratamiento tratSwap;
    private List<Descarga> lstDescargas; 
    private List<Curso> lstDestinos;
    private Map<Integer, String> mapTipoTratamiento;
    private Map<Integer, String> mapTipoDescarga;
    private List<Aforo> lstAforos;
    
    // Abastecimiento
    private Abastecimiento abastecimiento;
    private Abasto abasto;
    private Abasto abastoSwap;
    private List<Abasto> lstAbasto;
    private Map<Integer, String> mapOrigenes;
    private Map<Integer, String> mapCircuitos;
    private Map<Integer, String> mapFuentes;
    private List<TipoAbasto> lstTiposAbasto;
    private List<TipoCaudal> lstTiposCaudal;
    
    // Pozos
    private Pozo pozo;
    private Pozo pozoSwap;
    private List<Pozo> lstPozos;
    private Map<Integer, String> mapTipoBomba;
    
    // Horarios
    private Horario horario;
    private Dia dia;
    private Dia diaSwap;
    private List<Dia> lstDias;
    private Turno turno;
    private Turno turnoSwap;
    private List<Turno> lstTurnos;
    private Map<Integer, String> mapDiaSemana;    
    
    // values de los commandbuton
    private String gralRegistrar;
    private String gralLimpiar;
    private String gralGuardar;
    private String gralCancelarSel;
    private String mdAgregarAct;
    private String mdAgregarFecha;
    private String mdAgregarPersonal;
    private String mdAgregarSuperficie;
    private String mdActualizar;
    private String mdLimpiarForm;
    private String mdAgregar;
    private String mdLimpiarTodo;
    private String mdValidarDrp;
    private String mdGuardarDatosValidDrp;
    private String mdLimpiarDatosValidDrp;
    private String mdLimpiar;
    private String compGuardar;
    private String compLimpiar;
    private String compActualizar;
    private String compEliminar;
    private String vlcGuardar;
    private String vlcLimpiar;
    private String vlcActualizar;
    private String vlcEliminar;
    private String vlcAgregar;
    private String vlcTratamientos;
    private String vlcGuardarTrat;
    private String dscGuardarDesc;
    private String dscActualizarDesc;
    private String dscLimpiarDesc;
    private String dscEliminar;
    private String pzGuardar;
    private String pzActualizar;
    private String pzLimpiar;
    private String pzEliminar;
    private String abastGuardar;
    private String abastActualizar;
    private String abastLimpiar;
    private String abastEliminar;
    private String horarioGuardarDias;
    private String horarioActualizarDias;
    private String horarioLimpiarDias;
    private String horarioEliminarDias;    
    private String horarioGuardarTurnos;
    private String horarioLimpiarTurnos;
    
    // iconos
    private String pulgarComp;
    private String pulgarOn;
    
    // flags check vuelco
    private boolean activeAlcalino;
    
    private MbSesion sesion;
    private UsuarioExterno usLogueado;
    private Establecimiento est;
    private boolean edita;
    private boolean removido;
    private int ordenList;
    
    // flags para los tabs
    private boolean datosComReg;
    private boolean datosVuelco;
    private boolean datosDescargas;
    private boolean datosPozos;
    private boolean datosAbastos;
    private boolean datosProd;
    private boolean datosHorarios;
    
    // indicador de tab de inicio
    private int activeIndex;
    
    private String page="registro/datosgrales.xhtml";

    @EJB
    private BackendSrv backendSrv;

    public MbDeclaraciones() {
    }
    
    /**
     * Método que inicializa el Bean
     */
    @PostConstruct
    public void init(){
        // botones
        gralRegistrar = "Registrar Declaración";
        gralLimpiar = "Limpiar todo";
        gralGuardar = "Guardar";
        gralCancelarSel = "Cancelar selección";
        mdAgregarAct = "Agregar Actividad";
        mdActualizar = "Actualizar";
        mdLimpiarForm = "Limpiar formulario";
        mdAgregar = "Agregar/Editar";
        mdLimpiarTodo = "Vaciar listado";
        mdAgregarFecha = "Agregar Fecha";
        mdAgregarPersonal = "Agregar Personal";
        mdAgregarSuperficie = "Agregar Superficie";
        mdValidarDrp = "Validar el usuario en DRP";
        mdGuardarDatosValidDrp = "Guardar datos valid.";
        mdLimpiarDatosValidDrp = "Limpiar datos valid.";
        mdLimpiar = "Limpiar";
        compGuardar = "Guardar Datos Complementarios";
        compLimpiar = "Limpiar todos los Datos Complementarios";
        compActualizar = "Actualizar los Datos Complementarios";
        compEliminar = "Eliminar los Datos Complementarios";
        vlcGuardar = "Guardar Vuelco";
        vlcLimpiar = "Limpiar Vuelco";
        vlcEliminar = "Eliminar el Vuelco registrado";
        vlcActualizar  = "Actualizar Vuelco";
        vlcTratamientos = "Trat. liq.";
        vlcGuardarTrat = "Guardar Tratatamiento";
        dscGuardarDesc = "Guardar Descargas";
        dscLimpiarDesc = "Limpiar Descargas";
        dscActualizarDesc = "Actualizar Descargas registradas";
        dscEliminar = "Eliminar las Descargas registradas";
        pzGuardar = "Guardar Pozos";
        pzLimpiar = "Limpiar Pozos";
        pzActualizar = "Actualizar los Pozos registrados";
        pzEliminar = "Eliminar los Pozos registrados";
        abastGuardar = "Guardar Abastos";
        abastActualizar = "Actualizar los Abastos registrados";
        abastLimpiar = "Limpiar Abastos";
        abastEliminar = "Eliminar los Abastos registrados";
        
        horarioGuardarDias = "Guardar Días";
        horarioActualizarDias = "Actualizar los Días registrador";
        horarioLimpiarDias = "Limpiar Días";
        horarioEliminarDias = "Eliminar los Días registrados";    
        horarioGuardarTurnos = "Guardar Turnos";
        horarioLimpiarTurnos = "Limpiar Turnos";
        
        activeAlcalino = false;
        
        pulgarComp = "glyphicon-thumbs-down";
                
        // flags
        edita = false;
        removido = false;
        datosComReg = false;
        datosVuelco = false;
        datosDescargas = false;
        datosPozos = false;
        datosAbastos = false;
        datosProd = false;
        presentoDoc = false;
        activeIndex = 0;

        // hashmaps
        mapTipoFecha = FechaDec.getMP_TIPO_FECHAS();
        mapTipoPersonal = CantPersonalDec.getMP_TIPO_PERS();
        mapTipoSuperficie = SuperficieDec.getMP_TIPO_SUP();
        mapTipoVisado = DocDec.getMP_VISADOS();
        mapTipoTratamiento = Tratamiento.getMP_NOMBRES();
        mapTipoDescarga = Descarga.getMP_TIPODESC();
        mapTipoBomba = Pozo.getMP_TIPO_BOMBA();
        mapOrigenes = Abasto.getMP_ORIGEN();
        mapCircuitos = Abasto.getMP_CIRCUITO();
        mapFuentes = Abasto.getMP_FUENTE();
        mapDiaSemana = Dia.getMP_DIAS();
        
        // entidades y listados de formularios
        docDec = new DocDec();
        vuelco = new Vuelco();
        descarga = new Descarga();   
        abastecimiento = new Abastecimiento();
        pozo = new Pozo();
        abasto = new Abasto();
        lstPozos = new ArrayList<>();
        lstAbasto = new ArrayList<>();
        lstDescargas = new ArrayList<>(); 
        lstAforos = backendSrv.getAforosAll();
        lstDestinos = backendSrv.getCursosAll();
        lstTiposAbasto = backendSrv.getTipoAbastoAll();
        lstTiposCaudal = backendSrv.getTipoCaudalAll();
        horario = new Horario();
        dia = new Dia();
        lstDias = new ArrayList<>();
        turno = new Turno();
        lstTurnos = new ArrayList<>();

        // obtengo el usuario
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        sesion = (MbSesion)ctx.getSessionMap().get("mbSesion");
        usLogueado = sesion.getUsuario();
        est = sesion.getEstablecimiento();
    }   
    
    /**
     * Método que se ejecuta al cargar la vista /declaraciones/registrar.xhtml
     * Baja todos los bean que estén en sesión y no sean el de sesión ni el actual
     */
    public void iniciar(){
        if(declaracion == null) declaracion = new DeclaracionJurada();
        if(lstActividades == null) lstActividades = backendSrv.getActividadAll();
        prepareDatosComp();
        String s;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
        .getExternalContext().getSession(true);
        Enumeration enume = session.getAttributeNames();
        while(enume.hasMoreElements()){
            s = (String)enume.nextElement();
            if(s.substring(0, 2).equals("mb")){
                if(!s.equals("mbDeclaraciones")&& !s.equals("mbSesion")){ 
                    session.removeAttribute(s);
                }
            }
        }
    }

    
    /**********************
     * Métodos de acceso **
     **********************/
    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public List<Dia> getLstDias() {
        return lstDias;
    }

    public void setLstDias(List<Dia> lstDias) {
        this.lstDias = lstDias;
    }

    public List<Turno> getLstTurnos() {
        return lstTurnos;
    }

    public void setLstTurnos(List<Turno> lstTurnos) {
        this.lstTurnos = lstTurnos;
    }

    public Map<Integer, String> getMapDiaSemana() {
        return mapDiaSemana;
    }

    public void setMapDiaSemana(Map<Integer, String> mapDiaSemana) {
        this.mapDiaSemana = mapDiaSemana;
    }

    public String getHorarioGuardarDias() {
        return horarioGuardarDias;
    }

    public void setHorarioGuardarDias(String horarioGuardarDias) {
        this.horarioGuardarDias = horarioGuardarDias;
    }

    public String getHorarioActualizarDias() {
        return horarioActualizarDias;
    }

    public void setHorarioActualizarDias(String horarioActualizarDias) {
        this.horarioActualizarDias = horarioActualizarDias;
    }

    public String getHorarioLimpiarDias() {
        return horarioLimpiarDias;
    }

    public void setHorarioLimpiarDias(String horarioLimpiarDias) {
        this.horarioLimpiarDias = horarioLimpiarDias;
    }

    public String getHorarioEliminarDias() {
        return horarioEliminarDias;
    }

    public void setHorarioEliminarDias(String horarioEliminarDias) {
        this.horarioEliminarDias = horarioEliminarDias;
    }

    public String getHorarioGuardarTurnos() {
        return horarioGuardarTurnos;
    }

    public void setHorarioGuardarTurnos(String horarioGuardarTurnos) {
        this.horarioGuardarTurnos = horarioGuardarTurnos;
    }

    public String getHorarioLimpiarTurnos() {
        return horarioLimpiarTurnos;
    }

    public void setHorarioLimpiarTurnos(String horarioLimpiarTurnos) {
        this.horarioLimpiarTurnos = horarioLimpiarTurnos;
    }

    public boolean isDatosHorarios() {
        return datosHorarios;
    }

    public void setDatosHorarios(boolean datosHorarios) {
        this.datosHorarios = datosHorarios;
    }

    public Abasto getAbasto() {
        return abasto;
    }

    public void setAbasto(Abasto abasto) {
        this.abasto = abasto;
    }

    public List<Abasto> getLstAbasto() {
        return lstAbasto;
    }

    public void setLstAbasto(List<Abasto> lstAbasto) {
        this.lstAbasto = lstAbasto;
    }

    public Map<Integer, String> getMapOrigenes() {
        return mapOrigenes;
    }

    public void setMapOrigenes(Map<Integer, String> mapOrigenes) {
        this.mapOrigenes = mapOrigenes;
    }

    public Map<Integer, String> getMapCircuitos() {
        return mapCircuitos;
    }

    public void setMapCircuitos(Map<Integer, String> mapCircuitos) {
        this.mapCircuitos = mapCircuitos;
    }

    public Map<Integer, String> getMapFuentes() {
        return mapFuentes;
    }

    public void setMapFuentes(Map<Integer, String> mapFuentes) {
        this.mapFuentes = mapFuentes;
    }

    public List<TipoAbasto> getLstTiposAbasto() {
        return lstTiposAbasto;
    }

    public void setLstTiposAbasto(List<TipoAbasto> lstTiposAbasto) {
        this.lstTiposAbasto = lstTiposAbasto;
    }

    public List<TipoCaudal> getLstTiposCaudal() {
        return lstTiposCaudal;
    }

    public void setLstTiposCaudal(List<TipoCaudal> lstTiposCaudal) {
        this.lstTiposCaudal = lstTiposCaudal;
    }

    public String getAbastGuardar() {
        return abastGuardar;
    }

    public void setAbastGuardar(String abastGuardar) {
        this.abastGuardar = abastGuardar;
    }

    public String getAbastActualizar() {
        return abastActualizar;
    }

    public void setAbastActualizar(String abastActualizar) {
        this.abastActualizar = abastActualizar;
    }

    public String getAbastLimpiar() {
        return abastLimpiar;
    }

    public void setAbastLimpiar(String abastLimpiar) {
        this.abastLimpiar = abastLimpiar;
    }

    public String getAbastEliminar() {
        return abastEliminar;
    }

    public void setAbastEliminar(String abastEliminar) {
        this.abastEliminar = abastEliminar;
    }

    public String getDscActualizarDesc() {
        return dscActualizarDesc;
    }

    public void setDscActualizarDesc(String dscActualizarDesc) {
        this.dscActualizarDesc = dscActualizarDesc;
    }

    public String getPzActualizar() {
        return pzActualizar;
    }

    public void setPzActualizar(String pzActualizar) {
        this.pzActualizar = pzActualizar;
    }

    public String getPzEliminar() {
        return pzEliminar;
    }

    public void setPzEliminar(String pzEliminar) {
        this.pzEliminar = pzEliminar;
    }

    public String getPzGuardar() {
        return pzGuardar;
    }

    public void setPzGuardar(String pzGuardar) {
        this.pzGuardar = pzGuardar;
    }

    public String getPzLimpiar() {
        return pzLimpiar;
    }

    public void setPzLimpiar(String pzLimpiar) {
        this.pzLimpiar = pzLimpiar;
    }

    public boolean isDatosPozos() {
        return datosPozos;
    }

    public void setDatosPozos(boolean datosPozos) {
        this.datosPozos = datosPozos;
    }

    public Abastecimiento getAbastecimiento() {
        return abastecimiento;
    }

    public void setAbastecimiento(Abastecimiento abastecimiento) {
        this.abastecimiento = abastecimiento;
    }

    public Map<Integer, String> getMapTipoBomba() {
        return mapTipoBomba;
    }

    public void setMapTipoBomba(Map<Integer, String> mapTipoBomba) {
        this.mapTipoBomba = mapTipoBomba;
    }

    public Pozo getPozo() {
        return pozo;
    }

    public void setPozo(Pozo pozo) {
        this.pozo = pozo;
    }

    public Pozo getPozoSwap() {
        return pozoSwap;
    }

    public void setPozoSwap(Pozo pozoSwap) {
        this.pozoSwap = pozoSwap;
    }

    public List<Pozo> getLstPozos() {
        return lstPozos;
    }

    public void setLstPozos(List<Pozo> lstPozos) {
        this.lstPozos = lstPozos;
    }

    public String getDscEliminar() {
        return dscEliminar;
    }

    public void setDscEliminar(String dscEliminar) {
        this.dscEliminar = dscEliminar;
    }

    public String getDscLimpiarDesc() {
        return dscLimpiarDesc;
    }

    public void setDscLimpiarDesc(String dscLimpiarDesc) {
        this.dscLimpiarDesc = dscLimpiarDesc;
    }

    public String getGralCancelarSel() {
        return gralCancelarSel;
    }

    public void setGralCancelarSel(String gralCancelarSel) {
        this.gralCancelarSel = gralCancelarSel;
    }

    public String getPulgarComp() {
        return pulgarComp;
    }

    public void setPulgarComp(String pulgarComp) {
        this.pulgarComp = pulgarComp;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Descarga getDescSwap() {
        return descSwap;
    }

    public void setDescSwap(Descarga descSwap) {
        this.descSwap = descSwap;
    }

    public boolean isEdita() {
        return edita;
    }

    public void setEdita(boolean edita) {
        this.edita = edita;
    }

    public List<Curso> getLstDestinos() {
        return lstDestinos;
    }

    public String getGralGuardar() {
        return gralGuardar;
    }

    public void setGralGuardar(String gralGuardar) {
        this.gralGuardar = gralGuardar;
    }

    public void setLstDestinos(List<Curso> lstDestinos) {
        this.lstDestinos = lstDestinos;
    }

    public String getDscGuardarDesc() {
        return dscGuardarDesc;
    }

    public void setDscGuardarDesc(String dscGuardarDesc) {
        this.dscGuardarDesc = dscGuardarDesc;
    }

    public String getVlcGuardarTrat() {
        return vlcGuardarTrat;
    }

    public void setVlcGuardarTrat(String vlcGuardarTrat) {
        this.vlcGuardarTrat = vlcGuardarTrat;
    }

    public String getMdLimpiar() {
        return mdLimpiar;
    }

    public void setMdLimpiar(String mdLimpiar) {
        this.mdLimpiar = mdLimpiar;
    }

    public String getVlcTratamientos() {
        return vlcTratamientos;
    }

    public void setVlcTratamientos(String vlcTratamientos) {
        this.vlcTratamientos = vlcTratamientos;
    }

    public List<Aforo> getLstAforos() {
        return lstAforos;
    }

    public void setLstAforos(List<Aforo> lstAforos) {
        this.lstAforos = lstAforos;
    }

    public Map<Integer, String> getMapTipoDescarga() {
        return mapTipoDescarga;
    }

    public void setMapTipoDescarga(Map<Integer, String> mapTipoDescarga) {
        this.mapTipoDescarga = mapTipoDescarga;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Tratamiento getTratamientoSelected() {
        return tratamientoSelected;
    }

    public void setTratamientoSelected(Tratamiento tratamientoSelected) {
        this.tratamientoSelected = tratamientoSelected;
    }

    public Map<Integer, String> getMapTipoTratamiento() {
        return mapTipoTratamiento;
    }

    public void setMapTipoTratamiento(Map<Integer, String> mapTipoTratamiento) {
        this.mapTipoTratamiento = mapTipoTratamiento;
    }

    public String getVlcAgregar() {
        return vlcAgregar;
    }

    public void setVlcAgregar(String vlcAgregar) {
        this.vlcAgregar = vlcAgregar;
    }

    public Descarga getDescarga() {
        return descarga;
    }

    public void setDescarga(Descarga descarga) {
        this.descarga = descarga;
    }

    public List<Descarga> getLstDescargas() {
        return lstDescargas;
    }

    public void setLstDescargas(List<Descarga> lstDescargas) {
        this.lstDescargas = lstDescargas;
    }

    public boolean isDatosDescargas() {
        return datosDescargas;
    }

    public void setDatosDescargas(boolean datosDescargas) {
        this.datosDescargas = datosDescargas;
    }

    public boolean isActiveAlcalino() {
        return activeAlcalino;
    }

    public void setActiveAlcalino(boolean activeAlcalino) {
        this.activeAlcalino = activeAlcalino;
    }

    public String getVlcGuardar() {
        return vlcGuardar;
    }

    public void setVlcGuardar(String vlcGuardar) {
        this.vlcGuardar = vlcGuardar;
    }

    public String getVlcLimpiar() {
        return vlcLimpiar;
    }

    public void setVlcLimpiar(String vlcLimpiar) {
        this.vlcLimpiar = vlcLimpiar;
    }

    public String getVlcActualizar() {
        return vlcActualizar;
    }

    public void setVlcActualizar(String vlcActualizar) {
        this.vlcActualizar = vlcActualizar;
    }

    public String getVlcEliminar() {
        return vlcEliminar;
    }

    public void setVlcEliminar(String vlcEliminar) {
        this.vlcEliminar = vlcEliminar;
    }

    public String getMdGuardarDatosValidDrp() {
        return mdGuardarDatosValidDrp;
    }

    public void setMdGuardarDatosValidDrp(String mdGuardarDatosValidDrp) {
        this.mdGuardarDatosValidDrp = mdGuardarDatosValidDrp;
    }

    public String getMdLimpiarDatosValidDrp() {
        return mdLimpiarDatosValidDrp;
    }

    public void setMdLimpiarDatosValidDrp(String mdLimpiarDatosValidDrp) {
        this.mdLimpiarDatosValidDrp = mdLimpiarDatosValidDrp;
    }

    public String getMdValidarDrp() {
        return mdValidarDrp;
    }

    public void setMdValidarDrp(String mdValidarDrp) {
        this.mdValidarDrp = mdValidarDrp;
    }

    public ExpedienteDrp getExpediente() {
        return expediente;
    }

    public void setExpediente(ExpedienteDrp expediente) {
        this.expediente = expediente;
    }

    public Vuelco getVuelco() {
        return vuelco;
    }

    public void setVuelco(Vuelco vuelco) {
        this.vuelco = vuelco;
    }

    public String getUsDrp() {
        return usDrp;
    }

    public void setUsDrp(String usDrp) {
        this.usDrp = usDrp;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCompEliminar() {
        return compEliminar;
    }

    public void setCompEliminar(String compEliminar) {
        this.compEliminar = compEliminar;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public boolean isDatosAbastos() {
        return datosAbastos;
    }

    public void setDatosAbastos(boolean datosAbastos) {
        this.datosAbastos = datosAbastos;
    }

    public boolean isDatosProd() {
        return datosProd;
    }

    public void setDatosProd(boolean datosProd) {
        this.datosProd = datosProd;
    }

    public boolean isDatosVuelco() {
        return datosVuelco;
    }

    public void setDatosVuelco(boolean datosVuelco) {
        this.datosVuelco = datosVuelco;
    }

    public String getCompActualizar() {
        return compActualizar;
    }

    public void setCompActualizar(String compActualizar) {
        this.compActualizar = compActualizar;
    }

    public Map<Integer, String> getMapTipoVisado() {
        return mapTipoVisado;
    }

    public void setMapTipoVisado(Map<Integer, String> mapTipoVisado) {
        this.mapTipoVisado = mapTipoVisado;
    }

    public boolean isPresentoDoc() {
        return presentoDoc;
    }

    public void setPresentoDoc(boolean presentoDoc) {
        this.presentoDoc = presentoDoc;
    }

    public String getCompGuardar() {
        return compGuardar;
    }

    public void setCompGuardar(String compGuardar) {
        this.compGuardar = compGuardar;
    }

    public String getCompLimpiar() {
        return compLimpiar;
    }

    public void setCompLimpiar(String compLimpiar) {
        this.compLimpiar = compLimpiar;
    }

    public boolean isDatosComReg() {
        return datosComReg;
    }

    public void setDatosComReg(boolean datosComReg) {
        this.datosComReg = datosComReg;
    }

    public String getMdAgregarSuperficie() {
        return mdAgregarSuperficie;
    }

    public void setMdAgregarSuperficie(String mdAgregarSuperficie) {
        this.mdAgregarSuperficie = mdAgregarSuperficie;
    }

    public SuperficieDec getSupDecSelected() {
        return supDecSelected;
    }

    public void setSupDecSelected(SuperficieDec supDecSelected) {
        this.supDecSelected = supDecSelected;
    }

    public Map<Integer, String> getMapTipoSuperficie() {
        return mapTipoSuperficie;
    }

    public void setMapTipoSuperficie(Map<Integer, String> mapTipoSuperficie) {
        this.mapTipoSuperficie = mapTipoSuperficie;
    }

    public CantPersonalDec getCanPersDec() {
        return canPersDec;
    }

    public void setCanPersDec(CantPersonalDec canPersDec) {
        this.canPersDec = canPersDec;
    }

    public CantPersonalDec getCanPersDecSelected() {
        return canPersDecSelected;
    }

    public void setCanPersDecSelected(CantPersonalDec canPersDecSelected) {
        this.canPersDecSelected = canPersDecSelected;
    }

    public List<CantPersonalDec> getLstCantPers() {
        return lstCantPers;
    }

    public void setLstCantPers(List<CantPersonalDec> lstCantPers) {
        this.lstCantPers = lstCantPers;
    }

    public Map<Integer, String> getMapTipoPersonal() {
        return mapTipoPersonal;
    }

    public void setMapTipoPersonal(Map<Integer, String> mapTipoPersonal) {
        this.mapTipoPersonal = mapTipoPersonal;
    }

    public String getMdAgregarPersonal() {
        return mdAgregarPersonal;
    }

    public void setMdAgregarPersonal(String mdAgregarPersonal) {
        this.mdAgregarPersonal = mdAgregarPersonal;
    }

    public Map<Integer, String> getMapTipoFecha() {
        return mapTipoFecha;
    }

    public void setMapTipoFecha(Map<Integer, String> mapTipoFecha) {
        this.mapTipoFecha = mapTipoFecha;
    }

    public String getMdActualizar() {
        return mdActualizar;
    }

    public void setMdActualizar(String mdActualizar) {
        this.mdActualizar = mdActualizar;
    }

    public String getMdLimpiarForm() {
        return mdLimpiarForm;
    }

    public void setMdLimpiarForm(String mdLimpiarForm) {
        this.mdLimpiarForm = mdLimpiarForm;
    }

    public String getMdAgregar() {
        return mdAgregar;
    }

    public void setMdAgregar(String mdAgregar) {
        this.mdAgregar = mdAgregar;
    }

    public String getMdLimpiarTodo() {
        return mdLimpiarTodo;
    }

    public void setMdLimpiarTodo(String mdLimpiarTodo) {
        this.mdLimpiarTodo = mdLimpiarTodo;
    }

    public String getMdAgregarFecha() {
        return mdAgregarFecha;
    }

    public void setMdAgregarFecha(String mdAgregarFecha) {
        this.mdAgregarFecha = mdAgregarFecha;
    }

    public FechaDec getFechaDecSelected() {
        return fechaDecSelected;
    }

    public void setFechaDecSelected(FechaDec fechaDecSelected) {
        this.fechaDecSelected = fechaDecSelected;
    }

    public String getMdAgregarAct() {
        return mdAgregarAct;
    }

    public void setMdAgregarAct(String mdAgregarAct) {
        this.mdAgregarAct = mdAgregarAct;
    }

    public ActividadDec getActDecSelected() {
        return actDecSelected;
    }

    public void setActDecSelected(ActividadDec actDecSelected) {
        this.actDecSelected = actDecSelected;
    }

    public List<Actividad> getLstActividades() {
        return lstActividades;
    }

    public void setLstActividades(List<Actividad> lstActividades) {
        this.lstActividades = lstActividades;
    }

    public String getGralLimpiar() {
        return gralLimpiar;
    }

    public List<String> getLstVisados() {
        return lstVisados;
    }

    public void setLstVisados(List<String> lstVisados) {
        this.lstVisados = lstVisados;
    }

    public void setGralLimpiar(String gralLimpiar) {
        this.gralLimpiar = gralLimpiar;
    }

    public String getGralRegistrar() {
        return gralRegistrar;
    }

    public void setGralRegistrar(String value) {
        this.gralRegistrar = value;
    }

    public List<ActividadDec> getLstActDec() {
        return lstActDec;
    }

    public void setLstActDec(List<ActividadDec> lstActDec) {
        this.lstActDec = lstActDec;
    }

    public List<SuperficieDec> getLstSupDec() {
        return lstSupDec;
    }

    public void setLstSupDec(List<SuperficieDec> lstSupDec) {
        this.lstSupDec = lstSupDec;
    }

    public List<FechaDec> getLstFechaDec() {
        return lstFechaDec;
    }

    public void setLstFechaDec(List<FechaDec> lstFechaDec) {
        this.lstFechaDec = lstFechaDec;
    }

    public DeclaracionJurada getDeclaracion() {
        return declaracion;
    }

    public void setDeclaracion(DeclaracionJurada declaracion) {
        this.declaracion = declaracion;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public ActividadDec getActDec() {
        return actDec;
    }

    public void setActDec(ActividadDec actDec) {
        this.actDec = actDec;
    }

    public DocDec getDocDec() {
        return docDec;
    }

    public void setDocDec(DocDec docDec) {
        this.docDec = docDec;
    }

    public SuperficieDec getSupDec() {
        return supDec;
    }

    public void setSupDec(SuperficieDec supDec) {
        this.supDec = supDec;
    }

    public FechaDec getFechaDec() {
        return fechaDec;
    }

    public void setFechaDec(FechaDec fechaDec) {
        this.fechaDec = fechaDec;
    }

    /*********************************************
     * Métodos operativos Datos complementarios **
     *********************************************/

    /**
     * Método para agregar actividades al listado. Una vez completado el mismo se lo agregará a la Declaración
     * Valida que haya una actividad seleccionada para agregar al listado.
     */    
    public void addActividades(){
        /**
         * Validar que no vuelva a agregar la misma Actividad al listado
         */
        if(actividad != null){
            actDec = new ActividadDec();
            actDec.setActividad(actividad);
            lstActDec.add(actDec);
            
            actividad = null;
            
            if(activeIndex == 0) activeIndex = 1;
        }else{
            JsfUtil.addErrorMessage("No hay Actividades para agegar.");
        }
    }
    
    /**
     * Método para remover una Actividad seleccionada para la Declaración.
     * No puedo utilizar el método equals porque las ActividadDec, no fueron persistidas,
     * así que comparo con los id de la Actividad de las ActividadDec.
     */
    public void removeActividad(){
        int i = 0, r = -1;
        if(!lstActDec.isEmpty()){
            for(ActividadDec ad : lstActDec){
                if(ad.getActividad().getId() == actDecSelected.getActividad().getId()){
                    r = i;
                }
                i += 1; 
            }            
        }
        if(r > -1) lstActDec.remove(r);
    }
    
    /**
     * Método para remover todas las Actividades seleccionadas para la Declaración
     */
    public void removeActividades(){
        if(!lstActDec.isEmpty()){
            if(actividad != null) actividad = null;
            lstActDec.clear();
        }else{
            JsfUtil.addErrorMessage("No hay Actividades seleccionadas para eliminar.");
        }
    }
    
    /**
     * Método para agregar superficies al listado. Una vez completado el mismo se lo agregará a la Declaración
     * Valida que haya una superficie seleccionada para agregar al listado.
     */
    public void addSuperficies(){
        /**
         * Validar que no vuelva a agregar la misma Superficie al listado
         * Validar que la superficie ingresada sea mayor que 0
         */
        if(!edita){
            if(supDec != null){
                if(supDec.getCantidad() > 0){
                    lstSupDec.add(supDec);
                }

                supDec = new SuperficieDec();
                
                if(activeIndex == 0) activeIndex = 1;
            }else{
                JsfUtil.addErrorMessage("Debe consignar una cantidad de metros cuadrados para la superficie para poder agregarla a la Declaración Jurada.");
            }
        }else{
            lstSupDec.remove(ordenList);
            lstSupDec.add(supDec);
            supDec = null;
            edita = false;
            ordenList = 0;            
        }      
    }   
    
    /**
     * Método para agregar asignación de personal al listado. Una vez completado el mismo se lo agregará a la Declaración
     * Valida que haya una asignación de personal seleccionada para agregar al listado.
     */
    public void addPersonal(){
        /**
         * Validar que no vuelva a seleccionar el tipo ya incluido en el listado
         * Validar que la cantidad de personal ingresada sea mayor que 0
         */
        if(!edita){
            if(canPersDec != null){
                if(canPersDec.getCantidad() > 0){
                    lstCantPers.add(canPersDec);
                }

                canPersDec = new CantPersonalDec();
                
                if(activeIndex == 0) activeIndex = 1;
            }else{
                JsfUtil.addErrorMessage("Debe consignar una cantidad de personal para poder agregarla a la Declaración Jurada.");
            }
        }else{
            lstCantPers.remove(ordenList);
            lstCantPers.add(canPersDec);
            canPersDec = null;
            edita = false;
            ordenList = 0;            
        }
    }    
    
    /**
     * Método para agregar fechas al listado. Una vez completado el mismo se lo agregará a la Declaración
     * Valida que haya una fechas seleccionada para agregar al listado.
     */
    public void addFechas(){
        /**
         * Validar que no vuelva a ingresar el mismo tipo de fecha al listado
         */
        if(!edita){
            if(fechaDec != null){
                if(fechaDec.getFecha() != null){
                    lstFechaDec.add(fechaDec);
                }

                fechaDec = new FechaDec();
                
                if(activeIndex == 0) activeIndex = 1;
            }else{
                JsfUtil.addErrorMessage("Debe consignar una fecha para poder agregarla a la Declaración Jurada.");
            }
        }else{
            lstFechaDec.remove(ordenList);
            //fechaDec.setDescripcion(FechaDec.getLST_FECHA().get(itemFecha));
            lstFechaDec.add(fechaDec);
            fechaDec = null;
            edita = false;
            ordenList = 0;            
        }
    }    
    
    /**
     * Método para editar una fecha ya seleccionada para el listado
     */
    public void editFecha(){
        int i = 0;
        if(fechaDecSelected != null){
            for(FechaDec fd : lstFechaDec){
                if(fd.getTipoFecha() == fechaDecSelected.getTipoFecha()){
                    ordenList = i;
                }
                i += 1;
            }
            fechaDec = fechaDecSelected;
        }
        fechaDecSelected = null;

        edita = true; 
    }
    
    /**
     * Método para editar una asignación de personal ya seleccionada para el listado
     */
    public void editPersonal(){
        int i = 0;
        if(canPersDecSelected != null){
            for(CantPersonalDec pc : lstCantPers){
                if(pc.getTipoPers() == canPersDecSelected.getTipoPers()){
                    ordenList = i;
                }
                i += 1;
            }
            canPersDec = canPersDecSelected;
        }
        canPersDecSelected = null;

        edita = true; 
    }
    
    /**
     * Método para editar una asignación de metros cuadrados a un tipo de superficie edilicia
     */
    public void editSupericie(){
        int i = 0;
        if(supDecSelected != null){
            for(SuperficieDec sup : lstSupDec){
                if(sup.getTipoSup() == supDecSelected.getTipoSup()){
                    ordenList = i;
                }
                i += 1;
            }
            supDec = supDecSelected;
        }
        supDecSelected = null;

        edita = true; 
    } 
    
    /**
     * Método para editar un tratamiento del listado de una descarga.
     */
    public void editTratamiento(){
        int i = 0;
        if(tratamientoSelected != null && edita == false){
            tratSwap = new Tratamiento();
            for(Tratamiento trat : descarga.getTratamientos()){
                if(trat.getClaveNombre() == tratamientoSelected.getClaveNombre() && trat.getValor() == tratamientoSelected.getValor()){
                    ordenList = i;
                    tratSwap.setClaveNombre(trat.getClaveNombre());
                    tratSwap.setValor(trat.getValor());
                }
                i += 1;
            }
            tratamiento = tratamientoSelected;
            tratamientoSelected =  new Tratamiento();
            edita = true; 
        }
    }
    
    /**
     * Método para remover una fecha del listado provisorio
     */
    public void removeFecha(){
        int i = 0, r = -1;
        if(!lstFechaDec.isEmpty()){
            for(FechaDec fd : lstFechaDec){ 
                if(fd.getTipoFecha() == fechaDecSelected.getTipoFecha()){
                    r = i;
                }
                i += 1; 
            }            
        }
        if(r > -1) lstFechaDec.remove(r);
        fechaDecSelected = null;
        fechaDec = null;
    }
    
    /**
     * Método para remover una asignación de personal del listado provisorio
     */
    public void removePersonal(){
        int i = 0, r = -1;
        if(!lstCantPers.isEmpty()){
            for(CantPersonalDec pc : lstCantPers){ 
                if(pc.getTipoPers() == canPersDecSelected.getTipoPers()){
                    r = i;
                }
                i += 1; 
            }            
        }
        if(r > -1) lstCantPers.remove(r);
        canPersDecSelected = null;
        canPersDec = null;
    }
    
    /**
     * Método para eliminar una superficie del listado provisorio
     */
    public void removeSuperficie(){
        int i = 0, r = -1;
        if(!lstSupDec.isEmpty()){
            for(SuperficieDec sup : lstSupDec){ 
                if(sup.getTipoSup() == supDecSelected.getTipoSup()){
                    r = i;
                }
                i += 1; 
            }            
        }
        if(r > -1) lstSupDec.remove(r);
        supDecSelected = null;
        supDec = null;
    }
    
    /**
     * Método para remover una Descarga del Vuelco
     */
    public void removeDescarga(){
        int i = 0, r = -1;
        if(!removido){
            if(!lstDescargas.isEmpty()){
                for(Descarga desc : lstDescargas){ 
                    if(desc.getClaveTipo() == descarga.getClaveTipo() && desc.getNumOrden() == descarga.getNumOrden()){
                        r = i;
                    }
                    i += 1; 
                }            
            }
            if(r > -1) lstDescargas.remove(r);
            removido = true;
        }else{
            removido = false;
        }    
        descarga = new Descarga();
    }       
    
    /**
     * Método para remover un tratamiento del listado de la descarga
     */
    public void removeTratamiento(){
        int i = 0, r = -1;
        if(!removido){
            if(!descarga.getTratamientos().isEmpty()){
                for(Tratamiento trat : descarga.getTratamientos()){ 
                    if(trat.getClaveNombre() == tratamientoSelected.getClaveNombre() && trat.getValor() == tratamientoSelected.getValor()){
                        r = i;
                    }
                    i += 1; 
                }            
            }
            if(r > -1) descarga.getTratamientos().remove(r);
            tratamientoSelected = new Tratamiento();
            tratamiento = new Tratamiento();
            removido = true;
        }        
    }  
    
    /**
     * Método para remover un Pozo del Abasetecimiento de agua
     */
    public void removePozo(){
        int i = 0, r = -1;
        if(!removido){
            if(!lstPozos.isEmpty()){
                for(Pozo pz : lstPozos){ 
                    if(pz.getNumero() == pozo.getNumero()){
                        r = i;
                    }
                    i += 1; 
                }            
            }
            if(r > -1) lstPozos.remove(r);
            removido = true;
        }else{
            removido = false;
        } 
        pozo = new Pozo();
    }
    
    /**
     * Método para remover un Pozo del Abasetecimiento de agua
     */
    public void removeAbasto(){
        int i = 0, r = -1;
        if(!removido){
            if(!lstAbasto.isEmpty()){
                for(Abasto ab : lstAbasto){ 
                    if(ab.getTipoAbasto().equals(abasto.getTipoAbasto())){
                        r = i;
                    }
                    i += 1; 
                }            
            }
            if(r > -1) lstAbasto.remove(r);
            removido = true;
        }else{
            removido = false;
        } 
        abasto = new Abasto();
    }    
    
    /**
     * Método para remover un Pozo del Abasetecimiento de agua
     */
    public void removeDia(){
        int i = 0, r = -1;
        if(!removido){
            if(!lstDias.isEmpty()){
                for(Dia d : lstDias){ 
                    if(d.getNombre().equals(dia.getNombre())){
                        r = i;
                    }
                    i += 1; 
                }            
            }
            if(r > -1) lstDias.remove(r);
            removido = true;
        }else{
            removido = false;
        } 
        dia = new Dia();
    }        
    
    /**
     * Método para eliminar todas las fechas creadas
     */
    public void removeFechas(){
        if(!lstFechaDec.isEmpty()){
            if(fechaDec != null) fechaDec = null;
            lstFechaDec.clear();
        }else{
            JsfUtil.addErrorMessage("No hay Fchas seleccionadas para eliminar.");
        }
    }
    
    /**
     * Método para eliminar todos las asignaciones de Personal configuradas
     */
    public void removePersonales(){
        if(!lstCantPers.isEmpty()){
            if(canPersDec != null) canPersDec = null;
            lstCantPers.clear();
        }else{
            JsfUtil.addErrorMessage("No hay asignaciones de personal seleccionadas para eliminar.");
        }
    }    
    
    /**
     * Método para eliminar todas las Superficies edilicias asignadas
     */
    public void removeSuperficies(){
        if(!lstSupDec.isEmpty()){
            if(supDec != null) supDec = null;
            lstSupDec.clear();
        }else{
            JsfUtil.addErrorMessage("No hay superficies edilicias seleccionadas para eliminar.");
        }
    }
    
    /**
     * Método para eliminar todas los tratamientos de líquidos asignados a una descarga
     */
    public void removeTratamientos(){
        if(!descarga.getTratamientos().isEmpty()){
            if(tratamiento != null) tratamiento = null;
            descarga.getTratamientos().clear();
        }else{
            JsfUtil.addErrorMessage("No hay tratamientos asignados a la descarga, para eliminar.");
        }
    }    
    
    /**
     * Método para agregar descargas al listado. Una vez completado el mismo se lo agregará al Vuelco
     * en el método que lo guarda en la Declaración.
     * Valida que haya una descarga ya seteada y que los campos no nulos tengan valor
     */
    public void addDescargas(){
        if(!edita){
            if(descarga.getNumOrden() > 0 && descarga.getTipo() != null){    
                if(!validarDescarga()){
                    JsfUtil.addErrorMessage("No se pudieron validar los datos de la Descarga que intenta registrar.");
                }else{
                    lstDescargas.add(descarga);
                    JsfUtil.addSuccessMessage("La Descarga se guardó con exito. Puede agregar otra o cerrar el formulario.");
                }
                if(activeIndex == 1) activeIndex = 2;
            }else{
                JsfUtil.addErrorMessage("Debe consignar una Descarga para poder agregarla a la Declaración Jurada.");
            }
        }else{
            // valido la descarga a editar
            if(descarga.getNumOrden() > 0 && descarga.getTipo() != null){   
                if(!validarDescarga()){
                    lstDescargas.set(ordenList, descSwap);
                }else{
                    lstDescargas.set(ordenList, descarga);
                    JsfUtil.addSuccessMessage("La descarga se actualizó correctamente.");
                }
            }
            descarga = new Descarga();
            edita = false;
            ordenList = 0;           
        }
        descarga = new Descarga();
    }      
    
    /**
     * Método para agregar tratamientos al listado de cada descarga. 
     * Valida que haya una descarga ya seteada y que los campos no nulos tengan valor
     */
    public void addTratamientos(){
        if(!edita){
            if(tratamiento != null){
                if(tratamiento.getClaveNombre() > 0 && tratamiento.getClaveNombre() > 0){    
                    if(!validarTratamiento()){
                        JsfUtil.addErrorMessage("No se pudieron validar los datos del Tratamiento.");
                    }else{
                        descarga.getTratamientos().add(tratamiento) ;   
                        JsfUtil.addSuccessMessage("El tratamiento se agregó a la descarga.");
                    }
                }
                if(activeIndex == 1) activeIndex = 2;
            }else{
                JsfUtil.addErrorMessage("Debe consignar un Tratamiento para poder agregarlo a la Descarga.");
            }
        }else{
            if(!validarTratamiento()){
                descarga.getTratamientos().set(ordenList, tratSwap);
            }else{
                descarga.getTratamientos().set(ordenList, tratamiento);
                JsfUtil.addSuccessMessage("El Tratamiento se actualizó correctamente.");
            }
            
            edita = false;
            ordenList = 0;            
        }
        tratamiento = new Tratamiento();
    }          
    
    /**
     * Método para agregar Abastos al abastecimiento general de agua
     */
    public void addAbasto(){
        if(!edita){
            if(abasto.getTipoAbasto() != null){
                if(!validarAbasto()){
                    JsfUtil.addErrorMessage("No se pudieron validar los datos del Abasto.");
                }else{
                    lstAbasto.add(abasto);
                    JsfUtil.addSuccessMessage("El Abasto se agregó al Abastecimiento de agua.");
                }
            }
            if(activeIndex == 5) activeIndex = 6;
        }else{
            if(!validarAbasto()){
                lstAbasto.set(ordenList, abastoSwap);
            }else{
                lstAbasto.set(ordenList, abasto);
                JsfUtil.addSuccessMessage("El Abasto se actualizó correctamente.");
            }
            edita = false;
            ordenList = 0;  
        }
        abasto = new Abasto();
    }
    
    /**
     * Método para agregar (actualizar) un Día al Horario laboral
     */
    public void addDia(){
        if(!edita){
            if(dia.getCodDia() > 0){
                if(!validarDia()){
                    JsfUtil.addErrorMessage("No se pudieron validar los datos del Día a registrar.");
                }else{
                    lstDias.add(dia);
                    JsfUtil.addSuccessMessage("El Dia se agregó al Horario de trabajo.");
                }
            }
            if(activeIndex == 6) activeIndex = 7;
        }else{
            if(!validarDia()){
                lstDias.set(ordenList, diaSwap);
            }else{
                lstDias.set(ordenList, dia);
                JsfUtil.addSuccessMessage("El Día se actualizó correctamente.");
            }
            edita = false;
            ordenList = 0;  
        }
        dia = new Dia();
    }    
    
    /**
     * Método para agregar un Día al Abastecimiento general de agua del Establecimiento
     */
    public void addPozo(){
        if(!edita){
            if(pozo.getNumero() > 0){
                if(!validarPozo()){
                    JsfUtil.addErrorMessage("No se pudieron validar los datos del Pozo.");
                }else{
                    lstPozos.add(pozo);
                    JsfUtil.addSuccessMessage("El Pozo se agregó al Abastecimiento de agua.");
                }
            }
            if(activeIndex == 4) activeIndex = 5;
        }else{
            if(!validarPozo()){
                lstPozos.set(ordenList, pozoSwap);
            }else{
                lstPozos.set(ordenList, pozo);
                JsfUtil.addSuccessMessage("El Pozo se actualizó correctamente.");
            }
            edita = false;
            ordenList = 0;  
        }
        pozo = new Pozo();
    }
    
    /**
     * Método para agregar los datos complementarios a la Declaración.
     * Llama al método privado cargarDatosComp() que carga los datos y devuelve true si todo estado completo.
     * Setea el flag datosComReg para actualizar el formulario de datos complementarios.
     */
    public void createDatosCmplementarios(){
        datosComReg = validarDatosComp();
        if(datosComReg){
            agregarDatosComp();
            activeIndex = 2;
            page = "registro/vuelco.xhtml";
            pulgarComp = "glyphicon-thumbs-up";
            JsfUtil.addSuccessMessage("Los Datos Complementarios se agregaron a la Declaración Jurada que está confeccionado.");
        }else{
            JsfUtil.addErrorMessage("Los Datos Complementarios no fueron agregados a la Declaración Jurada.");
        }
    }

    /**
     * Método para actualizar los datos complementarios de una Declaración.
     * Llama al método privado validarDatosComp() que carga los datos y devuelve true si todo estado completo
     */
    public void editDatosComplementarios(){
        if(validarDatosComp()){
            agregarDatosComp();
            JsfUtil.addSuccessMessage("Los Datos Complementarios se actualizaron correctamente.");
        }else{
            JsfUtil.addErrorMessage("Los Datos Complementarios no fueron actualizados.");
        }
    }
    
    /**
     * Método para preparar la edición de una descarga
     */
    public void editDescarga(){
        int i = 0;
        if(descarga.getNumOrden() > 0 && !edita){
            descSwap = new Descarga();
            for(Descarga desc : lstDescargas){
                if(desc.getNumOrden() == descarga.getNumOrden()){
                    ordenList = i;
                    descSwap.setAforo(descarga.getAforo());
                    descSwap.setAnulado(descarga.isAnulado());
                    descSwap.setCamTomaMuestra(descarga.isCamTomaMuestra());
                    descSwap.setCaudal(descarga.getCaudal());
                    descSwap.setClaveTipo(descarga.getClaveTipo());
                    descSwap.setCurso(descarga.getCurso());
                    descSwap.setMayor5km(descarga.isMayor5km());
                    descSwap.setMotivoAnulado(descarga.getMotivoAnulado());
                    descSwap.setNumOrden(descarga.getNumOrden());
                    descSwap.setTratamientos(descarga.getTratamientos());
                }
                i += 1;
            }
            edita = true;
        }

    }    
    
    /**
     * Método para editar un Pozo de un Abastecimiento de agua
     */
    public void editPozo(){
        int i = 0;
        if(pozo.getNumero() > 0 && !edita){
            pozoSwap = new Pozo();
            for(Pozo pz : lstPozos){
                if(pz.getNumero() == pozo.getNumero()){
                    ordenList = i;
                    pozoSwap.setDiasFunc(pozo.getDiasFunc());
                    pozoSwap.setEnServicio(pozo.isEnServicio());
                    pozoSwap.setHorasFunc(pozo.getHorasFunc());
                    pozoSwap.setPotenciaBomba(pozo.getPotenciaBomba());
                    pozoSwap.setProfundidad(pozo.getProfundidad());
                    pozoSwap.setRendimiento(pozo.getRendimiento());
                    pozoSwap.setTipoBomba(pozo.getTipoBomba());
                }
                i += 1;
            }
            edita = true;
        }
    }
    
    /**
     * Método para editar un Abasto de un Abastecimiento general de agua
     */
    public void editAbasto(){
        int i = 0;
        if(abasto.getTipoAbasto() != null && !edita){
            abastoSwap = new Abasto();
            for(Abasto ab : lstAbasto){
                if(ab.getTipoAbasto().equals(abasto.getTipoAbasto()) && ab.getOrigenAbasto() == abasto.getOrigenAbasto() 
                        && ab.getCircuitoAbasto() == abasto.getCircuitoAbasto()){
                    ordenList = i;
                    abastoSwap.setCaudal(abasto.getCaudal());
                    if(abasto.getCircuitoAbasto() > 0) abastoSwap.setCircuitoAbasto(abasto.getCircuitoAbasto());
                    if(abasto.getFuenteAbasto() > 0) abastoSwap.setFuenteAbasto(abasto.getFuenteAbasto());
                    if(abasto.getOrigenAbasto() > 0) abastoSwap.setOrigenAbasto(abasto.getOrigenAbasto());
                    if(abasto.getPurga() > 0) abastoSwap.setPurga(abasto.getPurga());
                    abastoSwap.setTipoCaudal(abasto.getTipoCaudal());
                }
                i += 1;
            }
            edita = true;
        }
    }    
    
    /**
     * Método para editar un Dia del Horario laboral
     */
    public void editDia(){
        int i = 0;
        if(dia.getCodDia() > 0 && !edita){
            diaSwap = new Dia();
            for(Dia d : lstDias){
                if(d.getNombre().equals(dia.getNombre())){
                    ordenList = i;
                    diaSwap.setCodDia(dia.getCodDia());
                    diaSwap.setHorasInicDesc(dia.getHorasInicDesc());
                    diaSwap.setMinInicDesc(dia.getMinInicDesc());
                    diaSwap.setHorasFinDesc(dia.getHorasFinDesc());
                    diaSwap.setMinFinDesc(dia.getMinFinDesc());
                    diaSwap.setTurnos(dia.getTurnos());
                }
                i += 1;
            }
            edita = true;
        }
    }    
    
    
    /**
     * Método para eliminar los datos complementarios de la declaración jurada que se está registrando
     */
    public void deleteDatosComplementarios(){
        declaracion.setDocumentacion(null);
        declaracion.setActividades(null);
        declaracion.setSuperficies(null);
        declaracion.setCantPersonal(null);
        declaracion.setFechasDeclaracion(null);
        limpiarDatosComp();
        datosComReg = false;
        activeIndex = 0;
        page = "registro/datoscomp.xhtml";
        pulgarComp = "glyphicon-thumbs-down";
    }
    
    /**
     * Método para agregar las características de vuelco a la Declaración.
     * Setea el flag datosComReg para actualizar el formulario de datos complementarios.
     */
    public void createVuelcos(){
        declaracion.setVuelco(vuelco);
        datosVuelco = true;
        activeIndex = 3;
        JsfUtil.addSuccessMessage("Las características del Vuelco se agregaron a la Declaración Jurada que está confeccionado.");
    }    
    
    /**
     * Método para actualizar las características del Vuelco de una Declaración.
     */
    public void editVuelcos(){
        declaracion.setVuelco(vuelco);
        JsfUtil.addSuccessMessage("Las características del Vuelco se actualizaron correctamente.");
    }    
    
    /**
     * Método para eliminar las características del Vuelco de la declaración jurada que se está registrando
     */
    public void deleteVuelco(){
        declaracion.setVuelco(null);
        limpiarVuelco();
        datosVuelco = false;
        activeIndex = 1;
    }    
    
    /**
     * Método para guardar las descargas en el vuelco de la declaración
     */
    public void createDescargas(){
        if(!lstDescargas.isEmpty()){
            declaracion.getVuelco().setDescargas(lstDescargas);
            datosDescargas = true;
            activeIndex = 4;
            JsfUtil.addSuccessMessage("Las descargas confeccionadas se han agregado a las características del vuelco del Establecimiento."); 
        }else{
            JsfUtil.addErrorMessage("No hay descargas configuradas para agregar al Vuelco.");
        }
       
    }
    
    /**
     * Método para guardar los Pozos en el Abastecimiento de agua.
     * Posteriormente, se guardará el Abastecimiento en la declaración
     */
    public void createPozos(){
        if(!lstPozos.isEmpty()){
            abastecimiento.setPozos(lstPozos);
            declaracion.setAbastecimiento(abastecimiento);
            datosPozos = true;
            activeIndex = 5;
            JsfUtil.addSuccessMessage("Los Pozos confeccionados se han agregado al Abastecimiento de agua del Establecimiento."); 
        }else{
            JsfUtil.addErrorMessage("No hay Pozos configurados para agregar al Abastecimiento.");
        }
    }
    
    /**
     * Método para guardar los Abastos en el Abastecimiento general de agua.
     * Valido si el Abastecimiento ya está iniciado con Pozos
     */
    public void createAbastos(){
        if(!lstAbasto.isEmpty()){
            if(declaracion.getAbastecimiento() != null){
                declaracion.getAbastecimiento().setAbastos(lstAbasto);
            }else{
                abastecimiento.setAbastos(lstAbasto);
                declaracion.setAbastecimiento(abastecimiento);
            }
            datosAbastos = true;
            activeIndex = 6;
            JsfUtil.addSuccessMessage("Los Abastos confeccionados se han agregado al Abastecimiento general de agua del Establecimiento."); 
        }else{
            JsfUtil.addErrorMessage("No hay Abastos configurados para agregar al Abastecimiento general de agua.");
        }
    }    
    
    /**
     * Método para guardar los Días en el Horario laboral.
     * Luego agrego el Horario a la Declaración
     */
    public void createHorario(){
        if(!lstAbasto.isEmpty()){
            horario.setDias(lstDias);
            declaracion.setHorario(horario);
            datosHorarios = true;
            activeIndex = 7;
            JsfUtil.addSuccessMessage("Los Días confeccionados se han agregado al Horario laboral del Establecimiento."); 
        }else{
            JsfUtil.addErrorMessage("No hay Días configurados para agregar al Horario laboral.");
        }
    }       
    
    /**
     * Método para actualizar el listado de descargas correspondiente al vuelco de la declaración.
     */
    public void editDescargas(){
        declaracion.getVuelco().setDescargas(lstDescargas);
        JsfUtil.addSuccessMessage("Las descargas del vuelco se actualizaron correctamente.");
    }
    
    /**
     * Método para actualizar el listado de Pozos correspondientes al Abastecimiento de agua de la Declaración.
     */
    public void editPozos(){
        declaracion.getAbastecimiento().setPozos(lstPozos);
        JsfUtil.addSuccessMessage("Los Pozos del Abastecimiento se actualizaron correctamente.");
    }
    
    /**
     * Método para actualizar el listado de Pozos correspondientes al Abastecimiento de agua de la Declaración.
     */
    public void editAbastos(){
        declaracion.getAbastecimiento().setAbastos(lstAbasto);
        JsfUtil.addSuccessMessage("Los Abastos del Abastecimiento general de agua se actualizaron correctamente.");
    }
    
    /**
     * Método para actualizar el listado de Días correspondientes al Horario laboral de la Declaración.
     */
    public void editHorario(){
        declaracion.getHorario().setDias(lstDias);
        JsfUtil.addSuccessMessage("Los Días del Horario laborla se actualizaron correctamente.");
    }        
    
    /**
     * Método para eliminar las descargas del vuelco de la Declaración
     */
    public void deleteDescargas(){
        declaracion.getVuelco().getDescargas().clear();
        lstDescargas.clear();
        descarga = new Descarga();
        datosDescargas = false;
        activeIndex = 3;
    }
    
    /**
     * Método para eliminar los Pozos del Abastecimiento de la Declaración
     */
    public void deletePozos(){
        declaracion.getAbastecimiento().getPozos().clear();
        lstPozos.clear();
        pozo = new Pozo();
        datosPozos = false;
        activeIndex = 4;
    }
    
    /**
     * Método para eliminar los Abastos del Abastecimiento general de agua de la Declaración
     */
    public void deleteAbastos(){
        declaracion.getAbastecimiento().getAbastos().clear();
        lstAbasto.clear();
        abasto = new Abasto();
        datosAbastos = false;
        activeIndex = 4;
    }    
    
    /**
     * Método para eliminar los Días del Horario laboral de la Declaración
     */
    public void deleteHorario(){
        declaracion.getHorario().getDias().clear();
        lstDias.clear();
        dia = new Dia();
        datosHorarios = false;
        activeIndex = 6;
    }        
    
    public void guardarDtosValidDrp(){
        vuelco.setInscripto(true);
        vuelco.setExpNum(expediente.getExpNumero());
        vuelco.setExpAnio(expediente.getExpAnio());
        if(!expediente.getCaNumero().equals("") || !expediente.getCaNumero().equals("")) vuelco.setCaaNum(Integer.valueOf(expediente.getCaNumero()));
        else vuelco.setCaaNum(0);
        if(expediente.getCaVencimiento() != null) vuelco.setCaaFechaVenc(expediente.getCaVencimiento().toGregorianCalendar().getTime());
        else vuelco.setCaaFechaVenc(null);
        if(expediente.getCaVencimiento() != null) vuelco.setCaaVigente(verificarVigenciaCaa());
        else vuelco.setCaaVigente(false);
        vuelco.setUsDrp(expediente.getUsuario());
        vuelco.setNombreDrp(expediente.getNombre());
        if(expediente.getTipo() == 1) vuelco.setTipoDrp("Generador");
        if(expediente.getTipo() == 2) vuelco.setTipoDrp("Transportista");
        else vuelco.setTipoDrp("Operador");
        vuelco.setDescDrp(expediente.getDescripcion());
        activeIndex = 2;
    }
    
    public void limpiarVuelco(){
        vuelco = new Vuelco();
        usDrp = "";
        expediente = new ExpedienteDrp();
    }
    
    /**
     * Método para eliminar las descargas configuradas. Previo al guardado
     */
    public void limpiarDescargas(){
        lstDescargas.clear();
        descarga = new Descarga();
    }
    
    /**
     * Método para eliminar los Pozos configurados. Previo al guardado.
     */
    public void limpiarPozos(){
        lstPozos.clear();
        pozo = new Pozo();
    }
    
    /**
     * Método para eliminar los Abastos configurados. Previo al guardado.
     */
    public void limpiarAbastos(){
        lstAbasto.clear();
        abasto = new Abasto();
    }    
    
    /**
     * Método para eliminar los Días configurados. Previo al guardado.
     */
    public void limpiarHorario(){
        lstDias.clear();
        dia = new Dia();
    }        
    
    /**
     * Método para limpiar los datos del Usuario DRP validado
     */
    public void limpiarDatosValid(){
        usDrp = "";
        expediente = new ExpedienteDrp();
        limpiarUsDrp();
    }
    
    /**
     * Método para limpiar el formulario de descarga
     */
    public void limpiarDescarga(){
        if(edita){
            lstDescargas.set(ordenList, descSwap);
            descarga = descSwap;
        }else{
            descarga = new Descarga();
        }
    }
    
    /**
     * Método para limpiar el formulario de Pozo
     */
    public void limpiarPozo(){
        if(edita){
            lstPozos.set(ordenList, pozoSwap);
            pozo = new Pozo();
        }else{
            pozo = new Pozo();
        }
    }
    
    /**
     * Método para limpiar el formulario de Abasto
     */
    public void limpiarAbasto(){
        if(edita){
            lstAbasto.set(ordenList, abastoSwap);
            abasto = new Abasto();
        }else{
            abasto = new Abasto();
        }
    }    
    
    /**
     * Método para limpiar el formulario del Día
     */
    public void limpiarDia(){
        if(edita){
            lstDias.set(ordenList, diaSwap);
            dia = new Dia();
        }else{
            dia = new Dia();
        }
    }     
    
    /**
     * Método para cancelar la descarga seleccionada para su actualización
     */
    public void cancelarDescargaSel(){
        descarga = new Descarga();
        edita = false;
    }
    
    /**
     * Método para cancelar el Pozo seleccionado para su actualización
     */
    public void cancelarPozoSel(){
        pozo = new Pozo();
        edita = false;
    }
    
    /**
     * Método para cancelar el Abasto seleccionado para su actualización
     */
    public void cancelarAbastoSel(){
        abasto = new Abasto();
        edita = false;
    }
    
    /**
     * Método para cancelar el Día seleccionado para su actualización
     */
    public void cancelarDiaSel(){
        dia = new Dia();
        edita = false;
    }    
    
    public void createDeclaracion(){
        /**
         * Ver validaciones y demás, por ahora harcodeamos el firmante y levantamos el cude del usuario extrno
         */
        Firmante firmante = backendSrv.getFirmanteByID(Long.valueOf(1));
        declaracion.setFirmante(firmante);
        declaracion.setCude(usLogueado.getCude());
        
        // agrego la entidad administrativa
        Usuario us = backendSrv.getUsrByID(Long.valueOf(1));        
        Date date = new Date(System.currentTimeMillis());
        AdminEntidad admEnt = new AdminEntidad();
        admEnt.setFechaAlta(date);
        admEnt.setHabilitado(true);
        admEnt.setUsAlta(us);
        declaracion.setAdmin(admEnt);
        
        try{
            backendSrv.createDeclaracion(declaracion);
            JsfUtil.addSuccessMessage("La Declaración Jurada se ha guardado correctamente.");
        }catch(Exception ex){
            JsfUtil.addErrorMessage("Hubo un error al guardar la Declaración Jurada. " + ex.getMessage());
        }
        
    }

    /**
     * Método que limpia de la memoria las entidades que conforman los datos complementarios.
     * Cuando aún no han sido agregados a la Declaración.
     */
    public void limpiarDatosComp(){
        lstActDec.clear();
        actDec = new ActividadDec();
        docDec = new DocDec();
        lstFechaDec.clear();
        fechaDec = new FechaDec();
        lstCantPers.clear();
        canPersDec = new CantPersonalDec();
        supDec = new SuperficieDec();
        lstSupDec.clear();
        activeIndex = 1;
        presentoDoc = false;
    }
    
    /**
     * Método que valida el usuario DRP ingresado mediante el servicio AFIP del SIMEL
     */
    public void validarUsuarioDrp(){
        expediente = obtenerExpedienteDrp(usDrp);
        if(expediente != null){
            JsfUtil.addSuccessMessage("El Establecimiento se encuentra registrado en el DRP con los siguientes datos");
        }else{
            JsfUtil.addErrorMessage("El Establecimiento no se encuentra registrado en la DRP.");
        }
    }
    
    /**
     * Método para setear las propiedades necesarias para crear una descarga
     */
    public void prepareAddDescarga(){
        descarga = new Descarga();   
        lstAforos = backendSrv.getAforosAll();
        lstDestinos = backendSrv.getCursosAll();
    }

    
    /**
     * Método para setear las propieadades necesarias para crear un tipo de tratamiento para la descarga
     */
    public void prepareAddTratamiento(){
        tratamiento = new Tratamiento(); 
        tratamientoSelected = new Tratamiento();
        edita = false;
        removido = false;
    }
    
    /**
     * Métido para setear las peopiedades necesarias para editar un tipo de tratamiento existente
     */
    public void prepareEditTratamiento(){
        if(tratamientoSelected == null) tratamientoSelected = new Tratamiento();  
    }    
    
    /***********************
     * Métodos privados **
     ***********************/
    
    private void prepareDatosComp(){
        prepareAddActividades();
        prepareAddFechas();
        prepareAddPersonal();
        prepareAddSuperficie();
    }
    
    private void prepareAddActividades(){
        if(actDec == null) actDec = new ActividadDec();
        if(lstActDec == null) lstActDec = new ArrayList<>();
    }
    
    private void prepareAddFechas(){
        if(fechaDec == null) fechaDec = new FechaDec();
        if(fechaDecSelected == null) fechaDecSelected = new FechaDec();
        if(lstFechaDec == null) lstFechaDec = new ArrayList<>();
    }
    
    private void prepareAddPersonal(){
        if(canPersDec == null) canPersDec = new CantPersonalDec();
        if(canPersDecSelected == null) canPersDecSelected = new CantPersonalDec();
        if(lstCantPers == null) lstCantPers = new ArrayList<>();
    }
    
    private void prepareAddSuperficie(){
        if(supDec == null) supDec = new SuperficieDec();
        if(supDecSelected == null) supDecSelected = new SuperficieDec();
        if(lstSupDec == null) lstSupDec = new ArrayList<>();
    }    
    
    private DeclaracionJurada getDeclaracion(java.lang.Long id) {
        return backendSrv.getDeclaracionByID(id);
    }    
    
    private boolean buscarFechaInstalacion(List<FechaDec> lstFechas){
        boolean existe = false;
        for(FechaDec fecha : lstFechas){
            if(fecha.getDescripcion().equals("DE INSTALACION DEL ESTABLECIMIENTO")){
                existe = true;
            }
        }
        return existe;
    }
    
    /**
     * Método que carga la Declaración con las entidades que componen los datos complementarios.
     * Valida que todos estén completos
     */
    private boolean validarDatosComp(){
        boolean registra = true;
        
        if(docDec == null){
            registra = false;
            JsfUtil.addErrorMessage("Debe incluir un número y tipo de visado de Documentación.");
        }else{
            docDec.setDescripcion("Descripción de prueba_mb_7");
        }
        
        if(lstActDec == null){
            registra = false;
            JsfUtil.addErrorMessage("Debe incluir al menos una Actividad.");
        }
        
        if(lstSupDec == null){
            registra = false;
            JsfUtil.addErrorMessage("Debe incluir al menos los datos de un tipo de superficie.");
        }
        
        if(lstCantPers == null){
            registra = false;
            JsfUtil.addErrorMessage("Debe incluir al menos un rubro con cantidad de personal asignado.");
        }   
        
        if(lstFechaDec == null){
            registra = false;
            JsfUtil.addErrorMessage("Debe incluir al menos la fecha de instalación del Establecimiento.");
        }else{
            if(!buscarFechaInstalacion(lstFechaDec)){
                registra = false;
                JsfUtil.addErrorMessage("Debe incluir la fecha de instalación del Establecimiento.");
            }
        }     
        
        return registra;
    }
    
    private void agregarDatosComp(){
        declaracion.setDocumentacion(docDec);
        declaracion.setActividades(lstActDec);
        declaracion.setSuperficies(lstSupDec);
        declaracion.setCantPersonal(lstCantPers);
        declaracion.setFechasDeclaracion(lstFechaDec);
    }
    
    /**
     * Método privado que obtiene los datos de inscripción del Establecimiento en la DRP
     * a partir del usuario ([CUIT]/numOrden)
     * @param usuario
     * @return 
     */
    private ExpedienteDrp obtenerExpedienteDrp(String usuario){
        try { 
            CuitAfipWs port = service.getCuitAfipWsPort();
            return port.verExpediente(usuario);
        } catch (Exception ex) {
            // muestro un mensaje al usuario
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("cuitAfipWsError"));
            // lo escribo en el log del server
            System.out.println(ResourceBundle.getBundle("/Bundle").getString("cuitAfipWsError") + ex.getMessage());
            return null;
        }
    }    
    
    /**
     * Método que verifica el estado del CAA del expediente retornado por el servivio
     * @return true si la fecha de vencimiento es posterior a la actual, o false en caso contrario
     */
    private boolean verificarVigenciaCaa(){
        Date hoy = new Date(System.currentTimeMillis());
        XMLGregorianCalendar xmlHoy = toXMLGregorianCalendar(hoy);
        if(expediente.getCaVencimiento().toGregorianCalendar().compareTo(xmlHoy.toGregorianCalendar()) > 0){
            return true;
        }else{
            return false;
        }
    }
    
    private static XMLGregorianCalendar toXMLGregorianCalendar(Date date){
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        } catch (DatatypeConfigurationException ex) {
            System.out.println("Hubo un error convirtiendo fecha Date a XMLGregorianCalendar." + ex.getMessage());
        }
        return xmlCalendar;
    }

    private boolean validarDescarga() {
        boolean result = true;
        int i = 1;

        if(descarga.getNumOrden() == 0){
            result = false;
            JsfUtil.addErrorMessage("La descarga debe tener un número de orden.");
        }
        if(descarga.getCaudal() == 0){
            result = false;
            JsfUtil.addErrorMessage("La descarga debe tener un caudal.");
        }
        if(descarga.getCurso() == null){
            result = false;
            JsfUtil.addErrorMessage("La descarga debe tener un curso.");
        }
        if(descarga.isAnulado()){
            if(descarga.getMotivoAnulado() == null){
                result = false;
                JsfUtil.addErrorMessage("La descarga anulada debe tener un motivo que lo explique.");
            }
        }

        if(!lstDescargas.isEmpty()){
            for(Descarga desc : lstDescargas){
                if(desc.getNumOrden() == descarga.getNumOrden() && desc.getClaveTipo() == descarga.getClaveTipo()){
                    if(edita){
                        if(i != ordenList){
                            result = false;
                            JsfUtil.addErrorMessage("Ya existe una descarga de este tipo con el mismo número de orden.");
                        }
                    }else{
                        result = false;
                        JsfUtil.addErrorMessage("Ya existe una descarga de este tipo con el mismo número de orden.");
                    }
                }
                i += 1;
            }
        }
 
        return result;
    }

    private boolean validarTratamiento() {
        boolean result = true;
        int i = 0;
        
        if(tratamiento.getClaveNombre() == 1){
            if(tratamiento.getValor() < 1 || tratamiento.getValor() > 6){
                result = false;
                JsfUtil.addErrorMessage("El valor para el tipo " + Tratamiento.getMP_NOMBRES().get(1) + " debe ser entre 1 y 6.");
            }
        }else if(tratamiento.getClaveNombre() == 2){
            if(tratamiento.getValor() < 1 || tratamiento.getValor() > 3){
                result = false;
                JsfUtil.addErrorMessage("El valor para el tipo " + Tratamiento.getMP_NOMBRES().get(2) + " debe ser entre 1 y 3.");
            }
        }else if(tratamiento.getClaveNombre() == 3){
            if(tratamiento.getValor() < 1 || tratamiento.getValor() > 4){
                result = false;
                JsfUtil.addErrorMessage("El valor para el tipo " + Tratamiento.getMP_NOMBRES().get(3) + " debe ser entre 1 y 4.");
            }
        }else if(tratamiento.getClaveNombre() == 4){
            if(tratamiento.getValor() < 1 || tratamiento.getValor() > 4){
                result = false;
                JsfUtil.addErrorMessage("El valor para el tipo " + Tratamiento.getMP_NOMBRES().get(4) + " debe ser entre 1 y 4.");
            }
        }else{
            if(tratamiento.getValor() < 1 || tratamiento.getValor() > 2){
                result = false;
                JsfUtil.addErrorMessage("El valor para el tipo " + Tratamiento.getMP_NOMBRES().get(5) + " debe ser entre 1 y 2.");
            }
        }
        
        if(!descarga.getTratamientos().isEmpty()){
            for(Tratamiento trat : descarga.getTratamientos()){
                if(trat.getClaveNombre() == tratamiento.getClaveNombre() && trat.getValor() == tratamiento.getValor()){
                    if(edita){
                        if(i != ordenList){
                            result = false;
                            JsfUtil.addErrorMessage("Ya existe un Tratamiento de este tipo con el mismo valor.");
                        }
                    }else{
                        result = false;
                        JsfUtil.addErrorMessage("Ya existe un Tratamiento de este tipo con el mismo valor.");
                    }
                }
                i += 1;
            }
        }

        return result;
    }
    
    /**
     * Metodo para velidar los datos de un pozo para su registro
     */
    private boolean validarPozo(){
        boolean result = true;
        int i = 0;
        
        if(pozo.getProfundidad() == 0){
            result = false;
            JsfUtil.addErrorMessage("El pozo debe especificar su profundidad.");
        }
        if(pozo.getHorasFunc() == 0){
            result = false;
            JsfUtil.addErrorMessage("El pozo debe especificar la cantidad de horas diarias de funcionamiento.");
        }
        if(pozo.getDiasFunc() == 0){
            result = false;
            JsfUtil.addErrorMessage("El pozo debe especificar la cantidad de días semanales de funcionamiento.");
        }
        if(pozo.getTipoBomba() == 0){
            result = false;
            JsfUtil.addErrorMessage("El pozo debe especificar un tipo de bomba.");
        }
        if(pozo.getPotenciaBomba() == 0){
            result = false;
            JsfUtil.addErrorMessage("El pozo debe especificar la potencia de la bomba.");
        } 
        if(!lstPozos.isEmpty()){
            for(Pozo pz : lstPozos){
                if(pz.getNumero() == pozo.getNumero()){
                    if(edita){
                        if(i != ordenList){
                            result = false;
                            JsfUtil.addErrorMessage("Ya existe un pozo de este tipo con el mismo valor.");
                        }
                    }else{
                        result = false;
                        JsfUtil.addErrorMessage("Ya existe un pozo de este tipo con el mismo valor.");
                    }
                }
                i += 1;
            }
        }        
        return result;
    }
    
    /**
     * Metodo para validar los datos de un Abasto para su registro
     */
    private boolean validarAbasto(){
        boolean result = true;
        int i = 0;
        
        if(abasto.getTipoCaudal() == null){
            result = false;
            JsfUtil.addErrorMessage("El Abasto debe especificar un tipo de caudal.");
        }
        if(abasto.getCaudal() == 0){
            result = false;
            JsfUtil.addErrorMessage("El Abasto debe especificar un valor de caudal.");
        }
        
        if(abasto.getFuenteAbasto() == 3){
            if(abasto.getDescOtraFuente() == null){
                JsfUtil.addErrorMessage("Debe especificar una descripción de para la fuente seleccionada.");
                if(abasto.getDescOtraFuente().equals("")){
                    JsfUtil.addErrorMessage("Debe especificar una descripción de para la fuente seleccionada.");
                }
            }
        }
        
        if(!lstAbasto.isEmpty()){
            for(Abasto ab : lstAbasto){
                if(ab.getTipoAbasto().equals(abasto.getTipoAbasto()) && ab.getOrigenAbasto() == abasto.getOrigenAbasto() 
                        && ab.getCircuitoAbasto() == abasto.getCircuitoAbasto()){
                    if(edita){
                        if(i != ordenList){
                            result = false;
                            JsfUtil.addErrorMessage("Ya existe un Abasto de este tipo.");
                        }
                    }else{
                        result = false;
                        JsfUtil.addErrorMessage("Ya existe un Abasto de este tipo.");
                    }
                }
                i += 1;
            }
        }        
        return result;        
    }    
    
    /**
     * Metodo para validar los datos de un Día para su registro en el Horario Laboral
     */
    private boolean validarDia() {
        Calendar horaInicial = Calendar.getInstance();
        Calendar horaFinal = Calendar.getInstance();
        boolean result = true;
        int i = 0;
 
        
        if(dia.getHorasInicDesc() > 0 && dia.getHorasFinDesc() > 0){
            // convierto los enteros a horas
            horaInicial.set(Calendar.HOUR, dia.getHorasInicDesc());
            horaInicial.set(Calendar.MINUTE, dia.getMinInicDesc());
            
            horaFinal.set(Calendar.HOUR, dia.getHorasFinDesc());
            horaFinal.set(Calendar.MINUTE, dia.getMinFinDesc());
            
            // valido que la hora de finalización sea anterior a la de incicio
            if(!horaFinal.after(horaInicial)){
                result = false;
                JsfUtil.addErrorMessage("La hora de finalización de la descarga debe ser posterior a la de incicio.");
            }
        }else{
            if(dia.getHorasFinDesc() > 0){
                result = false;
                JsfUtil.addErrorMessage("Si no hay hora de incio de descarga no puede haber hora de finalización.");
            }
        }
        
        if(!lstDias.isEmpty()){
            for(Dia d : lstDias){
                if(d.getCodDia() == dia.getCodDia()){
                    if(edita){
                        if(i != ordenList){
                            result = false;
                            JsfUtil.addErrorMessage("Ya está registrado este Día de la Semana.");
                        }
                    }else{
                        result = false;
                        JsfUtil.addErrorMessage("Ya está registrado este Día de la Semana.");
                    }
                }
                i += 1;
            }
        }        
        return result;          
    }    

    /**
     * Método para limpiar los datos guardados de la validación de usuario DRP
     */
    private void limpiarUsDrp(){
        vuelco.setInscripto(false);
        vuelco.setExpNum(0);
        vuelco.setExpAnio(0);
        vuelco.setCaaNum(0);
        vuelco.setCaaFechaVenc(null);
        vuelco.setCaaVigente(false);
        vuelco.setUsDrp(null);
        vuelco.setNombreDrp(null);
        vuelco.setTipoDrp(null);
        vuelco.setDescDrp(null);
    }
    
    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = DeclaracionJurada.class)
    public static class DeclaracionJuradaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbDeclaraciones controller = (MbDeclaraciones) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbDeclaraciones");
            return controller.getDeclaracion(getKey(value));
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
            if (object instanceof DeclaracionJurada) {
                DeclaracionJurada o = (DeclaracionJurada) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + DeclaracionJurada.class.getName());
            }
        }
    }                
}
