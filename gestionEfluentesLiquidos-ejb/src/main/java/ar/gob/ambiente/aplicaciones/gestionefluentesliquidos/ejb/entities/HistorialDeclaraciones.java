

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Entidad que encapsula la información correspondiente a cada Declaración Jurada que haga el Establecimiento.
 * El conjunto de todas ellas constituye el historial de Declaraciones.
 * @author carmendariz
 */
@Entity
public class HistorialDeclaraciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Flag que indica que la Declaración es la activa
     */
    @Column
    private boolean activa;    
    
    /**
     * Campo que guarda el Establecimiento al que pertenece la Declaración jurada
     */
    @OneToOne
    @JoinColumn(name="establecimiento_id")
    private Establecimiento establecimiento;
    
    /**
     * Campo que guarda la Declaración Jurada registrada
     */
    @OneToOne
    @JoinColumn(name="declaracionjurada_id")
    private DeclaracionJurada declaracion;
    
    /**
     * Campo que guarda la fecha en la que se registró la Declaración Jurada
     */
    @Temporal(TemporalType.DATE)
    @Column(nullable=false)
    @NotNull(message = "El campo fecha no puede ser nulo")
    private Date fecha;    

    /**
     * Campo que guarda la Declaración Jurada anterior para tenerla como plantilla
     * al momento de registrar una nueva
     */
    @OneToOne
    @JoinColumn(name="declaracionjuradaant_id")
    private DeclaracionJurada decAnterior;
    
    /**
     * Campo que guarda el usuario externo que registró la Declaración
     */
    @ManyToOne 
    @JoinColumn(name="usuario_id")
    private UsuarioExterno usuario;

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public DeclaracionJurada getDeclaracion() {
        return declaracion;
    }

    public void setDeclaracion(DeclaracionJurada declaracion) {
        this.declaracion = declaracion;
    }

    public DeclaracionJurada getDecAnterior() {
        return decAnterior;
    }

    public void setDecAnterior(DeclaracionJurada decAnterior) {
        this.decAnterior = decAnterior;
    }

    public UsuarioExterno getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioExterno usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof HistorialDeclaraciones)) {
            return false;
        }
        HistorialDeclaraciones other = (HistorialDeclaraciones) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.HistorialDeclaraciones[ id=" + id + " ]";
    }
    
}