/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Recibo;
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
public class ReciboFacade extends AbstractFacade<Recibo> {
    @PersistenceContext(unitName = "ar.gob.ambiente.aplicaciones_gestionEfluentesLiquidos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReciboFacade() {
        super(Recibo.class);
    }
    
    public Integer getUltimo(){
        em = getEntityManager();    
        String queryString = "SELECT MAX(id) FROM recibo";
        Query q = em.createNativeQuery(queryString);
        if((Integer)q.getSingleResult() != null){
            return (Integer)q.getSingleResult();
        }
        return null;
    }
    
    public Recibo getByCodigo(String codigo){
        List<Recibo> listRec;
        em = getEntityManager();    
        String queryString = "SELECT rec FROM Recibo rec "
                + "WHERE rec.codigo = :codigo";  
        Query q = em.createQuery(queryString)
                .setParameter("codigo", codigo);
        listRec = q.getResultList();
        if(!listRec.isEmpty()){
            return listRec.get(0);
        }else{
            return null;
        } 
    }    
}
