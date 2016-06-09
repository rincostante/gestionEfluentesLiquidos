
package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Clase que encapsula los datos correspondientes a la Materia prima de elaboración de los Productos
 * @author rincostante
 */
@Entity
public class Materia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarda la descripción de la Materia prima
     */
    private String descripcion; 
    
    /**
     * Campo que guarada la cantidad de la Materia prima consumida para la elaboración del Producto
     */
    @Column (nullable=false)
    @NotNull(message = "El campo cantidadAnual no puede ser nulo")
    private float cantidadAnual; 
    
    /**
     * Guarda la Unidad de medida de la Materia prima consumida para la elaboración del Producto
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="unidad_id")
    private Unidad unidad;  

    
    /**********************
     * Métodos de acceso **
     **********************/    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public float getCantidadAnual() {
        return cantidadAnual;
    }

    public void setCantidadAnual(float cantidadAnual) {
        this.cantidadAnual = cantidadAnual;
    }

    public Unidad getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
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
        if (!(object instanceof Materia)) {
            return false;
        }
        Materia other = (Materia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Materia[ id=" + id + " ]";
    }
    
}
