/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Rol;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Usuario;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.UsuarioExterno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.RolFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.UsuarioExternoFacade;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade.UsuarioFacade;
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
}
