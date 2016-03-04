

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.modelo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase que encapsula los datos de la sesión del usuario
 * @author rincostante
 */
public class Sesion implements Serializable{
    
    private String cude;
    private String razonSocial;
    private String cuit;
    private String tipoEst;
    private String domCalle;
    private String domNumero;
    private String domMasDatos;
    private String localidad;

    public String getCude() {
        return cude;
    }

    public void setCude(String cude) {
        this.cude = cude;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getTipoEst() {
        return tipoEst;
    }

    public void setTipoEst(String tipoEst) {
        this.tipoEst = tipoEst;
    }

    public String getDomCalle() {
        return domCalle;
    }

    public void setDomCalle(String domCalle) {
        this.domCalle = domCalle;
    }

    public String getDomNumero() {
        return domNumero;
    }

    public void setDomNumero(String domNumero) {
        this.domNumero = domNumero;
    }

    public String getDomMasDatos() {
        return domMasDatos;
    }

    public void setDomMasDatos(String domMasDatos) {
        this.domMasDatos = domMasDatos;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 19 * hash + Objects.hashCode(this.cude);
        return hash;   
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sesion other = (Sesion) obj;
        if (!Objects.equals(this.cude, other.cude)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.modelo.Sesion{" + "cude=" + cude + '}';
    }
}
