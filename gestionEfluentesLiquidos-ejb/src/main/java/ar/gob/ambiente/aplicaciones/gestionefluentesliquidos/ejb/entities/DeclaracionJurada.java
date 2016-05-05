

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entidad que encapsula todos los datos correspondientes a la Declaración Jurada del Establecimiento
 * @author rincostante
 */
@Entity
public class DeclaracionJurada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que muestra las Actividades desarrolladas por el Establecimiento
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "declaracion_id", referencedColumnName = "id")
    private List<ActividadDec> actividades;   
    
    /**
     * Campo que guarda la documentación asociada
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @NotNull(message = "El campo documentacion no puede ser nulo") 
    private DocDec documentacion;     
    
    /**
     * Campo que guarda el vuelco asociado
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @NotNull(message = "El campo vuelco no puede ser nulo") 
    private Vuelco vuelco;     
    
    /**
     * Campo que guarda el horario del Establecimiento
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Horario horario;    
    
    /**
     * Campo que muestra la distribución de las superficies edilicias del Establecimiento
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "declaracion_id", referencedColumnName = "id")
    private List<SuperficieDec> superficies; 
    
    /**
     * Campo que muestra la cantidad de personal asignado a oficina y planta
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "declaracion_id", referencedColumnName = "id")
    private List<CantPersonalDec> cantPersonal;
    
    /**
     * Campo que muestra las distintas fechas vinculadas a las actividades del Establecimiento
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "declaracion_id", referencedColumnName = "id")
    private List<FechaDec> fechasDeclaracion;    
    
    /**
     * Campo que guarda el CUDE del Establecimiento
     */
    @Column (nullable=false, length=20)
    @NotNull(message = "El campo cude no puede ser nulo")
    @Size(message = "El campo cude no puede tener más de 20 caracteres", min = 1, max = 20)
    private String cude;  
    
    /**
     * Guarda la persona que firma la Declaración Jurada en representación del Establecimiento
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="firmante_id", nullable=false)
    @NotNull(message = "Debe existir un firmante")
    private Firmante firmante;
    
    /**
     * Campo que guarda el Abastecimiento asociado
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Abastecimiento abastecimiento;     
    
    /**
     * Guarda el conjunto de datos administrativos.
     * Se persiste en casacada
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @NotNull(message = "El campo admin no puede ser nulo") 
    private AdminEntidad admin;      
    
    /**
     * Campo no persisitido que contiene el historial en el que se registró la Declaración
     */
    @OneToOne(mappedBy="declaracion")
    private HistorialDeclaraciones historial;    
    
    /**
     * Campo que guarda el Recibo de la Declaración
     */
    @OneToOne
    @JoinColumn(name="recibo_id")
    private Recibo recibo;    
    
    
    /*****************
     ** Constructor **
     *****************/
    
    public DeclaracionJurada(){
        actividades = new ArrayList<>();
        superficies = new ArrayList<>();
        cantPersonal = new ArrayList<>();
        fechasDeclaracion = new ArrayList<>();
    }
    
    /**********************
     * Métodos de acceso **
     **********************/
    
    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    
    public Abastecimiento getAbastecimiento() {
        return abastecimiento;
    }

    public void setAbastecimiento(Abastecimiento abastecimiento) {
        this.abastecimiento = abastecimiento;
    }
    
    public Vuelco getVuelco() {
        return vuelco;
    }

    public void setVuelco(Vuelco vuelco) {
        this.vuelco = vuelco;
    }
    
    public List<ActividadDec> getActividades() {
        return actividades;
    }

    public void setActividades(List<ActividadDec> actividades) {
        this.actividades = actividades;
    }

    public DocDec getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(DocDec documentacion) {
        this.documentacion = documentacion;
    }

    public List<SuperficieDec> getSuperficies() {
        return superficies;
    }

    public void setSuperficies(List<SuperficieDec> superficies) {
        this.superficies = superficies;
    }

    public List<CantPersonalDec> getCantPersonal() {
        return cantPersonal;
    }

    public void setCantPersonal(List<CantPersonalDec> cantPersonal) {
        this.cantPersonal = cantPersonal;
    }

    public List<FechaDec> getFechasDeclaracion() {
        return fechasDeclaracion;
    }

    public void setFechasDeclaracion(List<FechaDec> fechasDeclaracion) {
        this.fechasDeclaracion = fechasDeclaracion;
    }
    
    public Recibo getRecibo() {
        return recibo;
    }

    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
    }

    public Firmante getFirmante() {
        return firmante;
    }

    public void setFirmante(Firmante firmante) {
        this.firmante = firmante;
    }
    
    public String getCude() {
        return cude;
    }

    public void setCude(String cude) {
        this.cude = cude;
    }

    public AdminEntidad getAdmin() {
        return admin;
    }

    public void setAdmin(AdminEntidad admin) {
        this.admin = admin;
    }

    public HistorialDeclaraciones getHistorial() {
        return historial;
    }

    public void setHistorial(HistorialDeclaraciones historial) {
        this.historial = historial;
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
        if (!(object instanceof DeclaracionJurada)) {
            return false;
        }
        DeclaracionJurada other = (DeclaracionJurada) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.DeclaracionJurada[ id=" + id + " ]";
    }
    
}
