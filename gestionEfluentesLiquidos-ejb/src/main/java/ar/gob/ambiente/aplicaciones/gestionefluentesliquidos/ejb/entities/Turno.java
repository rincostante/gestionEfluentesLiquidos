

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 * Clase que encapsula los datos correspondientes a cada turno laboral diario
 * @author rincostante
 */
@Entity
public class Turno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarda el número de orden del turno
     */
    private int numOrden;
    
    /**
     * Campo que guarda la hora de inicio del turno
     */
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date horaInic;
    
    /**
     * Campo que guarda la hora de finalización del turno
     */
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date horaFinal;    

    /**********************
     * Métodos de acceso **
     **********************/       
    public int getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(int numOrden) {
        this.numOrden = numOrden;
    }

    public Date getHoraInic() {
        return horaInic;
    }

    public void setHoraInic(Date horaInic) {
        this.horaInic = horaInic;
    }

    public Date getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Date horaFinal) {
        this.horaFinal = horaFinal;
    }
       
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turno)) {
            return false;
        }
        Turno other = (Turno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Turno[ id=" + id + " ]";
    }
    
}
