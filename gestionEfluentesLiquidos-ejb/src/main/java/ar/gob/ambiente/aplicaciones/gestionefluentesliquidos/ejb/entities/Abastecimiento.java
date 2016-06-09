

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
 * Clase que encapsula los datos correspondientes a los diferentes métodos de abastecimiento de agua del Establecimiento
 * @author rincostante
 */
@Entity
public class Abastecimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo agregado para poder persistir y evitar la Exception por no tener campos salvo la id
     */
    private boolean tieneAbastos;
    
    /**
     * Campo que muestra los Pozos que abastecen de agua al Establecimiento
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "abastecimiento_id", referencedColumnName = "id")
    private List<Pozo> pozos;  
    
    /**
     * Campo que muestra los Abastos que componen el Abstecimiento general de agua del Establecimiento
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "abastecimiento_id", referencedColumnName = "id")
    private List<Abasto> abastos;         
    
    /*****************
     ** Constructor **
     *****************/    
    public Abastecimiento(){
        pozos = new ArrayList<>();
        abastos = new ArrayList<>();
    }    

 
    /**********************
     * Métodos de acceso **
     **********************/    
    public boolean isTieneAbastos() {
        return tieneAbastos;
    }

    public void setTieneAbastos(boolean tieneAbastos) {
        this.tieneAbastos = tieneAbastos;
    }
    
    public List<Abasto> getAbastos() {
        return abastos;
    }

    public void setAbastos(List<Abasto> abastos) {
        this.abastos = abastos;
    }
    
    public List<Pozo> getPozos() {
        return pozos;
    }

    public void setPozos(List<Pozo> pozos) {
        this.pozos = pozos;
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
        if (!(object instanceof Abastecimiento)) {
            return false;
        }
        Abastecimiento other = (Abastecimiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Abstecimiento[ id=" + id + " ]";
    }
    
}
