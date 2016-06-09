
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que encapsula la información de los Establecimientos, relativa a la gestión de la DPyRA
 * Lleva una referencia al Establecimiento registrado en el Registro único de Personas (RUP)
 * @author rincostante
 */
@XmlRootElement(name = "establecimiento")
@Entity
@Table(name = "establecimiento")
public class Establecimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Referencia al identificador del Establecimiento del RUP
     */
    @Column (nullable=false, unique=true)
    @NotNull(message = "El campo idRupEst no puede ser nulo")
    private Long idRupEst;
    
    /**
     * Referencia a la razón social del RUP
     */
    @Column (nullable=false, unique=true)
    @NotNull(message = "El campo idRupRaz no puede ser nulo")
    private Long idRupRaz;
    
    /**
     * Indica si la razón social es una persona jurdíca
     */
    @Column 
    private boolean rzJuridica;
    
    /**
     * Guarda el nombre de la Razón social registrado en el RUP
     */
    @Column (nullable=false, length=100)
    @NotNull(message = "El campo razonSocial no puede ser nulo")
    @Size(message = "El campo razonSocial no puede tener más de 100 caracteres", min = 1, max = 100)
    private String razonSocial;
    
    /**
     * Guarda el cuit de la razón social retistrado en el RUP
     */
    @Column (nullable=false, unique=true)
    @NotNull(message = "El campo cuit no puede ser nulo")
    private Long cuit;

    /**
     * Guarda el número de orden del cambio de razón social del establecimiento registrado en las bases de la DPyRA
     * Solo se registrará al migrar la información de los establecimientos
     */
    @Column (nullable=false)
    @NotNull(message = "El campo crs no puede ser nulo")
    private int crs;
        
    /**
     * Guarda el inmueble en el que está emplazado el establecimiento
     * El inmueble está comprendido en el establecimiento y se persiste en cascada
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="inmueble_id")
    private Inmueble inmueble;
    
    /**
     * Guarda el firmante que represente al establecimiento
     */
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="firmante_id", nullable=false)
    @NotNull(message = "Debe existir un firmante")
    private Firmante firmante;
       
    /**
     * Muestra el conjunto de los cambios de inicio y fin de actividades registrados en el historial
     */
    @OneToMany(mappedBy="establecimiento")
    private List<HistorialActividades> historialActividades;
    
    /**
     * Muestra el conjunto de los cambios de firmantes registrados en el correspondiente historial
     */
    @OneToMany(mappedBy="establecimiento")
    private List<HistorialFirmantes> historialFirmantes;
    
    /**
     * Muestra el conjunto de declaraciones juradas registradas por el establecimiento
     */
    @OneToMany(mappedBy="establecimiento")
    private List<HistorialDeclaraciones> historialDeclaraciones;
   
    /**
     * Guarda un número registrado por la DPyRA
     */
    @Column (nullable=false)
    @NotNull(message = "El campo numero no puede ser nulo")
    private Long numero;
    
    /**
     * Guarda el partido al que pertenece el establecimiento
     */
    @ManyToOne
    @JoinColumn(name="partido_id")
    private Partido partido;
    
    /**
     * Guarda el proceso productivo del establecimiento
     */
    @Column (length=50)
    @Size(message = "El campo procProduct tiene un máximo de 50 caracteres", max = 50)
    private String procProduct;
    
    /**
     * Guarda el conjunto de datos administrativos.
     * Se persiste en casacada
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @NotNull(message = "El campo admin no puede ser nulo") 
    private AdminEntidad admin;    
    
    /**
     * Por problemas de integridad de información heredada, hasta no profundizar la depuración
     * Se tomará como código de partido para el CUDE lo que traían los Establecimientos heredados
     */
    private int partidoGel;
    
    /**
     * Guardará solo los recibos de la DJ del 2014
     */
    private String codRecibo;
    
    public Establecimiento() {
        historialActividades = new ArrayList();
        historialFirmantes = new ArrayList();
        historialDeclaraciones = new ArrayList();
    }      

    public String getCodRecibo() {
        return codRecibo;
    }

    public void setCodRecibo(String codRecibo) {
        this.codRecibo = codRecibo;
    }

    public String getCude(){
        String sPart, sNum, sCrs;
        sPart = String.valueOf(this.partidoGel);
        sNum = String.valueOf(this.numero);
        sCrs = String.valueOf(this.crs);
        
        return sPart + "-" + sNum + "-" + sCrs;
    }
    
    public int getPartidoGel() {
        return partidoGel;
    }

    public void setPartidoGel(int partidoGel) {
        this.partidoGel = partidoGel;
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

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public List<HistorialActividades> getHistorialActividades() {
        return historialActividades;
    }

    public void setHistorialActividades(List<HistorialActividades> historialActividades) {
        this.historialActividades = historialActividades;
    }

    public List<HistorialFirmantes> getHistorialFirmantes() {
        return historialFirmantes;
    }

    public void setHistorialFirmantes(List<HistorialFirmantes> historialFirmantes) {
        this.historialFirmantes = historialFirmantes;
    }

    public List<HistorialDeclaraciones> getHistorialDeclaraciones() {
        return historialDeclaraciones;
    }

    public void setHistorialDeclaraciones(List<HistorialDeclaraciones> historialDeclaraciones) {
        this.historialDeclaraciones = historialDeclaraciones;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }

    public Long getIdRupEst() {
        return idRupEst;
    }

    public void setIdRupEst(Long idRupEst) {
        this.idRupEst = idRupEst;
    }
    
    public Long getIdRupRaz() {
        return idRupRaz;
    }

    public void setIdRupRaz(Long idRupRaz) {
        this.idRupRaz = idRupRaz;
    }
    
    public boolean getRzJuridica() {
        return rzJuridica;
    }

    public void setRzJuridica(boolean rzJuridica) {
        this.rzJuridica = rzJuridica;
    }
    
    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
        this.cuit = cuit;
    }

    public int getCrs() {
        return crs;
    }

    public void setCrs(int crs) {
        this.crs = crs;
    }
    
    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }
    
    public Firmante getFirmante() {
        return firmante;
    }

    public void setFirmante(Firmante firmante) {
        this.firmante = firmante;
    }
    
    public String getProcProduct() {
        return procProduct;
    }

    public void setProcProduct(String procProduct) {
        this.procProduct = procProduct;
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
        if (!(object instanceof Establecimiento)) {
            return false;
        }
        Establecimiento other = (Establecimiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Establecimiento[ id=" + id + " ]";
    }
    
}