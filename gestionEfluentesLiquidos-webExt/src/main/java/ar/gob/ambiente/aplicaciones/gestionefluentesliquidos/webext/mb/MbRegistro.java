
package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.AdminEntidad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.UsuarioExterno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.modelo.SolicitudCuenta;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.CriptPass;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.JsfUtil;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.wsExt.CuitAfip;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.wsExt.CuitAfipWs;
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
    }
    
    public void iniciar(){
        if(solicitado){
            solicitado = false;
        }else{
            resultado = false;
            mostrarResult = false;
            solicitud = new SolicitudCuenta();
            
            cmbValidar = "Validar";
            cmbLimpiar = "Limpiar";            
        }
        
        if(!iniciado){
            String s;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
            .getExternalContext().getSession(true);
            Enumeration enume = session.getAttributeNames();
            while(enume.hasMoreElements()){
                s = (String)enume.nextElement();
                if(s.substring(0, 2).equals("mb")){
                    if(!s.equals("mbRegistro")&& !s.equals("mbSesion")){ 
                        session.removeAttribute(s);
                    }
                }
            }
        }
    }    

    /**********************
     * Métodos de acceso **
     **********************/
    
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

        try{
            if(solicitud.getCuitEstablecimiento().equals(solicitud.getCuitFirmante())){
                /*
                 * Aquí deberían validarse los datos brindados por el usuario y en caso de ser correctos, 
                 * generarse el usuario y enviar credenciales por correo electrónico.
                 * En este caso damos por válido los datos y generamos el usuario
                 */
                // seteo la admin. Primero obtengo el usuario de alta, deberá ser un usuario estandar, por ahora seré yo
                Date date = new Date(System.currentTimeMillis());
                AdminEntidad admEnt = new AdminEntidad();
                usExt = new UsuarioExterno();
                Usuario usSistema = backendSrv.getUsrByID(Long.valueOf(1));
                admEnt.setFechaAlta(date);
                admEnt.setHabilitado(true);
                admEnt.setUsAlta(usSistema);
                usExt.setAdmin(admEnt);

                // obtengo la razón social
                CuitAfip cuitAfip = getRazonSocial(solicitud.getCuitEstablecimiento());
                usExt.setRazonSocial(cuitAfip.getPejRazonSocial());

                // generación de usuario
                clave = CriptPass.generar();
                String claveEncriptada = CriptPass.encriptar(clave);
                
                usExt.setCude(solicitud.getCude());
                usExt.setClave(claveEncriptada);
                usExt.setPrimeraVez(true);
                usExt.setCuit(solicitud.getCuitEstablecimiento());
                usExt.setEmail(solicitud.getCorreoElectronico());
                
                /****************************************************************************
                 ***** Estos datos deberían surgir de la valicación del Establecimiento *****
                 ****************************************************************************/

                usExt.setTipoEst("Depósito");
                usExt.setDomCalle("CABOTO");
                usExt.setNumero("1129");
                usExt.setPiso("0");
                usExt.setDpto("0");
                usExt.setLocalidad("LA BOCA");

                // inserto
                backendSrv.createUsuarioExterno(usExt);

                if(!enviarCorreo(solicitud.getCorreoElectronico(), solicitud.getCude(), usExt.getRazonSocial())){
                    JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("envioCorreoUsError"));
                }else{
                    resultado = true;
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("validateRegExito"));
                }
            }else{
                // supondremos que el correo es inválido y que el cude también
                resultado = false;
                mensajeError = "Su correo electrónico es iválido. Su CUDE es inválido.";
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("panelRegIvalidoMessage_1") + ": " + mensajeError);
            }
            solicitado = true;
            mostrarResult = true;
            solicitud = null;
            
        }catch(Exception ex){
            // muestro un mensaje al usuario
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("generarUsError"));
            // lo escribo en el log del server
            System.out.println(ResourceBundle.getBundle("/Bundle").getString("generarUsError") + ex.getMessage());
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
    
    /**
     * Método para obtener la razón social vinculada al cuit
     * @param cuit
     * @return 
     */
    private CuitAfip getRazonSocial(Long cuit){
        
        try {
            CuitAfipWs port = service.getCuitAfipWsPort();
            return port.verPersona(cuit);
        } catch (Exception ex) {
            // muestro un mensaje al usuario
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("cuitAfipWsError"));
            // lo escribo en el log del server
            System.out.println(ResourceBundle.getBundle("/Bundle").getString("cuitAfipWsError") + ex.getMessage());
            return null;
        }
    }
    
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
            mensaje.setSubject("Sistema de Gestión de Efluentes líquidos - Credenciales de acceso");
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
