

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 * Clase que encapsula los datos del Transportista registrados en la DRP
 * @author rincostante
 */
@Entity
public class EstabDrp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * Campo que guarda el número de expediente
     */
    private int expNum;
    
    /**
     * Campo que guarda el anio del expediente
     */
    private int expAnio;
    
    /**
     * Campo que guarda el número del CAA
     */
    private int caaNum;
    
    /**
     * Campo que guarda la fecha de vencimiento del CAA
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date caaFechaVenc;
    
    /**
     * Campo que indica si el CAA está vigente
     */
    private boolean caaVigente;
    
    /**
     * Campo que indica el nombre de usuario del Establecimiento en la DRP
     */
    private String usDrp;
    
    /**
     * Campo que indica el nombre del Establecimiento para la DRP
     */
    private String nombreDrp;

    /**
     * Campo que indica el tipo de Establecimiento para la DRP (Generador, Transportista u Operador) (si corresponde)
     */
    private String tipoDrp;
    
    /**
     * Campo que indica la descripción de la actividad según la DRP (si corresponde)
     */
    private String descDrp;
    
    
    /**********************
     * Métodos de acceso **
     **********************/    
    public String getTipoDrp() {
        return tipoDrp;
    }

    public void setTipoDrp(String tipoDrp) {
        this.tipoDrp = tipoDrp;
    }

    public String getDescDrp() {
        return descDrp;
    }

    public void setDescDrp(String descDrp) {
        this.descDrp = descDrp;
    }
    
    public int getExpNum() {
        return expNum;
    }

    public void setExpNum(int expNum) {
        this.expNum = expNum;
    }

    public int getExpAnio() {
        return expAnio;
    }

    public void setExpAnio(int expAnio) {
        this.expAnio = expAnio;
    }

    public int getCaaNum() {
        return caaNum;
    }

    public void setCaaNum(int caaNum) {
        this.caaNum = caaNum;
    }

    public Date getCaaFechaVenc() {
        return caaFechaVenc;
    }

    public void setCaaFechaVenc(Date caaFechaVenc) {
        this.caaFechaVenc = caaFechaVenc;
    }

    public boolean isCaaVigente() {
        return caaVigente;
    }

    public void setCaaVigente(boolean caaVigente) {
        this.caaVigente = caaVigente;
    }

    public String getUsDrp() {
        return usDrp;
    }

    public void setUsDrp(String usDrp) {
        this.usDrp = usDrp;
    }

    public String getNombreDrp() {
        return nombreDrp;
    }

    public void setNombreDrp(String nombreDrp) {
        this.nombreDrp = nombreDrp;
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
        if (!(object instanceof EstabDrp)) {
            return false;
        }
        EstabDrp other = (EstabDrp) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.TransportistaDrp[ id=" + id + " ]";
    }
    
}
