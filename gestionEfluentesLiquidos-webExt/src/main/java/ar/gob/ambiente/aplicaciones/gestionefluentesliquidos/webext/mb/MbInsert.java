

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Actividad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.ActividadDec;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.AdminEntidad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Aforo;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Curso;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.DeclaracionJurada;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.DocDec;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Firmante;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Inmueble;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Operador;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Partido;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.TipoAbasto;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.TipoCaudal;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Unidad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;

/**
 * Bean de respaldo para hacer inserciones paramétricas
 * @author rincostante
 */
public class MbInsert implements Serializable{

    private Partido partido;
    private Firmante firmante;
    private Establecimiento est;
    private DeclaracionJurada dec;
    
    @EJB
    private BackendSrv backendSrv;
    
    public MbInsert() {
    }

    
    /**********************
     * métodos de acceso **
     **********************/
    
    public Firmante getFirmante() {
        return firmante;
    }

    public void setFirmante(Firmante firmante) {
        this.firmante = firmante;
    }
    
    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }
    
    
    /************************
     * Método de inserción **
     ************************/
    public void createPartido(){
        partido = new Partido();
        partido.setNombre("LA MATANZA");
        partido.setIdDpyra(Long.valueOf(155));
        partido.setIdRt(Long.valueOf(128));
        
        backendSrv.createPartido(partido);
    }
    
    public void createFirmante(){
        Usuario us = backendSrv.getUsrByID(Long.valueOf(1));

        Date date = new Date(System.currentTimeMillis());
        AdminEntidad admEnt = new AdminEntidad();
        admEnt.setFechaAlta(date);
        admEnt.setHabilitado(true);
        admEnt.setUsAlta(us);
        
        firmante = new Firmante();
        firmante.setAdmin(admEnt);
        firmante.setCuit(Long.valueOf("20042863719"));
        firmante.setIdRupFis(Long.valueOf(14));
        firmante.setNombreYApellido("TAMASI, OSCAR ALBERTO");
        
        backendSrv.createFirmante(firmante);
    }
    
    public void createEstablecimiento(){
        Usuario us = backendSrv.getUsrByID(Long.valueOf(1));
        Firmante frm = backendSrv.getFirmanteByID(Long.valueOf(1));
        Partido part = backendSrv.getPartidoByID(Long.valueOf(1));
        
        Date date = new Date(System.currentTimeMillis());
        AdminEntidad admEnt = new AdminEntidad();
        admEnt.setFechaAlta(date);
        admEnt.setHabilitado(true);
        admEnt.setUsAlta(us);
        
        Inmueble inm = new Inmueble();
        inm.setNomCatastral("CIRC IV - SEC 6 - MZ 0068 - PARC 00020");
        inm.setPartInmob("055-121254-8 ");
        inm.setPersonalFabrica(3);
        inm.setPersonalOficina(1);
        inm.setRadioServido(true);
        inm.setSupCubierta(367);
        inm.setSupLibre(55);

        est = new Establecimiento();
        est.setAdmin(admEnt);
        est.setCrs(1);
        est.setCuit(Long.valueOf("30501018453"));
        est.setFirmante(frm);
        est.setIdRupEst(Long.valueOf(96));
        est.setIdRupRaz(Long.valueOf(54));
        est.setInmueble(inm);
        est.setNumero(Long.valueOf(5555));
        est.setPartido(part);
        est.setProcProduct("Lavadero de autos");
        est.setRazonSocial("UNIONBAT SOCIEDAD ANONIMA");
        est.setRzJuridica(true);
        
        backendSrv.createEstablecimiento(est);
    }
    
    public void createCurso(){
        Curso curso = new Curso();
        curso.setNombre("A. CILDAÑEZ");
        curso.setCuenca(Curso.getLST_CUENCAS().get(0));
        backendSrv.createCurso(curso);
    }
    
    public void createUnidad(){
        Unidad unidad = new Unidad();
        unidad.setNombre("Tn");
        backendSrv.createUnidad(unidad);
    }
    
    public void createOperador(){
        Usuario us = backendSrv.getUsrByID(Long.valueOf(1));
        
        Date date = new Date(System.currentTimeMillis());
        AdminEntidad admEnt = new AdminEntidad();
        admEnt.setFechaAlta(date);
        admEnt.setHabilitado(true);
        admEnt.setUsAlta(us);
        
        Operador operador = new Operador();
        operador.setAdmin(admEnt);
        operador.setCuit(Long.valueOf("30691903334"));
        operador.setIdSimel(Long.valueOf(4));
        operador.setRazonSocial("TECMA SAN JUAN S.A.");
        operador.setUsSimel("30691903334/2");
        backendSrv.createOperador(operador);
    }
    
    public void createAforo(){
        Aforo aforo = new Aforo();
        aforo.setNombre("Tubo Venturi");
        backendSrv.createAforo(aforo);
    }
    
    public void createTipoAbasto(){
        TipoAbasto tipoAbasto = new TipoAbasto();
        tipoAbasto.setNombre("INDUSTRIAL DE REFRIGERACION");
        backendSrv.createTipoAbasto(tipoAbasto);
    }
    
    public void createTipoCaudal(){
        TipoCaudal tipoCaudal = new TipoCaudal();
        tipoCaudal.setNombre("Recirculación");
        backendSrv.createTipoCaudal(tipoCaudal);
    }
    
    public void createDeclaracion(){
        dec = new DeclaracionJurada();
        Firmante firm = backendSrv.getFirmanteByID(Long.valueOf(1));
        dec.setFirmante(firm);
        dec.setCude("990-5555-1");
        
        DocDec doc = new DocDec();
        doc.setDescripcion("Descripción de prueba");
        doc.setNumero(2653);
        doc.setTipoVisado(3);
        
        dec.setDocumentacion(doc);
        
        List<ActividadDec> lstAct = new ArrayList<>();
        ActividadDec act_1 = new ActividadDec();
        Actividad ac = backendSrv.getActividadByID(Long.valueOf(1));
        act_1.setActividad(ac);
        
        ActividadDec act_2 = new ActividadDec();
        Actividad ac_2 = backendSrv.getActividadByID(Long.valueOf(2));
        act_2.setActividad(ac_2);
        
        lstAct.add(act_1);
        lstAct.add(act_2);
        
        dec.setActividades(lstAct);
        
        // agrego la entidad administrativa
        Usuario us = backendSrv.getUsrByID(Long.valueOf(1));        
        Date date = new Date(System.currentTimeMillis());
        AdminEntidad admEnt = new AdminEntidad();
        admEnt.setFechaAlta(date);
        admEnt.setHabilitado(true);
        admEnt.setUsAlta(us);
        dec.setAdmin(admEnt);
        
        backendSrv.createDeclaracion(dec);
    }
}
