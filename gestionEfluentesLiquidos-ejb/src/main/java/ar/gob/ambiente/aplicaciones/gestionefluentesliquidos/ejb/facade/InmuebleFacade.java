/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Inmueble;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rincostante
 */
@Stateless
public class InmuebleFacade extends AbstractFacade<Inmueble> {
    @PersistenceContext(unitName = "ar.gob.ambiente.aplicaciones_gestionEfluentesLiquidos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InmuebleFacade() {
        super(Inmueble.class);
    }
    
    /**
     * Devuelve el total de Establecimientos georereferenciados por el usuario
     * @return 
     */
    public Long getTotalGeo(){
       em = getEntityManager();  
       String queryString = "SELECT COUNT(id) FROM inmueble WHERE latitud <> 0";
       Query q = em.createNativeQuery(queryString);
       return (Long)q.getSingleResult();
    }       
}
