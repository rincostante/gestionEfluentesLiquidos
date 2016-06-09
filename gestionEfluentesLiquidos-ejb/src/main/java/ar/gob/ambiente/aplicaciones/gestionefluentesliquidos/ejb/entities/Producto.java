

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * Clase que encapsula los datos de los Productos generados por los Establecimientos
 * @author rincostante
 */
@Entity
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarda la descripción del Producto elaborado por el Establecimiento
     */
    private String descripcion;
    
    /**
     * Campo que guarada la cantidad del producto generado anualmente por el Establecimiento
     */
    @Column (nullable=false)
    @NotNull(message = "El campo cantidadAnual no puede ser nulo")
    private float cantidadAnual; 
    
    /**
     * Guarda la Unidad de medida del Producto fabricado por el Establecimiento
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="unidad_id")
    private Unidad unidad;    
    
    /**
     * Campo que muestra las Materias primas que componen el Producto generado por el Establecimiento
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private List<Materia> materias;        
    

    /*****************
     ** Constructor **
     *****************/
    
    public Producto(){
        materias = new ArrayList<>();
    }    
    
    
    /**********************
     * Métodos de acceso **
     **********************/

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }


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
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Producto[ id=" + id + " ]";
    }
    
}
