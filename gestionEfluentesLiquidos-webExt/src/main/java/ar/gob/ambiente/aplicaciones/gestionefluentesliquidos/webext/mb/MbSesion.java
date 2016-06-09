

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.mb;


import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.UsuarioExterno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.modelo.Sesion;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.CriptPass;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.util.JsfUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 * Bean que gestiona el login y toda la sesión del usuario registrado
 * @author rincostante
 */
public class MbSesion implements Serializable{

    private String cude;
    private String recCude;
    private String contrasenia;
    private String newContrasenia;
    private String newContrasenia_2;
    private boolean logeado = false; 
    private UsuarioExterno usuario;  
    private UsuarioExterno usuarioARecuperar;
    private Sesion sesion;
    private boolean recupEnviado;
    private String clave;
    private boolean iniciado;
    
    // botonos
    private String cmbValidar;
    private String cmbLimpiar;
    private String cmbRecuperar;
    private String cmbEnviar;
    
    @EJB
    private BackendSrv backendSrv;  
    
    private Establecimiento establecimiento;
    
    @Resource(name="mail/ambiente")
    private Session mailSesion;
    
    private Message mensaje;    

    public MbSesion(){
    }
    
    public void iniciar(){

        if(!iniciado){
            String s;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
            .getExternalContext().getSession(true);
            Enumeration enume = session.getAttributeNames();
            while(enume.hasMoreElements()){
                s = (String)enume.nextElement();
                if(s.substring(0, 2).equals("mb")){
                    if(!s.equals("mbSesion")){ 
                        session.removeAttribute(s);
                    }
                }
            }
        }
        
        cmbValidar = "Iniciar Sesión";
        cmbLimpiar = "Limpiar";    
        cmbRecuperar = "Recuperar contraseña";
        cmbEnviar = "Enviar solitud";
        
        cude = "";
        contrasenia = "";
        sesion = new Sesion();
        newContrasenia = "";
        newContrasenia_2 = "";
    }    
    
    /**********************
     * Métodos de acceso **
     **********************/
    public String getCmbEnviar() {
        return cmbEnviar;
    }

    public void setCmbEnviar(String cmbEnviar) {
        this.cmbEnviar = cmbEnviar;
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

    public String getCmbRecuperar() {
        return cmbRecuperar;
    }

    public void setCmbRecuperar(String cmbRecuperar) {
        this.cmbRecuperar = cmbRecuperar;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public String getNewContrasenia() {
        return newContrasenia;
    }

    public void setNewContrasenia(String newContrasenia) {
        this.newContrasenia = newContrasenia;
    }

    public String getNewContrasenia_2() {
        return newContrasenia_2;
    }

    public void setNewContrasenia_2(String newContrasenia_2) {
        this.newContrasenia_2 = newContrasenia_2;
    }

    public String getRecCude() {
        return recCude;
    }

    public void setRecCude(String recCude) {
        this.recCude = recCude;
    }

    public boolean isRecupEnviado() {
        return recupEnviado;
    }

    public void setRecupEnviado(boolean recupEnviado) {
        this.recupEnviado = recupEnviado;
    }

    public Sesion getSesion() {
        return sesion;
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    public UsuarioExterno getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioExterno usuario) {
        this.usuario = usuario;
    }

    public String getCude() {
        return cude;
    }

    public void setCude(String cude) {
        this.cude = cude;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean isLogeado() {
        return logeado;
    }

    public void setLogeado(boolean logeado) {
        this.logeado = logeado;
    }

    public UsuarioExterno getUsuarioExterno(java.lang.Long id) {
        return getFacade().getUsrExtByID(id);
    }     
   
    
    /***********************
     * Métodos operativos **
     ***********************/
    public void login(){
        ExternalContext contextoExterno = FacesContext.getCurrentInstance().getExternalContext();
        RequestContext contextoActual = RequestContext.getCurrentInstance();
        // encripto la contraseña recibida
        clave = "";
        clave = CriptPass.encriptar(contrasenia);
        
        try{
            // valdo el usuario
            usuario = backendSrv.validarUsuarioExt(cude, clave);
            if(usuario != null){

                logeado = true;

                JsfUtil.addSuccessMessage("Bienvenid@ " + usuario.getCude());

                contextoActual.addCallbackParam("estaLogeado", logeado);
                
                // obtengo el Establecimiento vinculado al cude
                establecimiento = backendSrv.getEstablecimientoByCude(Long.valueOf(leerFracCude("partido")), Long.valueOf(leerFracCude("numero")), Integer.valueOf(leerFracCude("crs")));

                // obtengo el path completo con el contexto del servlet y redirecciono según sea o no la primera vez que inicia sesión
                String ctxPath = ((ServletContext) contextoExterno.getContext()).getContextPath();
                if(usuario.isPrimeraVez()){
                    contextoExterno.redirect(ctxPath + "/faces/sesion/iniciadoPrimeraVez.xhtml");
                }else{
                    contextoExterno.redirect(ctxPath + "/faces/sesion/iniciado.xhtml");
                }

            }else{
                logeado = false;
                JsfUtil.addErrorMessage("No se han validado los datos ingresados, alguno de los dos o ambos son incorrectos.");
            }
        }catch(IOException ex){
            System.out.println("Hubo un error iniciando la sesión. " + ex.getMessage());
        }

    }
    
    public void recuperarContrasenia(){
        if(cude.equals("")){
            JsfUtil.addErrorMessage("Para recibir una nueva contraseña debe completar el campo CUDE.");
        }else{
            JsfUtil.addSuccessMessage("Su nueva contraseña ha sido enviada a su cuenta de correo electrónico.");
        }
    }
    
    public void enviarSolicitud(){
        newContrasenia = "";
        clave = "";
        if(recupEnviado){
            JsfUtil.addErrorMessage("Su solicitud ya fue enviada a su correo electrónico.");
        }else{
            // valido que el CUDE pertenezca a un usuario
            try{
                usuarioARecuperar = backendSrv.getUsuarioExt(recCude);
                
                if(usuarioARecuperar != null){
                    // creo una nueva contraseña
                    newContrasenia = CriptPass.generar();

                    // encripto la contraseña creada
                    clave = CriptPass.encriptar(newContrasenia);

                    // actualizo los datos del usuario
                    usuarioARecuperar.setClave(clave);

                    // inserto
                    backendSrv.editUsuarioExterno(usuarioARecuperar);

                    // envío las nuevas credenciales al usuario
                    if(!enviarCorreoNuevaPass()){
                        JsfUtil.addErrorMessage("Hubo un error enviando la nueva contraseña por correo electrónico.");
                        recupEnviado = false;
                    }else{
                        recupEnviado = true;
                        JsfUtil.addSuccessMessage("Su solicitud fue enviada con exito. Por favor consulte su cuenta "
                                + "de correo electrónico, por favor cierre el formulario.");
                    }
                }else{
                    JsfUtil.addErrorMessage("El CUDE ingresado no registra una cuenta vinculada. "
                            + "Para mayoreres precisiones, le solicitamos contactarse con nosotros "
                            + "mediante correo electrónico a dpyra@ambiente.gob.ar ¡Muchas gracias!");
                    recupEnviado = false;
                }
                
            }catch(Exception ex){
                JsfUtil.addErrorMessage("Hubo un error recuperando el usuario. " + ex.getMessage());
            }
        }
        recCude = "";
    }
    
    public String actualizarContrasenia(){
        int i = 0;
        int num = 0;
        String pass;
        
        try{
            // valido que la nueva contraseña y la repetición coincidan
            if(newContrasenia.equals(newContrasenia_2)){
                // valido que la nueva contraseña tenga ocho caracteres y al menos dos números.
                if(newContrasenia.length() >= 8){
                    while(i < newContrasenia.length()){
                        if(newContrasenia.charAt(i) > 47 && newContrasenia.charAt(i) < 58){
                            num ++;
                        }
                        i ++;
                    }
                }
                if(num < 2){
                    JsfUtil.addErrorMessage("La nueva contraseña nueva debe contener al menos dos (2) números.");
                    return null;
                }

                // encripto
                pass = CriptPass.encriptar(newContrasenia);

                // actualizo el usuario
                usuario.setClave(pass);
                usuario.setPrimeraVez(false);
                backendSrv.editUsuarioExterno(usuario);

                // envío correo
                if(!enviarCorreoCambioPass()){
                    JsfUtil.addErrorMessage("Hubo un error enviando la nueva contraseña por correo electrónico.");
                    recupEnviado = false;
                }

                // mando mensaje y retorno al inicio son sesión
                JsfUtil.addSuccessMessage("Su contraseña ha sido actualizada correctamente");
                return "inciar";
            }else{
                JsfUtil.addErrorMessage("La nueva contraseña nueva no coincide con su confrimación.");
                return null;
            }
        }catch(Exception ex){
            JsfUtil.addErrorMessage("Hubo un error actualizando la contraseña. " + ex.getMessage());
            return null;
        }
    }
    
    public void logout(){

        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        
        // obtengo el path completo con el contexto del servlet
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();

        try {
          ((HttpSession) ctx.getSession(false)).invalidate();
          ctx.redirect(ctxPath + "/faces/libre/iniciar.xhtml");
        } catch (IOException ex) {
            System.out.println("Hubo un error cerrando la sesión.");
        }        
    }      
    

    /*********************
    ** Métodos privados **
    **********************/
    
    private BackendSrv getFacade() {
        return backendSrv;
    }   

    /**
     * Método para el envío del correo al usuario recién creado
     * @return 
     */
    private boolean enviarCorreoNuevaPass(){  
        boolean result;
        String bodyMessage;
        mensaje = new MimeMessage(mailSesion);
        bodyMessage = "<p>Estimado/a</p> "
                + "<p>Se ha generado una nueva contraseña para acceder al sistema: "
                + "<p><strong>usuario:</strong> " + usuarioARecuperar.getCude() + "<br/> "
                + "<strong>contraseña:</strong> " + newContrasenia + "</p> "
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
            mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(usuarioARecuperar.getEmail()));
            mensaje.setSubject("Sistema de Gestión de Efluentes líquidos - Nueva contraseña");
            mensaje.setContent(bodyMessage, "text/html; charset=utf-8");
            
            Date timeStamp = new Date();
            mensaje.setSentDate(timeStamp);
            
            Transport.send(mensaje);
            
            result = true;
            
        }catch(MessagingException ex){
            result = false;
            System.out.println("Hubo un error enviando la nueva contraseña por correo electrónico." + ex.getMessage());
        }
        
        return result;
    }      
    
    /**
     * Método para el envío del correo al usuario con su contraseña actualizada.
     */
    private boolean enviarCorreoCambioPass(){
        boolean result;
        String bodyMessage;
        mensaje = new MimeMessage(mailSesion);
        bodyMessage = "<p>Estimado/a</p> "
                + "<p>Usted ha cambiado su contraseña de acceso al sisema: "
                + "<p><strong>usuario:</strong> " + usuario.getCude() + "<br/> "
                + "<strong>contraseña:</strong> " + newContrasenia + "</p> "
                + "<p>Podrá acceder mediante este link " + ResourceBundle.getBundle("/Bundle").getString("Servidor") + ResourceBundle.getBundle("/Bundle").getString("RutaAplicacion") + " <br/> "
                + "Podrá volver a modificarla cuando lo desée.</p> "
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
            mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(usuario.getEmail()));
            mensaje.setSubject("Sistema de Gestión de Efluentes líquidos - Cambio de contraseña");
            mensaje.setContent(bodyMessage, "text/html; charset=utf-8");
            
            Date timeStamp = new Date();
            mensaje.setSentDate(timeStamp);
            
            Transport.send(mensaje);
            
            result = true;
            
        }catch(MessagingException ex){
            result = false;
            System.out.println("Hubo un error enviando la nueva contraseña por correo electrónico." + ex.getMessage());
        }
        
        return result;
    }
    
    /**
     * Método para obtener el elemento interno que compone el CUDE
     * @param elemento: los elementos pueden ser: "partido", "numero" o "crs" ([partido]-[numero]-[crs])
     * @return 
     */
    private String leerFracCude(String elemento){
        int iGuion_1, iGuion_2;
        String numCrs;
        switch (elemento) {
            case "partido":
                iGuion_1 = usuario.getCude().indexOf("-");
                return usuario.getCude().substring(0, iGuion_1);
            case "numero":
                iGuion_1 = usuario.getCude().indexOf("-");
                numCrs = usuario.getCude().substring(iGuion_1 + 1, usuario.getCude().length() - 1);
                iGuion_2 = numCrs.indexOf("-");
                return numCrs.substring(0, iGuion_2);
            default:
                iGuion_1 = usuario.getCude().indexOf("-");
                numCrs = usuario.getCude().substring(iGuion_1 + 1, usuario.getCude().length());
                iGuion_2 = numCrs.indexOf("-");
                return numCrs.substring(iGuion_2 + 1, numCrs.length());
        }
    }    
    
    
    /********************************************************************
    ** Converter. Se debe actualizar la entidad y el facade respectivo **
    *********************************************************************/
    @FacesConverter(forClass = UsuarioExterno.class)
    public static class EstablecimientoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MbSesion controller = (MbSesion) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mbSesion");
            return controller.getUsuarioExterno(getKey(value));
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
            if (object instanceof UsuarioExterno) {
                UsuarioExterno o = (UsuarioExterno) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UsuarioExterno.class.getName());
            }
        }
    }       
}
