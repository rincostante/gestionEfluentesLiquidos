

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Clase que encapsula las cantidades de m2 de superficie ocupada por el Establecimiento.
 * Tanto cubierta como libre.
 * @author rincostante 
 */
@Entity
public class SuperficieDec implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarada la cantidad de m2 para el tipo de superficie presentada en la Declaración
     */
    @Column (nullable=false)
    @NotNull(message = "El campo cantidad no puede ser nulo")
    private float cantidad;  
    
    /**
     * Campo que guarda la clave del tipo de superficie.
     * Con el método getTipo, se accede a la tipo de superficie correpondiente a la clave
     */
    @Column (nullable=false)
    @NotNull(message = "El campo tipoSup no puede ser nulo")
    private int tipoSup;      
    
    /**
     * Listado para seleccionar el tipo de superficie
     */
    @Transient
    private static final Map<Integer, String> MP_TIPO_SUP;
    static{
       MP_TIPO_SUP = new TreeMap<>();
       MP_TIPO_SUP.put(1, "CUBIERTA");
       MP_TIPO_SUP.put(2, "LIBRE");
    }       

    public static Map<Integer, String> getMP_TIPO_SUP() {
        return MP_TIPO_SUP;
    }     
    
    public int getTipoSup() {
        return tipoSup;
    }

    public void setTipoSup(int tipoSup) {
        this.tipoSup = tipoSup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return MP_TIPO_SUP.get(tipoSup);
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
        if (!(object instanceof SuperficieDec)) {
            return false;
        }
        SuperficieDec other = (SuperficieDec) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.SuperficieDoc[ id=" + id + " ]";
    }
    
}
