

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 * Entidad que encapsula los datos que describen el vuelco del Establecimento
 * @author rincostante
 */
@Entity
public class Vuelco implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que muestra las Descargas que componenen el Vuelco del Establecimiento
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "vuelco_id", referencedColumnName = "id")
    private List<Descarga> descargas;   
    
    /**
     * Campo que indica si el Establecimiento tiene corte de descarga industrial
     */
    private boolean tieneCorteDesague;      
    
    /**
     * Guarda el Operador (registrado en la DRP) en el caso que el Establecimiento tenga
     * corte de descarga industrial
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private DocDec operador;    

    /**
     * Campo que indica si el Establecimiento posee permiso de factibilidad hidráulica
     */
    private boolean tienePermisoFact;
    
    /**
     * Campo que indica si tiene Circuito  cerrado de refrigeración
     */
    private boolean circCerrRefrig;
    
    /**
     * Campo que indica si tiene Recirculación de agua
     */
    private boolean recircAgua;
    
    /**
     * Campo que indica si hay Lavado de pisos de locales industriales
     */
    private boolean lavaPisosInd;
    
    /**
     * Campo que indica si tiene Circuito abierto de refrigeración
     */
    private boolean circAbierntRefrig;
    
    /**
     * Campo que idinca si tiene Caldera
     */
    private boolean caldera;
    
    /**
     * Campo que indica si hay Captación de Agua Subterránea
     */
    private boolean captaAguaSubte;
    
    /**
     * Campo que indica si hay arsénico en el líquido vertido
     */
    private boolean arsenico;
    
    /**
     * Campo que indica si hay cadmio en el líquido vertido
     */
    private boolean cadmio;

    /**
     * Campo que indica si hay cianuro en el líquido vertido
     */
    private boolean cianuro;

    /**
     * Campo que indica si hay cromo en el líquido vertido
     */
    private boolean cromo;
    
    /**
     * Campo que indica si hay detergentes en el líquido vertido
     */
    private boolean detergentes;
    
    /**
     * Campo que indica si hay fenoles en el líquido vertido
     */
    private boolean fenoles;
    
    /**
     * Campo que indica si hay hidrocarburos en el líquido vertido
     */
    private boolean hidrocarburos;
    
    /**
     * Campo que indica si hay mercurio en el líquido vertido
     */
    private boolean mercurio;
    
    /**
     * Campo que indica si hay plomo en el líquido vertido
     */
    private boolean plomo;
    
    /**
     * Campo que indica si hay solventes orgánicos en el líquido vertido
     */
    private boolean solventesOrg;
    
    /**
     * Campo que indica si hay sustancias solubles en el líquido vertido
     */
    private boolean sustSolubles;
    
    /**
     * Campo que indica si hay sólidos sedimentables en el líquido vertido
     */
    private boolean solidosSediment;
    
    /**
     * Campo que indica si el líquido vertido tiene PH ácido
     */
    private boolean acido;
    
    /**
     * Campo que indica si el líquido vertido tiene PH neutro
     */
    private boolean neutro;
    
    /**
     * Campo que indica si el líquido vertido tiene PH alcalino
     */
    private boolean alcalino;
    
    /**
     * Campo que indica si el Establecimiento está inscripto en la DRP
     */
    private boolean inscripto;
    
    /**
     * Guarda el nombre de la calle sobre la cual se otorgó la factibilidad
     */
    private String calleFactibilidad;
    
    /**
     * Guarda el caudal autorizado para el Establecimiento
     * La suma de los caudales vertidos no debe superarlo
     */
    private float caudalAutorizado;
    
    /**
     * Guarda el Organismo autorizante de los vertidos
     * String (100)
     */
    private String orgAutorizante;
    
    /**
     * Flag que determina si hay un radio de servido
     */
    @Column
    private boolean radioServido;
    
    /**
     * Guarda la descripción del proceso productivo que realiza el Establecimiento.
     * Solo si el Establecimiento no se encuentra registrado en DRP
     */
    @Column
    private String procProduct;
    
    /**
     * Campo que muestra los días en los que se efectúan las descargas, si corresponiera
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "vuelco_id", referencedColumnName = "id")
    private List<DiaVuelco> dias;      
    
    
    /***********************************************
     * Campos vinculados la administración de DRP **
     ***********************************************/
    
    /**
     * Campo que guarda el número de expediente (si corresponde)
     */
    private int expNum;
    
    /**
     * Campo que guarda el anio del expediente (si corresponde)
     */
    private int expAnio;
    
    /**
     * Campo que guarda el número del CAA (si corresponde)
     */
    private int caaNum;
    
    /**
     * Campo que guarda la fecha de vencimiento del CAA (si corresponde)
     */
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date caaFechaVenc;
    
    /**
     * Campo que indica si el CAA está vigente (si corresponde)
     */
    private boolean caaVigente;
    
    /**
     * Campo que indica el nombre de usuario del Establecimiento en la DRP (si corresponde, [CUIT]/[numOrden])
     */
    private String usDrp;
    
    /**
     * Campo que indica el nombre del Establecimiento para la DRP (si corresponde)
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
    
    /**
     * Campo que indica si las descargas no se realizan en días y horarios específicos.
     * Si es verdadero con se incluirán días
     */
    private boolean horarioDiscontinuo;
    
 
    /*****************
     ** Constructor **
     *****************/    
    public Vuelco(){
        dias = new ArrayList<>();
    }       
    

    /**********************
     * Métodos de acceso **
     **********************/
    public List<DiaVuelco> getDias() {
        return dias;
    }

    public void setDias(List<DiaVuelco> dias) {
        this.dias = dias;
    }

    public boolean isHorarioDiscontinuo() {
        return horarioDiscontinuo;
    }

    public void setHorarioDiscontinuo(boolean horarioDiscontinuo) {
        this.horarioDiscontinuo = horarioDiscontinuo;
    }

    public String getProcProduct() {
        return procProduct;
    }

    public void setProcProduct(String procProduct) {
        this.procProduct = procProduct;
    }

    public boolean isRadioServido() {
        return radioServido;
    }

    public void setRadioServido(boolean radioServido) {
        this.radioServido = radioServido;
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

    public String getCalleFactibilidad() {
        return calleFactibilidad;
    }

    public void setCalleFactibilidad(String calleFactibilidad) {
        this.calleFactibilidad = calleFactibilidad;
    }

    public DocDec getOperador() {
        return operador;
    }

    public void setOperador(DocDec operador) {
        this.operador = operador;
    }

    public boolean isTieneCorteDesague() {
        return tieneCorteDesague;
    }

    public void setTieneCorteDesague(boolean tieneCorteDesague) {
        this.tieneCorteDesague = tieneCorteDesague;
    }

    public boolean isTienePermisoFact() {
        return tienePermisoFact;
    }

    public void setTienePermisoFact(boolean tienePermisoFact) {
        this.tienePermisoFact = tienePermisoFact;
    }

    public List<Descarga> getDescargas() {
        return descargas;
    }

    public void setDescargas(List<Descarga> descargas) {
        this.descargas = descargas;
    }

    public boolean isCircCerrRefrig() {
        return circCerrRefrig;
    }

    public void setCircCerrRefrig(boolean circCerrRefrig) {
        this.circCerrRefrig = circCerrRefrig;
    }

    public boolean isRecircAgua() {
        return recircAgua;
    }

    public void setRecircAgua(boolean recircAgua) {
        this.recircAgua = recircAgua;
    }

    public boolean isLavaPisosInd() {
        return lavaPisosInd;
    }

    public void setLavaPisosInd(boolean lavaPisosInd) {
        this.lavaPisosInd = lavaPisosInd;
    }

    public boolean isCircAbierntRefrig() {
        return circAbierntRefrig;
    }

    public void setCircAbierntRefrig(boolean circAbierntRefrig) {
        this.circAbierntRefrig = circAbierntRefrig;
    }

    public boolean isCaldera() {
        return caldera;
    }

    public void setCaldera(boolean caldera) {
        this.caldera = caldera;
    }

    public boolean isCaptaAguaSubte() {
        return captaAguaSubte;
    }

    public void setCaptaAguaSubte(boolean captaAguaSubte) {
        this.captaAguaSubte = captaAguaSubte;
    }

    public boolean isArsenico() {
        return arsenico;
    }

    public void setArsenico(boolean arsenico) {
        this.arsenico = arsenico;
    }

    public boolean isCadmio() {
        return cadmio;
    }

    public void setCadmio(boolean cadmio) {
        this.cadmio = cadmio;
    }

    public boolean isCianuro() {
        return cianuro;
    }

    public void setCianuro(boolean cianuro) {
        this.cianuro = cianuro;
    }

    public boolean isCromo() {
        return cromo;
    }

    public void setCromo(boolean cromo) {
        this.cromo = cromo;
    }

    public boolean isDetergentes() {
        return detergentes;
    }

    public void setDetergentes(boolean detergentes) {
        this.detergentes = detergentes;
    }

    public boolean isFenoles() {
        return fenoles;
    }

    public void setFenoles(boolean fenoles) {
        this.fenoles = fenoles;
    }

    public boolean isHidrocarburos() {
        return hidrocarburos;
    }

    public void setHidrocarburos(boolean hidrocarburos) {
        this.hidrocarburos = hidrocarburos;
    }

    public boolean isMercurio() {
        return mercurio;
    }

    public void setMercurio(boolean mercurio) {
        this.mercurio = mercurio;
    }

    public boolean isPlomo() {
        return plomo;
    }

    public void setPlomo(boolean plomo) {
        this.plomo = plomo;
    }

    public boolean isSolventesOrg() {
        return solventesOrg;
    }

    public void setSolventesOrg(boolean solventesOrg) {
        this.solventesOrg = solventesOrg;
    }

    public boolean isSustSolubles() {
        return sustSolubles;
    }

    public void setSustSolubles(boolean sustSolubles) {
        this.sustSolubles = sustSolubles;
    }

    public boolean isSolidosSediment() {
        return solidosSediment;
    }

    public void setSolidosSediment(boolean solidosSediment) {
        this.solidosSediment = solidosSediment;
    }

    public boolean isAcido() {
        return acido;
    }

    public void setAcido(boolean acido) {
        this.acido = acido;
    }

    public boolean isNeutro() {
        return neutro;
    }

    public void setNeutro(boolean neutro) {
        this.neutro = neutro;
    }

    public boolean isAlcalino() {
        return alcalino;
    }

    public void setAlcalino(boolean alcalino) {
        this.alcalino = alcalino;
    }

    public boolean isInscripto() {
        return inscripto;
    }

    public void setInscripto(boolean inscripto) {
        this.inscripto = inscripto;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    /**************************
     * Métodos sobreescritos **
     **************************/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vuelco)) {
            return false;
        }
        Vuelco other = (Vuelco) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Vuelco[ id=" + id + " ]";
    }
    
}
