/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.facade;

import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.DeclaracionJurada;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento;
import ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.HistorialDeclaraciones;
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
public class HistorialDeclaracionesFacade extends AbstractFacade<HistorialDeclaraciones> {
    @PersistenceContext(unitName = "ar.gob.ambiente.aplicaciones_gestionEfluentesLiquidos-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistorialDeclaracionesFacade() {
        super(HistorialDeclaraciones.class);
    }
    
    /**
     * Método para obtener el registro de la última Declaración vigente para un Establecimiento
     * @param est: Establecimiento soble el que se consulta
     * @return 
     */
    public HistorialDeclaraciones getUltimoActivo(Establecimiento est){
        List<HistorialDeclaraciones> listHisDec;
        em = getEntityManager();    
        String queryString = "SELECT hd FROM HistorialDeclaraciones hd "
                + "WHERE hd.establecimiento = :est "
                + "AND hd.activa = true";  
        Query q = em.createQuery(queryString)
                .setParameter("est", est);
        listHisDec = q.getResultList();
        if(!listHisDec.isEmpty()){
            return listHisDec.get(0);
        }else{
            return null;
        }        
    }    
    
    /**
     * Método para obtener el Historial de la Declaración activa según la declaración
     * @param dec
     * @return 
     */
    public HistorialDeclaraciones getByDeclaracion(DeclaracionJurada dec){
        List<HistorialDeclaraciones> listHisDec;
        em = getEntityManager();    
        String queryString = "SELECT hd FROM HistorialDeclaraciones hd "
                + "WHERE hd.declaracion = :dec "
                + "AND hd.activa = true";  
        Query q = em.createQuery(queryString)
                .setParameter("dec", dec);
        listHisDec = q.getResultList();
        if(!listHisDec.isEmpty()){
            return listHisDec.get(0);
        }else{
            return null;
        } 
    }
}
