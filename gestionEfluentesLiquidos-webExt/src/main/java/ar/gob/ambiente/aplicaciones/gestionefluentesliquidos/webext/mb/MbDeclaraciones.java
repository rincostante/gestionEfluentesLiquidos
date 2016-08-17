

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Abastecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Abasto;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Actividad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.ActividadDec;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.AdminEntidad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Aforo;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Barro;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.CantPersonalDec;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Curso;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.DeclaracionJurada;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Descarga;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Dia;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.DiaVuelco;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.DocDec;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.EstabDrp;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.FechaDec;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Firmante;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.HistorialDeclaraciones;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.HistorialFirmantes;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Horario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Materia;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Pozo;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Producto;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.SuperficieDec;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.TipoAbasto;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.TipoCaudal;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Tratamiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Turno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Unidad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.UsuarioExterno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Vuelco;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.JsfUtil;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.wsExt.CuitAfipWs;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.wsExt.CuitAfipWs_Service;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.wsExt.ExpedienteDrp;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 * Bean de respaldo para el registro de Declaraciones Juradas
 * @author rincostante
 */
public class MbDeclaraciones implements Serializable{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/vmdeswebjava.medioambiente.gov.ar_8080/CuitAfipWs/CuitAfipWs.wsdl")
    private CuitAfipWs_Service service;
    private DeclaracionJurada declaracion;
    
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
    private List<String> lstVisados;

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
    private ExpedienteDrp expGen;
    private DiaVuelco diaVuelco;
    private DiaVuelco diaVuelcoSwap;
    private List<DiaVuelco> lstDiasVuelco;
    private Map<Integer, String> mapDiaVuelcoSemana;    

    // Descargas
    private Descarga descarga;
    private Descarga descSwap;
    private Tratamiento tratamiento;
    private Tratamiento tratSwap;
    private List<Descarga> lstDescargas; 
    private List<Curso> lstDestinos;
    private Map<Integer, String> mapTipoTratamiento;
    private Map<Integer, String> mapTipoDescarga;
    private Map<Integer, String> mapValoresTrat;
    private List<Aforo> lstAforos;
    private int valorTipoTrat;
    
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
    private Map<Integer, String> mapDiaSemana;    
    
    // Productos/Materias primas
    private Producto producto;
    private Producto productoSwap;
    private List<Producto> lstProductos;
    private Materia materia;
    private Materia materiaSwap;
    private List<Unidad> lstUnidades;
    
    // Barros
    private Barro barro;
    private EstabDrp transpDrp;
    private EstabDrp operadorDrp;
    private ExpedienteDrp expTran;
    private ExpedienteDrp expOp;
    private boolean habilitaTransDrp;
    private boolean habilitaOpDrp;
    private boolean habilitaActivo;
    private boolean otroTratamiento;
    private boolean otrosMedios;
    private boolean usoInsumo;
    private boolean otroDestino;
    
    // Archivos
    private boolean subeBalance;
    private boolean subeManifYCert;
    private boolean subePermisoFact;
    private boolean subeInicioFact;
    private boolean subeCroquis;
    private boolean subeCertRetiroYDispFinal;
    private boolean subeProtocolo;
    
    // Firma
    private Firmante firmante;
    private Firmante newFirmanteSel;
    private Firmante newFirmanteReg;
    private Firmante upFirmExist;
    private Firmante firmanteSwap;
    private Establecimiento est;
    private boolean firmDeshechado;
    private List<Firmante> lstFirmantes;
    
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
    private String vlcGuardarDescargas;
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
    private String prodGuardarProductos;
    private String prodActualizarProductos;
    private String prodLimpiarProductos;
    private String prodEliminarProductos;
    private String prodGuardarMaterias;
    private String prodLimpiarMaterias;
    private String prodGuardar;
    private String prodGuardarMat;
    private String barrGuardar;
    private String barrLimpiar;
    private String barrActualizar;
    private String barrEliminar;
    private String firAgregarExistente;
    private String firRegistarNuevo;
    private String firFirmar;
    private String firModificar;   
    private String firDeshechar;
    private String firCancelar;
    private String firVincularSel;
    private String firVincularReg;
  
    // iconos
    private String pulgarComp;
    private String pulgarVuelco;
    private String pulgarDesc;
    private String pulgarPozos;
    private String pulgarAbasto;
    private String pulgarHorario;
    private String pulgarProductos;
    private String pulgarBarros;
    private String pulgarDocumentos;
    private String pulgarFirma;

    private boolean activeAlcalino;
    private MbSesion sesion;
    private UsuarioExterno usLogueado;
    private boolean edita;
    private boolean editaHijo;
    private boolean removido;
    private int ordenList;
    private int ordenListHijo;
    
    // flags para los tabs
    private boolean datosComReg;
    private boolean datosVuelco;
    private boolean datosDescargas;
    private boolean datosPozos;
    private boolean datosAbastos;
    private boolean datosHorarios;
    private boolean datosProductos;
    private boolean datosBarros;
    private boolean decRegistrada;
    private boolean eliminandoDec;
    
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
        // obtento el usuario
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        sesion = (MbSesion)ctx.getSessionMap().get("mbSesion");
        usLogueado = sesion.getUsuario();
        
        HttpSession session = (HttpSession) ctx.getSession(true);
        //Enumeration enume = session.getAttributeNames();
        session.removeAttribute("mbEstablecimiento");
        session.removeAttribute("mbRecibo");
        
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
        vlcActualizar  = "Actualizar Vuelco registrado";
        vlcTratamientos = "Trat. liq.";
        vlcGuardarDescargas = "Guardar Descarga";
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
        horarioActualizarDias = "Actualizar los Días registrados";
        horarioLimpiarDias = "Limpiar Días";
        horarioEliminarDias = "Eliminar los Días registrados";    
        horarioGuardarTurnos = "Guardar Turnos";
        horarioLimpiarTurnos = "Limpiar Turnos";
        prodGuardarProductos = "Guardar Productos";
        prodActualizarProductos = "Actualizar los Productos registrados";
        prodLimpiarProductos = "Limpiar Productos";
        prodEliminarProductos = "Eliminar los Productos registrados";
        prodGuardarMaterias = "Guardar Materias";
        prodLimpiarMaterias = "Limpiar Materias";  
        prodGuardar = "Guardar Producto";
        prodGuardarMat = "Guardar Materia";
        barrGuardar = "Guardar Barros";
        barrLimpiar = "Limpiar Barros";
        barrActualizar = "Actualizar Barros registrados";
        barrEliminar = "Eliminar Barros registrados";
        firAgregarExistente = "Agregar existente";
        firRegistarNuevo = "Registrar nuevo";
        firFirmar = "Firmar";
        firModificar = "Modificar firma";
        firDeshechar = "Deshechar";
        firCancelar = "Cancelar";
        firVincularSel = "Vincular seleccionado";
        firVincularReg = "Vincular registrado";
        
        activeAlcalino = false;
        pulgarFirma = "glyphicon-thumbs-down";
        
        setEstablecimiento(usLogueado.getCude());
        instanciarDeclaracion();
                
        // flags
        edita = false;
        editaHijo = false;
        removido = false;
        presentoDoc = false;
        activeIndex = 0;
        habilitaActivo = false;
        otroTratamiento = false;
        otrosMedios = false;
        usoInsumo = false;
        otroDestino = false;
        firmDeshechado = false;
        decRegistrada = false;
        eliminandoDec = false;

        // hashmaps
        mapTipoFecha = FechaDec.getMP_TIPO_FECHAS();
        mapTipoPersonal = CantPersonalDec.getMP_TIPO_PERS();
        mapTipoSuperficie = SuperficieDec.getMP_TIPO_SUP();
        mapTipoVisado = DocDec.getMP_VISADOS();
        mapTipoTratamiento = Tratamiento.getMP_NOMBRES();
        mapValoresTrat = new HashMap<>();
        mapTipoDescarga = Descarga.getMP_TIPODESC();
        mapTipoBomba = Pozo.getMP_TIPO_BOMBA();
        mapOrigenes = Abasto.getMP_ORIGEN();
        mapCircuitos = Abasto.getMP_CIRCUITO();
        mapFuentes = Abasto.getMP_FUENTE();
        mapDiaSemana = Dia.getMP_DIAS();
        mapDiaVuelcoSemana = DiaVuelco.getMP_DIAS();
        
        // entidades y listados de formularios
        diaVuelco = new DiaVuelco();
        descarga = new Descarga();   
        tratamiento = new Tratamiento();
        pozo = new Pozo();
        abasto = new Abasto();
        lstAforos = backendSrv.getAforosAll();
        lstDestinos = backendSrv.getCursosAll();
        lstTiposAbasto = backendSrv.getTipoAbastoAll();
        lstTiposCaudal = backendSrv.getTipoCaudalAll();
        dia = new Dia();
        turno = new Turno();
        producto = new Producto();
        productoSwap = new Producto();
        materia  = new Materia();
        materiaSwap = new Materia();
        lstUnidades = backendSrv.getUnidadesAll();
        transpDrp = new EstabDrp();
        operadorDrp = new EstabDrp();
        lstFirmantes = backendSrv.getFirmantesAll();
        firmante = est.getFirmante();
        newFirmanteSel = new Firmante();
        newFirmanteReg = new Firmante();
    }   
    
    /**
     * Método que se ejecuta al cargar la vista /declaraciones/registrar.xhtml
     * Baja todos los bean que estén en sesión y no sean el de sesión ni el actual
     */
    public void iniciar(){
        if(lstActividades == null) lstActividades = backendSrv.getActividadAll();
        prepareDatosComp();
    }

    
    /**********************
     * Métodos de acceso **
     **********************/
    public boolean isDecRegistrada() {
        return decRegistrada;
    }

    public void setDecRegistrada(boolean decRegistrada) {
        this.decRegistrada = decRegistrada;
    }

    public Map<Integer, String> getMapDiaVuelcoSemana() {
        return mapDiaVuelcoSemana;
    }

    public void setMapDiaVuelcoSemana(Map<Integer, String> mapDiaVuelcoSemana) {
        this.mapDiaVuelcoSemana = mapDiaVuelcoSemana;
    }

    public DiaVuelco getDiaVuelco() {
        return diaVuelco;
    }

    public void setDiaVuelco(DiaVuelco diaVuelco) {
        this.diaVuelco = diaVuelco;
    }

    public List<DiaVuelco> getLstDiasVuelco() {
        return lstDiasVuelco;
    }

    public void setLstDiasVuelco(List<DiaVuelco> lstDiasVuelco) {
        this.lstDiasVuelco = lstDiasVuelco;
    }

    public Firmante getUpFirmExist() {
        return upFirmExist;
    }

    public void setUpFirmExist(Firmante upFirmExist) {
        this.upFirmExist = upFirmExist;
    }

    public String getPulgarVuelco() {
        return pulgarVuelco;
    }

    public String getPulgarDesc() {
        return pulgarDesc;
    }

    public String getPulgarPozos() {
        return pulgarPozos;
    }

    public String getPulgarAbasto() {
        return pulgarAbasto;
    }

    public String getPulgarHorario() {
        return pulgarHorario;
    }

    public String getPulgarProductos() {
        return pulgarProductos;
    }

    public String getPulgarBarros() {
        return pulgarBarros;
    }

    public String getPulgarDocumentos() {
        return pulgarDocumentos;
    }

    public String getPulgarFirma() {
        return pulgarFirma;
    }

    public Firmante getNewFirmanteReg() {
        return newFirmanteReg;
    }

    public void setNewFirmanteReg(Firmante newFirmanteReg) {
        this.newFirmanteReg = newFirmanteReg;
    }

    public Firmante getFirmanteSwap() {
        return firmanteSwap;
    }

    public void setFirmanteSwap(Firmante firmanteSwap) {
        this.firmanteSwap = firmanteSwap;
    }

    public String getFirVincularSel() {
        return firVincularSel;
    }

    public String getFirVincularReg() {
        return firVincularReg;
    }

    public List<Firmante> getLstFirmantes() {
        return lstFirmantes;
    }

    public void setLstFirmantes(List<Firmante> lstFirmantes) {
        this.lstFirmantes = lstFirmantes;
    }

    public boolean isFirmDeshechado() {
        return firmDeshechado;
    }

    public void setFirmDeshechado(boolean firmDeshechado) {
        this.firmDeshechado = firmDeshechado;
    }

    public String getFirDeshechar() {
        return firDeshechar;
    }

    public String getFirCancelar() {
        return firCancelar;
    }

    public String getFirAgregarExistente() {
        return firAgregarExistente;
    }

    public String getFirRegistarNuevo() {
        return firRegistarNuevo;
    }

    public String getFirFirmar() {
        return firFirmar;
    }

    public String getFirModificar() {
        return firModificar;
    }

    public Firmante getFirmante() {
        return firmante;
    }

    public void setFirmante(Firmante firmante) {
        this.firmante = firmante;
    }

    public Firmante getNewFirmanteSel() {
        return newFirmanteSel;
    }

    public void setNewFirmanteSel(Firmante newFirmanteSel) {
        this.newFirmanteSel = newFirmanteSel;
    }

    public Establecimiento getEst() {
        return est;
    }

    public void setEst(Establecimiento est) {
        this.est = est;
    }

    public boolean isSubeManifYCert() {
        return subeManifYCert;
    }

    public void setSubeManifYCert(boolean subeManifYCert) {
        this.subeManifYCert = subeManifYCert;
    }

    public boolean isSubePermisoFact() {
        return subePermisoFact;
    }

    public void setSubePermisoFact(boolean subePermisoFact) {
        this.subePermisoFact = subePermisoFact;
    }

    public boolean isSubeInicioFact() {
        return subeInicioFact;
    }

    public void setSubeInicioFact(boolean subeInicioFact) {
        this.subeInicioFact = subeInicioFact;
    }

    public boolean isSubeCroquis() {
        return subeCroquis;
    }

    public void setSubeCroquis(boolean subeCroquis) {
        this.subeCroquis = subeCroquis;
    }

    public boolean isSubeCertRetiroYDispFinal() {
        return subeCertRetiroYDispFinal;
    }

    public void setSubeCertRetiroYDispFinal(boolean subeCertRetiroYDispFinal) {
        this.subeCertRetiroYDispFinal = subeCertRetiroYDispFinal;
    }

    public boolean isSubeProtocolo() {
        return subeProtocolo;
    }

    public void setSubeProtocolo(boolean subeProtocolo) {
        this.subeProtocolo = subeProtocolo;
    }

    public boolean isSubeBalance() {
        return subeBalance;
    }

    public void setSubeBalance(boolean subeBalance) {
        this.subeBalance = subeBalance;
    }

    public ExpedienteDrp getExpGen() {
        return expGen;
    }

    public void setExpGen(ExpedienteDrp expGen) {
        this.expGen = expGen;
    }

    public ExpedienteDrp getExpTran() {
        return expTran;
    }

    public void setExpTran(ExpedienteDrp expTran) {
        this.expTran = expTran;
    }

    public ExpedienteDrp getExpOp() {
        return expOp;
    }

    public void setExpOp(ExpedienteDrp expOp) {
        this.expOp = expOp;
    }

    public boolean isOtroTratamiento() {
        return otroTratamiento;
    }

    public void setOtroTratamiento(boolean otroTratamiento) {
        this.otroTratamiento = otroTratamiento;
    }

    public boolean isOtrosMedios() {
        return otrosMedios;
    }

    public void setOtrosMedios(boolean otrosMedios) {
        this.otrosMedios = otrosMedios;
    }

    public boolean isHabilitaTransDrp() {
        return habilitaTransDrp;
    }

    public void setHabilitaTransDrp(boolean habilitaTransDrp) {
        this.habilitaTransDrp = habilitaTransDrp;
    }

    public boolean isUsoInsumo() {
        return usoInsumo;
    }

    public void setUsoInsumo(boolean usoInsumo) {
        this.usoInsumo = usoInsumo;
    }

    public boolean isOtroDestino() {
        return otroDestino;
    }

    public void setOtroDestino(boolean otroDestino) {
        this.otroDestino = otroDestino;
    }

    public boolean isHabilitaOpDrp() {
        return habilitaOpDrp;
    }

    public void setHabilitaOpDrp(boolean habilitaOpDrp) {
        this.habilitaOpDrp = habilitaOpDrp;
    }

    public boolean isDatosBarros() {
        return datosBarros;
    }

    public void setDatosBarros(boolean datosBarros) {
        this.datosBarros = datosBarros;
    }

    public Barro getBarro() {
        return barro;
    }

    public void setBarro(Barro barro) {
        this.barro = barro;
    }

    public EstabDrp getTranspDrp() {
        return transpDrp;
    }

    public void setTranspDrp(EstabDrp transpDrp) {
        this.transpDrp = transpDrp;
    }

    public EstabDrp getOperadorDrp() {
        return operadorDrp;
    }

    public void setOperadorDrp(EstabDrp operadorDrp) {
        this.operadorDrp = operadorDrp;
    }

    public String getBarrGuardar() {
        return barrGuardar;
    }

    public String getBarrLimpiar() {
        return barrLimpiar;
    }

    public String getBarrActualizar() {
        return barrActualizar;
    }

    public String getBarrEliminar() {
        return barrEliminar;
    }

    public String getVlcGuardarDescargas() {
        return vlcGuardarDescargas;
    }

    public int getValorTipoTrat() {
        return valorTipoTrat;
    }

    public void setValorTipoTrat(int valorTipoTrat) {
        this.valorTipoTrat = valorTipoTrat;
    }

    public Map<Integer, String> getMapValoresTrat() {
        return mapValoresTrat;
    }

    public void setMapValoresTrat(Map<Integer, String> mapValoresTrat) {
        this.mapValoresTrat = mapValoresTrat;
    }

    public String getProdGuardar() {
        return prodGuardar;
    }

    public String getProdGuardarMat() {
        return prodGuardarMat;
    }

    public boolean isDatosProductos() {
        return datosProductos;
    }

    public void setDatosProductos(boolean datosProductos) {
        this.datosProductos = datosProductos;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Producto> getLstProductos() {
        return lstProductos;
    }

    public void setLstProductos(List<Producto> lstProductos) {
        this.lstProductos = lstProductos;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Materia getMateriaSwap() {
        return materiaSwap;
    }

    public void setMateriaSwap(Materia materiaSwap) {
        this.materiaSwap = materiaSwap;
    }

    public List<Unidad> getLstUnidades() {
        return lstUnidades;
    }

    public void setLstUnidades(List<Unidad> lstUnidades) {
        this.lstUnidades = lstUnidades;
    }

    public String getProdGuardarProductos() {
        return prodGuardarProductos;
    }

    public String getProdActualizarProductos() {
        return prodActualizarProductos;
    }

    public String getProdLimpiarProductos() {
        return prodLimpiarProductos;
    }

    public String getProdEliminarProductos() {
        return prodEliminarProductos;
    }

    public String getProdGuardarMaterias() {
        return prodGuardarMaterias;
    }

    public String getProdLimpiarMaterias() {
        return prodLimpiarMaterias;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

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

    public Map<Integer, String> getMapDiaSemana() {
        return mapDiaSemana;
    }

    public void setMapDiaSemana(Map<Integer, String> mapDiaSemana) {
        this.mapDiaSemana = mapDiaSemana;
    }

    public String getHorarioGuardarDias() {
        return horarioGuardarDias;
    }

    public String getHorarioActualizarDias() {
        return horarioActualizarDias;
    }

    public String getHorarioLimpiarDias() {
        return horarioLimpiarDias;
    }

    public String getHorarioEliminarDias() {
        return horarioEliminarDias;
    }

    public String getHorarioGuardarTurnos() {
        return horarioGuardarTurnos;
    }

    public String getHorarioLimpiarTurnos() {
        return horarioLimpiarTurnos;
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

    public String getAbastActualizar() {
        return abastActualizar;
    }

    public String getAbastLimpiar() {
        return abastLimpiar;
    }

    public String getAbastEliminar() {
        return abastEliminar;
    }

    public String getDscActualizarDesc() {
        return dscActualizarDesc;
    }

    public String getPzActualizar() {
        return pzActualizar;
    }

    public String getPzEliminar() {
        return pzEliminar;
    }

    public String getPzGuardar() {
        return pzGuardar;
    }

    public String getPzLimpiar() {
        return pzLimpiar;
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

    public String getDscLimpiarDesc() {
        return dscLimpiarDesc;
    }

    public String getGralCancelarSel() {
        return gralCancelarSel;
    }

    public String getPulgarComp() {
        return pulgarComp;
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

    public void setLstDestinos(List<Curso> lstDestinos) {
        this.lstDestinos = lstDestinos;
    }

    public String getDscGuardarDesc() {
        return dscGuardarDesc;
    }

    public String getVlcGuardarTrat() {
        return vlcGuardarTrat;
    }

    public String getMdLimpiar() {
        return mdLimpiar;
    }

    public String getVlcTratamientos() {
        return vlcTratamientos;
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

    public String getVlcLimpiar() {
        return vlcLimpiar;
    }

    public String getVlcActualizar() {
        return vlcActualizar;
    }

    public String getVlcEliminar() {
        return vlcEliminar;
    }

    public String getMdGuardarDatosValidDrp() {
        return mdGuardarDatosValidDrp;
    }

    public String getMdLimpiarDatosValidDrp() {
        return mdLimpiarDatosValidDrp;
    }

    public String getMdValidarDrp() {
        return mdValidarDrp;
    }
    
    public Vuelco getVuelco(){
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

    public boolean isDatosVuelco() {
        return datosVuelco;
    }

    public void setDatosVuelco(boolean datosVuelco) {
        this.datosVuelco = datosVuelco;
    }

    public String getCompActualizar() {
        return compActualizar;
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
    
    public String getCompLimpiar() {
        return compLimpiar;
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
    
    public Map<Integer, String> getMapTipoFecha() {
        return mapTipoFecha;
    }

    public void setMapTipoFecha(Map<Integer, String> mapTipoFecha) {
        this.mapTipoFecha = mapTipoFecha;
    }

    public String getMdActualizar() {
        return mdActualizar;
    }

    public String getMdLimpiarForm() {
        return mdLimpiarForm;
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

    public String getMdAgregarFecha() {
        return mdAgregarFecha;
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

    public String getGralRegistrar() {
        return gralRegistrar;
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
                    /*
                    // se reportó un error (10/08/2016): recibe la fecha con un día menos. Compensamos acá
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fechaDec.getFecha());
                    calendar.add(Calendar.DAY_OF_YEAR, 1); 
                    fechaDec.setFecha(calendar.getTime());
                    */
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
            /*
            // se reportó un error: recibe la fecha con un día menos. Compensamos acá
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaDec.getFecha());
            calendar.add(Calendar.DAY_OF_YEAR, 1); 
            fechaDec.setFecha(calendar.getTime());
            */
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
        if(tratamiento != null && editaHijo == false){
            // actualizo el valor del tratado
            valorTipoTrat = tratamiento.getValor();
            tratSwap = new Tratamiento();
            for(Tratamiento trat : descarga.getTratamientos()){
                if(trat.getClaveNombre() == tratamiento.getClaveNombre() && trat.getValor() == tratamiento.getValor()){
                    ordenListHijo = i;
                    tratSwap.setClaveNombre(trat.getClaveNombre());
                    tratSwap.setValor(trat.getValor());
                }
                i += 1;
            }
            tratamiento =  new Tratamiento();
            editaHijo = true; 
        }
    }
    
    /**
     * Método para editar un Turno del listado de Días.
     */
    public void editTurno(){
        int i = 0;
        
        if(turno.getNumOrden() > 0 && editaHijo == false){
            turnoSwap = new Turno();
            for(Turno trn : dia.getTurnos()){
                if(trn.getNumOrden() == turno.getNumOrden()){
                    ordenListHijo = i;
                    turnoSwap.setHorasInicio(turno.getHorasInicio());
                    turnoSwap.setMinInicio(turno.getMinInicio());
                    turnoSwap.setHorasFin(turno.getHorasFin());
                    turnoSwap.setMinFin(turno.getMinFin());
                }
                i += 1;
            }
            turno =  new Turno();
            editaHijo = true; 
        }
    }    
    
    /**
     * Método para editar una Materia prima del Producto
     */
    public void editMateria(){
        int i = 0;
        
        if(!materia.getDescripcion().equals("") && editaHijo == false){
            materiaSwap = new Materia();
            for(Materia mat : producto.getMaterias()){
                if(mat.getDescripcion().equals(materia.getDescripcion())){
                    ordenListHijo = i;
                    materiaSwap.setCantidadAnual(materia.getCantidadAnual());
                    materiaSwap.setDescripcion(materia.getDescripcion());
                    materiaSwap.setUnidad(materia.getUnidad());
                }
                i += 1;
            }
            materia =  new Materia();
            editaHijo = true; 
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
                    if(trat.getClaveNombre() == tratamiento.getClaveNombre() && trat.getValor() == tratamiento.getValor()){
                        r = i;
                    }
                    i += 1; 
                }            
            }
            if(r > -1) descarga.getTratamientos().remove(r);
            removido = true;
        }else{
            removido = false;
        }     
        tratamiento = new Tratamiento();
    }  
    
    /**
     * Método para remover un Turno del listado del Día
     */
    public void removeTurno(){
        int i = 0, r = -1;
        if(!removido){
            if(!dia.getTurnos().isEmpty()){
                for(Turno trn : dia.getTurnos()){ 
                    if(trn.getNumOrden() == turno.getNumOrden()){
                        r = i;
                    }
                    i += 1; 
                }            
            }
            if(r > -1) dia.getTurnos().remove(r);
            turno = new Turno();
            removido = true;
        }    
    }    
    
    /**
     * Método para remover una Mareria prima del listado del Producto
     */  
    public void removeMateria(){
        int i = 0, r = -1;
        if(!removido){
            if(!producto.getMaterias().isEmpty()){
                for(Materia mat : producto.getMaterias()){ 
                    if(mat.getDescripcion().equals(materia.getDescripcion())){
                        r = i;
                    }
                    i += 1; 
                }            
            }
            if(r > -1) producto.getMaterias().remove(r);
            removido = true;
        }else{
            removido = false;
        } 
        materia = new Materia();
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
     * Método para remover un Día del Horario laboral
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
     * Método para remover un Día del Horario de Vuelcos
     */    
    public void removeDiaVuelco(){
        int i = 0, r = -1;
        if(!removido){
            if(!lstDiasVuelco.isEmpty()){
                for(DiaVuelco d : lstDiasVuelco){ 
                    if(d.getNombre().equals(diaVuelco.getNombre())){
                        r = i;
                    }
                    i += 1; 
                }            
            }
            if(r > -1) lstDiasVuelco.remove(r);
            removido = true;
        }else{
            removido = false;
        } 
        diaVuelco = new DiaVuelco();
    }
    
    /**
     * Método para remover un Producto
     */    
    public void removeProducto(){
        int i = 0, r = -1;
        if(!removido){
            if(!lstProductos.isEmpty()){
                for(Producto prod : lstProductos){ 
                    if(prod.getDescripcion().equals(producto.getDescripcion())){
                        r = i;
                    }
                    i += 1; 
                }            
            }
            if(r > -1) lstProductos.remove(r);
            removido = true;
        }else{
            removido = false;
        } 
        producto = new Producto();
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
            if(tratamiento != null) tratamiento = new Tratamiento();
            descarga.getTratamientos().clear();
        }else{
            JsfUtil.addErrorMessage("No hay tratamientos asignados a la descarga, para eliminar.");
        }
    }    
    
    /**
     * Método para eliminar todos los Turnos del Día
     */
    public void removeTurnos(){
        if(!dia.getTurnos().isEmpty()){
            if(turno.getNumOrden() > 0) turno = new Turno();
            dia.getTurnos().clear();
        }else{
            JsfUtil.addErrorMessage("No hay Turnos asignados al Día, para eliminar.");
        }
    }     
    
    /**
     * Método para eliminar todas las Materias primas del Producto
     */
    public void removeMaterias(){
        if(!producto.getMaterias().isEmpty()){
            if(materia.getDescripcion() != null) materia = new Materia();
            producto.getMaterias().clear();
        }else{
            JsfUtil.addErrorMessage("No hay Mateiras asignados al Producto, para eliminar.");
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
        if(!editaHijo){
            if(tratamiento != null){
                tratamiento.setValor(valorTipoTrat);
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
                descarga.getTratamientos().set(ordenListHijo, tratSwap);
            }else{
                tratamiento.setValor(valorTipoTrat);
                descarga.getTratamientos().set(ordenListHijo, tratamiento);
                JsfUtil.addSuccessMessage("El Tratamiento se actualizó correctamente.");
            }
            
            editaHijo = false;
            ordenListHijo = 0;            
        }
        valorTipoTrat = 0;
        tratamiento = new Tratamiento();
    }
    
    /**
     * Método para agregar Turnos al Día semanal. 
     */
    public void addTurnos(){
        if(!editaHijo){
            if(turno.getNumOrden() > 0){ 
                if(!validarTurno()){
                    JsfUtil.addErrorMessage("No se pudieron validar los datos del Turno.");
                }else{
                    dia.getTurnos().add(turno); 
                    JsfUtil.addSuccessMessage("El Turno se agregó al Día.");
                }
            }else{
                JsfUtil.addErrorMessage("Debe consignar un Turno para poder agregarlo al Día.");
            }
        }else{
            if(!validarTurno()){
                dia.getTurnos().set(ordenListHijo, turnoSwap);
            }else{
                dia.getTurnos().set(ordenListHijo, turno);
                JsfUtil.addSuccessMessage("El Turno se actualizó correctamente.");
            }
            
            editaHijo = false;
            ordenListHijo = 0;            
        }
        turno = new Turno();
    }    
    
    /**
     * Método para agregar Materias al Producto. 
     */  
    public void addMaterias(){
        if(!editaHijo){
            if(materia.getDescripcion() != null){ 
                if(!validarMateria()){
                    JsfUtil.addErrorMessage("No se pudieron validar los datos de la Materia.");
                }else{
                    producto.getMaterias().add(materia);
                    JsfUtil.addSuccessMessage("La Materia se agregó al Producto.");
                }
            }else{
                JsfUtil.addErrorMessage("Debe consignar una Materia para poder agregarla al Producto.");
            }
        }else{
            if(!validarMateria()){
                producto.getMaterias().set(ordenList, materiaSwap);
            }else{
                producto.getMaterias().set(ordenList, materia);
                JsfUtil.addSuccessMessage("La Materia se actualizó correctamente.");
            }
            
            editaHijo = false;
            ordenListHijo = 0;            
        }
        materia = new Materia();
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
                    abasto = new Abasto();
                }
            }
            if(activeIndex == 5) activeIndex = 6;
        }else{
            if(!validarAbasto()){
                lstAbasto.set(ordenList, abastoSwap);
                JsfUtil.addErrorMessage("No se pudieron validar los datos del Abasto.");
            }else{
                lstAbasto.set(ordenList, abasto);
                JsfUtil.addSuccessMessage("El Abasto se actualizó correctamente.");
                edita = false;
                ordenList = 0;  
                abasto = new Abasto();
            }
        }
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
     * Método que agrega un día al horario de vuelcos
     */
    public void addDiaVuelco(){
        if(!edita){
            if(diaVuelco.getCodDia() > 0){
                /*
                if(!validarDiaVuelco()){
                    JsfUtil.addErrorMessage("No se pudieron validar los datos del Día del Vuelco a registrar.");
                }else{
                    lstDiasVuelco.add(diaVuelco);
                    JsfUtil.addSuccessMessage("El Dia se agregó al Horario del Vuelco.");
                }
                */
                lstDiasVuelco.add(diaVuelco);
                JsfUtil.addSuccessMessage("El Dia se agregó al Horario del Vuelco.");
            }
            if(activeIndex == 2) activeIndex = 3;
        }else{
            /*
            if(!validarDiaVuelco()){
                lstDiasVuelco.set(ordenList, diaVuelcoSwap);
            }else{
                lstDiasVuelco.set(ordenList, diaVuelco);
                JsfUtil.addSuccessMessage("El Día del Vuelco se actualizó correctamente.");
            }
            */
            
            lstDiasVuelco.set(ordenList, diaVuelco);
            JsfUtil.addSuccessMessage("El Día del Vuelco se actualizó correctamente.");
            
            edita = false;
            ordenList = 0;  
        }
        diaVuelco = new DiaVuelco();
    }
    
    /**
     * Método para agregar (actualizar) un Producto al listado de Productos de la Declaración
     */  
    public void addProducto(){
        if(!edita){
            if(!producto.getDescripcion().equals("")){
                if(!validarProducto()){
                    JsfUtil.addErrorMessage("No se pudieron validar los datos del Producto a registrar.");
                }else{
                    lstProductos.add(producto);
                    JsfUtil.addSuccessMessage("El Producto se agregó al listado.");
                }
            }else{
                JsfUtil.addErrorMessage("Debe ingresar una descripción del Producto.");
            }
            if(activeIndex == 7) activeIndex = 8;
        }else{
            if(!validarProducto()){
                lstProductos.set(ordenList, productoSwap);
            }else{
                lstProductos.set(ordenList, producto);
                JsfUtil.addSuccessMessage("El Producto se actualizó correctamente.");
            }
            edita = false;
            ordenList = 0;  
        }
        producto = new Producto();
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
        resetEdit();
        datosComReg = validarDatosComp();
        if(datosComReg){
            agregarDatosComp();
            creatDeclaracionBorr();
            activeIndex = 2;
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
        resetEdit();
        if(validarDatosComp()){
            agregarDatosComp();
            JsfUtil.addSuccessMessage("Los Datos Complementarios se actualizaron correctamente.");
            creatDeclaracionBorr();
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
                    abastoSwap.setTipoAbasto(abasto.getTipoAbasto());
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
     * Método para editar un Dia del Horariio de Vuelcos
     */
    public void editDiaVuelco(){
        int i = 0;
        if(diaVuelco.getCodDia() > 0 && !edita){
            diaVuelcoSwap = new DiaVuelco();
            for(DiaVuelco d : lstDiasVuelco){
                if(d.getNombre().equals(diaVuelco.getNombre())){
                    ordenList = i;
                    diaVuelcoSwap.setCodDia(dia.getCodDia());
                    diaVuelcoSwap.setHorasInicDesc(dia.getHorasInicDesc());
                    diaVuelcoSwap.setMinInicDesc(dia.getMinInicDesc());
                    diaVuelcoSwap.setHorasFinDesc(dia.getHorasFinDesc());
                    diaVuelcoSwap.setMinFinDesc(dia.getMinFinDesc());
                }
                i += 1;
            }
            edita = true;
        }
    }
    
    
    /**
     * Método para editar un Producto del listado
     */
    public void editProducto(){
        int i = 0;
        if(!producto.getDescripcion().equals("") && !edita){
            productoSwap = new Producto();
            for(Producto prod : lstProductos){
                if(prod.getDescripcion().equals(producto.getDescripcion())){
                    ordenList = i;
                    productoSwap.setCantidadAnual(producto.getCantidadAnual());
                    productoSwap.setDescripcion(producto.getDescripcion());
                    productoSwap.setMaterias(producto.getMaterias());
                    productoSwap.setUnidad(producto.getUnidad());
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
        resetEdit();
        declaracion.setDocumentacion(null);
        declaracion.setActividades(null);
        declaracion.setSuperficies(null);
        declaracion.setCantPersonal(null);
        declaracion.setFechasDeclaracion(null);
        limpiarDatosComp();
        datosComReg = false;
        activeIndex = 0;
        pulgarComp = "glyphicon-thumbs-down";
    }
    
    /**
     * Método para agregar las características de vuelco a la Declaración.
     */
    public void createVuelcos(){
        resetEdit();
        if(validarVuelco()){
            vuelco.setDias(lstDiasVuelco);
            declaracion.setVuelco(vuelco);
            creatDeclaracionBorr();
            datosVuelco = true;
            activeIndex = 3;
            pulgarVuelco = "glyphicon-thumbs-up";
            JsfUtil.addSuccessMessage("Las características del Vuelco se agregaron a la Declaración Jurada que está confeccionado.");
        }else{
            JsfUtil.addErrorMessage("Los datos especificados para el Vuelco no se han podido validar. No se ingresarán a la Declaración Jurada.");
        }
    }    
    
    /**
     * Método para actualizar las características del Vuelco de una Declaración.
     */
    public void editVuelcos(){
        resetEdit();
        if(validarVuelco()){
            vuelco.setDias(lstDiasVuelco);
            declaracion.setVuelco(vuelco);
            creatDeclaracionBorr();
            JsfUtil.addSuccessMessage("Las características del Vuelco se actualizaron correctamente.");
        }else{
            JsfUtil.addErrorMessage("Los datos especificados para el Vuelco no se han podido validar. No se actualizarán en la Declaración Jurada.");
        }
    }    
    
    /**
     * Método para eliminar las características del Vuelco de la declaración jurada que se está registrando
     */
    public void deleteVuelco(){
        resetEdit();
        declaracion.setVuelco(null);
        limpiarVuelco();
        creatDeclaracionBorr();
        datosVuelco = false;
        activeIndex = 1;
        pulgarVuelco = "glyphicon-thumbs-down";
    }  
    
    /**
     * Método para agregar las características del Barro a la Declaración.
     */
    public void createBarros(){
        resetEdit();
        if(validarBarros()){
            declaracion.setBarro(barro);
            creatDeclaracionBorr();
            datosBarros = true;
            activeIndex = 9;
            pulgarBarros = "glyphicon-thumbs-up";
            JsfUtil.addSuccessMessage("Las características del Barro se agregaron a la Declaración Jurada que está confeccionado.");
        }else{
            JsfUtil.addErrorMessage("No se pudieron validar los datos del Barro.");
        }
    }      
    
    /**
     * Método para actualizar las características del Barro de una Declaración.
     */
    public void editBaros(){
        resetEdit();
        if(validarBarros()){
            declaracion.setBarro(barro);
            creatDeclaracionBorr();
            JsfUtil.addSuccessMessage("Las características del Barro se actualizaron correctamente.");
        }else{
            JsfUtil.addErrorMessage("No se pudieron validar los datos del Barro a actualizar.");
        }
    }     
    
    /**
     * Método para eliminar las características del Barro de la declaración jurada que se está registrando
     */
    public void deleteBarros(){
        resetEdit();
        declaracion.setBarro(null);
        limpiarBarros();
        creatDeclaracionBorr();
        datosBarros = false;
        activeIndex = 8;
        pulgarBarros = "glyphicon-thumbs-down";
    }      
    
    /**
     * Método para guardar las descargas en el vuelco de la declaración
     */
    public void createDescargas(){
        resetEdit();
        if(!lstDescargas.isEmpty()){
            if(declaracion.getVuelco() == null){
                Vuelco vuelco = new Vuelco();
                declaracion.setVuelco(vuelco);
            }
            declaracion.getVuelco().setDescargas(lstDescargas);
            creatDeclaracionBorr();
            datosDescargas = true;
            activeIndex = 4;
            pulgarDesc = "glyphicon-thumbs-up";
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
        resetEdit();
        if(!lstPozos.isEmpty()){
            abastecimiento.setPozos(lstPozos);
            declaracion.setAbastecimiento(abastecimiento);
            creatDeclaracionBorr();
            datosPozos = true;
            activeIndex = 5;
            pulgarPozos = "glyphicon-thumbs-up";
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
        resetEdit();
        if(!lstAbasto.isEmpty()){
            if(declaracion.getAbastecimiento() != null){
                declaracion.getAbastecimiento().setTieneAbastos(true);
                declaracion.getAbastecimiento().setAbastos(lstAbasto);
            }else{
                abastecimiento.setAbastos(lstAbasto);
                abastecimiento.setTieneAbastos(true);
                declaracion.setAbastecimiento(abastecimiento);
            }
            creatDeclaracionBorr();
            datosAbastos = true;
            activeIndex = 6;
            pulgarAbasto = "glyphicon-thumbs-up";
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
        resetEdit();
        if(!lstDias.isEmpty()){
            horario.setDias(lstDias);
            horario.setTieneDias(true);
            declaracion.setHorario(horario);
            creatDeclaracionBorr();
            datosHorarios = true;
            activeIndex = 7;
            pulgarHorario = "glyphicon-thumbs-up";
            JsfUtil.addSuccessMessage("Los Días confeccionados se han agregado al Horario laboral del Establecimiento."); 
        }else{
            JsfUtil.addErrorMessage("No hay Días configurados para agregar al Horario laboral.");
        }
    }       
    
    /**
     * Método para guardar los Productos en la Declaración.
     */ 
    public void createProductos(){
        resetEdit();
        if(!lstProductos.isEmpty()){
            declaracion.setProductos(lstProductos);
            creatDeclaracionBorr();
            datosProductos = true;
            activeIndex = 8;
            pulgarProductos = "glyphicon-thumbs-up";
            JsfUtil.addSuccessMessage("Los Productos confeccionados se han agregado a la Declaración."); 
        }else{
            JsfUtil.addErrorMessage("No hay Productos configurados para agregar a la Declaración.");
        }
    }      
    
    /**
     * Método para actualizar el listado de descargas correspondiente al vuelco de la declaración.
     */
    public void editDescargas(){
        resetEdit();
        declaracion.getVuelco().setDescargas(lstDescargas);
        creatDeclaracionBorr();
        JsfUtil.addSuccessMessage("Las descargas del vuelco se actualizaron correctamente.");
    }
    
    /**
     * Método para actualizar el listado de Pozos correspondientes al Abastecimiento de agua de la Declaración.
     */
    public void editPozos(){
        resetEdit();
        declaracion.getAbastecimiento().setPozos(lstPozos);
        creatDeclaracionBorr();
        JsfUtil.addSuccessMessage("Los Pozos del Abastecimiento se actualizaron correctamente.");
    }
    
    /**
     * Método para actualizar el listado de Abastos correspondientes al Abastecimiento de agua de la Declaración.
     */
    public void editAbastos(){
        resetEdit();
        declaracion.getAbastecimiento().setAbastos(lstAbasto);
        creatDeclaracionBorr();
        JsfUtil.addSuccessMessage("Los Abastos del Abastecimiento general de agua se actualizaron correctamente.");
    }
    
    /**
     * Método para actualizar el listado de Días correspondientes al Horario laboral de la Declaración.
     */
    public void editHorario(){
        resetEdit();
        declaracion.getHorario().setDias(lstDias);
        creatDeclaracionBorr();
        JsfUtil.addSuccessMessage("Los Días del Horario laborla se actualizaron correctamente.");
    }   
    
    /**
     * Método para actualizar el listado de Productos correspondientes al listado de la Declaración.
     */  
    public void editProductos(){
        resetEdit();
        declaracion.setProductos(lstProductos);
        creatDeclaracionBorr();
        JsfUtil.addSuccessMessage("Los Productos del listado de la Declaración se actualizaron correctamente.");
    }       
    
    /**
     * Método para eliminar las descargas del vuelco de la Declaración
     */
    public void deleteDescargas(){
        resetEdit();
        declaracion.getVuelco().getDescargas().clear();
        lstDescargas.clear();
        descarga = new Descarga();
        creatDeclaracionBorr();
        datosDescargas = false;
        activeIndex = 3;
        pulgarDesc = "glyphicon-thumbs-down";
    }
    
    /**
     * Método para eliminar los Pozos del Abastecimiento de la Declaración
     */
    public void deletePozos(){
        resetEdit();
        declaracion.getAbastecimiento().getPozos().clear();
        lstPozos.clear();
        pozo = new Pozo();
        creatDeclaracionBorr();
        datosPozos = false;
        activeIndex = 4;
        pulgarPozos = "glyphicon-thumbs-down";
    }
    
    /**
     * Método para eliminar los Abastos del Abastecimiento general de agua de la Declaración
     */
    public void deleteAbastos(){
        resetEdit();
        declaracion.getAbastecimiento().getAbastos().clear();
        lstAbasto.clear();
        abasto = new Abasto();
        creatDeclaracionBorr();
        datosAbastos = false;
        activeIndex = 4;
        pulgarAbasto = "glyphicon-thumbs-down";
    }    
    
    /**
     * Método para eliminar los Días del Horario laboral de la Declaración
     */
    public void deleteHorario(){
        resetEdit();
        declaracion.getHorario().getDias().clear();
        lstDias.clear();
        dia = new Dia();
        creatDeclaracionBorr();
        datosHorarios = false;
        activeIndex = 6;
        pulgarHorario = "glyphicon-thumbs-down";
    }   
    
    /**
     * Método para eliminar los Productos del listado de la Declaración
     */
    public void deleteProductos(){
        resetEdit();
        declaracion.getProductos().clear();
        lstProductos.clear();
        producto = new Producto();
        creatDeclaracionBorr();
        datosProductos = false;
        activeIndex = 7;
        pulgarProductos = "glyphicon-thumbs-down";
    }     
    
    /**
     * Método para guardar los datos del Establecimiento validado en la DRP
     */
    public void guardarDtosValidDrp(){
        vuelco.setInscripto(true);
        vuelco.setExpNum(expGen.getExpNumero());
        vuelco.setExpAnio(expGen.getExpAnio());
        if(!expGen.getCaNumero().equals("") || !expGen.getCaNumero().equals("")) vuelco.setCaaNum(Integer.valueOf(expGen.getCaNumero()));
        else vuelco.setCaaNum(0);
        if(expGen.getCaVencimiento() != null) vuelco.setCaaFechaVenc(expGen.getCaVencimiento().toGregorianCalendar().getTime());
        else vuelco.setCaaFechaVenc(null);
        if(expGen.getCaVencimiento() != null) vuelco.setCaaVigente(verificarVigenciaCaa(expGen));
        else vuelco.setCaaVigente(false);
        vuelco.setUsDrp(expGen.getUsuario());
        vuelco.setNombreDrp(expGen.getNombre());
        if(expGen.getTipo() == 1) vuelco.setTipoDrp("Generador");
        if(expGen.getTipo() == 2) vuelco.setTipoDrp("Transportista");
        else vuelco.setTipoDrp("Operador");
        vuelco.setDescDrp(expGen.getDescripcion());
        activeIndex = 2;
    }
    
    /**
     * Método para guardar el Transportista validado en la DRP
     */
    public void guardarTransDrp(){
        if(expTran.getTipo() == 2 && usDrp != null){
            // completo el transportista
            transpDrp = guardarEstabDrp(transpDrp, expTran);
            
            // agrego el transportista al barro
            barro.setTranspDrp(transpDrp);
            activeIndex = 9;
        }else if(usDrp == null){
            JsfUtil.addErrorMessage("Debe obtener los datos de un Transportista validado para poder agregarlos.");
        }else{
            JsfUtil.addErrorMessage("El Establecimiento seleccionado no está registrado como Transportista de Residuos peligrosos.");
        }
    }
    
    /**
     * Método para guardar el Operador validado en la DRP
     */
    public void guardarOpDrp(){
        if(expOp.getTipo() == 3 && usDrp != null){
            // completo el operador
            operadorDrp = guardarEstabDrp(operadorDrp, expOp);

            // agrego el operador al barro
            barro.setOperadorDrp(operadorDrp);
            activeIndex = 9;
        }else if(usDrp == null){
            JsfUtil.addErrorMessage("Debe obtener los datos de un Operador validado para poder agregarlos.");
        }else{
            JsfUtil.addErrorMessage("El Establecimiento seleccionado no está registrado como Operador de Residuos peligrosos.");
        }
    }
    
    public void limpiarVuelco(){
        vuelco = new Vuelco();
        lstDiasVuelco.clear();
        usDrp = "";
        expGen = new ExpedienteDrp();
    }
    
    public void limpiarBarros(){
        barro = new Barro();
        usDrp = "";
        habilitaTransDrp = true;
        habilitaOpDrp = true;
        otroTratamiento = false;
        otrosMedios = false;
        usoInsumo = false;
        otroDestino = false;
        if(transpDrp != null) transpDrp = new EstabDrp();
        if(operadorDrp != null) operadorDrp = new EstabDrp();
        limpiarTransValid();
        limpiarOpValid();
    }
    
    /**
     * Método para eliminar las descargas configuradas. Previo al guardado
     */
    public void limpiarDescargas(){
        resetEdit();
        lstDescargas.clear();
        descarga = new Descarga();
    }
    
    /**
     * Método para eliminar los Pozos configurados. Previo al guardado.
     */
    public void limpiarPozos(){
        resetEdit();
        lstPozos.clear();
        pozo = new Pozo();
    }
    
    /**
     * Método para eliminar los Abastos configurados. Previo al guardado.
     */
    public void limpiarAbastos(){
        resetEdit();
        lstAbasto.clear();
        abasto = new Abasto();
    }    
    
    /**
     * Método para eliminar los Días configurados. Previo al guardado.
     */
    public void limpiarHorario(){
        resetEdit();
        lstDias.clear();
        dia = new Dia();
    }   
    
    /**
     * Método para eliminar los Productos configurados. Previo al guardado.
     */  
    public void limpiarProductos(){
        resetEdit();
        lstProductos.clear();
        producto = new Producto();
    } 
    
    /**
     * Método para limpiar los datos del Usuario DRP validado
     */
    public void limpiarDatosValid(){
        usDrp = "";
        expGen = new ExpedienteDrp();
        limpiarUsDrp();
    }
    
    /**
     * Método para limpiar los datos del Transportista DRP validado
     */
    public void limpiarTransValid(){
        usDrp = "";
        expTran = new ExpedienteDrp();
        transpDrp = limpiarEstabDrp(transpDrp);
    }    
    
    /**
     * Método para limpiar los datos del Operador DRP validado
     */
    public void limpiarOpValid(){
        usDrp = "";
        expOp = new ExpedienteDrp();
        operadorDrp = limpiarEstabDrp(operadorDrp);
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
     * Método para limpiar el formulario de la Materia prima
     */    
    public void limpiarTratamiento(){
        if(editaHijo){
            descarga.getTratamientos().set(ordenListHijo, tratSwap);
            valorTipoTrat = 0;
            tratamiento = new Tratamiento();
        }else{
            valorTipoTrat = 0;
            tratamiento = new Tratamiento();
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
     * Método para limpiar el formulario del Día del Vuelco
     */ 
    public void limpiarDiaVuelco(){
        if(edita){
            lstDiasVuelco.set(ordenList, diaVuelcoSwap);
            diaVuelco = new DiaVuelco();
        }else{
            diaVuelco = new DiaVuelco();
        }
    } 
    
    /**
     * Método para limpiar el formulario del Producto
     */    
    public void limpiarProducto(){
        if(edita){
            lstProductos.set(ordenList, productoSwap);
            producto = new Producto();
        }else{
            producto = new Producto();
        }
    }   
    
    /**
     * Método para limpiar el formulario del Turno
     */    
    public void limpiarTurno(){
        if(editaHijo){
            dia.getTurnos().set(ordenListHijo, turnoSwap);
            turno = new Turno();
        }else{
            turno = new Turno();
        }
    }        
    
    /**
     * Método para limpiar el formulario de la Materia prima
     */    
    public void limpiarMateria(){
        if(editaHijo){
            producto.getMaterias().set(ordenListHijo, materiaSwap);
            materia = new Materia();
        }else{
            materia = new Materia();
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
    
    /**
     * Método para cancelar el Día de Vuelco seleccionado para su actualización
     */
    public void cancelarDiaVuelcoSel(){
        diaVuelco = new DiaVuelco();
        edita = false;
    }  
    
    /**
     * Método para cancelar el Producto seleccionado para su actualización
     */
    public void cancelarProductoSel(){
        producto = new Producto();
        edita = false;
    }    
    
    /**
     * Método para cancelar el Turno seleccionado para su actualización
     */
    public void cancelarTurnoSel(){
        turno = new Turno();
        editaHijo = false;
    }      
    
    /**
     * Método para cancelar la Materia seleccionada para su actualización
     */
    public void cancelarMateriaSel(){
        materia = new Materia();
        editaHijo = false;
    }  
    
    /**
     * Método para cancelar el Tratamiento seleccionado para su actualización
     */
    public void cancelarTratSel(){
        valorTipoTrat = 0;
        tratamiento = new Tratamiento();
        editaHijo = false;
    }  
    
    /**
     * Método para el guardado en borrador de la Declaración
     */
    public void creatDeclaracionBorr(){
        try{
            if(declaracion.getId() != null){
                // seteo la entidad administrativa      
                Date date = new Date(System.currentTimeMillis());
                declaracion.getAdmin().setFechaModif(date);
                declaracion.getAdmin().setUsExtModif(usLogueado);
                // actualizo la Declaración
                backendSrv.editDeclaracion(declaracion);
            }else{
                declaracion.setCude(usLogueado.getCude());
                // agrego la entidad administrativa      
                Date date = new Date(System.currentTimeMillis());
                AdminEntidad admEnt = new AdminEntidad();
                admEnt.setFechaAlta(date);
                admEnt.setHabilitado(true);
                admEnt.setUsExtAlta(usLogueado);
                declaracion.setAdmin(admEnt);
                // inserto la Declaración, previo asignar estado (1, correspondiente a "PROVISORIA")
                declaracion.setClaveEstado(1);
                backendSrv.createDeclaracion(declaracion);
            }

            JsfUtil.addSuccessMessage("La Declaración Jurada se ha guardado en borrador. Podrá continuar la carga en otra sesión.");
        }catch(Exception ex){
            JsfUtil.addErrorMessage("Hubo un error al guardar la Declaración Jurada. " + ex.getMessage());
        }
        
    }
    
    public void createDeclaracion(){
        /**
         * valido el último dígito del cuit.
         * del 0 al 4 registran en Julio.
         * del 5 al 9 registran en Agosto.
         */ 
        if(validarUltimoDigito()){
            Date date = new Date(System.currentTimeMillis());

            // seteo Firmante y CUDE
            declaracion.setFirmante(est.getFirmante()); 
            declaracion.setCude(usLogueado.getCude());

            // creo el historial para el registro de la Declaración
            HistorialDeclaraciones histDecUltimo = new HistorialDeclaraciones();
            HistorialDeclaraciones histDecNuevo = new HistorialDeclaraciones();
            // leo la última Declaración si la hubiera
            if(backendSrv.getUltimaDeclaracion(est) != null){
                histDecUltimo = backendSrv.getUltimaDeclaracion(est);
            }        
            // en cualquier caso actualizo el estado de la Declaración (2, correspondiente a "REGISTRADA") y 
            declaracion.setClaveEstado(2);

            try{
                // verifico si la Declaración está persistida como PROVISORIA
                if(declaracion.getId() != null){
                    // si está persisitida, actualizo admin
                    declaracion.getAdmin().setFechaModif(date);
                    declaracion.getAdmin().setUsExtModif(usLogueado);

                    // actualizo la Declaración, previo asignar estado (2, correspondiente a "REGISTRADA")
                    backendSrv.editDeclaracion(declaracion);

                    // asigno la Declaración existente al historial
                    histDecNuevo.setDeclaracion(declaracion);

                }else{
                    // si debo insertar agrego la entidad administrativa      
                    AdminEntidad admEnt = new AdminEntidad();
                    admEnt.setFechaAlta(date);
                    admEnt.setHabilitado(true);
                    admEnt.setUsExtAlta(usLogueado);
                    declaracion.setAdmin(admEnt);

                    // inserto la Declaración
                    backendSrv.createDeclaracion(declaracion);
                    histDecNuevo.setDeclaracion(declaracion);
                    /*
                    // obtengo el id de la Declaración insertada
                    Integer idDecla = backendSrv.obtenerDeclaReciente(usLogueado.getCude());
                    // obtengo la Declaración
                    DeclaracionJurada dc = backendSrv.getDeclaracionByID(Long.valueOf(idDecla));
                    // seteo la Declaración insertada en el historial
                    histDecNuevo.setDeclaracion(dc);
                    */
                }

                // para cualquier caso completo el seteo del historial
                histDecNuevo.setActiva(true);
                histDecNuevo.setEstablecimiento(est);
                histDecNuevo.setFecha(date);
                histDecNuevo.setUsuario(usLogueado);
                // si hubo una anterior la apago y seteo la Declaración anterior
                if(histDecUltimo.getId() != null){
                    histDecNuevo.setDecAnterior(histDecUltimo.getDeclaracion());
                    histDecUltimo.setActiva(false);
                    backendSrv.editHisDeclaracion(histDecUltimo);
                }
                // persisto el historial
                backendSrv.createHisDeclaracion(histDecNuevo);
                JsfUtil.addSuccessMessage("La Declaración Jurada se ha registrado correctamente. "
                        + "Puede imprimir el recibo. ¡Muchas gracias!");
                decRegistrada = true;
            }catch(Exception ex){
                JsfUtil.addErrorMessage("Hubo un error regitrando la Declaración Jurada. " + ex.getMessage());
            }
        }else{
            JsfUtil.addErrorMessage("Según el último dígito del CUIT del Establecimiento, la Declaración jurada solo podrá "
                        + "registrarse a partir del 1° de Agosto. Los datos consignados permanecerán en estado provisorio.");
        }
    }
    
    public void deleteDeclaracionBorr(){
        if(declaracion.getId() != null){
            try{
                backendSrv.deleteDeclaBorrador(declaracion);
                eliminandoDec = true;
                instanciarDeclaracion();
                limpiarDatosComp();
                limpiarVuelco();
                limpiarDescargas();
                limpiarPozos();
                limpiarAbastos();
                limpiarHorario();
                limpiarProductos();
                limpiarBarros();
                deleteArchivos();
                JsfUtil.addSuccessMessage("La Declaración provisoria ha sido eliminada.");
            }catch(Exception ex){
                JsfUtil.addErrorMessage("Hubo un error al eliminar la Declaración Jurada provisoria. " + ex.getMessage());
            }
        }else{
            JsfUtil.addErrorMessage("La Declaración a eliminar debe estar en estado 'PROVISORIA'");
        }
    }

    /**
     * Método que limpia de la memoria las entidades que conforman los datos complementarios.
     * Cuando aún no han sido agregados a la Declaración.
     */
    public void limpiarDatosComp(){
        resetEdit();
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
     * @param tipo entero remitido desde la vista
     */
    public void validarUsuarioDrp(int tipo){
        boolean result = false;
        switch(tipo){
            case 1:
                expGen = obtenerExpedienteDrp(usDrp);
                if(expGen != null) result = true;
                break;
            case 2:
                expTran = obtenerExpedienteDrp(usDrp);
                if(expTran != null) result = true;
                break;
            default:
                expOp = obtenerExpedienteDrp(usDrp);
                if(expOp != null) result = true;
                break;
        }

        if(result){
            JsfUtil.addSuccessMessage("El Establecimiento se encuentra registrado en el DRP con los siguientes datos");
        }else{
            JsfUtil.addErrorMessage("El Establecimiento no se encuentra registrado en la DRP.");
        }
    }

    /**
     * Método para poblar el combo de valores de tipo de tratado, 
     * según el Tipo de Tratamiento seleccionado
     */    
    public void tipoTratChangeListener(){
        switch (tratamiento.getClaveNombre()){
            case 1: 
                mapValoresTrat = Tratamiento.getMP_TIPOPRET();
                break;
            case 2:
                mapValoresTrat = Tratamiento.getMP_TIPOTRATPRIMFIS();
                break;
            case 3:
                mapValoresTrat = Tratamiento.getMP_TIPOTRATPRIMQUIM();
                break;
            case 4:
                mapValoresTrat = Tratamiento.getMP_TIPOTRATSEC();
                break;
            default:
                mapValoresTrat = Tratamiento.getMP_TIPODESINF();
                break;
        }
    }    
    
    /**
     * Método para habilitar/deshabilitar los botones de selección de datos del Establecimiento en la DRP
     */
    public void habilitarGenDrp(){
        if(!habilitaActivo){
            if(vuelco.isInscripto()){
                vuelco.setInscripto(false);
            }else{
                vuelco.setInscripto(true);
            }
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        }
    }
    
    /**
     * Método para habilitar/deshabilitar los input para la creación de días de Vuelco
     */
    public void habilitarDiasVuelco(){
        if(!habilitaActivo){
            if(vuelco.isHorarioDiscontinuo()){
                vuelco.setHorarioDiscontinuo(false);
            }else{
                vuelco.setHorarioDiscontinuo(true);
            }
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        }
    }
    
    /**
     * Método para habilitar/deshabilitar los chekbox de los tratamientos
     * Limpio los datos de tratamientos si habilito transportista DRP
     * o limpio los datos del expediente DRP y transportista (si ya está cargado)
     * en el caso contrario.
     */
    public void habilitarTransDrp(){
        if(!habilitaActivo){
            if(habilitaTransDrp){
               limpiarTratBarros();
               if(otroTratamiento) otroTratamiento = false;
               if(otrosMedios) otrosMedios = false;
            }else{
                expTran = new ExpedienteDrp();
                barro.setTranspDrp(null);
                usDrp = "";
            }
            habilitaTransDrp = !habilitaTransDrp;
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        }
    }
    
    /**
     * Método para habilitar/deshabilitar los chekbox de destinos finales
     * Limpio los datos de tratamientos si habilito transportista DRP
     * o limpio los datos del expediente DRP y transportista (si ya está cargado)
     * en el caso contrario.
     */
    public void habilitarOpDrp(){
        if(!habilitaActivo){
            if(habilitaOpDrp){
                limpiarDestBarros();
                if(otroDestino) otroDestino = false;
                if(usoInsumo) usoInsumo = false;
            }else{
                expOp = new ExpedienteDrp();
                barro.setOperadorDrp(null);
                usDrp = "";
            }
            habilitaOpDrp = !habilitaOpDrp;
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        }
    }
    
    /**
     * Método para habilitar/deshabilitar detalle de otro tratamiento realizado a los barros
     */
    public void habilitarOtroTrat(){
        if(!habilitaActivo){
            otroTratamiento = !otroTratamiento;
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        }
    }   
    
    /**
     * Método para habilitar/deshabilitar detalle de otros medios de retiro de los barros
     */
    public void habilitarOtrosMedios(){
        if(!habilitaActivo){
            otrosMedios = !otrosMedios;
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        }
    }        
    
    /**
     * Método para habilitar/deshabilitar detalle otros destinos del barro
     */
    public void habilitarOtros(){
        if(!habilitaActivo){
            otroDestino = !otroDestino;
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        }
    }
    
    /**
     * Métodos para habilitar/deshabilitar detalle del uso como insumo de los barros
     */
    public void habilitarInsumo(){
        if(!habilitaActivo){
            usoInsumo = !usoInsumo;
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        }
    }
    
    /**
     * Método para habilitar/deshabilitar la subida de archivos de balance de masas
     */
    public void habilitarSubBalances(){
        if(!habilitaActivo){
            subeBalance = !subeBalance;
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        } 
    }    
    
    /**
     * Método para habilitar/deshabilitar la subida de Manifiestos y Certificado ambiental
     */
    public void habilitarManifYCert(){
        if(!habilitaActivo){
            subeManifYCert = !subeManifYCert;
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        } 
    }    
    
    /**
     * Método para habilitar/deshabilitar la subida delPermiso de factibilidad
     */
    public void habilitarPermisoFact(){
        if(!habilitaActivo){
            subePermisoFact = !subePermisoFact;
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        } 
    }       
    
    /**
     * Método para habilitar/deshabilitar la subida del Inicio de trámite de Factibilidad
     */
    public void habilitarInicoFact(){
        if(!habilitaActivo){
            subeInicioFact = !subeInicioFact;
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        } 
    }           
    
    /**
     * Método para habilitar/deshabilitar la subida del Croquis
     */
    public void habilitarCroquis(){
        if(!habilitaActivo){
            subeCroquis = !subeCroquis;
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        } 
    }      
    
    /**
     * Método para habilitar/deshabilitar la subida del Certificado de retiro y disposición final de las descargas
     */
    public void habilitarCertRetiroYDispFinal(){
        if(!habilitaActivo){
            subeCertRetiroYDispFinal = !subeCertRetiroYDispFinal;
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        } 
    }       
    
    /**
     * Método para habilitar/deshabilitar la subida del Protocolo de caracterización de barros
     */
    public void habilitarProtocolo(){
        if(!habilitaActivo){
            subeProtocolo = !subeProtocolo;
            habilitaActivo = true;
        }else{
            habilitaActivo = false;
        } 
    }      
    
    /*************************************
     ** Mátodos para gestionar archivos **
     *************************************/
    /**
     * Método para subir la copia del balance de masas.
     * El subdirectorio correspondiente, se llama "balances"
     * @param event: evento de selección de archivo en el cliente
     * Se utiliza el método estático copyFile de la clase de utilidades
     */
    public void subirBalance(FileUploadEvent event){
        if(declaracion.getRutaBalanceMasas() == null){
            try{
                UploadedFile fileBalMasas = event.getFile();
                String destino = ResourceBundle.getBundle("/Bundle").getString("RutaArchivos") + ResourceBundle.getBundle("/Bundle").getString("SubdirBalance");
                // obtengo el nombre del archivo
                String nombreArchivo = getNombreArchivoASubir(fileBalMasas, "balMasas");
                // si todo salió bien, procedo
                if(nombreArchivo != null){
                    // si logré subir el archivo, guardo la ruta
                    if(JsfUtil.copyFile(nombreArchivo, fileBalMasas.getInputstream(), destino)){
                        JsfUtil.addSuccessMessage("El archivo " + fileBalMasas.getFileName() + " se ha subido al servidor con el nombre " + nombreArchivo + ". "
                                + "Por favor, actualice el campo de texto para ver la ruta completa");
                        declaracion.setRutaBalanceMasas(destino + nombreArchivo);
                        creatDeclaracionBorr();
                        if(pulgarDocumentos.equals("glyphicon-thumbs-down")) pulgarDocumentos = "glyphicon-thumbs-up";
                        activeIndex = 10;
                    }
                }else{
                    JsfUtil.addErrorMessage("No se pudo obtener el destino de la copia de Balance de masas.");
                }
            }catch(IOException e){
                JsfUtil.addErrorMessage("Hubo un error subiendo la copia de Balance de masas " + e.getLocalizedMessage());
            }
        }else{
            JsfUtil.addErrorMessage("Ya hay una ruta para el archvo de Balance de masas, por vavor elimine la existente y vuelva a intentarlo.");
        }
    }
    
    /**
     * Método para eliminar la copia del balance de masas
     */
    public void deleteBalance(){
        if(JsfUtil.deleteFile(declaracion.getRutaBalanceMasas())){
            declaracion.setRutaBalanceMasas(null);
            subeBalance = false;
            declaracion.setAdjuntaBalanceMasas(subeBalance);
            creatDeclaracionBorr();
            if(pulgarDocumentos.equals("glyphicon-thumbs-up")) pulgarDocumentos = "glyphicon-thumbs-down";
            activeIndex = 9;
            JsfUtil.addSuccessMessage("La copia de Balance de masas ha sido eliminada");
        }else{
            JsfUtil.addErrorMessage("No se pudo eliminar la copia de Balance de masas");
        }
    }   
    
    /**
     * Método para subir la copia de los Manifiestos y Certificados de disposición final.
     * El subdirectorio correspondiente, se llama "manifiestos"
     * @param event: evento de selección de archivo en el cliente
     * Se utiliza el método estático copyFile de la clase de utilidades
     */
    public void subirManifYCert(FileUploadEvent event){
        if(declaracion.getRutaManifYCert() == null){
            try{
                UploadedFile fileManifYCert = event.getFile();
                String destino = ResourceBundle.getBundle("/Bundle").getString("RutaArchivos") + ResourceBundle.getBundle("/Bundle").getString("SubdirManifYCert");
                // obtengo el nombre del archivo
                String nombreArchivo = getNombreArchivoASubir(fileManifYCert, "manifYCert");
                // si todo salió bien, procedo
                if(nombreArchivo != null){
                    // si logré subir el archivo, guardo la ruta
                    if(JsfUtil.copyFile(nombreArchivo, fileManifYCert.getInputstream(), destino)){
                        JsfUtil.addSuccessMessage("El archivo " + fileManifYCert.getFileName() + " se ha subido al servidor con el nombre " + nombreArchivo + ". "
                                + "Por favor, actualice el campo de texto para ver la ruta completa");
                        declaracion.setRutaManifYCert(destino + nombreArchivo);
                        creatDeclaracionBorr();
                        if(pulgarDocumentos.equals("glyphicon-thumbs-down")) pulgarDocumentos = "glyphicon-thumbs-up";
                        activeIndex = 10;
                    }
                }else{
                    JsfUtil.addErrorMessage("No se pudo obtener el destino de la copia de Manifiestos y Certificados de disposición final.");
                }
            }catch(IOException e){
                JsfUtil.addErrorMessage("Hubo un error subiendo la copia de Manifiestos y Certificados de disposición final " + e.getLocalizedMessage());
            }
        }else{
            JsfUtil.addErrorMessage("Ya hay una ruta para este archivo de Manifiestos y Certificados de disposición final, por vavor elimine la existente y vuelva a intentarlo.");
        }
    }    
    
    /**
     * Método para eliminar la copia del Manifiestos y Certificados de disposición final
     */
    public void deleteManifYCert(){
        if(JsfUtil.deleteFile(declaracion.getRutaManifYCert())){
            declaracion.setRutaManifYCert(null);
            subeManifYCert = false;
            declaracion.setAdjuntaManifYCert(subeManifYCert);
            creatDeclaracionBorr();
            if(pulgarDocumentos.equals("glyphicon-thumbs-up")) pulgarDocumentos = "glyphicon-thumbs-down";
            activeIndex = 9;
            JsfUtil.addSuccessMessage("La copia de Manifiestos y Certificados de disposición final ha sido eliminados.");
        }else{
            JsfUtil.addErrorMessage("No se pudo eliminar la copia de Manifiestos y Certificados de disposición final.");
        }
    }      
    
    /**
     * Método para subir la copia del Permiso de factibilidad
     * El subdirectorio correspondiente, se llama "permisosFact"
     * @param event: evento de selección de archivo en el cliente
     * Se utiliza el método estático copyFile de la clase de utilidades
     */
    public void subirPermisoFact(FileUploadEvent event){
        if(declaracion.getRutaPermisoFact() == null){
            try{
                UploadedFile filePermisosFact = event.getFile();
                String destino = ResourceBundle.getBundle("/Bundle").getString("RutaArchivos") + ResourceBundle.getBundle("/Bundle").getString("SubdirPermisoFact");
                // obtengo el nombre del archivo
                String nombreArchivo = getNombreArchivoASubir(filePermisosFact, "permisoFact");
                // si todo salió bien, procedo
                if(nombreArchivo != null){
                    // si logré subir el archivo, guardo la ruta
                    if(JsfUtil.copyFile(nombreArchivo, filePermisosFact.getInputstream(), destino)){
                        JsfUtil.addSuccessMessage("El archivo " + filePermisosFact.getFileName() + " se ha subido al servidor con el nombre " + nombreArchivo + ". "
                                + "Por favor, actualice el campo de texto para ver la ruta completa");
                        declaracion.setRutaPermisoFact(destino + nombreArchivo);
                        creatDeclaracionBorr();
                        if(pulgarDocumentos.equals("glyphicon-thumbs-down")) pulgarDocumentos = "glyphicon-thumbs-up";
                        activeIndex = 10;
                    }
                }else{
                    JsfUtil.addErrorMessage("No se pudo obtener el destino de la copia de Permiso de factibilidad.");
                }
            }catch(IOException e){
                JsfUtil.addErrorMessage("Hubo un error subiendo la copia de Permiso de factibilidad " + e.getLocalizedMessage());
            }
        }else{
            JsfUtil.addErrorMessage("Ya hay una ruta para este archivo de Permiso de factibilidad, por vavor elimine la existente y vuelva a intentarlo.");
        }
    }     
    
    /**
     * Método para eliminar la copia del Permiso de factibilidad
     */
    public void deletePermisoFact(){
        if(JsfUtil.deleteFile(declaracion.getRutaPermisoFact())){
            declaracion.setRutaPermisoFact(null);
            subePermisoFact = false;
            declaracion.setAdjuntaPermisoFact(subePermisoFact);
            creatDeclaracionBorr();
            if(pulgarDocumentos.equals("glyphicon-thumbs-up")) pulgarDocumentos = "glyphicon-thumbs-down";
            activeIndex = 9;
            JsfUtil.addSuccessMessage("La copia de Permiso de factibilidad ha sido eliminada.");
        }else{
            JsfUtil.addErrorMessage("No se pudo eliminar la copia de Permiso de factibilidad.");
        }
    }
    
    /**
     * Método para subir la copia del Inicio de trámite de Factibilidad
     * El subdirectorio correspondiente, se llama "inicioFact"
     * @param event: evento de selección de archivo en el cliente
     * Se utiliza el método estático copyFile de la clase de utilidades
     */
    public void subirInicoFact(FileUploadEvent event){
        if(declaracion.getRutaInicioFact() == null){
            try{
                UploadedFile filePermisosFact = event.getFile();
                String destino = ResourceBundle.getBundle("/Bundle").getString("RutaArchivos") + ResourceBundle.getBundle("/Bundle").getString("SubdirInicoFact");
                // obtengo el nombre del archivo
                String nombreArchivo = getNombreArchivoASubir(filePermisosFact, "inicioFact");
                // si todo salió bien, procedo
                if(nombreArchivo != null){
                    // si logré subir el archivo, guardo la ruta
                    if(JsfUtil.copyFile(nombreArchivo, filePermisosFact.getInputstream(), destino)){
                        JsfUtil.addSuccessMessage("El archivo " + filePermisosFact.getFileName() + " se ha subido al servidor con el nombre " + nombreArchivo + ". "
                                + "Por favor, actualice el campo de texto para ver la ruta completa");
                        declaracion.setRutaInicioFact(destino + nombreArchivo);
                        creatDeclaracionBorr();
                        if(pulgarDocumentos.equals("glyphicon-thumbs-down")) pulgarDocumentos = "glyphicon-thumbs-up";
                        activeIndex = 10;
                    }
                }else{
                    JsfUtil.addErrorMessage("No se pudo obtener el destino de la copia de Inicio de trámite de Factibilidad.");
                }
            }catch(IOException e){
                JsfUtil.addErrorMessage("Hubo un error subiendo la copia de Inicio de trámite de Factibilidad " + e.getLocalizedMessage());
            }
        }else{
            JsfUtil.addErrorMessage("Ya hay una ruta para este archivo de Inicio de trámite de Factibilidad, por vavor elimine la existente y vuelva a intentarlo.");
        }
    } 
    
    /**
     * Método para eliminar la copia del Inicio de trámite de Factibilidad
     */
    public void deleteInicioFact(){
        if(JsfUtil.deleteFile(declaracion.getRutaInicioFact())){
            declaracion.setRutaInicioFact(null);
            subeInicioFact = false;
            declaracion.setAdjuntaInicoFact(subeInicioFact);
            creatDeclaracionBorr();
            if(pulgarDocumentos.equals("glyphicon-thumbs-up")) pulgarDocumentos = "glyphicon-thumbs-down";
            activeIndex = 9;
            JsfUtil.addSuccessMessage("La copia de Inicio de trámite de Factibilidad ha sido eliminada.");
        }else{
            JsfUtil.addErrorMessage("No se pudo eliminar la copia de Inicio de trámite de Factibilidad.");
        }
    }    
    
    /**
     * Método para subir la copia del Croquis
     * El subdirectorio correspondiente, se llama "croquis"
     * @param event: evento de selección de archivo en el cliente
     * Se utiliza el método estático copyFile de la clase de utilidades
     */
    public void subirCroquis(FileUploadEvent event){
        if(declaracion.getRutaCroquis() == null){
            try{
                UploadedFile filePermisosFact = event.getFile();
                String destino = ResourceBundle.getBundle("/Bundle").getString("RutaArchivos") + ResourceBundle.getBundle("/Bundle").getString("SubdirCroquis");
                // obtengo el nombre del archivo
                String nombreArchivo = getNombreArchivoASubir(filePermisosFact, "croquis");
                // si todo salió bien, procedo
                if(nombreArchivo != null){
                    // si logré subir el archivo, guardo la ruta
                    if(JsfUtil.copyFile(nombreArchivo, filePermisosFact.getInputstream(), destino)){
                        JsfUtil.addSuccessMessage("El archivo " + filePermisosFact.getFileName() + " se ha subido al servidor con el nombre " + nombreArchivo + ". "
                                + "Por favor, actualice el campo de texto para ver la ruta completa");
                        declaracion.setRutaCroquis(destino + nombreArchivo);
                        creatDeclaracionBorr();
                        if(pulgarDocumentos.equals("glyphicon-thumbs-down")) pulgarDocumentos = "glyphicon-thumbs-up";
                        activeIndex = 10;
                    }
                }else{
                    JsfUtil.addErrorMessage("No se pudo obtener el destino de la copia del Croquis.");
                }
            }catch(IOException e){
                JsfUtil.addErrorMessage("Hubo un error subiendo la copia del Croquis" + e.getLocalizedMessage());
            }
        }else{
            JsfUtil.addErrorMessage("Ya hay una ruta para este archivo de Croquis, por vavor elimine la existente y vuelva a intentarlo.");
        }
    }   
    
    /**
     * Método para eliminar la copia del Croquis
     */
    public void deleteCroquis(){
        if(JsfUtil.deleteFile(declaracion.getRutaCroquis())){
            declaracion.setRutaCroquis(null);
            subeCroquis = false;
            declaracion.setAdjuntaCroquis(subeCroquis);
            creatDeclaracionBorr();
            if(pulgarDocumentos.equals("glyphicon-thumbs-up")) pulgarDocumentos = "glyphicon-thumbs-down";
            activeIndex = 9;
            JsfUtil.addSuccessMessage("La copia del Croquis ha sido eliminada.");
        }else{
            JsfUtil.addErrorMessage("No se pudo eliminar la copia del Croquis.");
        }
    }  
    
    /**
     * Método para subir la copia del Certificado de retiro y disposición final de las descargas
     * El subdirectorio correspondiente, se llama "certRetiros"
     * @param event: evento de selección de archivo en el cliente
     * Se utiliza el método estático copyFile de la clase de utilidades
     */
    public void subirCertRetiroYDispFinal(FileUploadEvent event){
        if(declaracion.getRutaCertRetiroYDispFinal() == null){
            try{
                UploadedFile filePermisosFact = event.getFile();
                String destino = ResourceBundle.getBundle("/Bundle").getString("RutaArchivos") + ResourceBundle.getBundle("/Bundle").getString("SubdirCertRetiroYDispFinal");
                // obtengo el nombre del archivo
                String nombreArchivo = getNombreArchivoASubir(filePermisosFact, "certRetiros");
                // si todo salió bien, procedo
                if(nombreArchivo != null){
                    // si logré subir el archivo, guardo la ruta
                    if(JsfUtil.copyFile(nombreArchivo, filePermisosFact.getInputstream(), destino)){
                        JsfUtil.addSuccessMessage("El archivo " + filePermisosFact.getFileName() + " se ha subido al servidor con el nombre " + nombreArchivo + ". "
                                + "Por favor, actualice el campo de texto para ver la ruta completa");
                        declaracion.setRutaCertRetiroYDispFinal(destino + nombreArchivo);
                        creatDeclaracionBorr();
                        if(pulgarDocumentos.equals("glyphicon-thumbs-down")) pulgarDocumentos = "glyphicon-thumbs-up";
                        activeIndex = 10;
                    }
                }else{
                    JsfUtil.addErrorMessage("No se pudo obtener el destino de la copia del Certificado de retiro y disposición final de las descargas.");
                }
            }catch(IOException e){
                JsfUtil.addErrorMessage("Hubo un error subiendo la copia del Certificado de retiro y disposición final de las descargas" + e.getLocalizedMessage());
            }
        }else{
            JsfUtil.addErrorMessage("Ya hay una ruta para este archivo de Certificado de retiro y disposición final de las descargas, por vavor elimine la existente y vuelva a intentarlo.");
        }
    }       
    
    /**
     * Método para eliminar la copia del Certificado de retiro y disposición final de las descargas
     */
    public void deleteCertRetiroYDispFinal(){
        if(JsfUtil.deleteFile(declaracion.getRutaCertRetiroYDispFinal())){
            declaracion.setRutaCertRetiroYDispFinal(null);
            subeCertRetiroYDispFinal = false;
            declaracion.setAdjuntaCertRetiroYDispFinal(subeCertRetiroYDispFinal);
            creatDeclaracionBorr();
            if(pulgarDocumentos.equals("glyphicon-thumbs-up")) pulgarDocumentos = "glyphicon-thumbs-down";
            activeIndex = 9;
            JsfUtil.addSuccessMessage("La copia del Certificado de retiro y disposición final de las descargas.");
        }else{
            JsfUtil.addErrorMessage("No se pudo eliminar la copia del Certificado de retiro y disposición final de las descargas.");
        }
    }      
    
    /**
     * Método para subir la copia del Protocolo de caracterización de barros
     * El subdirectorio correspondiente, se llama "protocolos"
     * @param event: evento de selección de archivo en el cliente
     * Se utiliza el método estático copyFile de la clase de utilidades
     */
    public void subirProtocolos(FileUploadEvent event){
        if(declaracion.getRutaProtocolo() == null){
            try{
                UploadedFile filePermisosFact = event.getFile();
                String destino = ResourceBundle.getBundle("/Bundle").getString("RutaArchivos") + ResourceBundle.getBundle("/Bundle").getString("SubdirProtocolos");
                // obtengo el nombre del archivo
                String nombreArchivo = getNombreArchivoASubir(filePermisosFact, "protocolos");
                // si todo salió bien, procedo
                if(nombreArchivo != null){
                    // si logré subir el archivo, guardo la ruta
                    if(JsfUtil.copyFile(nombreArchivo, filePermisosFact.getInputstream(), destino)){
                        JsfUtil.addSuccessMessage("El archivo " + filePermisosFact.getFileName() + " se ha subido al servidor con el nombre " + nombreArchivo + ". "
                                + "Por favor, actualice el campo de texto para ver la ruta completa");
                        declaracion.setRutaProtocolo(destino + nombreArchivo);
                        creatDeclaracionBorr();
                        if(pulgarDocumentos.equals("glyphicon-thumbs-down")) pulgarDocumentos = "glyphicon-thumbs-up";
                        activeIndex = 10;
                    }
                }else{
                    JsfUtil.addErrorMessage("No se pudo obtener el destino de la copia del Protocolo de caracterización de barros.");
                }
            }catch(IOException e){
                JsfUtil.addErrorMessage("Hubo un error subiendo la copia del Protocolo de caracterización de barros" + e.getLocalizedMessage());
            }
        }else{
            JsfUtil.addErrorMessage("Ya hay una ruta para este archivo de Protocolo de caracterización de barros, por vavor elimine la existente y vuelva a intentarlo.");
        }
    }           
    
    /**
     * Método para eliminar la copia del Protocolo de caracterización de barros
     */
    public void deleteProtocolos(){
        if(JsfUtil.deleteFile(declaracion.getRutaProtocolo())){
            declaracion.setRutaProtocolo(null);
            subeProtocolo = false;
            declaracion.setAdjuntaProtocolo(subeProtocolo);
            creatDeclaracionBorr();
            if(pulgarDocumentos.equals("glyphicon-thumbs-up")) pulgarDocumentos = "glyphicon-thumbs-down";
            activeIndex = 9;
            JsfUtil.addSuccessMessage("La copia del Protocolo de caracterización de barros.");
        }else{
            JsfUtil.addErrorMessage("No se pudo eliminar la copia del Protocolo de caracterización de barros.");
        }
    }      
    
    /**
     * Método para eliminar todas las copias de archivos subidas al servidor
     */
    public void deleteArchivos(){
        if(declaracion.getRutaBalanceMasas() != null) deleteBalance();
        if(declaracion.getRutaCertRetiroYDispFinal() != null) deleteCertRetiroYDispFinal();
        if(declaracion.getRutaCroquis() != null) deleteCroquis();
        if(declaracion.getRutaInicioFact() != null) deleteInicioFact();
        if(declaracion.getRutaManifYCert() != null) deleteManifYCert();
        if(declaracion.getRutaPermisoFact() != null) deletePermisoFact();
        if(declaracion.getRutaProtocolo() != null) deleteProtocolos();
        if(!eliminandoDec && declaracion.getActividades().size() > 0) creatDeclaracionBorr();
        else eliminandoDec = false;
        pulgarDocumentos = "glyphicon-thumbs-down";
        activeIndex = 9;
        JsfUtil.addSuccessMessage("Todos los archivos subidos se han eliminado.");
    }
    
    /**
     * Método para deshechar Firmante
     */
    public void deshecharFirmante(){
        // guardo el firmante que desvinculo
        firmanteSwap = new Firmante();
        firmanteSwap.setAdmin(firmante.getAdmin());
        if(firmante.getId() != null) firmanteSwap.setId(firmante.getId());
        if(firmante.getCuit() != 0) firmanteSwap.setCuit(firmante.getCuit());
        if(firmante.getDni() != 0) firmanteSwap.setDni(firmante.getDni());
        if(firmante.getDniLetra() != null) firmanteSwap.setDniLetra(firmante.getDniLetra());
        if(firmante.getIdRupFis() != null) firmanteSwap.setIdRupFis(firmante.getIdRupFis());
        firmanteSwap.setNombreYApellido(firmante.getNombreYApellido());
        // limpio el firmante vinculado
        est.setFirmante(null);
        firmante = new Firmante();
        firmDeshechado = true;
        JsfUtil.addSuccessMessage("El firmante se ha desvinculado del Establecimiento. Podrá seleccionar uno existente o registrar otro. "
                + "Podrá cancerlar la desvinculación mediante el botón 'Cancelar'");
    }
    
    /**
     * Método para cancelar el deehecho de Firmante
     */
    public void cancelarDesFirmante(){
        // recupero al firmante anterior
        firmante = new Firmante();
        firmante.setAdmin(firmanteSwap.getAdmin());
        if(firmanteSwap.getId() != null) firmante.setId(firmanteSwap.getId());
        if(firmanteSwap.getCuit() != 0) firmante.setCuit(firmanteSwap.getCuit());
        if(firmanteSwap.getDni() != 0) firmante.setDni(firmanteSwap.getDni());
        if(firmanteSwap.getDniLetra() != null) firmante.setDniLetra(firmanteSwap.getDniLetra());
        if(firmanteSwap.getIdRupFis() != null) firmante.setIdRupFis(firmanteSwap.getIdRupFis());
        firmante.setNombreYApellido(firmanteSwap.getNombreYApellido());
        // actualizo
        est.setFirmante(firmante);
        firmDeshechado = false;
        JsfUtil.addSuccessMessage("El firmante se ha vuelto a vincular con el Establecimiento.");
    } 
    
    /**
     * Método para actualizar los datos del firmante vinculado
     */
    public void editFirmVinculado(){
        boolean validaLetra = true;
        String mensajeError = "";
        
        // valido la letra del DNI si corresponde
        if(upFirmExist.getDniLetra() != null){
            if(upFirmExist.getDniLetra().length() > 1){
                validaLetra = false;
                mensajeError = mensajeError + "El DNI solo puede contener una letra.";
            }else{
                if(!upFirmExist.getDniLetra().equals("M") && !upFirmExist.getDniLetra().equals("m") 
                        && !upFirmExist.getDniLetra().equals("F") && !upFirmExist.getDniLetra().equals("f")){
                    validaLetra = false;
                    mensajeError = mensajeError + "La letra del DNI solo puede ser una 'F' o una 'M'.";
                }
            }
        }
        
        if(validaLetra){
            // valido que no esté repitiendo cuit o dni
            boolean valida = true;
            if(upFirmExist.getDni() > 0){
                if(!backendSrv.firXDniNoExiste(upFirmExist.getDni()) && est.getFirmante().getDni() != upFirmExist.getDni()) valida = false;
            }else{
                if(!backendSrv.firXCuitNoExiste(upFirmExist.getCuit()) && est.getFirmante().getCuit() != upFirmExist.getCuit()) valida = false;
            }
            
            if(valida){
                try{
                    // actualizo la entidad administrativa    
                    Date date = new Date(System.currentTimeMillis());
                    upFirmExist.getAdmin().setFechaModif(date);
                    upFirmExist.getAdmin().setUsExtModif(usLogueado);
                    // paso a mayúsculas el nombre y apellido
                    String tmpNombre = upFirmExist.getNombreYApellido();
                    upFirmExist.setNombreYApellido(tmpNombre.toUpperCase());
                    // actualizo
                    backendSrv.editFirmante(upFirmExist);
                }catch(Exception ex){
                    JsfUtil.addErrorMessage("Hubo un error actualizando los datos del Firmante. " + ex.getMessage());
                }
            }else{
                JsfUtil.addErrorMessage("El cuit o el dni del Firmante ya se encuentra registrado.");
            }
        }else{
            JsfUtil.addErrorMessage("No se pudieron validar los datos ingresados. " + mensajeError);
        }
    }  
    
    /**
     * Método para vincular firmante existente
     */
    public void vincularFirmante(){
        // actualizo
        firmante.setAdmin(newFirmanteSel.getAdmin());
        if(newFirmanteSel.getId() != null) firmante.setId(newFirmanteSel.getId());
        if(newFirmanteSel.getCuit() != 0) firmante.setCuit(newFirmanteSel.getCuit());
        if(newFirmanteSel.getDni() != 0) firmante.setDni(newFirmanteSel.getDni());
        if(newFirmanteSel.getDniLetra() != null) firmante.setDniLetra(newFirmanteSel.getDniLetra());
        if(newFirmanteSel.getIdRupFis() != null) firmante.setIdRupFis(newFirmanteSel.getIdRupFis());
        firmante.setNombreYApellido(newFirmanteSel.getNombreYApellido());
        newFirmanteSel = new Firmante();
        String estadoTrans = "";
        try{
            estadoTrans = estadoTrans + "Se actualizó el Firmante del Establecimiento.";
            est.setFirmante(firmante);
            JsfUtil.addSuccessMessage(estadoTrans);
        }catch(Exception ex){
            JsfUtil.addErrorMessage("Hubo un error vinculando al Firmante. " + ex.getMessage());
        }   
    }
    
    /**
     * Método para crear un nuevo firmante. Se guarda y se vincula al Establecimiento.
     * No se validará en el RUP.
     */
    public void createFirmante(){
        boolean validaLetra = true;
        String mensajeError = "";
        
        // valido la letra del DNI si corresponde
        if(newFirmanteReg.getDniLetra() != null){
            if(newFirmanteReg.getDniLetra().length() > 1){
                validaLetra = false;
                mensajeError = mensajeError + "El DNI solo puede contener una letra.";
            }else{
                if(!newFirmanteReg.getDniLetra().equals("M") && !newFirmanteReg.getDniLetra().equals("m") 
                        && !newFirmanteReg.getDniLetra().equals("F") && !newFirmanteReg.getDniLetra().equals("f")){
                    validaLetra = false;
                    mensajeError = mensajeError + "La letra del DNI solo puede ser una 'F' o una 'M'.";
                }
            }
        }
        
        if(validaLetra){
            // valido que no esté repitiendo cuit o dni
            boolean valida = true;
            if(newFirmanteReg.getDni() >= 0){
                if(!backendSrv.firXDniNoExiste(newFirmanteReg.getDni())) valida = false;
            }else{
                if(!backendSrv.firXCuitNoExiste(newFirmanteReg.getCuit())) valida = false;
            }
            
            if(valida){
                try{
                    // agrego la entidad administrativa      
                    Date date = new Date(System.currentTimeMillis());
                    AdminEntidad admEnt = new AdminEntidad();
                    admEnt.setFechaAlta(date);
                    admEnt.setHabilitado(true);
                    admEnt.setUsExtAlta(usLogueado);
                    newFirmanteReg.setAdmin(admEnt);
                    // paso a mayúsculas el nombre y apellido
                    String tmpNombre = newFirmanteReg.getNombreYApellido();
                    newFirmanteReg.setNombreYApellido(tmpNombre.toUpperCase());
                    // persisto
                    backendSrv.createFirmante(newFirmanteReg);
                    // vinculo al Establecimiento
                    if(newFirmanteReg.getCuit() > 0) firmante = backendSrv.getFirmanteByCuit(newFirmanteReg.getCuit());
                    else firmante = backendSrv.getFirmanteByDni(newFirmanteReg.getDni());
                    est.setFirmante(firmante);
                    newFirmanteReg = new Firmante();
                    lstFirmantes = backendSrv.getFirmantesAll();
                    JsfUtil.addSuccessMessage("Se creó el Firmante.");
                }catch(Exception ex){
                    JsfUtil.addErrorMessage("Hubo un error insertando el nuevo Firmante. " + ex.getMessage());
                }
            }else{
                JsfUtil.addErrorMessage("Ya existe un Firmante registrado con el CUIT o el DNI que intenta registrar.");
            }
        }else{
            JsfUtil.addErrorMessage("No se pudieron validar los datos ingresados. " + mensajeError);
        }
    }
    
    public void limpiarNewRegFirmante(){
        newFirmanteReg = new Firmante();
    }
    
    /**
     * Método para vincular definitivamente el nuevo Firmante (seleccionado o registrado)
     * al Establecimiento, como paso previo a la firma
     */
    public void vincularDefinitivo(){
        // valido que el Establecimiento llegue con un Firmante
        if(est.getFirmante() != null){
            String estTrans = "";
            try{
                Date date = new Date(System.currentTimeMillis());
                // verifico que no haya vuelto a poner al Firmante anterior
                if(!firmanteSwap.equals(est.getFirmante())){
                    HistorialFirmantes hisFirUltimo = new HistorialFirmantes();
                    HistorialFirmantes hisFirNuevo = new HistorialFirmantes();
                    // leo el último cambio de Firmante si lo hubiera
                    if(backendSrv.getUltimoFirmante(est) != null){
                        hisFirUltimo = backendSrv.getUltimoFirmante(est);
                    }
                    // seteo el nuevo historial
                    hisFirNuevo.setFirmanteActual(est.getFirmante());
                    hisFirNuevo.setActiva(true);
                    hisFirNuevo.setEstablecimiento(est);
                    hisFirNuevo.setFecha(date);
                    hisFirNuevo.setMotivo("Actualización durante el registro de la DJ.");
                    hisFirNuevo.setUsuarioExterno(usLogueado);
                    // si hubo una anterior la apago y seteo la razón social anterior
                    if(hisFirUltimo.getId() != null){
                        hisFirNuevo.setFirmanteAnterior(hisFirUltimo.getFirmanteActual());
                        hisFirUltimo.setActiva(false);
                        backendSrv.editHisFirmante(hisFirUltimo);
                        estTrans = estTrans + "Se actualizó el hisorial del Firmante saliente. ";
                    }else{
                        // si no había un registro de historial anterior, pongo como anterior al guardado como saliente
                        hisFirNuevo.setFirmanteAnterior(firmanteSwap);
                    }
                    // creo el historial del nuevo Firmante
                    backendSrv.createHisFirmante(hisFirNuevo);
                    estTrans = estTrans + "Se agregó el hisorial del Firmante seleccionado. ";
                }
                // si no hubo cambios solo actualizo el establecimiento
                Usuario us = backendSrv.getUsrByID(Long.valueOf(3)); 
                est.getAdmin().setFechaModif(date);
                est.getAdmin().setUsModif(us);
                backendSrv.editEstablecimiento(est);
                firmDeshechado = false;
                JsfUtil.addSuccessMessage(estTrans + "El Firmante ha sido vinculado al Establecimiento, puede firmar la Declaración.");
            }catch(Exception ex){
                JsfUtil.addErrorMessage("Hubo un error vinculando el nuevo Firmante al Establecimiento. " + ex.getMessage());
            }
        }else{
            JsfUtil.addErrorMessage("No se pudo detectar un Firmante para vincular al Establecimiento.");
        }
    }
    
    /**
     * Método para asignar el Firmante (ya vinculado al Esteblacimiento) a la declaración
     */
    public void firmarDeclaracion(){
        if(validarPasos()){
            declaracion.setFirmante(firmante);
            pulgarFirma = "glyphicon-thumbs-up";
            activeIndex = 11;
            JsfUtil.addSuccessMessage("La Declaración Jurada ha sido firmada por " + declaracion.getFirmante().getNombreYApellido() + ", podrá "
                    + "cancelar la firma si lo desea con el botón correspondiente.");
        }else{
            JsfUtil.addErrorMessage("No se pudo firmar la Declaración debido a que no se pudieron validar los pasos obligatorios");
        }

    }
    
    /**
     * Método para cancelar la firma de la Declaración
     */
    public void cancelarFirmaDeclaracion(){
        declaracion.setFirmante(null);
        pulgarFirma = "glyphicon-thumbs-down";
        activeIndex = 10;
        JsfUtil.addSuccessMessage("La firma de la Declaración ha sido cancelada, puede iniciar el proceso de firma nuevamente.");
    }
    
    /**
     * Método para forzar los flags de edición que pudieran quedar en true sin haber guardado cambios
     */
    public void resetEdit(){
        if(edita) edita = false;
        if(editaHijo) editaHijo = false;
    }
    
    /**
     * Método para cargar los datos del firmante a actualizar
     */
    public void prepareUpdateFirm(){
        upFirmExist = est.getFirmante();
    }
    
    /**
     * Método para limpiar el formulario de actualización
     */
    public void limpiarRegActFirmanteExist(){
        upFirmExist = new Firmante();
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
        docDec.setDescripcion("Descripción de prueba_mb_7");
        
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
    private boolean verificarVigenciaCaa(ExpedienteDrp exp){
        Date hoy = new Date(System.currentTimeMillis());
        XMLGregorianCalendar xmlHoy = toXMLGregorianCalendar(hoy);
        if(exp.getCaVencimiento().toGregorianCalendar().compareTo(xmlHoy.toGregorianCalendar()) > 0){
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
        int i = 0;

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
        
        if(!descarga.getTratamientos().isEmpty()){
            for(Tratamiento trat : descarga.getTratamientos()){
                if(trat.getClaveNombre() == tratamiento.getClaveNombre() && trat.getValor() == tratamiento.getValor()){
                    if(editaHijo){
                        if(i != ordenListHijo){
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
        
        if(abasto.getFuenteAbasto() == 0){
            result = false;
            JsfUtil.addErrorMessage("El Abasto debe especificar una fuente.");
        }else if(abasto.getFuenteAbasto() == 3 && abasto.getDescOtraFuente().equals("")){
            result = false;
            JsfUtil.addErrorMessage("Debe especificar la descripción de la otra fuente.");
        }
        
        if(abasto.getOrigenAbasto() == 0){
            result = false;
            JsfUtil.addErrorMessage("El Abasto debe especificar un origen.");
        }
        
        if(abasto.getCircuitoAbasto() == 0){
            result = false;
            JsfUtil.addErrorMessage("El Abasto debe especificar un circuito.");
        }
        
        if(abasto.getTipoAbasto() == null){
            result = false;
            JsfUtil.addErrorMessage("El Abasto debe especificar un tipo.");
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
        //Calendar horaInicial = Calendar.getInstance();
        //Calendar horaFinal = Calendar.getInstance();
        boolean result = true;
        int i = 0;
        
        /*
        // convierto los enteros a horas
        horaInicial.set(Calendar.HOUR, dia.getHorasInicDesc());
        horaInicial.set(Calendar.MINUTE, dia.getMinInicDesc());

        horaFinal.set(Calendar.HOUR, dia.getHorasFinDesc());
        horaFinal.set(Calendar.MINUTE, dia.getMinFinDesc());

        // valido que la hora de finalización sea anterior a la de incicio
        if(!horaFinal.after(horaInicial)){
            result = false;
            JsfUtil.addErrorMessage("La hora de finalización de la jornada laboral debe ser posterior a la de incicio.");
        }
        */
        
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
     * Metodo para validar los datos de un Día para su registro en el Horario de Vuelcos
     */
    private boolean validarDiaVuelco() {
        Calendar horaInicial = Calendar.getInstance();
        Calendar horaFinal = Calendar.getInstance();
        boolean result = true;
        int i = 0;
        
        if(diaVuelco.getHorasInicDesc() > 0 && diaVuelco.getHorasFinDesc() > 0){
            // convierto los enteros a horas
            horaInicial.set(Calendar.HOUR, diaVuelco.getHorasInicDesc());
            horaInicial.set(Calendar.MINUTE, diaVuelco.getMinInicDesc());
            
            horaFinal.set(Calendar.HOUR, diaVuelco.getHorasFinDesc());
            horaFinal.set(Calendar.MINUTE, diaVuelco.getMinFinDesc());
            
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
    
    /**
     * Método para limpiar un EstabDrp
     */
    private EstabDrp limpiarEstabDrp(EstabDrp drp){
        drp.setCaaFechaVenc(null);
        drp.setCaaNum(0);
        drp.setCaaVigente(false);
        drp.setDescDrp(null);
        drp.setExpAnio(0);
        drp.setExpNum(0);
        drp.setNombreDrp(null);
        drp.setTipoDrp(null);
        drp.setUsDrp(null);
        
        return drp;
    }

    /**
     * Método para validar un Turno
     * @return 
     */
    private boolean validarTurno() {
        //Calendar horaInicial = Calendar.getInstance();
        //Calendar horaFinal = Calendar.getInstance();
        boolean result = true;
        int i = 0;
 
        /*
        if(turno.getHorasInicio() > 0 && turno.getHorasFin() > 0){
            // convierto los enteros a horas
            horaInicial.set(Calendar.HOUR, turno.getHorasInicio());
            horaInicial.set(Calendar.MINUTE, turno.getMinInicio());
            
            horaFinal.set(Calendar.HOUR, turno.getHorasFin());
            horaFinal.set(Calendar.MINUTE, turno.getMinFin());
            
            // valido que la hora de finalización sea anterior a la de incicio
            if(!horaFinal.after(horaInicial)){
                result = false;
                JsfUtil.addErrorMessage("La hora de finalización del Turno debe ser posterior a la de incicio.");
            }
        }else{
            if(turno.getHorasFin() > 0){
                result = false;
                JsfUtil.addErrorMessage("Si no hay hora de incio del Turno no puede haber hora de finalización.");
            }
        }
        */
        if(!dia.getTurnos().isEmpty()){
            for(Turno trn : dia.getTurnos()){
                if(trn.getNumOrden() == turno.getNumOrden()){
                    if(edita){
                        if(i != ordenList){
                            result = false;
                            JsfUtil.addErrorMessage("Ya está registrado este Turno en el Día.");
                        }
                    }else{
                        result = false;
                        JsfUtil.addErrorMessage("Ya está registrado este Turno en el Día.");
                    }
                }
                i += 1;
            }
        }        
        return result;    
    }

    /**
     * Método para validar un Producto
     */
    private boolean validarProducto() {
        boolean result = true;
        int i = 0;
        
        if(producto.getCantidadAnual() == 0){
            result = false;
            JsfUtil.addErrorMessage("El Producto debe especificar una cantidad anual de producción.");
        }
        if(producto.getDescripcion() == null){
            result = false;
            JsfUtil.addErrorMessage("El Producto debe especificar una descripción.");
        }
        
        if(producto.getUnidad() == null){
            result = false;
            JsfUtil.addErrorMessage("El Producto debe especificar una unidad de medida.");
        }
        
        if(!lstProductos.isEmpty()){
            for(Producto prod : lstProductos){
                if(prod.getDescripcion().equals(producto.getDescripcion())){
                    if(edita){
                        if(i != ordenList){
                            result = false;
                            JsfUtil.addErrorMessage("Ya existe un Producto con esta descripción.");
                        }
                    }else{
                        result = false;
                        JsfUtil.addErrorMessage("Ya existe un Producto con esta descripción.");
                    }
                }
                i += 1;
            }
        }        
        return result;           
    }

    /**
     * Método para validar una Materia prima
     */    
    private boolean validarMateria() {
        boolean result = true;
        int i = 0;
        
        if(materia.getCantidadAnual() == 0){
            result = false;
            JsfUtil.addErrorMessage("La Materia debe especificar una cantidad anual de consumo.");
        }
        if(materia.getDescripcion() == null){
            result = false;
            JsfUtil.addErrorMessage("La Materia debe especificar una descripción.");
        }
        
        if(materia.getUnidad() == null){
            result = false;
            JsfUtil.addErrorMessage("La Materia debe especificar una unidad de medida.");
        }
        
        if(!producto.getMaterias().isEmpty()){
            for(Materia mat : producto.getMaterias()){
                if(mat.getDescripcion().equals(materia.getDescripcion())){
                    if(editaHijo){
                        if(i != ordenListHijo){
                            result = false;
                            JsfUtil.addErrorMessage("Ya existe una Materia con esta descripción.");
                        }
                    }else{
                        result = false;
                        JsfUtil.addErrorMessage("Ya existe una Materia con esta descripción.");
                    }
                }
                i += 1;
            }
        }        
        return result;           
    }
    
    /**
     * Método para setear un Transportista o un Operador DRP validado
     */
    private EstabDrp guardarEstabDrp(EstabDrp drp, ExpedienteDrp exp){
        drp.setExpNum(exp.getExpNumero());
        drp.setExpAnio(exp.getExpAnio());
        if(!exp.getCaNumero().equals("") || !exp.getCaNumero().equals("")) drp.setCaaNum(Integer.parseInt(exp.getCaNumero()));
        else drp.setCaaNum(0);
        if(exp.getCaVencimiento() != null) drp.setCaaFechaVenc(exp.getCaVencimiento().toGregorianCalendar().getTime());
        else drp.setCaaFechaVenc(null);
        if(exp.getCaVencimiento() != null) drp.setCaaVigente(verificarVigenciaCaa(exp));
        else drp.setCaaVigente(false);
        drp.setUsDrp(exp.getUsuario());
        drp.setNombreDrp(exp.getNombre());
        drp.setDescDrp(exp.getDescripcion());
        
        return drp;
    }

    /**
     * Método para limpiar los check de tratamientos de barros
     */
    private void limpiarTratBarros() {
        barro.setEstabilizacion(false);
        barro.setDeshidratacion(false);
        barro.setDesinfeccion(false);
        barro.setRetiraMediosPropios(false);
        barro.setOtrosTratamiento(false);
        barro.setRetiraOtrosMedios(false);
        barro.setDescOtroTratamiento("");
        barro.setDescOtrosMedios("");
        barro.setVolumen(0);
        barro.setPorcentaje(0);
        barro.setDatosTranspNoDrp("");
    }

    /**
     * Método para limpiar los check de destinos finales de los barros
     */
    private void limpiarDestBarros() {
        barro.setDestLandFarming(false);
        barro.setDestRellSanitario(false);
        barro.setDestInsumo(false);
        barro.setDestOtros(false);
        barro.setUsoComoInsumo("");
        barro.setEspecifOtroDestino("");
        barro.setDatosOperadorNoDrp("");
    }

    private boolean validarVuelco() {
        boolean valida = true;
        if(vuelco.isHorarioDiscontinuo()){
            if(!lstDiasVuelco.isEmpty()){
                valida = false;
                JsfUtil.addErrorMessage("Debe seleccionar 'Horario de Vuelco' para ingresar días.");
            }
        }
        
        if(vuelco.isInscripto()){
            if(vuelco.getExpNum() <= 0){
                valida = false;
                JsfUtil.addErrorMessage("Ha seleccionado la opción de solicitar los datos del Establecimiento en la DRP pero no hay un número de expediente seleccionado.");
            }
            if(vuelco.getExpAnio() <= 0){
                valida = false;
                JsfUtil.addErrorMessage("Ha seleccionado la opción de solicitar los datos del Establecimiento en la DRP pero no hay un año de expediente seleccionado.");
            }
        }
        
        /*
        if(vuelco.getOrgAutorizante() == null || vuelco.getOrgAutorizante().equals("")){
            valida = false;
            JsfUtil.addErrorMessage("Debe especificar el Organismo autorizante.");
        }
        
        if(vuelco.getCalleFactibilidad() == null || vuelco.getCalleFactibilidad().equals("")){
            valida = false;
            JsfUtil.addErrorMessage("Debe especificar la calle sobre la que autorizó la factibilidad.");
        }
        
        if(vuelco.getCaudalAutorizado() <= 0){
            valida = false;
            JsfUtil.addErrorMessage("El caudal autorizado debe ser mayor que 0.");
        }
        */
        
        return valida;
    }

    private String getNombreArchivoASubir(UploadedFile file, String prefijo){
        Date date = new Date(System.currentTimeMillis());
        String extension = file.getFileName().substring(file.getFileName().lastIndexOf(".") + 1);
        String sufijo = JsfUtil.getDateInString(date);
        String nombreArchivo = prefijo + "_" + usLogueado.getCude() + "_" + sufijo + "." + extension;
        return nombreArchivo;
    }

    private void setEstablecimiento(String cude) {
        Long partido, numEst;
        int crs, primerGuion, segundoGuion;
        Firmante fir;
        // desarmo el cude
        primerGuion = cude.indexOf("-");
        segundoGuion = cude.lastIndexOf("-");
        partido = Long.valueOf(cude.substring(0, primerGuion));
        numEst = Long.valueOf(cude.substring(primerGuion + 1, segundoGuion));
        crs = Integer.parseInt(cude.substring(segundoGuion + 1, cude.length()));
        // obtengo el establecimiento
        try{
            est = backendSrv.getEstablecimientoByCude(partido, numEst, crs);
        }catch(Exception ex){
            JsfUtil.addErrorMessage("Hubo un error obteniendo el Establecimiento del usuario.");
        }
    }

    private boolean validarBarros() {
        boolean result = true;
        
        if(barro.isRetiraTransDrp()){
            if(barro.getTranspDrp() == null){
                result = false;
                JsfUtil.addErrorMessage("Debe consignar un Transportista registrado en la DRP.");
            }
        }else{
            if(barro.getVolumen() == 0){
                result = false;
                JsfUtil.addErrorMessage("Debe consignar un volumen diario.");
            }
            if(barro.getPorcentaje() == 0){
                result = false;
                JsfUtil.addErrorMessage("Debe consignar un porcentaje de humedad.");
            }
        }
        
        if(barro.isTrataOpDrp()){
            if(barro.getOperadorDrp() == null){
                result = false;
                JsfUtil.addErrorMessage("Debe consignar un Operador registrado en la DRP.");
            }
        }
        return result;
    }

    private void instanciarDeclaracion() {
        // verifico si no tiene una Declaración en borrador
        DeclaracionJurada dec = backendSrv.getDeclaracionByCude(est.getCude());
        DeclaracionJurada dec_1 = backendSrv.getDeclaracionByCude(usLogueado.getCude()); 
        if(dec == null) dec = dec_1;
        if(dec != null){
            declaracion = dec;
            // seteo datos complementarios
            datosComReg = true;
            pulgarComp = "glyphicon-thumbs-up";
            docDec = declaracion.getDocumentacion();
            if(docDec == null){
                docDec = new DocDec();
            }
            lstActDec = declaracion.getActividades();
            lstFechaDec = declaracion.getFechasDeclaracion();
            lstCantPers = declaracion.getCantPersonal();
            lstSupDec = declaracion.getSuperficies();
            if(declaracion.getVuelco() != null){
                datosVuelco = true;
                pulgarVuelco = "glyphicon-thumbs-up";
                vuelco = declaracion.getVuelco();
                if(!vuelco.getDias().isEmpty()) lstDiasVuelco = vuelco.getDias();
                else lstDiasVuelco = new ArrayList<>();
                lstDescargas = vuelco.getDescargas();
                if(lstDescargas.isEmpty()){
                    datosDescargas = false;
                    pulgarDesc = "glyphicon-thumbs-down";
                }
                else{
                    datosDescargas = true;
                    pulgarDesc = "glyphicon-thumbs-up";
                }
            }
            else{
                datosVuelco = false;
                pulgarVuelco = "glyphicon-thumbs-down";
                datosDescargas = false;
                pulgarDesc = "glyphicon-thumbs-down";
                vuelco = new Vuelco();
                lstDiasVuelco = new ArrayList<>();
                lstDescargas = new ArrayList<>(); 
            }
            if(declaracion.getAbastecimiento() != null){
                abastecimiento = declaracion.getAbastecimiento();
                if(!declaracion.getAbastecimiento().getPozos().isEmpty()){
                    lstPozos = declaracion.getAbastecimiento().getPozos();
                    datosPozos = true;
                    pulgarPozos = "glyphicon-thumbs-up";
                }else{
                    lstPozos = new ArrayList<>();
                    datosPozos = false;
                    pulgarPozos = "glyphicon-thumbs-down";
                }
                if(!declaracion.getAbastecimiento().getAbastos().isEmpty()){
                    lstAbasto = declaracion.getAbastecimiento().getAbastos();
                    datosAbastos = true;
                    pulgarAbasto = "glyphicon-thumbs-up";
                }else{
                    lstAbasto = new ArrayList<>();
                    datosAbastos = false;
                    pulgarAbasto = "glyphicon-thumbs-down";
                }
            }else{
                abastecimiento = new Abastecimiento();
                lstPozos = new ArrayList<>();
                datosPozos = false;
                pulgarPozos = "glyphicon-thumbs-down";
                lstAbasto = new ArrayList<>();
                datosAbastos = false;
                pulgarAbasto = "glyphicon-thumbs-down";
            }
            if(declaracion.getHorario() != null){
                if(!declaracion.getHorario().getDias().isEmpty()){
                    lstDias = declaracion.getHorario().getDias();
                    datosHorarios = true;
                    pulgarHorario = "glyphicon-thumbs-up";
                }
            }else{
                horario = new Horario();
                lstDias = new ArrayList<>();
                datosHorarios = false;
                pulgarHorario = "glyphicon-thumbs-down";
            }
            if(!declaracion.getProductos().isEmpty()){
                lstProductos = declaracion.getProductos();
                datosProductos = true;
                pulgarProductos = "glyphicon-thumbs-up";
            }else{
                lstProductos = new ArrayList<>();
                datosProductos = false;
                pulgarProductos = "glyphicon-thumbs-down";
            }
            if(declaracion.getBarro() != null){
                barro = declaracion.getBarro();
                if(barro.isRetiraTransDrp()) habilitaTransDrp = false;
                else habilitaTransDrp = true;
                if(barro.isTrataOpDrp()) habilitaOpDrp = false;
                else habilitaOpDrp = true;
                datosBarros = true;
                pulgarBarros = "glyphicon-thumbs-up";
            }else{
                barro = new Barro();
                datosBarros = false;
                habilitaTransDrp = true;
                habilitaOpDrp = true;
                pulgarBarros = "glyphicon-thumbs-down";
            }
            if(declaracion.isAdjuntaBalanceMasas() || declaracion.isAdjuntaCertRetiroYDispFinal() || declaracion.isAdjuntaCroquis()
                    || declaracion.isAdjuntaInicoFact() || declaracion.isAdjuntaManifYCert() || declaracion.isAdjuntaPermisoFact() || declaracion.isAdjuntaProtocolo()){
                subeBalance = declaracion.isAdjuntaBalanceMasas();
                subeCertRetiroYDispFinal = declaracion.isAdjuntaCertRetiroYDispFinal();
                subeManifYCert = declaracion.isAdjuntaManifYCert();
                subePermisoFact = declaracion.isAdjuntaPermisoFact();
                subeInicioFact = declaracion.isAdjuntaInicoFact();
                subeCroquis = declaracion.isAdjuntaCroquis();
                subeProtocolo = declaracion.isAdjuntaProtocolo();
                pulgarDocumentos = "glyphicon-thumbs-up";
            }else{
                subeBalance = false;
                subeManifYCert = false;
                subePermisoFact = false;
                subeInicioFact = false;
                subeCroquis = false;
                subeCertRetiroYDispFinal = false;
                subeProtocolo = false;
                pulgarDocumentos = "glyphicon-thumbs-down";
            }
            if(declaracion.getClaveEstado() == 2) pulgarFirma = "glyphicon-thumbs-up";
        }else{
            declaracion = new DeclaracionJurada();
            datosComReg = false;
            pulgarComp = "glyphicon-thumbs-down";
            docDec = new DocDec();
            vuelco = new Vuelco();
            lstDiasVuelco = new ArrayList<>();
            pulgarVuelco = "glyphicon-thumbs-down";
            datosVuelco = false;
            lstDescargas = new ArrayList<>();
            datosDescargas = false;
            pulgarDesc = "glyphicon-thumbs-down";
            abastecimiento = new Abastecimiento();
            lstPozos = new ArrayList<>();
            datosPozos = false;
            pulgarPozos = "glyphicon-thumbs-down";
            lstAbasto = new ArrayList<>();
            datosAbastos = false;
            pulgarAbasto = "glyphicon-thumbs-down";
            horario = new Horario();
            lstDias = new ArrayList<>();
            datosHorarios = false;
            pulgarHorario = "glyphicon-thumbs-down";
            lstProductos = new ArrayList<>();
            datosProductos = false;
            pulgarProductos = "glyphicon-thumbs-down";
            barro = new Barro();
            datosBarros = false;
            pulgarBarros = "glyphicon-thumbs-down";
            pulgarDocumentos = "glyphicon-thumbs-down";
            habilitaTransDrp = true;
            habilitaOpDrp = true;
            subeBalance = false;
            subeManifYCert = false;
            subePermisoFact = false;
            subeInicioFact = false;
            subeCroquis = false;
            subeCertRetiroYDispFinal = false;
            subeProtocolo = false;
        }
    }

    private boolean validarPasos() {
        boolean result = true;
        if(!datosComReg){
            result = false;
            JsfUtil.addErrorMessage("Debe consignar los Datos Complementarios - Paso 1.");
        }
        if(!datosDescargas){
            result = false;
            JsfUtil.addErrorMessage("Debe consignar al menos una Descarga Cloacal - Paso 3.");
        }
        /* Validación cancelada por pedido de la DPyRA (14/07/2016)
        if(!datosPozos && !datosAbastos){
            result = false;
            JsfUtil.addErrorMessage("Debe consignar algún tipo de Abastecimiento de agua, sea de redo o Pozo - Pasos 4 o 5.");
        }
        */
        if(!datosHorarios){
            result = false;
            JsfUtil.addErrorMessage("Debe consignar los Horarios de trabajo - Paso 6.");
        }
        if(!datosProductos){
            result = false;
            JsfUtil.addErrorMessage("Debe consignar los Productos elaborados - Paso 7.");
        }    
        if(declaracion.getRutaCroquis() == null || declaracion.getRutaCroquis().equals("")){
            result = false;
            JsfUtil.addErrorMessage("Debe adjuntar al menos el Croquis de ubicación del Establecimiento con la/s descarga/s - Paso 9."); 
        }
        
        return result;
    }

    private boolean validarUltimoDigito() {
        String strCuit = String.valueOf(est.getCuit());
        String ultimo = strCuit.substring(strCuit.length() - 1);
        Integer iUltimo = Integer.valueOf(ultimo);
        //return iUltimo <= 4;
        //A partir del 01/07/2016 permito registrar al los cuit mayores que 4 (ult. dig.)
        // Debido a la prórroga también permito registrar a los cuit hasta 4
        // Rubén Incostante 29/07/2016
        return true;
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
