

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entidad que encapsula los datos correspondientes a las diferentes descargas que componen el vuelco de un Establecimiento
 * @author rincostante
 */
@Entity
public class Descarga implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Guarda el Curso de la Descarga
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="curso_id")
    private Curso curso;
    
    /**
     * Guarda el método de Aforo de la Descarga
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="aforo_id")
    private Aforo aforo;

    /**
     * Campo que guarda el número de orden de la descarga
     */
    @Column (nullable=false)
    @NotNull(message = "El campo numOrden no puede ser nulo")
    private int numOrden;
    
    /**
     * Campo que guarda la clave del tipo de Descarga.
     * Con el método getTipo, se accede a la descripción correpondiente a la clave
     */
    @Column (nullable=false)
    @NotNull(message = "El campo claveTipo no puede ser nulo")
    private int claveTipo;    
    
    /**
     * Listado para seleccionar el tipo de Descarga
     */
    @Transient
    private static final Map<Integer, String> MP_TIPODESC;
    static{
       MP_TIPODESC = new TreeMap<>();
       MP_TIPODESC.put(1, "CLOACAL");
       MP_TIPODESC.put(2, "INDUSTRIAL");
       MP_TIPODESC.put(3, "COMBINADO");
    }    
    
    /**
     * Campo que guarda el caudal en m3 por día
     */
    @Column (nullable=false)
    @NotNull(message = "El campo caudal no puede ser nulo")
    private float caudal;
    
    /**
     * Campo booleano que indica si el vertido de la descarga es mayor a 5 km
     */
    private boolean mayor5km;
    
    /**
     * Campo booleano que indica si la descarga está anulada
     */
    private boolean anulado;
    
    /**
     * Campo que guarda el motivo de anulación de la descarga, en caso que esté anulada
     */
    @Column (length=500)
    @Size(message = "El campo motivoAnulado no puede tener más de 20 caracteres", max = 500)
    private String motivoAnulado; 
    
    /**
     * Campo booleano que indica si hay una cámara de toma de muestra
     */
    private boolean camTomaMuestra;
    
    /**
     * Campo que guarda el caudal en m3 autorizado
     */
    @Column (nullable=true)
    private float caudalAutorizado;
    
    /**
     * Campo que guarda el nombre del organismo autorizante
     */
    @Column (length=255)
    @Size(message = "El campo orgAutorizante no puede tener más de 255 caracteres", max = 255)
    private String orgAutorizante;   
    
    /**
     * Campo que muestra los Tratamientos que pueda tener el líquido descargado
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "descarga_id", referencedColumnName = "id")
    private List<Tratamiento> tratamientos;  
    
    // constructor
    public Descarga() {
        tratamientos = new ArrayList();
    }   
    
    
    /**********************
     * Métodos de acceso ** 
     **********************/
    public int getClaveTipo() {
        return claveTipo;
    }

    public void setClaveTipo(int claveTipo) {
        this.claveTipo = claveTipo;
    }

    public String getTipo() {
        return MP_TIPODESC.get(claveTipo);
    }    
    
    public static Map<Integer, String> getMP_TIPODESC() {
        return MP_TIPODESC;
    }  
    
    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Aforo getAforo() {
        return aforo;
    }

    public void setAforo(Aforo aforo) {
        this.aforo = aforo;
    }

    public int getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(int numOrden) {
        this.numOrden = numOrden;
    }

    public float getCaudal() {
        return caudal;
    }

    public void setCaudal(float caudal) {
        this.caudal = caudal;
    }

    public boolean isMayor5km() {
        return mayor5km;
    }

    public void setMayor5km(boolean mayor5km) {
        this.mayor5km = mayor5km;
    }

    public boolean isAnulado() {
        return anulado;
    }

    public void setAnulado(boolean anulado) {
        this.anulado = anulado;
    }

    public String getMotivoAnulado() {
        return motivoAnulado;
    }

    public void setMotivoAnulado(String motivoAnulado) {
        this.motivoAnulado = motivoAnulado;
    }

    public boolean isCamTomaMuestra() {
        return camTomaMuestra;
    }

    public void setCamTomaMuestra(boolean camTomaMuestra) {
        this.camTomaMuestra = camTomaMuestra;
    }

    public float getCaudalAutorizado() {
        return caudalAutorizado;
    }

    public void setCaudalAutorizado(float caudalAutorizado) {
        this.caudalAutorizado = caudalAutorizado;
    }

    public String getOrgAutorizante() {
        return orgAutorizante;
    }

    public void setOrgAutorizante(String orgAutorizante) {
        this.orgAutorizante = orgAutorizante;
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
        if (!(object instanceof Descarga)) {
            return false;
        }
        Descarga other = (Descarga) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Descarga[ id=" + id + " ]";
    }
    
}
