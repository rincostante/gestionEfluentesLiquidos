/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.HistorialFirmantes;
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
public class HistorialFirmantesFacade extends AbstractFacade<HistorialFirmantes> {
    @PersistenceContext(unitName = "ar.gob.ambiente.aplicaciones_gestionEfluentesLiquidos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistorialFirmantesFacade() {
        super(HistorialFirmantes.class);
    }
    
    /**
     * Método para obtener el registro del último cambio de Firmante para un Establecimiento
     * @param est: Establecimiento soble el que se consulta
     * @return 
     */
    public HistorialFirmantes getUltimoActivo(Establecimiento est){
        List<HistorialFirmantes> listHisFirm;
        em = getEntityManager();    
        String queryString = "SELECT hf FROM HistorialFirmantes hf "
                + "WHERE hf.establecimiento = :est "
                + "AND hf.activa = true";  
        Query q = em.createQuery(queryString)
                .setParameter("est", est);
        listHisFirm = q.getResultList();
        if(!listHisFirm.isEmpty()){
            return listHisFirm.get(0);
        }else{
            return null;
        }
    }    
}
