/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Actividad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Aforo;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Curso;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.DeclaracionJurada;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Firmante;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.HistorialDeclaraciones;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.HistorialFirmantes;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Operador;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Partido;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Recibo;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Rol;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.TipoAbasto;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.TipoCaudal;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Unidad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.UsuarioExterno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.ActividadFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.AforoFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.CursoFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.DeclaracionJuradaFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.EstablecimientoFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.FirmanteFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.HistorialDeclaracionesFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.HistorialFirmantesFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.OperadorFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.RolFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.UsuarioExternoFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.UsuarioFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.PartidoFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.TipoAbastoFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.TipoCaudalFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.UnidadFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.ReciboFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author rincostante
 */
@Stateless
@LocalBean
public class BackendSrv {
    
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private RolFacade rolFacade;
    @EJB
    private UsuarioExternoFacade usExtFacade;
    @EJB
    private PartidoFacade partidoFacade;    
    @EJB
    private FirmanteFacade firmanteFacade;    
    @EJB
    private EstablecimientoFacade estFacade;
    @EJB
    private CursoFacade cursoFacade;
    @EJB
    private UnidadFacade unidadFacade;
    @EJB
    private OperadorFacade operadorFacade;
    @EJB
    private AforoFacade aforoFacade;
    @EJB
    private TipoAbastoFacade tipoAbastoFacade;
    @EJB
    private TipoCaudalFacade tipoCaudalFacade;
    @EJB
    private ActividadFacade actividadFacade;
    @EJB
    private DeclaracionJuradaFacade decFacade;
    @EJB
    private HistorialFirmantesFacade historialFirmFacade;
    @EJB
    private HistorialDeclaracionesFacade historialDeclaFacade;
    
    @EJB
    private ReciboFacade reciboFacade;

    /******************************
     * Métodos para los Usuarios **
     ******************************/
    
    /**
     * Método para validar si existe un usuario con el nombre recibido
     * @param nombre
     * @return 
     */
    public boolean usrNoExiste(String nombre){
        return usuarioFacade.noExiste(nombre);
    }
    
    /**
     * Método para obtener los usuarios activos
     * @return 
     */
    public List<Usuario> getUsrActivos(){
        return usuarioFacade.getActivos();
    }
    
    /**
     * Método para obtener un usuario por su nombre
     * @param nombre
     * @return 
     */
    public Usuario getUsuario(String nombre){
        return usuarioFacade.getUsuario(nombre);
    }
    
    /**
     * Método para saber si el usuario está afectado a transacciones
     * @param id
     * @return 
     */
    public boolean usrNoTieneDependencias(Long id) {
        return usuarioFacade.noTieneDependencias(id);
    }
    
    /**
     * Método para obtener un usuario según una id. Se implementa para poder exponerlo como servicio.
     * @param id
     * @return 
     */
    public Usuario getUsrByID(Long id){
        return usuarioFacade.find(id);
    }
    
    /**
     * Método para insertar un nuevo usuario. Se implementa para poder exponerlo como servicio.
     * @param usr 
     */
    public void createUsuario(Usuario usr){
        usuarioFacade.create(usr);
    }
    
    /**
     * Método para editar un usuario existente. Se implementa para poder exponerlo como servicio.
     * @param usr 
     */
    public void editUsuario(Usuario usr){
        usuarioFacade.edit(usr);
    }
    
    /**
     * Método para obtener todos los usuarios. Se implementa para poder exponerlo como servicio.
     * @return 
     */
    public List<Usuario> getUsrAll(){
        return usuarioFacade.findAll();
    }    
    
    /***************************************
     * Métodos para los Usuarios Externos **
     ***************************************/
    
    /**
     * Método para validar si existe un usuario externo con el cude recibido
     * @param cude
     * @return 
     */
    public boolean usrExtNoExiste(String cude){
        return usExtFacade.noExiste(cude);
    }
    
    /**
     * Método para obtener los usuarios activos
     * @return 
     */
    public List<UsuarioExterno> getUsrExtActivos(){
        return usExtFacade.getActivos();
    }
    
    /**
     * Método para obtener un usuario externo por su cude
     * @param cude
     * @return 
     */
    public UsuarioExterno getUsuarioExt(String cude){
        return usExtFacade.getUsuario(cude);
    }
    
    /**
     * Método para validar un usuario externo, si valida, devuelve el usuario,
     * si no, devuleve nulo
     * @param cude
     * @param clave
     * @return 
     */
    public UsuarioExterno validarUsuarioExt(String cude, String clave){
        return usExtFacade.validar(cude, clave);
    }
    
    /**
     * Método para saber si el usuario externo está afectado a transacciones
     * @param id
     * @return 
     */
    public boolean usrExtNoTieneDependencias(Long id) {
        return usExtFacade.noTieneDependencias(id);
    }
    
    /**
     * Método para obtener un usuario externo según una id. Se implementa para poder exponerlo como servicio.
     * @param id
     * @return 
     */
    public UsuarioExterno getUsrExtByID(Long id){
        return usExtFacade.find(id);
    }
    
    /**
     * Método para insertar un nuevo usuario externo. Se implementa para poder exponerlo como servicio.
     * @param usr 
     */
    public void createUsuarioExterno(UsuarioExterno usr){
        usExtFacade.create(usr);
    }
    
    /**
     * Método para editar un usuario externo existente. Se implementa para poder exponerlo como servicio.
     * @param usr 
     */
    public void editUsuarioExterno(UsuarioExterno usr){
        usExtFacade.edit(usr);
    }
    
    /**
     * Método para obtener todos los usuarios externos. Se implementa para poder exponerlo como servicio.
     * @return 
     */
    public List<UsuarioExterno> getUsrExtAll(){
        return usExtFacade.findAll();
    }      
    
    
    /**************************
     * Método para los roles **
     **************************/

    /**
     * Método para saber si existe un rol con el nombre recibido
     * @param nombre
     * @return 
     */
    public boolean rolExiste(String nombre){
        return rolFacade.existe(nombre);
    }    
    
    /**
     * Método para saber si el rol está vinculado a algún usuario
     * @param id
     * @return 
     */
    public boolean rolNoTieneDependencias(Long id){
        return rolFacade.noTieneDependencias(id);
    }
    
    /**
     * Método para obtener los roles activos
     * @return 
     */
    public List<Rol> getRolActivos(){
        return rolFacade.getActivos();
    }
    
    /**
     * Método para obtener un Rol según su id. Se implementa para poder exponerlo como servicio.
     * @param id
     * @return 
     */
    public Rol getRolByID(Long id){
        return rolFacade.find(id);
    }
    
    /**
     * Método para crear un Rol. Se implementa para poder exponerlo como servicio.
     * @param rol 
     */
    public void createRol(Rol rol){
        rolFacade.create(rol);
    }
    
    /**
     * Método para editar un Rol. Se implementa para poder exponerlo como servicio.
     * @param rol 
     */
    public void editRol(Rol rol){
        rolFacade.edit(rol);
    }
    
    /**
     * Método para obtener todos los Roles. Se implementa para poder exponerlo como servicio.
     * @return 
     */
    public List<Rol> getRolAll(){
        return rolFacade.findAll();
    }
    
    
    /***************************
     ** Métodos para Partidos **
     ***************************/
           
    /**
     * Método para validar si existe un partido con el nombre recibido
     * @param nombre
     * @return 
     */
    public boolean partidoNoExiste(String nombre){
        return partidoFacade.noExiste(nombre);
    }
     
    /**
     * Método para insertar un nuevo partido. Se implementa para poder exponerlo como servicio.
     * @param par 
     */
    public void createPartido(Partido par){
        partidoFacade.create(par);
    }
    
    /**
     * Método para editar un Partido existente. Se implementa para poder exponerlo como servicio.
     * @param par 
     */
    public void editPartido(Partido par){
        partidoFacade.edit(par);
    }
          
    /**
     * Método para obtener un partido según un id. Se implementa para poder exponerlo como servicio.
     * @param id
     * @return 
     */
    public Partido getPartidoByID(Long id){
        return partidoFacade.find(id);
    }  
    
    /**
     * Método para obtener el Partido según el id del Registro Territorial
     * @param idRt
     * @return 
     */
    public Partido getPartidoByIdRt(Long idRt){
        return partidoFacade.getXIdRt(idRt);
    }
    
    /**
     * Método para obtener todos los Partidos. Se implementa para poder exponerlo como servicio.
     * @return 
     */
    public List<Partido> getPartidosAll(){
        return partidoFacade.findAll();
    }     
    
 
    /****************************
     ** Métodos para Firmantes **
     ****************************/

    
    /**
     * Método para validar si existe un firmante con el Registro Unico de personas físicas recibida
     * @param idRupFis
     * @return 
     */
    public boolean rupFisNoExiste(Long idRupFis){
        return firmanteFacade.noExiste(idRupFis);
    }
    
    /**
     * Método para validar que no existe un Firmante con el dni enviado.
     * @param dni
     * @return 
     */
    public boolean firXDniNoExiste(Long dni){
        return firmanteFacade.noExisteDni(dni);
    }    
    
    /**
     * Método para validar que no existe un Firmante con el cuit enviado.
     * @param cuit
     * @return 
     */
    public boolean firXCuitNoExiste(Long cuit){
        return firmanteFacade.noExisteCuit(cuit);
    }    
    
    /**
     * Método para obtener las Declaraciones  Juradas activas
     * @return 
     */
    public List<Firmante> getActivos(){
        return firmanteFacade.getActivos();
    }
    
    /**
     * Método para obtener un firmante según un id. Se implementa para poder exponerlo como servicio.
     * @param id
     * @return 
     */
    public Firmante getFirmanteByID(Long id){
        return firmanteFacade.find(id);
    }
    
    /**
     * Método para obtener el Firmante correspondiente al CUIT
     * @param cuit
     * @return 
     */
    public Firmante getFirmanteByCuit(Long cuit){
        return firmanteFacade.getByCuit(cuit);
    }
    
    /**
     * Método para obtener el Firmante correspondiente al DNI
     * @param dni
     * @return 
     */
    public Firmante getFirmanteByDni(Long dni){
        return firmanteFacade.getByDni(dni);
    }
    
    /**
     * Método para insertar un nuevo firmante. Se implementa para poder exponerlo como servicio.
     * @param fir 
     */
    public void createFirmante(Firmante fir){
        firmanteFacade.create(fir);
    }
    
    /**
     * Método para obtener el úlitmo firmante de un Establecimiento
     * @param est: Establecimiento del que se quiere obtener el último Firmante
     * @return: El historial del último activo.
     */
    public HistorialFirmantes getUltimoFirmante(Establecimiento est){
        return historialFirmFacade.getUltimoActivo(est);
    }
    
    /**
     * Método para insertar un nuevo Historial de Firmante. Se implementa para poder exponerlo como servicio.
     * @param hisFir 
     */
    public void createHisFirmante(HistorialFirmantes hisFir){
        historialFirmFacade.create(hisFir);
    }  
    
    /**
     * Método para editar un historial de Firmante. Se implementa para poder exponerlo como servicio.
     * @param hisFir 
     */
    public void editHisFirmante(HistorialFirmantes hisFir){
        historialFirmFacade.edit(hisFir);
    }    
    
    /**
     * Método para editar un firmante existente. Se implementa para poder exponerlo como servicio.
     * @param fir 
     */
    public void editFirmante(Firmante fir){
        firmanteFacade.edit(fir);
    }
    
    /**
     * Método para obtener todos los Firmantes. Se implementa para poder exponerlo como servicio.
     * @return 
     */
    public List<Firmante> getFirmantesAll(){
        return firmanteFacade.getByOrder();
    }     
    
    
    /***************************************
     * Métodos para los Usuarios Externos **
     ***************************************/
    
    /**
     * Método para insertar establecimientos
     * @param est 
     */
    public void createEstablecimiento(Establecimiento est){
        estFacade.create(est);
    }
    
    /**
     * Método para editar un establecimiento existente
     * @param est 
     */
    public void editEstablecimiento(Establecimiento est){
        estFacade.edit(est);
    }
    
    /**
     * Método para obtener un Establecimiento a partir del CUDE desintegrado en sus partes componentes
     * @param idPartido
     * @param numEst
     * @param crs
     * @return 
     */
    public Establecimiento getEstablecimientoByCude(Long idPartido, Long numEst, int crs){
        return estFacade.getByCude(idPartido, numEst, crs);
    }
    
    /**
     * Método que obtiene todos los Establecimientos vinculados al cuit recibido
     * @param cuit
     * @return 
     */
    public List<Establecimiento> getEstablecimientosByCuit(Long cuit){
        return estFacade.getByCuit(cuit);
    }
    
    /**
     * Método que devuelve todos los Establecimientos habilitados
     * @return 
     */
    public List<Establecimiento> getEstablecimientos(){
        return estFacade.getHabilitados();
    }
    
    public Establecimiento getEstablecimientoById(Long id){
        return estFacade.find(id);
    }    
    
    /**
     * Metodo que valida si existe o no un Establecimiento con el idRupEst enviado
     * @param idRupEst
     * @return 
     */
    public boolean existeEstByIdRupEst(Long idRupEst){
        return estFacade.existeIdRupEst(idRupEst);
    }
    
    /************************************
     * Métodos para los Cursos de agua **
     ************************************/    
    
    /**
     * Método para insertar Cursos de agua
     * @param curso 
     */
    public void createCurso(Curso curso){
        cursoFacade.create(curso);
    }
    
    /**
     * Método para editar un Cursos de agua existentes
     * @param curso 
     */
    public void editCurso(Curso curso){
        cursoFacade.edit(curso);
    }
    
    /**
     * Método para obtener un Curso según su id
     * @param id
     * @return 
     */
    public Curso getCursoByID(Long id){
        return cursoFacade.find(id);
    }    
    
    /**
     * Método para obtener todos los Cursos de agua. Se implementa para poder exponerlo como servicio.
     * @return 
     */
    public List<Curso> getCursosAll(){
        return cursoFacade.getByOrder();
    } 
    
    
    /*************************************************************
     * Métodos para las Unidades de Materias primas y Productos **
     *************************************************************/
    
    /**
     * Método para insertar Unidades de mediad de Materias primas y Productos
     * @param unidad
     */
    public void createUnidad(Unidad unidad){
        unidadFacade.create(unidad);
    }
    
    /**
     * Método para editar una Unidad de mediad de Materias primas y Productos existentes
     * @param unidad 
     */
    public void editUnidad(Unidad unidad){
        unidadFacade.edit(unidad);
    }
    
    /**
     * Método para obtener una Unidad de mediad de Materias primas y Productos según su id
     * @param id
     * @return 
     */
    public Unidad getUnidadByID(Long id){
        return unidadFacade.find(id);
    }    
    
    /**
     * Método para obtener todos las Unidades de mediad de Materias primas y Producto. 
     * Se implementa para poder exponerlo como servicio.
     * @return 
     */
    public List<Unidad> getUnidadesAll(){
        return unidadFacade.findAll();
    } 
    
    
    /********************************************
     * Métodos para los Operadores de residuos **
     ********************************************/
    
    /**
     * Método para insertar Operadores de residuos
     * @param operador
     */
    public void createOperador(Operador operador){
        operadorFacade.create(operador);
    }
    
    /**
     * Método para editar un Operador de residuos
     * @param operador 
     */
    public void editOperador(Operador operador){
        operadorFacade.edit(operador);
    }
    
    /**
     * Método para obtener un Operador de residuos según su id
     * @param id
     * @return 
     */
    public Operador getOperadorByID(Long id){
        return operadorFacade.find(id);
    }    
    
    /**
     * Método para obtener todos los Operadores de residuos
     * Se implementa para poder exponerlo como servicio.
     * @return 
     */
    public List<Operador> getOperadoresAll(){
        return operadorFacade.findAll();
    }   
    
    
    /**************************************
     * Métodos para los métodos de Aforo **
     **************************************/
    
    /**
     * Método para insertar métodos de Aforo
     * @param aforo
     */
    public void createAforo(Aforo aforo){
        aforoFacade.create(aforo);
    }
    
    /**
     * Método para editar un método de Aforo
     * @param aforo 
     */
    public void editAforo(Aforo aforo){
        aforoFacade.edit(aforo);
    }
    
    /**
     * Método para obtener un método de Aforo según su id
     * @param id
     * @return 
     */
    public Aforo getAforoByID(Long id){
        return aforoFacade.find(id);
    }    
    
    /**
     * Método para obtener todos los métodos de Aforo
     * Se implementa para poder exponerlo como servicio.
     * @return 
     */
    public List<Aforo> getAforosAll(){
        return aforoFacade.getByOrder();
    }    
    
    
    /*************************************
     * Métodos para los Tipos de Abasto **
     *************************************/
    
    /**
     * Método para insertar Tipos de Abasto
     * @param tipoAbasto
     */
    public void createTipoAbasto(TipoAbasto tipoAbasto){
        tipoAbastoFacade.create(tipoAbasto);
    }
    
    /**
     * Método para editar un Tipo de Abasto
     * @param tipoAbasto 
     */
    public void editTipoAbasto(TipoAbasto tipoAbasto){
        tipoAbastoFacade.edit(tipoAbasto);
    }
    
    /**
     * Método para obtener un Tipo de Abasto según su id
     * @param id
     * @return 
     */
    public TipoAbasto getTipoAbastoByID(Long id){
        return tipoAbastoFacade.find(id);
    }    
    
    /**
     * Método para obtener todos los Tipos de Abasto
     * Se implementa para poder exponerlo como servicio.
     * @return 
     */
    public List<TipoAbasto> getTipoAbastoAll(){
        return tipoAbastoFacade.findAll();
    }  
    
    
    /*************************************
     * Métodos para los Tipos de Caudal **
     *************************************/
    
    /**
     * Método para insertar Tipos de Caudal
     * @param tipoCaudal
     */
    public void createTipoCaudal(TipoCaudal tipoCaudal){
        tipoCaudalFacade.create(tipoCaudal);
    }
    
    /**
     * Método para editar un Tipo de Caudal
     * @param tipoCaudal 
     */
    public void editTipoCaudal(TipoCaudal tipoCaudal){
        tipoCaudalFacade.edit(tipoCaudal);
    }
    
    /**
     * Método para obtener un Tipo de Caudal según su id
     * @param id
     * @return 
     */
    public TipoCaudal getTipoCaudalByID(Long id){
        return tipoCaudalFacade.find(id);
    }    
    
    /**
     * Método para obtener todos los Tipos de Caudal
     * Se implementa para poder exponerlo como servicio.
     * @return 
     */
    public List<TipoCaudal> getTipoCaudalAll(){
        return tipoCaudalFacade.findAll();
    }       
    
    
    /*********************************
     * Métodos para las Actividades **
     *********************************/
    
    /**
     * Método para insertar una Actividad
     * @param actividad
     */
    public void createActividad(Actividad actividad){
        actividadFacade.create(actividad);
    }
    
    /**
     * Método para editar una Actividad
     * @param actividad 
     */
    public void editActividad(Actividad actividad){
        actividadFacade.edit(actividad);
    }
    
    /**
     * Método para obtener una Actividad según su id
     * @param id
     * @return 
     */
    public Actividad getActividadByID(Long id){
        return actividadFacade.find(id);
    }    
    
    /**
     * Método para obtener todas las Actividades
     * Se implementa para poder exponerlo como servicio.
     * @return 
     */
    public List<Actividad> getActividadAll(){
        return actividadFacade.findAll();
    }       
    
    
    /*******************************************
     * Métodos para las Declaraciones Juradas **
     *******************************************/
    
    /**
     * Método para insertar una DeclaracionJurada
     * @param decJurada
     */
    public void createDeclaracion(DeclaracionJurada decJurada){
        decFacade.create(decJurada);
    }
    
    /**
     * Método para editar una DeclaracionJurada
     * @param decJurada 
     */
    public void editDeclaracion(DeclaracionJurada decJurada){
        decFacade.edit(decJurada);
    }
    
    /**
     * Método para obtener una DeclaracionJurada según su id
     * @param id
     * @return 
     */
    public DeclaracionJurada getDeclaracionByID(Long id){
        return decFacade.find(id);
    } 
    
    /**
     * Método para obtener la Declaración jurada en proceso de carga según el CUDE
     * @param cude
     * @return 
     */
    public DeclaracionJurada getDecBorrador(String cude){
        return decFacade.getEnProceso(cude);
    }
    
    /**
     * Metodo para obteber la Declaración jurada del CUDE
     * @param cude
     * @return 
     */
    public DeclaracionJurada getDeclaracionByCude(String cude){
        return decFacade.getByCude(cude);
    }
    
    /**
     * Método para obtener todas las Declaraciones Juradas
     * Se implementa para poder exponerlo como servicio.
     * @return 
     */
    public List<DeclaracionJurada> getDeclaracionAll(){
        return decFacade.findAll();
    }     
    
    /**
     * Método para obtener la última Declaración presentada de un Establecimiento
     * @param est: Establecimiento del que se quiere obtener la última Declaración
     * @return: El historial del último activo.
     */
    public HistorialDeclaraciones getUltimaDeclaracion(Establecimiento est){
        return historialDeclaFacade.getUltimoActivo(est);
    }
    
    /**
     * Método para obtener el Historial de la Declaración activa según la declaración
     * @param dec
     * @return 
     */
    public HistorialDeclaraciones getHistorialDecByDec(DeclaracionJurada dec){
        return historialDeclaFacade.getByDeclaracion(dec);
    }
    
    /**
     * Método para insertar un nuevo Historial de Declaración. Se implementa para poder exponerlo como servicio.
     * @param hisDecla 
     */
    public void createHisDeclaracion(HistorialDeclaraciones hisDecla){
        historialDeclaFacade.create(hisDecla);
    }  
    
    /**
     * Método para editar un historial de Declaración. Se implementa para poder exponerlo como servicio.
     * @param hisDecla 
     */
    public void editHisDeclaracion(HistorialDeclaraciones hisDecla){
        historialDeclaFacade.edit(hisDecla);
    }  
    
    /**
     * Método para obtener la Declaración recién ingresada
     * @param cude: CUDE del Establecimiento vinculado a la Declaración insertada
     * @return 
     */
    public Integer obtenerDeclaReciente(String cude){
        return decFacade.getInsertada(cude);
    }
    
    public void deleteDeclaBorrador(DeclaracionJurada dec){
        decFacade.remove(dec);
    }
    
    public Integer getUltimoIdRecibo(){
        return reciboFacade.getUltimo();
    }
    
    public Recibo getReciboByCodigo(String codigo){
        return reciboFacade.getByCodigo(codigo);
    }
    
    public void createRecibo(Recibo rec){
        reciboFacade.create(rec);
    }
}
