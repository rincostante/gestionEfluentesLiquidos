

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que encapsula la información correspondiente a cada cambio de firmante para un establecimiento
 * @author rincostante
 */
@XmlRootElement(name = "historialfirmantes")
@Entity
@Table(name = "historialfirmantes")
public class HistorialFirmantes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Guarda la referencia al establecimiento cuyo firmanta ha cambiado
     */
    @ManyToOne
    @JoinColumn(name="establecimiento_id")
    private Establecimiento establecimiento;
    
    /**
     * Guarada la referencia al firmante anterior
     */
    @ManyToOne
    @JoinColumn(name="firmanteant_id")
    private Firmante firmanteAnterior;
    
    /**
     * Guarda la referencia al firmante actual
     */
    @ManyToOne  
    @JoinColumn(name="firmanteact_id")
    private Firmante firmanteActual;
    
    /**
     * Guarda la fecha de la actualización
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    
    /**
     * Guarda el motivo del cambio si lo hubiera.
     */
    @Column
    private String motivo;
    
    /**
     * Flag que indica que el cambio es el vigente
     */
    @Column
    private boolean activa;
    
    /**
     * Muestra la fecha en formato string
     */    
    @Transient
    String strFecha;
    
    /**
     * Guarda la referencia al usuario que registró el cambio (si fue un usuario interno)
     */
    @ManyToOne 
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
    
    /**
     * Guarda la referencia al usuario que registró el cambio (si fue un usuario interno)
     */
    @ManyToOne 
    @JoinColumn(name="usuarioexterno_id")
    private UsuarioExterno usuarioExterno;

    public UsuarioExterno getUsuarioExterno() {
        return usuarioExterno;
    }

    public void setUsuarioExterno(UsuarioExterno usuarioExterno) {
        this.usuarioExterno = usuarioExterno;
    }

    
    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public Firmante getFirmanteAnterior() {
        return firmanteAnterior;
    }

    public void setFirmanteAnterior(Firmante firmanteAnterior) {
        this.firmanteAnterior = firmanteAnterior;
    }

    public Firmante getFirmanteActual() {
        return firmanteActual;
    }

    public void setFirmanteActual(Firmante firmanteActual) {
        this.firmanteActual = firmanteActual;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
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
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        if (!(object instanceof HistorialFirmantes)) {
            return false;
        }
        HistorialFirmantes other = (HistorialFirmantes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.HistorialFirmantes[ id=" + id + " ]";
    }
    
}