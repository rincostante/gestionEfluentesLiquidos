

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Clase que encapsula los datos correspondientes al Recibo entregado por la DPyRA
 * al validar la Declaración Jurada del Establecimiento
 * @author carmendariz
 */
@Entity
public class Recibo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Campo que guarda el código del Recibo que servirá, entre otras cosas,
     * para validar la vigencia de las Declaraciones Juradas y el registro de usuarios.
     */
    @Column (nullable=false, length=10, unique=false)
    @NotNull(message = "El campo codigo no puede ser nulo")
    @Size(message = "El campo codigo no puede tener más de 10 caracteres", min = 1, max = 10)
    private String codigo;         

    @OneToOne(mappedBy="recibo")
    private DeclaracionJurada declaracion;
    
    /**
     * Campo que guarda la fecha en la que se validó la Declaración Jurada
     */
    @Temporal(TemporalType.DATE)
    @Column(nullable=false)
    @NotNull(message = "El campo fecha no puede ser nulo")
    private Date fecha;    
    
    /**
     * Campo que guarda los datos encapsulados sobre la administración del objeto.
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @NotNull(message = "El campo admin no puede ser nulo") 
    private AdminEntidad admin;    

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public DeclaracionJurada getDeclaracion() {
        return declaracion;
    }

    public void setDeclaracion(DeclaracionJurada declaracion) {
        this.declaracion = declaracion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }  
        
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
        if (!(object instanceof Recibo)) {
            return false;
        }
        Recibo other = (Recibo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Recibo[ id=" + id + " ]";
    }
    
}