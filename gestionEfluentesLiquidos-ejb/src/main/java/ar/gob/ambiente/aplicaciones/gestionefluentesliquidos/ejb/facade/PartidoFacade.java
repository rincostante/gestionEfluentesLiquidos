/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Partido;
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
public class PartidoFacade extends AbstractFacade<Partido> {
    @PersistenceContext(unitName = "ar.gob.ambiente.aplicaciones_gestionEfluentesLiquidos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PartidoFacade() {
        super(Partido.class);
    }
    
    /**
     * Método para validad que no exista un Partido con ese nombre
     * @param nombre
     * @return 
     */
    public boolean noExiste(String nombre){
        em = getEntityManager();
        String queryString = "SELECT par FROM Partido par "
                + "WHERE par.nombre = :nombre";
        Query q = em.createQuery(queryString)
                .setParameter("nombre", nombre);
        return q.getResultList().isEmpty();
    }  
    
    /**
     * Método para obtener el partido según el id de Departamento del Registro territorial
     * como en la migración se invirtieron los valos migrados entre las id de RT y DPyRA se invierten 
     * también en su búsqueda provisoriamente hasta que se arregle.
     * @param idDpyra
     * @return 
     */
    public Partido getXIdRt(Long idDpyra){
        List<Partido> lstPart;
        em = getEntityManager();
        String queryString = "SELECT par FROM Partido par "
                + "WHERE par.idDpyra = :idDpyra";
        Query q = em.createQuery(queryString)
                .setParameter("idDpyra", idDpyra);
        lstPart = q.getResultList();
        if(lstPart.isEmpty()){
            return null;
        }else{
            return lstPart.get(0);
        }
    }
}
