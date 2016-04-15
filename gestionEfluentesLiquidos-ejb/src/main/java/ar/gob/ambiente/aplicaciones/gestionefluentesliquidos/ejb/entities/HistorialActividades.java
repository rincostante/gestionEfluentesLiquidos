
package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que encapsula la información correspondiente a cada cambio en el inicio y fin de actividades del establecimiento.
 * El conjunto de todas ellas constituye el historial de actividades.
 * @author rincostante
 */
@XmlRootElement(name = "historialactividades")
@Entity
@Table(name = "historialactividades")
public class HistorialActividades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Guarda una refencia al establecimiento del que se registra un cambio de estado de actividad
     */
    @ManyToOne
    @JoinColumn(name="establecimiento_id")
    private Establecimiento establecimiento;

    /**
     * Guarda el cambio de estado en la actividad, es true si se inicia o reinicia y flase si se suspende.
     */
    @Column 
    private boolean accion;
    
    /**
     * Guarda la fecha del cambio de estado de actividad
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    
    /**
     * Guarda el motivo del cambio
     */
    @Column (length=100)
    @Size(message = "El campo motivo tiene un máximo de 100 caracteres", max = 100)
    private String motivo;
    
    /**
     * Guarda el tiempo estimado del cambio de estado
     */
    @Column 
    private int tiempoEstimado;
    
    /**
     * Guarda el usuario que registró el cambio
     */
    @ManyToOne 
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
    
    /**
     * Muestra la fecha en formato string
     */
    @Transient
    String strFecha;
    
    public String getStrFecha() {
        if(fecha != null){
            SimpleDateFormat formateador = new SimpleDateFormat("dd'/'MM'/'yyyy", new Locale("es_ES"));
            strFecha = formateador.format(fecha);
            return strFecha;
        }
        return "";
    }

    public void setStrFecha(String strFecha) {
        this.strFecha = strFecha;
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
    
    public boolean getAccion() {
        return accion;
    }

    public void setAccion(boolean accion) {
        this.accion = accion;
    }
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(int tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
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
        if (!(object instanceof HistorialActividades)) {
            return false;
        }
        HistorialActividades other = (HistorialActividades) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.HistorialActividades[ id=" + id + " ]";
    }
    
}