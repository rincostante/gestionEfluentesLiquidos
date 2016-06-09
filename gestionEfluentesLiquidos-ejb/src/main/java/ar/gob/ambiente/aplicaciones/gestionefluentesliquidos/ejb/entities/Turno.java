

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private int horasInicio;
    
    /**
     * Campo que guarda los minutos inicio del turno
     */
    private int minInicio;
    
    /**
     * Campo que guarda la hora de finalización del turno
     */
    private int horasFin;
    
    /**
     * Campo que guarda los minutos finalizazión del turno
     */
    private int minFin;     

    /**********************
     * Métodos de acceso **
     **********************/       
    public int getHorasInicio() {
        return horasInicio;
    }

    public void setHorasInicio(int horasInicio) {
        this.horasInicio = horasInicio;
    }

    public int getMinInicio() {
        return minInicio;
    }

    public void setMinInicio(int minInicio) {
        this.minInicio = minInicio;
    }

    public int getHorasFin() {
        return horasFin;
    }

    public void setHorasFin(int horasFin) {
        this.horasFin = horasFin;
    }

    public int getMinFin() {
        return minFin;
    }

    public void setMinFin(int minFin) {
        this.minFin = minFin;
    }
       
    public int getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(int numOrden) {
        this.numOrden = numOrden;
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
