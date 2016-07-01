/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
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
public class EstablecimientoFacade extends AbstractFacade<Establecimiento> {
    @PersistenceContext(unitName = "ar.gob.ambiente.aplicaciones_gestionEfluentesLiquidos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EstablecimientoFacade() {
        super(Establecimiento.class);
    }
    
    /**
     * Método que devuelve el Establecimiento correpondiente al CUDE
     * @param idPartido
     * @param numEst
     * @param crs
     * @return 
     */
    public Establecimiento getByCude(Long idPartido, Long numEst, int crs){
        List<Establecimiento> lstEst;
        em = getEntityManager();
        String queryString = "SELECT est FROM Establecimiento est "
                + "WHERE est.partido.idDpyra = :idPartido "
                + "AND est.numero = :numEst "
                + "AND est.crs = :crs";
        Query q = em.createQuery(queryString)
                .setParameter("idPartido", idPartido)
                .setParameter("numEst", numEst)
                .setParameter("crs", crs);
        lstEst = q.getResultList();
        if(lstEst.isEmpty()){
            return null;
        }else{
            return lstEst.get(0);
        }
    }
    
    /**
     * Método que devuelve todos los establecimientos vinculados el CUIT recibido
     * @param cuit
     * @return 
     */
    public List<Establecimiento> getByCuit(Long cuit){
        em = getEntityManager();
        String queryString = "SELECT est FROM Establecimiento est "
                + "WHERE est.cuit = :cuit";
        Query q = em.createQuery(queryString)
                .setParameter("cuit", cuit);
        return q.getResultList();
    }
    
    /**
     * Método que devuelve todos los Establecimientos habilitados
     * @return 
     */
    public List<Establecimiento> getHabilitados(){
        em = getEntityManager();
        String queryString = "SELECT est FROM Establecimiento est "
                + "WHERE est.admin.habilitado = true"; 
        Query q = em.createQuery(queryString);
        return q.getResultList();
    }    
}
