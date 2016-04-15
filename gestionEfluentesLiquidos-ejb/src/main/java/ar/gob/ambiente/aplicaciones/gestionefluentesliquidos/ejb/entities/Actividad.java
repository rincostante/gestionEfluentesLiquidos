

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Clase paramétrica que encapsula los datos correspondientes a las Actividades de los Establecimientos administrados por la DPyRA
 * Al validarse las DDJJ se importará cada una (en caso de no existir ya) al RUP
 * @author rincostante
 */
@Entity
public class Actividad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarda el código alfanumérico existente de la Actividad
     */
    @Column (nullable=true, length=10)
    @Size(message = "El campo codigo no puede tener más de 10 caracteres", min = 1, max = 10)
    private String codigo;      
    
    /**
     * Campo que guarda la descripón de la Actividad
     * En caso de estar registrado en el SIMEL, surge del servicio.
     */
    @Column (nullable=false, length=500)
    @NotNull(message = "El campo descripcion no puede ser nulo")
    @Size(message = "El campo descripcion no puede tener más de 500 caracteres", min = 1, max = 500)
    private String descripcion;          

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if (!(object instanceof Actividad)) {
            return false;
        }
        Actividad other = (Actividad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Actividad[ id=" + id + " ]";
    }
    
}
