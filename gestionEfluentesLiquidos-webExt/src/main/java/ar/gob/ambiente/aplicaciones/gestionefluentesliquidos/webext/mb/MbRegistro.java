
package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.AdminEntidad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.UsuarioExterno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.modelo.SolicitudCuenta;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.CriptPass;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.JsfUtil;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.wsExt.CuitAfipWs_Service;
import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author rincostante
 */
public class MbRegistro implements Serializable{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/vmdeswebjava.medioambiente.gov.ar_8080/CuitAfipWs/CuitAfipWs.wsdl")
    private CuitAfipWs_Service service;
    
    @Resource(name="mail/ambiente")
    private Session mailSesion;
    
    private Message mensaje;
    
    private boolean solicitado;
    private boolean resultado;
    private boolean mostrarResult;
    private SolicitudCuenta solicitud;   
    private String mensajeError;
    private String clave;
    private boolean iniciado;
    private Establecimiento est;
    
    // botonos
    private String cmbValidar;
    private String cmbLimpiar;    
    
    @EJB
    private BackendSrv backendSrv;
    
    private UsuarioExterno usExt;

    /****************
     * Constructor **
     ****************/
    public MbRegistro() {
    }
    
    @PostConstruct
    public void init(){
        iniciado = true;
        solicitud = new SolicitudCuenta();
    }
    
    public void iniciar(){
        if(solicitado){
            solicitado = false;
        }else{
            resultado = false;
            mostrarResult = false;

            cmbValidar = "Validar";
            cmbLimpiar = "Limpiar";            
        }

        String s;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
        .getExternalContext().getSession(true);
        //Enumeration enume = session.getAttributeNames();
        session.removeAttribute("mbCude");
        session.removeAttribute("mbSesion");
    }    

    /**********************
     * Métodos de acceso **
     **********************/
    public Establecimiento getEst() {
        return est;
    }

    public void setEst(Establecimiento est) {
        this.est = est;
    }

    public String getCmbValidar() {
        return cmbValidar;
    }

    public void setCmbValidar(String cmbValidar) {
        this.cmbValidar = cmbValidar;
    }

    public String getCmbLimpiar() {
        return cmbLimpiar;
    }

    public void setCmbLimpiar(String cmbLimpiar) {
        this.cmbLimpiar = cmbLimpiar;
    }

    
    public boolean isResultado() {
        return resultado;
    }

    public void setResultado(boolean resultado) {
        this.resultado = resultado;
    }

    public boolean isSolicitado() {
        return solicitado;
    }

    public void setSolicitado(boolean solicitado) {
        this.solicitado = solicitado;
    }

    public boolean isMostrarResult() {
        return mostrarResult;
    }

    public void setMostrarResult(boolean mostrarResult) {
        this.mostrarResult = mostrarResult;
    }

    public SolicitudCuenta getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudCuenta solicitud) {
        this.solicitud = solicitud;
    }
    
    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
    
  
    /*****************************
     * Métodos de procesamiento **
     *****************************/
    
    public void solicitar(){
        mensajeError = "";
        boolean valida = true, validaLetra = true;
        
        /*
        // valido la letra, si es que viene
        if(solicitud.getDniLetra() != null){
            if(solicitud.getDniLetra().length() > 1){
                validaLetra = false;
                mensajeError = mensajeError + "El DNI solo puede contener una letra.";
            }else{
                if(!solicitud.getDniLetra().equals("M") && !solicitud.getDniLetra().equals("m") 
                        && !solicitud.getDniLetra().equals("F") && !solicitud.getDniLetra().equals("f")){
                    validaLetra = false;
                    mensajeError = mensajeError + "La letra del DNI solo puede ser una 'F' o una 'M'.";
                }
            }
        }
        */
        if(validaLetra){
            // obtengo el Establecimiento según el CUDE ingresado
            String cude = solicitud.getCude();
            int primerGuion = cude.indexOf("-");
            int segundoGuion = cude.lastIndexOf("-");
            Long lPart = Long.valueOf(cude.substring(0, primerGuion));
            Long lNumEst = Long.valueOf(cude.substring(primerGuion + 1, segundoGuion));
            int lCrs = Integer.parseInt(cude.substring(segundoGuion + 1, cude.length()));

            est = backendSrv.getEstablecimientoByCude(lPart, lNumEst, lCrs);
            if(est != null){
                if(!solicitud.getCodigoRecibo().equals(est.getCodRecibo())){
                    valida = false;
                    mensajeError = "El código de recibo ingresado no corresponde con el último vigente.";
                }else{

                    // verifico que el esablecimiento no tenga ya una cuenta
                    if(!backendSrv.usrExtNoExiste(cude)){
                        String email = backendSrv.getUsuarioExt(cude).getEmail();
                        valida = false;
                        mensajeError = "El Establecimiento ya tiene una cuenta de usuario vinculada al correo " + email + ".";
                    }

                }
            }else{
                valida = false;
                mensajeError = "El cude ingresado no corresponde a ningún Establecimiento registrado.";
            }

            if(valida){
                try{
                    // Solo prosigo si validé los procedimientos anteriores
                    // seteo la admin. Primero obtengo el usuario de alta, deberá ser un usuario estandar, por ahora seré yo
                    Date date = new Date(System.currentTimeMillis());
                    AdminEntidad admEnt = new AdminEntidad();
                    usExt = new UsuarioExterno();
                    Usuario usSistema = backendSrv.getUsrByID(Long.valueOf(3));
                    admEnt.setFechaAlta(date);
                    admEnt.setHabilitado(true);
                    admEnt.setUsAlta(usSistema);
                    usExt.setAdmin(admEnt);

                    usExt.setRazonSocial(est.getRazonSocial());

                    // generación de clave
                    clave = CriptPass.generar();
                    String claveEncriptada = CriptPass.encriptar(clave);

                    usExt.setCude(solicitud.getCude());
                    usExt.setClave(claveEncriptada);
                    usExt.setPrimeraVez(true);
                    usExt.setCuit(est.getCuit());
                    usExt.setEmail(solicitud.getCorreoElectronico());

                    usExt.setDomCalle(est.getInmueble().getCalle());
                    usExt.setNumero(est.getInmueble().getNumero());
                    usExt.setLocalidad(est.getInmueble().getLocalidad());

                    // inserto
                    backendSrv.createUsuarioExterno(usExt);


                    if(!enviarCorreo(usExt.getEmail(), usExt.getCude(), usExt.getRazonSocial())){
                        JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("envioCorreoUsError"));
                    }else{
                        JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("validateRegExito"));
                    }

                    resultado = true;

                }catch(Exception ex){
                    // muestro un mensaje al usuario
                    JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("generarUsError"));
                    // lo escribo en el log del server
                    System.out.println(ResourceBundle.getBundle("/Bundle").getString("generarUsError") + ex.getMessage());
                }
            }else{
                resultado = false;
                mensajeError = mensajeError + " No se pudo procesar su solicitud.";
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("panelRegIvalidoMessage_1") + ": " + mensajeError);
            }
            solicitado = true;
            mostrarResult = true;
            solicitud = new SolicitudCuenta();
        }else{
            JsfUtil.addErrorMessage("No se pudieron validar los datos ingresados. " + mensajeError);
        }
    }
    
    public void limpiar(){
        solicitud = new SolicitudCuenta();
        solicitado = false;
        resultado = false;
    }    
    
    /*********************
     * Métodos privados **
     *********************/
    
    private boolean enviarCorreo(String correo, String cude, String razonSocial){  
        boolean result;
        String bodyMessage;
        mensaje = new MimeMessage(mailSesion);
        bodyMessage = "<p>Estimado/a</p> "
                + "<p>Se ha creado una cuenta para la gestión del Establecimiento cuyo CUDE es " + cude + ", de " + razonSocial
                + "<p>Las credenciales de acceso son las siguientes:</p> "
                + "<p><strong>usuario:</strong> " + usExt.getCude() + "<br/> "
                + "<strong>contraseña:</strong> " + clave + "</p> "
                + "<p>Podrá acceder mediante este link " + ResourceBundle.getBundle("/Bundle").getString("Servidor") + ResourceBundle.getBundle("/Bundle").getString("RutaAplicacion") + " <br/> "
                + "Una vez iniciada la sesión, el sistema lo redireccionará para cambiar la contraseña por una de su elección.</p> "
                + "<p>Por favor, no responda este correo. No divulgue ni comparta sus credenciales de acceso.</p> "
                + "<p>Saludos cordiales</p> "
                + "<p>Dirección de Prevención y Recomposición Ambiental (DPyRA)<br/> "
                + "Subsecretaría de Fiscalización y Control Ambiental y Prevención de la Contaminación<br/> "
                + "Secretaría de Control y Monitoreo Ambiental<br/> "
                + "Ministerio de Ambiente y Desarrollo Sustentable<br/> "
                + "Presidencia de la Nación<br/> "
                + "AU Ezeiza - Cañuelas, Tramo Jorge Newbery km. 1,620<br/> "
                + "1804 Ezeiza - Provincia de Buenos Aires <br/> "
                + "Teléfono: (54) (11) 4480-4500, int.2497 <br /> "
                + "Correo electrónico: <a href=\"mailto:dpyra@ambiente.gob.ar\">dpyra@ambiente.gob.ar </a></p>";     
        
        try{
            mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(correo));
            mensaje.setSubject("Sistema de Gestión de Efluentes líquidos - CUDE: " + cude + " - Credenciales de acceso" );
            mensaje.setContent(bodyMessage, "text/html; charset=utf-8");
            
            Date timeStamp = new Date();
            mensaje.setSentDate(timeStamp);
            
            Transport.send(mensaje);
            
            result = true;
            
        }catch(MessagingException ex){
            result = false;
            System.out.println("Hubo un error enviando el correo de prueba" + ex.getMessage());
        }
        
        return result;
    }    
}
