

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
 * Entidad que encapsula los distintos tratamientos previos que puede sufrir la descarga del efluente líquido
 * @author rincostante
 */
@Entity
public class Tratamiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarda la clave del nombre del tratamiento.
     * Con el método getNombre, se accede a la descripción correpondiente a la clave
     */
    @Column (nullable=false)
    @NotNull(message = "El campo claveNombre no puede ser nulo")
    private int claveNombre;
    
    /**
     * Campo que guarda el valor del tipo de tratamiento
     * Podrá ser entre 1 y 6
     */
    @Column (nullable=false)
    @NotNull(message = "El campo valor no puede ser nulo")
    private int valor;
    
    /**
     * Listado para seleccionar el nombre del tratamiento
     */
    @Transient
    private static final Map<Integer, String> MP_NOMBRES;
    static{
       MP_NOMBRES = new TreeMap<>();
       MP_NOMBRES.put(1, "PRETRATAMIENTO");
       MP_NOMBRES.put(2, "PRIMARIO FISICO");
       MP_NOMBRES.put(3, "PRIMARIO QUIMICO");
       MP_NOMBRES.put(4, "SECUNDARIO");
       MP_NOMBRES.put(5, "DESINFECCION");
    }        

    /**********************
     * Métodos de acceso **
     **********************/   
    public String getNombre() {
        return MP_NOMBRES.get(claveNombre);
    }    
    
    public static Map<Integer, String> getMP_NOMBRES() {
        return MP_NOMBRES;
    }  
    
    public int getClaveNombre() {
        return claveNombre;
    }

    public void setClaveNombre(int claveNombre) {
        this.claveNombre = claveNombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
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
        if (!(object instanceof Tratamiento)) {
            return false;
        }
        Tratamiento other = (Tratamiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Tratamiento[ id=" + id + " ]";
    }
    
}
