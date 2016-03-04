/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.UsuarioExterno;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rincostante
 */
@Stateless
public class UsuarioExternoFacade extends AbstractFacade<UsuarioExterno> {
    @PersistenceContext(unitName = "ar.gob.ambiente.aplicaciones_gestionEfluentesLiquidos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioExternoFacade() {
        super(UsuarioExterno.class);
    }
    
    /**
     * Método para validad que no exista un Usuario con ese CUDE
     * @param cude
     * @return 
     */
    public boolean noExiste(String cude){
        em = getEntityManager();
        String queryString = "SELECT us FROM UsuarioExterno us "
                + "WHERE us.cude = :cude";
        Query q = em.createQuery(queryString)
                .setParameter("cude", cude);
        return q.getResultList().isEmpty();
    }     
    
   /**
     * Método que devuelve un LIST con las entidades HABILITADAS
     * @return: True o False
     */
    public List<UsuarioExterno> getActivos(){
        em = getEntityManager();        
        List<UsuarioExterno> result;
        String queryString = "SELECT us FROM UsuarioExterno us " 
                + "WHERE us.admin.habilitado = true";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    }      
    
    /**
     * Método que devulve los datos del usuario logeado
     * @param cude
     * @return 
     */
    public UsuarioExterno getUsuario(String cude){
        em = getEntityManager();
        List<UsuarioExterno> lUs;
        String queryString = "SELECT us FROM UsuarioExterno us "
                + "WHERE us.cude = :cude";
        Query q = em.createQuery(queryString)
                .setParameter("cude", cude);
        lUs = q.getResultList();
        if(!lUs.isEmpty()){
            return lUs.get(0);
        }else{
            return null;
        }
    }         
    
    /**
     * Método a implementar según las características de la aplicación
     * @param id
     * @return 
     */
    public boolean noTieneDependencias(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }        
}
