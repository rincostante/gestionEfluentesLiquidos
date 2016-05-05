/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.validarcuit.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author rincostante
 */
@Embeddable
public class EstabDatosIPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "partido")
    private int partido;
    @Basic(optional = false)
    @Column(name = "numero")
    private int numero;
    @Basic(optional = false)
    @Column(name = "c_r_s")
    private int cRS;

    public EstabDatosIPK() {
    }

    public EstabDatosIPK(int partido, int numero, int cRS) {
        this.partido = partido;
        this.numero = numero;
        this.cRS = cRS;
    }

    public int getPartido() {
        return partido;
    }

    public void setPartido(int partido) {
        this.partido = partido;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCRS() {
        return cRS;
    }

    public void setCRS(int cRS) {
        this.cRS = cRS;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) partido;
        hash += (int) numero;
        hash += (int) cRS;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstabDatosIPK)) {
            return false;
        }
        EstabDatosIPK other = (EstabDatosIPK) object;
        if (this.partido != other.partido) {
            return false;
        }
        if (this.numero != other.numero) {
            return false;
        }
        if (this.cRS != other.cRS) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.validarcuit.entities.EstabDatosIPK[ partido=" + partido + ", numero=" + numero + ", cRS=" + cRS + " ]";
    }
    
}
