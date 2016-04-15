

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Clase que encapsula los datos correspondientes a las fechas vinculadas a las actividades del Establecimiento, presentada en la Declaración
 * @author rincostante
 */
@Entity
public class FechaDec implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarda la fecha específica
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column (nullable=false)
    @NotNull(message = "El campo fecha no puede ser nulo")    
    private Date fecha;    
    
    /**
     * Campo que guarda la clave del tipo de fecha.
     * Con el método getDescripición, se accede a la descripción correpondiente a la clave
     */
    @Column (nullable=false)
    @NotNull(message = "El campo tipoFecha no puede ser nulo")
    private int tipoFecha;  
    
    /**
     * Listado para seleccionar la descripción de la fecha
     */
    @Transient
    private static final Map<Integer, String> MP_TIPO_FECHAS;
    static{
       MP_TIPO_FECHAS = new TreeMap<>();
       MP_TIPO_FECHAS.put(1, "DE INSTALACION DEL ESTABLECIMIENTO");
       MP_TIPO_FECHAS.put(2, "DE CAMBIO DE RAZON SOCIAL");
       MP_TIPO_FECHAS.put(3, "DE DE CESACION DE ACTIVIDADES DEL ESTABLECIMIENTO");
    }

    public int getTipoFecha() {
        return tipoFecha;
    }

    public void setTipoFecha(int tipoFecha) {
        this.tipoFecha = tipoFecha;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return MP_TIPO_FECHAS.get(tipoFecha);
    }

    public static Map<Integer, String> getMP_TIPO_FECHAS() {
        return MP_TIPO_FECHAS;
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
        if (!(object instanceof FechaDec)) {
            return false;
        }
        FechaDec other = (FechaDec) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.FechaDec[ id=" + id + " ]";
    }
    
}
