/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.mb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.AdminEntidad;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.UsuarioExterno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.modelo.SolicitudCuenta;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.Correo;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.CorreoEnvios;
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
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;

/**
 *
 * @author rincostante
 */
public class MbRegistro implements Serializable{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8080/CuitAfipWs/CuitAfipWs.wsdl")
    private CuitAfipWs_Service service;
    
    private boolean solicitado;
    private boolean resultado;
    private boolean mostrarResult;
    private SolicitudCuenta solicitud;   
    private String mensajeError;
    
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
        String s;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
        .getExternalContext().getSession(true);
        Enumeration enume = session.getAttributeNames();
        while(enume.hasMoreElements()){
            s = (String)enume.nextElement();
            if(s.substring(0, 2).equals("mb")){
                if(!s.equals("mbRegistro")){
                    session.removeAttribute(s);
                }
            }
        }
    }
    
    public void iniciar(){
        if(solicitado){
            solicitado = false;
        }else{
            resultado = false;
            mostrarResult = false;
            solicitud = new SolicitudCuenta();
        }
    }    

    /**********************
     * Métodos de acceso **
     **********************/
    
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
        if(solicitud.getCuitEstablecimiento().equals(solicitud.getCuitFirmante())){
            /*
             * Aquí deberían validarse los datos brindados por el usuario y en caso de ser correctos, 
             * generarse el usuario y enviar credenciales por correo electrónico.
             * En este caso damos por válido los datos y generamos el usuario
             */
            
            try{
                // seteo la admin. Primero obtengo el usuario de alta, deberá ser un usuario estandar, por ahora seré yo
                Date date = new Date(System.currentTimeMillis());
                AdminEntidad admEnt = new AdminEntidad();
                Usuario usSistema = backendSrv.getUsrByID(Long.valueOf(1));
                admEnt.setFechaAlta(date);
                admEnt.setHabilitado(true);
                admEnt.setUsAlta(usSistema);
                usExt.setAdmin(admEnt);
                
                // obtengo la razón social
                CuitAfip cuitAfip = getRazonSocial(solicitud.getCuitEstablecimiento());
                usExt.setRazonSocial(cuitAfip.getPejRazonSocial());

                // generación de usuario
                String clave = CriptPass.generar();
                String claveEncriptada = CriptPass.encriptar(clave);

                usExt = new UsuarioExterno();
                usExt.setCude(solicitud.getCude());
                usExt.setClave(claveEncriptada);
                usExt.setPrimeraVez(true);
                usExt.setCuit(solicitud.getCuitEstablecimiento());
                
                usExt.setTipoEst("Depósito");
                usExt.setDomCalle("CABOTO");
                usExt.setNumero("1129");
                usExt.setPiso("0");
                usExt.setDpto("0");
                usExt.setLocalidad("LA BOCA");
                
                // inserto
                backendSrv.createUsuarioExterno(usExt);
                
                // envío el correo electrónico
                /******************
                 * VER TODO ESTO **
                 ******************/
                
            }catch(Exception ex){
                // muestro un mensaje al usuario
                JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("generarUsError"));
                // lo escribo en el log del server
                System.out.println(ResourceBundle.getBundle("/Bundle").getString("generarUsError") + ex.getMessage());
            }

            
            
            
            
            resultado = true;
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("validateRegExito"));
        }else{
            // supondremos que el correo es inválido y que el cude también
            resultado = false;
            mensajeError = "Su correo electrónico es iválido. Su CUDE es inválido.";
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("panelRegIvalidoMessage_1") );
        }
        solicitado = true;
        mostrarResult = true;
        solicitud = null;
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
    
    private boolean enviarCorreo(){
        String correo;
        String nombre;      
        String bodyMessage;
        Correo c = new Correo();
        CorreoEnvios envio = new CorreoEnvios();

        correo = "nombreCorreo";
        nombre = "nombreDestino";
        
        bodyMessage = "<p>Estimado/a " + nombre + "</p> "
                + "<p>Se ha creado una cuenta a su nombre en el Sistema " + ResourceBundle.getBundle("/Bundle").getString("Aplicacion") + " con las siguientes credenciales de acceso:</p> "
                + "<p><strong>usuario:</strong> " + usExt.getCude() + "<br/> "
                + "<strong>contraseña:</strong> " + usExt.getClave() + "</p> "
                + "<p>Podrá acceder mediantes esle link " + ResourceBundle.getBundle("/Bundle").getString("Servidor") + ResourceBundle.getBundle("/Bundle").getString("RutaAplicacion") + " <br/> "
                + "Una vez iniciada la sesión, el sistema lo redireccionará para cambiar la contraseña por una de su elección.</p> "
                + "<p>Por favor, no responda este correo. No divulgue ni comparta sus credenciales de acceso.</p> "
                + "<p>Saludos cordiales</p> "
                + "<p>Instituto Provincial de la Administración Pública<br/> "
                + "Subsecretaría para la Modernización del Estado<br/> "
                + "Calle 12 y 53 - Torre Gubernamental II - Piso 11. Código Postal 1900. La Plata, Provincia de Buenos Aires, República Argentina<br/> "
                + "Teléfono: (0221) 429 5574/76<br /> "
                + "Correo electrónico: <a href=\"mailto:privadaipap@ipap.sg.gba.gov.ar\">privadaipap@ipap.sg.gba.gov.ar</a></p>";        
        
        c.setContrasenia("usgpxriehulvqxmz");
        c.setUsuarioCorreo("gestionipap@gmail.com");
        c.setAsunto(ResourceBundle.getBundle("/Bundle").getString("Aplicacion") + ": Nuevo registro de cuenta en el Sistema");
        c.setMensaje(bodyMessage);
        c.setDestino(correo);

        return envio.enviarCorreo(c);
    }    
}
