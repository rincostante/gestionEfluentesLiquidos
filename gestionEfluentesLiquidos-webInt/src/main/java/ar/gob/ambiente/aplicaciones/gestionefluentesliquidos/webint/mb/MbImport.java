

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.AdminEntidad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Firmante;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Inmueble;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Partido;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.wsClient.CentroPoblado;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.wsClient.CentrosPobladosWebService;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webint.wsClient.CentrosPobladosWebService_Service;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Actividad;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Actividadesrubro;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Domicilio;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Especialidad;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Estado;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Expediente;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.PerJuridica;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.TipoEstablecimiento;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.TipoPersonaJuridica;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.TmpEstDpyra;
import ar.gob.ambiente.servicios.registrounicopersonas.ejb.servicio.PersonasServicio;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.xml.ws.WebServiceRef;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author rincostante
 */
public class MbImport implements Serializable{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/CentrosPobladosWebService/CentrosPobladosWebService.wsdl")
    private CentrosPobladosWebService_Service service;
    private List<String> listColumnas;
    private TmpEstDpyra tmpEstDpyra;
    private MbLogin login;
    private Usuario usLogeado;
    
    @EJB
    private PersonasServicio rupBachendSrv;
    @EJB
    private BackendSrv backendSrv;

    /**
     * Creates a new instance of MbImport
     */
    public MbImport() {
    }
    
    /**
     * Método que se ejecuta luego de instanciada la clase e inicializa los datos del usuario
     */
    @PostConstruct
    public void init(){
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        login = (MbLogin)ctx.getSessionMap().get("mbLogin");
        usLogeado = login.getUsLogeado();  
    }    
    
    public void importar() throws IOException{
        listColumnas = new ArrayList<>();
        listColumnas.add("nombre");
        listColumnas.add("tipo");
        listColumnas.add("cuit");
        listColumnas.add("calle");
        listColumnas.add("numero");
        listColumnas.add("depto");
        listColumnas.add("localidad");
        listColumnas.add("partido");
        listColumnas.add("Actividad");
        listColumnas.add("esJurdica");
        listColumnas.add("crs");
        listColumnas.add("numero");
        listColumnas.add("codPartido ");
        listColumnas.add("procProductivo");
        listColumnas.add("partInmob");
        listColumnas.add("nomCatastral");
        listColumnas.add("nombreCompleto");
        listColumnas.add("dni");
        listColumnas.add("letra");
        listColumnas.add("recibo");
        
        File f = new File("C:\\gelProduccion\\import\\newEst_8.xlsx");
        if(f.exists()){
            System.out.println("Lo encontró!");
            leerExcel(f);
        }
    }
    
    /**
     * Lee el archivo y por cada registro genera un tmpEstDpyra y lo inserta.
     * @param fileName
     * @throws IOException 
     */
    public void leerExcel(File fileName) throws IOException{
        int fila = 0;
        try(FileInputStream fileInputStream = new FileInputStream(fileName)) {
            // Instancio un libro de excel
            XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
            // Obtengo la primera hoja del libro
            XSSFSheet hssfSheet = workBook.getSheetAt(0);
            // Levanto las filas de la hoja y las guardo en un iterator
            Iterator rowIterator = hssfSheet.rowIterator();
            // recorro las filas
            while(rowIterator.hasNext()){
                tmpEstDpyra = new TmpEstDpyra();
                int i = 0;
                // obtengo los datos de cada fila y por cada una instancio e inserto un tmpEst
                XSSFRow hssfRow = (XSSFRow) rowIterator.next();
                // instancio otro iterator para guardar las celdas de la fila
                Iterator cellIterator = hssfRow.cellIterator();
                while(cellIterator.hasNext()){
                    XSSFCell xssfCell = (XSSFCell) cellIterator.next();
                    String valor = xssfCell.toString();
                    switch(i){
                        case 0:
                            tmpEstDpyra.setRs_nombre(valor.toUpperCase());
                            break;
                        case 1:
                            tmpEstDpyra.setRs_tipo(valor.toUpperCase());
                            break;
                        case 2:
                            tmpEstDpyra.setRs_cuit(Long.valueOf(valor));
                            break;
                        case 3:
                            tmpEstDpyra.setDom_calle(valor.toUpperCase());
                            break;
                        case 4:
                            if(valor.indexOf(".") > 0){
                                tmpEstDpyra.setDom_numero(valor.substring(0, valor.indexOf(".")));
                            }else{
                                tmpEstDpyra.setDom_numero(valor);
                            }
                            break;
                        case 5:
                            tmpEstDpyra.setDom_dpto(valor);
                            break;
                        case 6:
                            tmpEstDpyra.setDom_localidad(valor.toUpperCase());
                            break;
                        case 7:
                            tmpEstDpyra.setDom_partido(valor.toUpperCase());
                            break;
                        case 8:
                            /*
                            if(valor.indexOf(".") > 0){
                                tmpEstDpyra.setEst_actividad(valor.substring(0, valor.indexOf(".")));
                            }else{
                                tmpEstDpyra.setEst_actividad(valor);
                            }
                            */
                            tmpEstDpyra.setEst_actividad(valor);
                            break;
                        case 9:
                            tmpEstDpyra.setEst_esjuridica(valor);
                            break;
                        case 10:
                            if(valor.indexOf(".") > 0){
                                tmpEstDpyra.setEst_crs(Integer.valueOf(valor.substring(0, valor.indexOf("."))));
                            }else{
                                tmpEstDpyra.setEst_crs(Integer.valueOf(valor));
                            }
                            break;
                        case 11:
                            if(valor.indexOf(".") > 0){
                                tmpEstDpyra.setEst_numero(Integer.valueOf(valor.substring(0, valor.indexOf("."))));
                            }else{
                                tmpEstDpyra.setEst_numero(Integer.valueOf(valor));
                            }
                            break;
                        case 12:
                            if(valor.indexOf(".") > 0){
                                tmpEstDpyra.setEst_codpartido(Integer.valueOf(valor.substring(0, valor.indexOf("."))));
                            }else{
                                tmpEstDpyra.setEst_codpartido(Integer.valueOf(valor));
                            }
                            break;

                        case 13:
                            tmpEstDpyra.setEst_procproductivo(valor);
                            break;

                        case 14:
                            tmpEstDpyra.setInm_partinmob(valor);
                            break;
                        case 15:
                            tmpEstDpyra.setInm_nomcatastral(valor);
                            break;
                        case 16:
                            tmpEstDpyra.setFir_nomcompleto(valor.toUpperCase());
                            break;
                        case 17:
                            /*
                            if(valor.indexOf(".") > 0){
                                tmpEstDpyra.setFir_dni(Long.valueOf(valor.substring(0, valor.indexOf("."))));
                            }else{
                                tmpEstDpyra.setFir_dni(Long.valueOf(valor));
                            }
                            */
                            tmpEstDpyra.setFir_dni(Long.valueOf(valor));
                            break;
                            
                        case 18:
                            tmpEstDpyra.setInm_letra(valor);
                            break;
                            
                        default:

                            if(valor.indexOf(".") > 0){
                                tmpEstDpyra.setCodercibo(valor.substring(0, valor.indexOf(".")));
                            }else{
                                tmpEstDpyra.setCodercibo(valor);
                            }

                            tmpEstDpyra.setCodercibo(valor.toUpperCase());
                            break;
                    }
                    i += 1;
                }
                /*
                System.out.println(tmpEstDpyra.getRs_nombre() + " | "
                        + "" + tmpEstDpyra.getRs_tipo() + " | "
                        + "" + tmpEstDpyra.getRs_cuit() + " | "
                        + "" + tmpEstDpyra.getDom_calle() + " | "
                        + "" + tmpEstDpyra.getDom_numero() + " | "
                        + "" + tmpEstDpyra.getDom_dpto() + " | "
                        + "" + tmpEstDpyra.getDom_localidad() + " | "
                        + "" + tmpEstDpyra.getDom_partido() + " | "
                        + "" + tmpEstDpyra.getEst_actividad() + " | "
                        + "" + tmpEstDpyra.getEst_esjuridica() + " | "
                        + "" + tmpEstDpyra.getEst_crs() + " | "
                        + "" + tmpEstDpyra.getEst_numero() + " | "
                        + "" + tmpEstDpyra.getEst_codpartido() + " | "
                        + "" + tmpEstDpyra.getEst_procproductivo() + " | "
                        + "" + tmpEstDpyra.getInm_partinmob() + " | "
                        + "" + tmpEstDpyra.getInm_nomcatastral() + " | "
                        + "" + tmpEstDpyra.getFir_nomcompleto() + " | "
                        + "" + tmpEstDpyra.getFir_dni() + " | "
                        + "" + tmpEstDpyra.getInm_letra() + " | "
                        + "" + tmpEstDpyra.getCodercibo());
                */
                rupBachendSrv.createTempEst(tmpEstDpyra);
                
                System.out.println("-----------------guardó " + fila + "--------------");
                fila += 1;
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Inserta las personas jurídicas en el RUP
     */
    public void insertPerJur(){
        PerJuridica perJur;
        boolean valida = true;
        String mensaje = "";
        List<TmpEstDpyra> listEstDpyra = rupBachendSrv.getEstDpyra();
        // obtengo el usuario homonimo del RUP
        ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Usuario usRup;
        usRup = rupBachendSrv.getUsuarioByNombre(usLogeado.getNombre());
        Date date = new Date(System.currentTimeMillis());
        int i = 0;
        for(TmpEstDpyra estDpyra : listEstDpyra){
            if(estDpyra.getIdRsRup() == null){
                try{
                    // primero valido si no está registrada, en cuyo caso la levanto
                    perJur = rupBachendSrv.getPerJuridicasPorCuit(estDpyra.getRs_cuit());
                    if(perJur == null){
                        // si no existe una perJuridica para el CUIT, la creo.
                        perJur = new PerJuridica();
                        // genero la entidad administrativa
                        ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.AdminEntidad admin = new ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.AdminEntidad();
                        admin.setFechaAlta(date);
                        admin.setUsAlta(usRup);
                        admin.setHabilitado(true);
                        perJur.setAdmin(admin);

                        // levanto el estado
                        Estado estado = rupBachendSrv.getEstadoByNombre("Migrado");
                        if(estado == null){
                            valida = false;
                            mensaje = mensaje + "No encontró el estado.";
                        }

                        // levanto el tipo de persona jurídica
                        String tipoPer;
                        switch (estDpyra.getRs_tipo()) {
                            case "OTRO":
                                tipoPer = "Otro DPyRA";
                                break;
                            case "SA":
                                tipoPer = "Sociedad Anónima";
                                break;
                            default:
                                tipoPer = "Sociedad de Responsabilidad Limitada";
                                break;
                        }

                        TipoPersonaJuridica tipoPerJur = rupBachendSrv.getTipoPerJurByNombre(tipoPer);
                        if(tipoPerJur == null){
                            valida = false;
                            mensaje = mensaje + "No encontró el tipo de persona jurídica.";
                        }

                        if(valida){
                            // completo
                            perJur.setEstado(estado);
                            perJur.setTipoPersonaJuridica(tipoPerJur);
                            perJur.setRazonSocial(estDpyra.getRs_nombre());
                            perJur.setCuit(estDpyra.getRs_cuit());

                            // persisito
                            rupBachendSrv.createPerJuridica(perJur);
                            System.out.println("Persistió " + estDpyra.getRs_nombre());
                        }else{
                            System.out.println("No se pudo crear la persona con el CUIT: " + estDpyra.getRs_cuit() + " porque " + mensaje);
                        }
                    }
                    System.out.println("Obtuvo la perJur " + estDpyra.getRs_nombre() + " | idRup: " + perJur.getId());

                    if(valida){
                        // actualizo la tabla temporal
                        estDpyra.setIdRsRup(perJur.getId());
                        rupBachendSrv.editTmpEstDpyra(estDpyra);
                        System.out.println("Se migró y/o actualizó la RS del tmpEst " + estDpyra.getId());
                        //System.out.println("Actualizó " + estDpyra.getRs_nombre());
                        System.out.println("---------------------------------" + i + "-----------------------------------");
                    }
                    i += 1;
                }catch (Exception ex){
                    System.out.println("Hubo un error con la inserción del registro " + estDpyra.getId() + ". " + mensaje);
                }   
            }
        }
    }
    
    /**
     * Inserta los establecimientos en el RUP con la persona jurídica referenciada 
     */
    public void insertEstabRup(){
        Domicilio domicilio;
        ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento estRup;
        List<ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento> listExistentes;
        boolean valida = true;
        String mensaje;
        List<TmpEstDpyra> listEstDpyra = rupBachendSrv.getEstDpyra();    
        
        // obtengo el usuario homonimo del RUP
        ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Usuario usRup;
        usRup = rupBachendSrv.getUsuarioByNombre(usLogeado.getNombre());
        Date date = new Date(System.currentTimeMillis());
        int i = 0;
        for(TmpEstDpyra estDpyra : listEstDpyra){
            valida = true;
            mensaje = "";
            if(estDpyra.getIdEstRup() == null){
                try{
                    // primero valido si hay algún Establecimiento del GEL para la RS
                    // Obtengo la localidad
                    CentrosPobladosWebService port = service.getCentrosPobladosWebServicePort();
                    CentroPoblado localidad = port.buscarCentrosPorNomCpNomDto(estDpyra.getDom_localidad(), estDpyra.getDom_partido());
                    if(localidad != null){
                        TipoEstablecimiento tipo = rupBachendSrv.getTipoEstByNombre("Planta Productora");
                        listExistentes = rupBachendSrv.getEstablecimientosExistentes(estDpyra.getDom_calle(), estDpyra.getDom_numero(), localidad.getId(), tipo);
                        if(listExistentes.isEmpty()){
                            // creo el domicilio
                            domicilio = new Domicilio();
                            domicilio.setCalle(estDpyra.getDom_calle());
                            domicilio.setNumero(estDpyra.getDom_numero());
                            domicilio.setLocalidad(localidad.getNombre());
                            domicilio.setDepartamento(localidad.getDepartamento().getNombre());
                            domicilio.setIdLocalidad(localidad.getId());
                            // obtengo la provincia
                            String provincia;
                            if(estDpyra.getDom_partido().equals("CAPITAL FEDERAL")) provincia = "CIUDAD AUTONOMA DE BUENOS AIRES";
                            else provincia = "BUENOS AIRES";
                            domicilio.setProvincia(provincia);

                            // creo el Establecimiento
                            estRup = new ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento();
                            // seteo Actividad
                            List<Actividad> actividades = new ArrayList<>();
                            Actividadesrubro actRub = rupBachendSrv.getActRubroPorCodigo(estDpyra.getEst_actividad());
                            if(actRub != null){
                                // obtento la actividad RUP
                                Actividad act = rupBachendSrv.getActividadPorNombre(actRub.getNombre());
                                if(act != null){
                                    actividades.add(act);
                                }else{
                                    valida = false;
                                    mensaje = mensaje + " No se encontró la Actividad para " + estDpyra.getId();
                                    System.out.println(mensaje);
                                }
                            }else{
                                valida = false;
                                mensaje = mensaje + " No se encontró la Actividad RUP para " + estDpyra.getId();
                                System.out.println(mensaje);
                            }

                            // seteo Especlalidad
                            List<Especialidad> especialidades = new ArrayList<>();
                            Especialidad esp = rupBachendSrv.getEspecialidadByNombre("Vertido de Efluentes");
                            if(esp != null){
                                especialidades.add(esp);
                            }else{
                                valida = false;
                                mensaje = mensaje + " No se encontró la Especialida RUP para " + estDpyra.getId();
                                System.out.println(mensaje);
                            }

                            // obtengo la perJuridica
                            PerJuridica perJur = rupBachendSrv.getPerJuridicaPorId(Long.valueOf(estDpyra.getIdRsRup()));
                            if(perJur == null){
                                valida = false;
                                mensaje = mensaje + " No se encontró la PerJuridica RUP para " + estDpyra.getId();
                                System.out.println(mensaje);
                            }

                            // obtengo el estado | getEstadoByNombre | Migrado
                            Estado est = rupBachendSrv.getEstadoByNombre("Migrado");
                            if(est == null){
                                valida = false;
                                mensaje = mensaje + " No se encontró el Estado RUP para " + estDpyra.getId();
                                System.out.println(mensaje);
                            }

                            // obtengo el expediente | getExpedienteById
                            Expediente exp = rupBachendSrv.getExpedienteById(Long.valueOf(66));
                            if(exp == null){
                                valida = false;
                                mensaje = mensaje + " No se encontró el Expediente RUP para " + estDpyra.getId();
                                System.out.println(mensaje);
                            }

                            // seteo
                            if(valida){
                                estRup.setActividades(actividades);
                                estRup.setEspecialidades(especialidades);
                                estRup.setPerJuridica(perJur);
                                estRup.setTipo(tipo);
                                estRup.setDomicilio(domicilio);
                                estRup.setEstado(est);
                                estRup.setExpediente(exp);

                                // Creo el admin
                                ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.AdminEntidad admin = new ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.AdminEntidad();
                                admin.setFechaAlta(date);
                                admin.setUsAlta(usRup);
                                admin.setHabilitado(true);
                                estRup.setAdmin(admin);

                                // persisto
                                rupBachendSrv.createEstablecimiento(estRup);
                                System.out.println("Se creo el Establecimiento RUP para " + estDpyra.getId());

                                // actualizo 
                                estDpyra.setIdEstRup(estRup.getId());
                                rupBachendSrv.editTmpEstDpyra(estDpyra);
                                System.out.println("------------------------------------" + i + "----------------------------------");
                            }
                        }else{
                            for(ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento exist : listExistentes){
                                mensaje = mensaje + " Hay un Establecimiento " + exist.getId() + "para " + estDpyra.getId();
                                System.out.println(mensaje);
                            }
                        }
                    }else{
                        valida = false;
                        mensaje = mensaje + "No encontró la localidad para " + estDpyra.getDom_localidad() + " | " + estDpyra.getDom_partido();
                        System.out.println(mensaje);
                    }
                    
                    if(!valida){
                        estDpyra.setMensaje(mensaje);
                        rupBachendSrv.editTmpEstDpyra(estDpyra);
                    }
                    
                }catch(Exception ex){
                    System.out.println("ERROR--------------------------------" + i + "----------------------------ERROR");
                }
            }
            i += 1;
        }
    }
    
    public void insertEstabGel(){
        Inmueble inmueble = new Inmueble();
        Establecimiento estGel = new Establecimiento();
        Establecimiento existente;
        String mensaje;
        ar.gob.ambiente.servicios.registrounicopersonas.ejb.entities.Establecimiento estRup;
        boolean valida = true, insertoInm;
        List<TmpEstDpyra> listEstDpyra = rupBachendSrv.getEstDpyra();    

        Date date = new Date(System.currentTimeMillis());
        int i = 0;
        for(TmpEstDpyra estDpyra : listEstDpyra){
            valida = true;
            insertoInm = false;
            mensaje = "";
            if(estDpyra.getIdEstGel() == null && estDpyra.getIdEstRup() != null){
                try{
                    // valido que no exista el establecimiento en el GEL
                    existente = backendSrv.getEstablecimientoByCude(Long.valueOf(estDpyra.getEst_codpartido()), Long.valueOf(estDpyra.getEst_numero()), estDpyra.getEst_crs());
                    if(existente == null){
                        // si no existe, voy obteniendo los parámetros. Partido
                        Partido part = backendSrv.getPartidoByIdDpyra(Long.valueOf(estDpyra.getEst_codpartido()));
                        if(part == null){
                            valida = false;
                            mensaje = mensaje + " No existe el partido para el código " + estDpyra.getEst_codpartido();
                            System.out.println(mensaje);
                        }
                        // valido el Firmante, si no existe lo creo
                        Firmante firm;
                        if(backendSrv.firXDniNoExiste(estDpyra.getFir_dni())){
                            firm = new Firmante();
                            firm.setNombreYApellido(estDpyra.getFir_nomcompleto());
                            firm.setDni(estDpyra.getFir_dni());
                            
                            // seteo la entidad administrativa
                            AdminEntidad admin = new AdminEntidad();
                            admin.setFechaAlta(date);
                            admin.setUsAlta(usLogeado);
                            admin.setHabilitado(true);
                            firm.setAdmin(admin);
                            
                            // inserto
                            backendSrv.createFirmante(firm);
                        }else{
                            firm = backendSrv.getFirmanteByDni(estDpyra.getFir_dni());
                        }
                        if(firm == null){
                            valida = false;
                            mensaje = mensaje + " No se obtuvo Firmante para " + estDpyra.getFir_dni();
                            System.out.println(mensaje);
                        }
                        // seteo el Establecimiento RUP
                        estRup = rupBachendSrv.getEstablecimientoPorId(estDpyra.getIdEstRup());
                        if(estRup == null){
                            valida = false;
                            mensaje = mensaje + " No se obtuvo el Establecimiento RUP para " + estDpyra.getId();
                            System.out.println(mensaje);
                        }
                        // si todo anduvo bien, inserto el Establecimiento en el GEL
                        if(valida){
                            // seteo el inmueble
                            inmueble.setCalle(estRup.getDomicilio().getCalle());
                            inmueble.setNumero(estRup.getDomicilio().getNumero());
                            inmueble.setLocalidad(estRup.getDomicilio().getLocalidad());
                            inmueble.setDepartamento(estRup.getDomicilio().getDepartamento());
                            inmueble.setIdRupDom(estRup.getDomicilio().getId());
                            inmueble.setPartInmob(estDpyra.getInm_partinmob());
                            inmueble.setNomCatastral(estDpyra.getInm_nomcatastral());
                            
                            // inserto el inmueble
                            backendSrv.createInmueble(inmueble);
                            insertoInm = true;
                            
                            // seteo la entidad administrativa
                            AdminEntidad admin = new AdminEntidad();
                            admin.setFechaAlta(date);
                            admin.setUsAlta(usLogeado);
                            admin.setHabilitado(true);
                            
                            // instancio el Establecimiento
                            estGel.setAdmin(admin);
                            estGel.setPartido(part);
                            estGel.setFirmante(firm);
                            estGel.setInmueble(inmueble);
                            estGel.setIdRupEst(estRup.getId());
                            estGel.setIdRupRaz(estRup.getPerJuridica().getId());
                            if(estDpyra.getEst_esjuridica().equals("SI")) estGel.setRzJuridica(true);
                            else estGel.setRzJuridica(false);
                            estGel.setRazonSocial(estRup.getPerJuridica().getRazonSocial());
                            estGel.setCuit(estRup.getPerJuridica().getCuit());
                            estGel.setCrs(estDpyra.getEst_crs());
                            estGel.setNumero(Long.valueOf(estDpyra.getEst_numero()));
                            estGel.setPartidoGel(part.getIdRt().intValue());
                            estGel.setHistorico(false);
                            estGel.setCodRecibo(estDpyra.getCodercibo());
                            
                            // inserto
                            backendSrv.createEstablecimiento(estGel);
                            
                            // actualizo el listado
                            estDpyra.setIdEstGel(estGel.getId());
                            rupBachendSrv.editTmpEstDpyra(estDpyra);
                            
                            System.out.println("Se creo el Establecimiento para " + estDpyra.getId() + ", rz: " + estDpyra.getRs_nombre());
                            System.out.println("cude: " + estGel.getPartidoGel() + "-" + estGel.getNumero() + "-" + estGel.getCrs() + " | rs: " + estGel.getRazonSocial() + " | cuit: "
                                    + "" + estGel.getCuit() + " | codRec: " + estGel.getCodRecibo() + " | Firmante: " + estGel.getFirmante().getNombreYApellido() + " | dni: "
                                    + "" + estGel.getFirmante().getDni() + " | partido: " + estGel.getPartido().getNombre());
                            System.out.println("-------------------------------------" + i + "----------------------------------");
                        }
                        
                    }else{
                        valida = false;
                        mensaje = mensaje + " Ya existe el Establecimiento en el GEL para " + estDpyra.getEst_codpartido() + "-" + estDpyra.getEst_numero() + "-" + estDpyra.getEst_crs();
                        System.out.println(mensaje);
                    }
                    if(!valida){
                        estDpyra.setMensaje(mensaje);
                        rupBachendSrv.editTmpEstDpyra(estDpyra);
                    }
                }catch(Exception ex){
                    // si cree un inmueble lo elimino
                    if(insertoInm){
                        //backendSrv.deleteInmueble(inmueble);
                    }
                    System.out.println("ERROR-------------------------------- inicia: " + i + "----------------------------ERROR");
                    System.out.println(ex.getMessage());
                    System.out.println("ERROR------------------------------ finaliza: " + i + "----------------------------ERROR");
                }
            }
            i += 1;
        }
    }
}
