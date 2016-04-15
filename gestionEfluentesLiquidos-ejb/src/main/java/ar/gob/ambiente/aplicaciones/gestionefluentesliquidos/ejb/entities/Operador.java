

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Clase que encapsula los datos correspondientes al Operador de residuos peligrosos administrador por la DRP
 * @author carmendariz
 */
@Entity
public class Operador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarda la referencia al Establecimiento Operador registrado en el 
     * Sistema de Manifiestos en línea (SIMEL), en caso que se encuentre registrado
     */
    @Column (nullable=false, unique=true)
    @NotNull(message = "El campo idSimel no puede ser nulo")
    private Long idSimel;          

    /**
     * Campo que guarda el campo identificador del Establecimiento Operador como usuario del SIMEL,
     * en caso que se encuentre registrado
     */
    @Column (nullable=false, length=13, unique=true)
    @NotNull(message = "El campo usSimel no puede ser nulo")
    @Size(message = "El campo usSimel no puede tener más de 13 caracteres", min = 1, max = 13)
    private String usSimel;         
    
    /**
     * Campo que guarda el CUIT de la Razón Social del Operador.
     * En caso de estar registrado en el SIMEL, surge del servicio
     * que provee la información.
     */
    @Column (nullable=false)
    @NotNull(message = "El campo cuit no puede ser nulo")
    private Long cuit;         
    
    /**
     * Campo que guarda la Razón Social del Establecimiento.
     * En caso de estar registrado en el SIMEL, surge del servicio.
     */
    @Column (nullable=false, length=100)
    @NotNull(message = "El campo usSimel no puede ser nulo")
    @Size(message = "El campo idSimel no puede tener más de 100 caracteres", min = 1, max = 100)
    private String razonSocial;         
    
    /**
     * Campo que guarda los datos encapsulados sobre la administración del objeto.
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @NotNull(message = "Debe haber un paquete administrativo para la entidad") 
    private AdminEntidad admin;    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSimel() {
        return idSimel;
    }

    public void setIdSimel(Long idSimel) {
        this.idSimel = idSimel;
    }

    public String getUsSimel() {
        return usSimel;
    }

    public void setUsSimel(String usSimel) {
        this.usSimel = usSimel;
    }

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
/*
    public List<Descarga> getDescargas() {
        return descargas;
    }

    public void setDescargas(List<Descarga> descargas) {
        this.descargas = descargas;
    }
*/
    public AdminEntidad getAdmin() {
        return admin;
    }

    public void setAdmin(AdminEntidad admin) {
        this.admin = admin;
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
        if (!(object instanceof Operador)) {
            return false;
        }
        Operador other = (Operador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Operador[ id=" + id + " ]";
    }
    
}
