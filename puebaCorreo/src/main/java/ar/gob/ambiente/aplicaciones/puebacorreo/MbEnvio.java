/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.puebacorreo;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Resource;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author rincostante
 */
public class MbEnvio implements Serializable{

    @Resource(name="mail/ambiente")
    private Session mailSesion;
    
    private Message mensaje;
    
    public MbEnvio() {
    }
    
    public void enviar(){
        mensaje = new MimeMessage(mailSesion);
        try{
            mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress("rincostante@gmail.com"));
            mensaje.setSubject("Prueba envío");
            mensaje.setText("Algo para escribir en el mensaje");
            
            Date timeStamp = new Date();
            
            mensaje.setSentDate(timeStamp);
            mensaje.setHeader("X-Mailer", "rincostante@ambiente.gob.ar");
            
            Transport.send(mensaje);
            
        }catch(MessagingException ex){
            System.out.println("Hubo un error enviando el correo de prueba" + ex.getMessage());
        }
    }
    
}
