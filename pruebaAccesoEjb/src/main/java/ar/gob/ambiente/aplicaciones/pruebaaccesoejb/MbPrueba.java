/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.pruebaaccesoejb;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.UsuarioExterno;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.srv.BackendSrv;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author rincostante
 */
public class MbPrueba implements Serializable{
    
    @EJB
    private BackendSrv backEndSrv;
    
    private UsuarioExterno usExt;

    public MbPrueba() {
    }
    
    @PostConstruct
    public void init(){
        
    }
    
    /**********************
     * Métodos de acceso **
     **********************/
    public UsuarioExterno getUsExt() {
        return usExt;
    }

    public void setUsExt(UsuarioExterno usExt) {
        this.usExt = usExt;
    }

    
    
    public void iniciar(){
        //usExt = new UsuarioExterno();
    }
    
    public void buscarUsExt(){
        String cude = "065-698745-2";
        
        usExt = backEndSrv.getUsuarioExt(cude);
    }
}
