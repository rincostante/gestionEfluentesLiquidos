

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * Clase que encapsula el conjunto de días semanales trabajados
 * @author rincostante
 */
@Entity
public class Horario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo agregado para poder persistir y evitar la Exception por no tener campos salvo la id
     */
    private boolean tieneDias;    
    
    /**
     * Campo que muestra los días que componen el horario semanal
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "horario_id", referencedColumnName = "id")
    private List<Dia> dias;   
    
    /*****************
     ** Constructor **
     *****************/    
    public Horario(){
        dias = new ArrayList<>();
    }      

    /**********************
     * Métodos de acceso **
     **********************/
    public boolean isTieneDias() {
        return tieneDias;
    }

    public void setTieneDias(boolean tieneDias) {
        this.tieneDias = tieneDias;
    }

    public List<Dia> getDias() {
        return dias;
    }

    public void setDias(List<Dia> dias) {
        this.dias = dias;
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
        if (!(object instanceof Horario)) {
            return false;
        }
        Horario other = (Horario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Horario[ id=" + id + " ]";
    }
    
}
