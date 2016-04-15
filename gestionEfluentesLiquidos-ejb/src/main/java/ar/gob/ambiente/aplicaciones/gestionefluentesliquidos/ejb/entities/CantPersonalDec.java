

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Clase que encapsula la distribución del personal en el Establecimiento según lo presentado en la Declaración
 * @author rincostante
 */
@Entity
public class CantPersonalDec implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarada la cantidad de personas para cada rubro, presentada en la Declaración
     */
    @Column (nullable=false)
    @NotNull(message = "El campo cantidad no puede ser nulo")
    private int cantidad;    
    
    /**
     * Campo que guarda la clave del tipo de personal.
     * Con el método getDescripición, se accede a la descripción correpondiente a la clave
     */
    @Column (nullable=false)
    @NotNull(message = "El campo tipoPers no puede ser nulo")
    private int tipoPers;      
    
    /**
     * Listado para seleccionar la descripción del rubro del personal
     */
    @Transient
    private static final Map<Integer, String> MP_TIPO_PERS;
    static{
       MP_TIPO_PERS = new TreeMap<>();
       MP_TIPO_PERS.put(1, "DE OFICINA");
       MP_TIPO_PERS.put(2, "DE FABRICA");
    }    

    public static Map<Integer, String> getMP_TIPO_PERS() {
        return MP_TIPO_PERS;
    }  
    
    public int getTipoPers() {
        return tipoPers;
    }

    public void setTipoPers(int tipoPers) {
        this.tipoPers = tipoPers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return MP_TIPO_PERS.get(tipoPers);
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
        if (!(object instanceof CantPersonalDec)) {
            return false;
        }
        CantPersonalDec other = (CantPersonalDec) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.CantPersonalDec[ id=" + id + " ]";
    }
    
}
