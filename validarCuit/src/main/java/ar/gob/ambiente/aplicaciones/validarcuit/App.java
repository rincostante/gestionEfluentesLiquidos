package ar.gob.ambiente.aplicaciones.validarcuit;

import ar.gob.ambiente.aplicaciones.validarcuit.entities.EstabDatosI;
import ar.gob.ambiente.aplicaciones.validarcuit.ws.CuitAfip;
import ar.gob.ambiente.aplicaciones.validarcuit.ws.CuitAfipWs;
import ar.gob.ambiente.aplicaciones.validarcuit.ws.CuitAfipWs_Service;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.ws.WebServiceRef;

/**
 *
 */
public class App 
{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/vmdeswebjava.medioambiente.gov.ar_8080/CuitAfipWs/CuitAfipWs.wsdl")
    private static CuitAfipWs_Service service;

    public static void main( String[] args )
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ar.gob.ambiente.aplicaciones_validarCuit_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        String queryString;
        List<EstabDatosI> lstEstablecimientos;
        CuitAfip persona;
        
        try{
            queryString = "SELECT ed FROM EstabDatosI ed WHERE ed.rsValidada IS NULL";
            Query q = em.createQuery(queryString);
            lstEstablecimientos = q.getResultList();
            
            /**
             * Por cada establecimiento, valido el cuit contra el servicio.
             * En la tabla agrego un campo para la razón social obtenida.
             */

            for(EstabDatosI est : lstEstablecimientos){
                if(est.getCuit() != null){
                    // obtengo la persona relacionada al cuit
                    persona = obtenerPersona(Long.valueOf(est.getCuit()));
                    // si obtuve una persona actualizo el establecimiento
                    if(persona != null){
                        if(persona.getPejRazonSocial() != null){
                            // actualizo
                            est.setRsValidada(persona.getPejRazonSocial());
                            // persisto
                            em.getTransaction().begin();
                            em.persist(est);
                            em.getTransaction().commit();
                        }
                    }
                }
            }            

        }catch(NumberFormatException ex){
            System.out.println("Hubo un error leyendo los establecimientos. " + ex.getMessage());
        }finally{
            em.close();
        }
    }

    private static CuitAfip obtenerPersona(Long cuit){
        try {
            service = new CuitAfipWs_Service();
            CuitAfipWs port = service.getCuitAfipWsPort();
            System.out.println("Persona retornada desde el servicio: " + cuit);
            return port.verPersona(cuit);
        } catch (Exception ex) {
            System.out.println("Hubo un error ejecutando el método verPersona() del servicio. " + ex.getMessage());
            return null;
        }
    }
}
