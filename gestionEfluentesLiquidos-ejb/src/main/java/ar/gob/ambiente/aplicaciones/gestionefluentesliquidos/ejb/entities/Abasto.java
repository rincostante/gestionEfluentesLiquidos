

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Clase que encapsula los datos correspondientes a los distintos abastecimientos de agua
 * @author rincostante
 */
@Entity
public class Abasto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarda el caudal del abastecimiento en m3/día
     */
    @Column (nullable=false)
    @NotNull(message = "El campo caudal no puede ser nulo")
    private float caudal;   
    
    /**
     * Campo que guarda el caudal de purga (si hubiera) del abastecimiento en m3/día
     */
    private float purga;       
    
    /**
     * Campo que guarda el Tipo de Abasto
     */
    @ManyToOne
    @JoinColumn(name="tipoabasto_id", nullable=false)
    @NotNull(message = "Debe existir el Tipo de abasto vinculado")
    private TipoAbasto tipoAbasto;
    
    /**
     * Campo que guarda el Tipo de Caudal
     */
    @ManyToOne
    @JoinColumn(name="tipocaudal_id", nullable=false)
    @NotNull(message = "Debe existir el Tipo de Caudal vinculado")
    private TipoCaudal tipoCaudal;    
    
    /**
     * Campo que guarda la descripción de otra fuente si existiera
     */
    @Column (length=250)
    @Size(message = "El campo descOtraFuente no puede tener más de 250 caracteres", max = 250)
    private String descOtraFuente;
    
    /**
     * Campo que guarda la clave del origen del Abasto
     * Con el método getOrigen, se accede al origen del Abasto correpondiente a la clave
     */
    private int origenAbasto;    
    
    /**
     * Listado para seleccionar el origen del Abasto
     */
    @Transient
    private static final Map<Integer, String> MP_ORIGEN;
    static{
       MP_ORIGEN = new TreeMap<>();
       MP_ORIGEN.put(1, "PROPIO");
       MP_ORIGEN.put(2, "PUBLICO");
    }
    
    /**
     * Campo que guarda la clave del origen del Abasto
     * Con el método getCircuito, se accede al circuito del Abasto correpondiente a la clave
     */
    private int circuitoAbasto;      
    
    /**
     * Listado para seleccionar el circuito del Abasto
     */
    @Transient
    private static final Map<Integer, String> MP_CIRCUITO;
    static{
       MP_CIRCUITO = new TreeMap<>();
       MP_CIRCUITO.put(1, "ABIERTO");
       MP_CIRCUITO.put(2, "CERRADO");
    }    
    
    /**
     * Campo que guarda la clave de la fuente del Abasto
     * Con el método getFuente, se accede a la fuente del Abasto correpondiente a la clave
     */
    private int fuenteAbasto;     
    
    /**
     * Listado para seleccionar el circuito del Abasto
     */
    @Transient
    private static final Map<Integer, String> MP_FUENTE;
    static{
       MP_FUENTE = new TreeMap<>();
       MP_FUENTE.put(1, "SUBTERRANEA");
       MP_FUENTE.put(2, "SUPERFICIE");
       MP_FUENTE.put(3, "OTRA");
    }       
    
    /**********************
     * Métodos de acceso **
     **********************/
    public String getDescOtraFuente() {
        return descOtraFuente;
    }

    public void setDescOtraFuente(String descOtraFuente) {
        this.descOtraFuente = descOtraFuente;
    }

    public float getPurga() {
        return purga;
    }

    public void setPurga(float purga) {
        this.purga = purga;
    }

    public int getOrigenAbasto() {
        return origenAbasto;
    }

    public void setOrigenAbasto(int origenAbasto) {
        this.origenAbasto = origenAbasto;
    }

    public int getCircuitoAbasto() {
        return circuitoAbasto;
    }

    public void setCircuitoAbasto(int circuitoAbasto) {
        this.circuitoAbasto = circuitoAbasto;
    }

    public int getFuenteAbasto() {
        return fuenteAbasto;
    }

    public void setFuenteAbasto(int fuenteAbasto) {
        this.fuenteAbasto = fuenteAbasto;
    }

    public String getOrigen() {
        return MP_ORIGEN.get(origenAbasto);
    }     
    
    public static Map<Integer, String> getMP_ORIGEN() {
        return MP_ORIGEN;
    }     
    
    public String getCircuito() {
        return MP_CIRCUITO.get(circuitoAbasto);
    }     
    
    public static Map<Integer, String> getMP_CIRCUITO() {
        return MP_CIRCUITO;
    }    
    
    public String getFuente() {
        return MP_FUENTE.get(fuenteAbasto);
    }     
    
    public static Map<Integer, String> getMP_FUENTE() {
        return MP_FUENTE;
    }      
    
    public float getCaudal() {
        return caudal;
    }

    public void setCaudal(float caudal) {
        this.caudal = caudal;
    }

    public TipoAbasto getTipoAbasto() {
        return tipoAbasto;
    }

    public void setTipoAbasto(TipoAbasto tipoAbasto) {
        this.tipoAbasto = tipoAbasto;
    }

    public TipoCaudal getTipoCaudal() {
        return tipoCaudal;
    }

    public void setTipoCaudal(TipoCaudal tipoCaudal) {
        this.tipoCaudal = tipoCaudal;
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
        if (!(object instanceof Abasto)) {
            return false;
        }
        Abasto other = (Abasto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Abasto[ id=" + id + " ]";
    }
    
}
