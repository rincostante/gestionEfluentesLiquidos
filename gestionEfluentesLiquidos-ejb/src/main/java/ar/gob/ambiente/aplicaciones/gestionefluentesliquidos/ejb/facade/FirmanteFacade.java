/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Firmante;
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
public class FirmanteFacade extends AbstractFacade<Firmante> {
    @PersistenceContext(unitName = "ar.gob.ambiente.aplicaciones_gestionEfluentesLiquidos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FirmanteFacade() {
        super(Firmante.class);
    }
    
    /**
     * Método para validad que no exista un Firmante con ese id en el Registro Unico de Personas Fisicas
     * @param idRupFis
     * @return 
     */
    public boolean noExiste(Long idRupFis){
        em = getEntityManager();
        String queryString = "SELECT fir FROM Firmante fir "
                + "WHERE fir.idRupFis = :idRupFis";
        Query q = em.createQuery(queryString)
                .setParameter("idRupFis", idRupFis);
        return q.getResultList().isEmpty();
    } 
    
    /**
     * Método para validar que no exista un Firmante con el dni enviado
     * @param dni
     * @return 
     */
    public boolean noExisteDni(Long dni){
        em = getEntityManager();
        String queryString = "SELECT fir FROM Firmante fir "
                + "WHERE fir.dni = :dni";
        Query q = em.createQuery(queryString)
                .setParameter("dni", dni);
        return q.getResultList().isEmpty();
    } 
    
    /**
     * Método para validar que no exista un Firmante con el dni enviado
     * @param cuit
     * @return 
     */
    public boolean noExisteCuit(Long cuit){
        em = getEntityManager();
        String queryString = "SELECT fir FROM Firmante fir "
                + "WHERE fir.cuit = :cuit";
        Query q = em.createQuery(queryString)
                .setParameter("cuit", cuit);
        return q.getResultList().isEmpty();
    }     
    
    /**
     * Método que devuelve un LIST con las entidades HABILITADAS
     * @return: True o False
     */
    public List<Firmante> getActivos(){
        em = getEntityManager();        
        List<Firmante> result;
        String queryString = "SELECT fir FROM Firmante fir " 
                + "WHERE fir.admin.habilitado = true";                   
        Query q = em.createQuery(queryString);
        result = q.getResultList();
        return result;
    } 
    
    /**
     * Método que devuelve el Firmante correspondiente al CUIT
     * @param cuit
     * @return 
     */
    public Firmante getByCuit(Long cuit){
        em = getEntityManager();
        List<Firmante> lFir;
        String queryString = "SELECT fir FROM Firmante fir "
                + "WHERE fir.cuit = :cuit";
        Query q = em.createQuery(queryString)
                .setParameter("cuit", cuit);
        lFir = q.getResultList();
        if(!lFir.isEmpty()){
            return lFir.get(0);
        }else{
            return null;
        }
    }
    
    /**
     * Método que devuelve el Firmante correspondiente al DNI
     * @param dni
     * @return 
     */
    public Firmante getByDni(Long dni){
        em = getEntityManager();
        List<Firmante> lFir;
        String queryString = "SELECT fir FROM Firmante fir "
                + "WHERE fir.dni = :dni";
        Query q = em.createQuery(queryString)
                .setParameter("dni", dni);
        lFir = q.getResultList();
        if(!lFir.isEmpty()){
            return lFir.get(0);
        }else{
            return null;
        }
    }
    
    public List<Firmante> getByOrder(){
        em = getEntityManager();
        String queryString = "SELECT fir FROM Firmante fir "
                + "ORDER BY fir.nombreYApellido";
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }  
    
    /**
     * Devuelve el total de Firmantes registrados
     * @return 
     */
    public Long getTotal(){
       em = getEntityManager();  
       String queryString = "SELECT COUNT(id) FROM firmante";
       Query q = em.createNativeQuery(queryString);
       return (Long)q.getSingleResult();
    }    
}

