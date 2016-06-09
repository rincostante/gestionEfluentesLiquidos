/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.DeclaracionJurada;
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
public class DeclaracionJuradaFacade extends AbstractFacade<DeclaracionJurada> {
    @PersistenceContext(unitName = "ar.gob.ambiente.aplicaciones_gestionEfluentesLiquidos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DeclaracionJuradaFacade() {
        super(DeclaracionJurada.class);
    }
    
    /**
     * Método para obtener la ultima Declaración generada 
     * @param cude: CUDE del Establecimiento asociado a la Declaración
     * @return 
     */
    public Integer getInsertada(String cude){    
        em = getEntityManager();    
        String queryString = "SELECT MAX(id) FROM declaracionjurada WHERE cude = ?1"; 
        Query q = em.createNativeQuery(queryString)
                .setParameter(1, cude);
        return (Integer)q.getSingleResult();
    }
}
