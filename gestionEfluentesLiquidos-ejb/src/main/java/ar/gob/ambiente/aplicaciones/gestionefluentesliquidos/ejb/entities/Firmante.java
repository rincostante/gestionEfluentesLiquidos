
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que encapsula la información de la persona que firma la declaración jurada en representación del establecimiento
 * @author rincostante
 */
@XmlRootElement(name = "firmante")
@Entity
@Table(name = "firmante")
public class Firmante implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Guarda la referencia correspondiente a la persona física en el RUP
     */
    @Column (nullable=false, unique=true)
    @NotNull(message = "El campo idRupFis no puede ser nulo")
    private Long idRupFis;
    
    /**
     * Muestra el conjunto de establecimientos del cual el firmante es representante
     */
    @OneToMany (mappedBy="firmante")
    private List<Establecimiento> establecimientos;
    
    /**
     * Muestra el conjunto de establecimientos de los cuales la persona fue firmante, mediante sus respectivos historiales
     */
    @OneToMany (mappedBy="firmanteAnterior")
    private List<HistorialFirmantes> historialEstablecimientos;
 
    /**
     * Guarda el nombre y apellido obtenido de la persona del RUP
     */
    @Column (nullable=false, length=50, unique=true)
    @NotNull(message = "El campo nombreYApellido no puede ser nulo")
    @Size(message = "El campo nombreYApellido tiene un máximo de 50 caracteres", min = 1, max = 50)
    private String nombreYApellido;
    
    /**
     * Guarda el cuti/cuil de la persona obtenida del RUP
     */
    @Column (nullable=false, unique=true)
    @NotNull(message = "El campo cuit no puede ser nulo")
    private long cuit;
   
    /**
     * Muestra las Declaraciones Juradas vinculadas al Firmante
     */
    @OneToMany (mappedBy="firmante")
    private List<DeclaracionJurada> declaraciones;    
    
    /**
     * Guarda el conjunto de datos administrativos.
     * Se persiste en casacada
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @NotNull(message = "{El campo procProduct no puede ser nulo") 
    private AdminEntidad admin;    
    
    
    public Firmante(){
        establecimientos = new ArrayList<>();
        historialEstablecimientos = new ArrayList<>();
        declaraciones = new ArrayList<>();
    }

    public String getNombreYApellido() {
        return nombreYApellido;
    }

    public void setNombreYApellido(String nombreYApellido) {
        this.nombreYApellido = nombreYApellido;
    }

    public long getCuit() {
        return cuit;
    }

    public void setCuit(long cuit) {
        this.cuit = cuit;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdminEntidad getAdmin() {
        return admin;
    }

    public void setAdmin(AdminEntidad admin) {
        this.admin = admin;
    }

    public List<DeclaracionJurada> getDeclaraciones() {
        return declaraciones;
    }

    public void setDeclaraciones(List<DeclaracionJurada> declaraciones) {
        this.declaraciones = declaraciones;
    }
    
    public Long getIdRupFis() {
        return idRupFis;
    }

    public void setIdRupFis(Long idRupFis) {
        this.idRupFis = idRupFis;
    }

    public List<Establecimiento> getEstablecimientos() {
         return establecimientos;
    }
    
    public void setEstablecimientos(List<Establecimiento> establecimientos) {
         this.establecimientos = establecimientos;
    }
    
    public List<HistorialFirmantes> getHistorialEstablecimientos() {
         return historialEstablecimientos;
    }
    
    public void setHistorialEstablecimientos(List<HistorialFirmantes> historialEstablecimientos) {
         this.historialEstablecimientos = historialEstablecimientos;
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
        if (!(object instanceof Firmante)) {
            return false;
        }
        Firmante other = (Firmante) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Firmante[ id=" + id + " ]";
    }
    
}