

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que encapsula la información del inmueble en el que el establecimiento está emplazado.
 * Los datos correspondientes al domicilio del mismo, están contenidos en el establecimiento del RUP
 * @author rincostante
 */
@XmlRootElement(name = "inmueble")
@Entity
@Table(name = "inmueble")
public class Inmueble implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
        
    /**
     * Guarda los datos correspondientes a la partida inmobiliara
     */
    @Column ( length=20)
    @Size(message = "El campo partInmob tiene un máximo de 20 caracteres", max = 20)
    private String partInmob;
    
    /**
     * Guarda los datos correspondientes a la identificación catastral del inmuebble
     */
    @Column (length=100)
    @Size(message = "El campo nomCatastral tiene un máximo de 20 caracteres", max = 100)
    private String nomCatastral;
    
    /**
     * Correspondiente al punto de georreferenciación del inmueble
     */
    @Column
    private double latitud;
    
    /**
     * Correspondiente al punto de georreferenciación del inmueble
     */    
    @Column
    private double longitud;
    
    /**
     * Cantidad de personas que se desempeñan en la planta
     */
    @Column
    private int personalFabrica;
    
    /**
     * Cantidad de personas que se desempeñan en la oficina
     */
    @Column
    private int personalOficina;
    
    /**
     * Superficie cubieta del inmueble
     */
    @Column
    private float supCubierta;
    
    /**
     * Superficie libre del inmueble
     */
    @Column
    private float supLibre;
    
    /**
     * Flag que determina si hay un radio de servido
     */
    @Column
    private boolean radioServido;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPartInmob() {
        return partInmob;
    }

    public void setPartInmob(String partInmob) {
        this.partInmob = partInmob;
    }
    
    public String getNomCatastral() {
        return nomCatastral;
    }

    public void setNomCatastral(String nomCatastral) {
        this.nomCatastral = nomCatastral;
    }
    
    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }
    
    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    } 
    
    public int getPersonalFabrica() {
        return personalFabrica;
    }

    public void setPersonalFabrica(int personalFabrica) {
        this.personalFabrica = personalFabrica;
    }
    
    public int getPersonalOficina() {
        return personalOficina;
    }

    public void setPersonalOficina(int personalOficina) {
        this.personalOficina = personalOficina;
    }
    
    public float getSupCubierta() {
        return supCubierta;
    }

    public void setSupCubierta(float supCubierta) {
        this.supCubierta = supCubierta;
    }
    
    public float getSupLibre() {
        return supLibre;
    }

    public void setSupLibre(float supLibre) {
        this.supLibre = supLibre;
    }
    
    public boolean getRadioServido() {
        return radioServido;
    }

    public void setRadioServido(boolean radioServido) {
        this.radioServido = radioServido;
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
        if (!(object instanceof Inmueble)) {
            return false;
        }
        Inmueble other = (Inmueble) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Inmueble[ id=" + id + " ]";
    }
    
}