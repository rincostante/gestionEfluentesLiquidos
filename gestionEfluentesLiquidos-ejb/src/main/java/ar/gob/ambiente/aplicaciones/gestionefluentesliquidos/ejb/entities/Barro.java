

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Clase que encapsula los datos correspondientes a las características del barro generado por un Establecimiento
 * @author rincostante
 */
@Entity
public class Barro implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarda el volúmen en m3/día del barro generado
     */
    private float volumen;
    
    /**
     * Campo que guarda el porcentaje de agua del Barro
     */
    private float porcentaje;
    
    /**
     * Campo que especifica si el Barro tiene tratamiento propio (Es excluyente con el retiro por terceros)
     */
    private boolean tratPropio;
    
    /**
     * Campo que especifica, en caso de tener tratamiento propio, si dicho tratamiento es de deshidratación
     */
    private boolean deshidratacion;
    
    /**
     * Campo que especifica, en caso de tener tratamiento propio, si dicho tratamiento es de estabilización
     */
    private boolean estabilizacion;    
    
    /**
     * Campo que especifica, en caso de tener tratamiento propio, si dicho tratamiento es de desinfección
     */
    private boolean desinfeccion; 
    
    /**
     * Campo que especifica, en caso de tener tratamiento propio, si se trata de tratamiento no especificados
     */
    private boolean otrosTratamiento; 
    
    /**
     * Campo que detalla, en caso de tener tratamiento propio y este no estar especificado,
     * el tipo de tratamiento del que se trata
     */
    private String descOtroTratamiento;   
    
    /**
     * Campo que especifica, si hay retiro de los Barros (es excluyente con el tratamiento propio)
     */
    private boolean retiraMediosPropios;   
    
    /**
     * Campo que especifica, si el retiro se hace por otros medios
     */
    private boolean retiraOtrosMedios;      
    
    /**
     * Campo que detalla, en caso de retiras Barros por medios no especificados,
     * el tipo de retiro del que se trata
     */
    private String descOtrosMedios;       
    
    /**
     * Trasportista rgistrado en la DRP que retire los barros, si corresponde
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private EstabDrp transpDrp;     
    
    /**
     * Operador que trate los barros registrado en la DRP, si corresponde ese destino final
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private EstabDrp operadorDrp;   
    
    /**
     * Campo que especifica si el destino final es Landfarming
     */
    private boolean destLandFarming;
    
    /**
     * Campo que especifica si el destino final es relleno sanitario
     */
    private boolean destRellSanitario;
    
    /**
     * Campo que especifica si el destino final es como insumo
     */
    private boolean destInsumo;
    
    /**
     * Campo que detalla, en caso de tener destino final como insumo,
     * el uso de dichos barros
     */
    private String usoComoInsumo;         
    
    /**
     * Campo que especifica si el destino final es otro no especificado
     */
    private boolean destOtros;
    
    /**
     * Campo que detalla, en caso de tener un destino final no especificado
     * la especificación de tal destino
     */
    private String especifOtroDestino;        
    
    /**
     * Campo que especifica si los barros contienen algún elemento listado en las tablas del Anexo D del instructivo
     */
    private boolean contieneElemAnexoD;
    
    /**
     * Campo que especifica si No se retiraron barros durante el período declarado
     */
    private boolean adjuntaProtocolo;
    
    /**
     * Campo que especifica si los barros se retiran por un Transportista registrado en la DRP
     */
    private boolean retiraTransDrp;    
    
    /**
     * Campo que especifica si los barros son tratados por un Operador registrado en la DRP
     */
    private boolean trataOpDrp;    
    
    /**
     * Campo que especifica los datos del Transportista, en caso de utilizarlo y que el mismo no esté registrado en la DRP
     * size = 100
     */
    private String datosTranspNoDrp;
    
    /**
     * Campo que especifica los datos del Operador, en caso de utilizarlo y que el mismo no esté registrado en la DRP
     * size = 100
     */
    private String datosOperadorNoDrp;
    
    
    /***********************
     ** Métodos de acceso **
     ***********************/
    public String getDatosTranspNoDrp() {
        return datosTranspNoDrp;
    }

    public void setDatosTranspNoDrp(String datosTranspNoDrp) {
        this.datosTranspNoDrp = datosTranspNoDrp;
    }

    public String getDatosOperadorNoDrp() {
        return datosOperadorNoDrp;
    }

    public void setDatosOperadorNoDrp(String datosOperadorNoDrp) {
        this.datosOperadorNoDrp = datosOperadorNoDrp;
    }

    public boolean isRetiraOtrosMedios() {
        return retiraOtrosMedios;
    }

    public void setRetiraOtrosMedios(boolean retiraOtrosMedios) {
        this.retiraOtrosMedios = retiraOtrosMedios;
    }

    public boolean isRetiraTransDrp() {
        return retiraTransDrp;
    }

    public void setRetiraTransDrp(boolean retiraTransDrp) {
        this.retiraTransDrp = retiraTransDrp;
    }

    public boolean isTrataOpDrp() {
        return trataOpDrp;
    }

    public void setTrataOpDrp(boolean trataOpDrp) {
        this.trataOpDrp = trataOpDrp;
    }

    public String getUsoComoInsumo() {
        return usoComoInsumo;
    }

    public void setUsoComoInsumo(String usoComoInsumo) {
        this.usoComoInsumo = usoComoInsumo;
    }

    public String getEspecifOtroDestino() {
        return especifOtroDestino;
    }

    public void setEspecifOtroDestino(String especifOtroDestino) {
        this.especifOtroDestino = especifOtroDestino;
    }

    public float getVolumen() {
        return volumen;
    }

    public void setVolumen(float volumen) {
        this.volumen = volumen;
    }

    public float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(float porcentaje) {
        this.porcentaje = porcentaje;
    }

    public boolean isTratPropio() {
        return tratPropio;
    }

    public void setTratPropio(boolean tratPropio) {
        this.tratPropio = tratPropio;
    }

    public boolean isDeshidratacion() {
        return deshidratacion;
    }

    public void setDeshidratacion(boolean deshidratacion) {
        this.deshidratacion = deshidratacion;
    }

    public boolean isEstabilizacion() {
        return estabilizacion;
    }

    public void setEstabilizacion(boolean estabilizacion) {
        this.estabilizacion = estabilizacion;
    }

    public boolean isDesinfeccion() {
        return desinfeccion;
    }

    public void setDesinfeccion(boolean desinfeccion) {
        this.desinfeccion = desinfeccion;
    }

    public boolean isOtrosTratamiento() {
        return otrosTratamiento;
    }

    public void setOtrosTratamiento(boolean otrosTratamiento) {
        this.otrosTratamiento = otrosTratamiento;
    }

    public String getDescOtroTratamiento() {
        return descOtroTratamiento;
    }

    public void setDescOtroTratamiento(String descOtroTratamiento) {
        this.descOtroTratamiento = descOtroTratamiento;
    }

    public boolean isRetiraMediosPropios() {
        return retiraMediosPropios;
    }

    public void setRetiraMediosPropios(boolean retiraMediosPropios) {
        this.retiraMediosPropios = retiraMediosPropios;
    }

    public String getDescOtrosMedios() {
        return descOtrosMedios;
    }

    public void setDescOtrosMedios(String descOtrosMedios) {
        this.descOtrosMedios = descOtrosMedios;
    }

    public EstabDrp getTranspDrp() {
        return transpDrp;
    }

    public void setTranspDrp(EstabDrp transpDrp) {
        this.transpDrp = transpDrp;
    }

    public EstabDrp getOperadorDrp() {
        return operadorDrp;
    }

    public void setOperadorDrp(EstabDrp operadorDrp) {
        this.operadorDrp = operadorDrp;
    }

    public boolean isDestLandFarming() {
        return destLandFarming;
    }

    public void setDestLandFarming(boolean destLandFarming) {
        this.destLandFarming = destLandFarming;
    }

    public boolean isDestRellSanitario() {
        return destRellSanitario;
    }

    public void setDestRellSanitario(boolean destRellSanitario) {
        this.destRellSanitario = destRellSanitario;
    }

    public boolean isDestInsumo() {
        return destInsumo;
    }

    public void setDestInsumo(boolean destInsumo) {
        this.destInsumo = destInsumo;
    }

    public boolean isDestOtros() {
        return destOtros;
    }

    public void setDestOtros(boolean destOtros) {
        this.destOtros = destOtros;
    }

    public boolean isContieneElemAnexoD() {
        return contieneElemAnexoD;
    }

    public void setContieneElemAnexoD(boolean contieneElemAnexoD) {
        this.contieneElemAnexoD = contieneElemAnexoD;
    }

    public boolean isAdjuntaProtocolo() {
        return adjuntaProtocolo;
    }

    public void setAdjuntaProtocolo(boolean adjuntaProtocolo) {
        this.adjuntaProtocolo = adjuntaProtocolo;
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
        if (!(object instanceof Barro)) {
            return false;
        }
        Barro other = (Barro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Barro[ id=" + id + " ]";
    }
    
}
