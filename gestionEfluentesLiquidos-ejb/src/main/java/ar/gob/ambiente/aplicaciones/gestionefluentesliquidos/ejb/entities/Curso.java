

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Clase que encapsula los datos del curso de agua en el que se realizan las descargas de efluentes
 * @author carmendariz
 */
@Entity
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Transient
    private static final List<String> LST_CUENCAS;
    static{
      ArrayList<String> tmp = new ArrayList<>();
      tmp.add("MATANZA-RIACHUELO");
      tmp.add("RIO DE LA PLATA");
      tmp.add("RECONQUISTA");
      tmp.add("COLECTORA");
      LST_CUENCAS = Collections.unmodifiableList(tmp);
    }
   
    /**
     * Nombre del curso de agua
     */
    @Column (nullable=false, length=100, unique=false)
    @NotNull(message = "El campo nombre no puede ser nulo")
    @Size(message = "El campo nombre no puede tener más de 100 caracteres", min = 1, max = 100)
    private String nombre;
    
    /**
     * Cuenca a la que pertenece el curso de agua
     */
    @Column (nullable=false, length=100, unique=false)
    @NotNull(message = "El campo cuenca no puede ser nulo")
    @Size(message = "El campo nombre no puede tener más de 100 caracteres", min = 1, max = 100)
    private String cuenca;
    
    //@OneToMany(mappedBy="curso")
    //private List<Descarga> descargas;

    public static List<String> getLST_CUENCAS() {
    return LST_CUENCAS;
    }

    public Long getId() {
    return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCuenca() {
        return cuenca;
    }

    public void setCuenca(String cuenca) {
        this.cuenca = cuenca;
    }
/*
    public List<Descarga> getDescargas() {
        return descargas;
    }

    public void setDescargas(List<Descarga> descargas) {
        this.descargas = descargas;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Curso[ id=" + id + " ]";
    }
    
}