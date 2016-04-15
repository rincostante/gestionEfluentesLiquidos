

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que encapsula los datos del Partido territorial dentro del cual está emplazado el establecimiento
 * @author rincostante
 */
@XmlRootElement(name = "partido")
@Entity
@Table(name = "partido")
public class Partido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Referencia al departamento en el Registro territorial
     */
    @Column (nullable=false, unique=true)
    @NotNull(message = "El campo idRt no puede ser nulo")
    private Long idRt;
    
    /**
     * Guarda el nombre del partido
     */
    @Column (nullable=false, length=40, unique=true)
    @NotNull(message = "El campo nombre no puede ser nulo")
    @Size(message = "El campo nombre no debe ser mayor de 40 caracteres", min = 1, max = 40)
    private String nombre;
    
    
    @Column (nullable=false, unique=true)
    @NotNull(message = "El campo idDpyra no puede ser nulo")
    private Long idDpyra;
    
    /**
     * Muestra el conjunto de los establecimientos emplazados en el partido
     */
    @OneToMany(mappedBy="partido")
    private List<Establecimiento> establecimientos;
    
    public Partido(){
        establecimientos = new ArrayList<>();
    }    

    public Long getIdDpyra() {
        return idDpyra;
    }

    public void setIdDpyra(Long idDpyra) {
        this.idDpyra = idDpyra;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getIdRt() {
        return idRt;
    }

    public void setIdRt(Long idRt) {
        this.idRt = idRt;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public List<Establecimiento> getEstablecimientos() {
         return establecimientos;
    }
    
    public void setEstablecimientos(List<Establecimiento> establecimientos) {
         this.establecimientos = establecimientos;
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
        if (!(object instanceof Partido)) {
            return false;
        }
        Partido other = (Partido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Partido[ id=" + id + " ]";
    }
    
}