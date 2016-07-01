/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author rincostante
 */
@Entity
public class Pozo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarda el número de orden del pozo
     */
    @Column (nullable=false)
    @NotNull(message = "El campo numero no puede ser nulo")
    private int numero;     
    
    /**
     * Campo que indica si el pozo está en servicio
     */
    private boolean enServicio;
    
    /**
     * Campo que guarda el rendimiento del pozo en m3/h
     */
    @Column
    private float rendimiento;  
    
    /**
     * Campo que guarda las horas diarias de funcionamiento del pozo
     */
    @Column (nullable=false)
    @NotNull(message = "El campo horasFunc no puede ser nulo")
    private float horasFunc;    
    
    /**
     * Campo que guarda la cantidad de días por semana de funcionamiento del pozo
     */
    @Column (nullable=false)
    @NotNull(message = "El diasFunc numero no puede ser nulo")
    private int diasFunc;     
    
    /**
     * Campo que guarda la profundidad en mts del pozo.
     */
    @Column (nullable=false)
    @NotNull(message = "El campo profundidad no puede ser nulo")
    private float profundidad; 
    
    /**
     * Campo que guarda la potencia de la bomba en mts del pozo.
     */
    @Column
    private float potenciaBomba;     
    
    /**
     * Campo que guarda la clave del tipo de bomba
     * Con el método getTipo, se accede a la tipo de bomba correpondiente a la clave
     */
    @Column
    private int tipoBomba;
    
    /**
     * Listado para seleccionar el tipo de bomba
     */
    @Transient
    private static final Map<Integer, String> MP_TIPO_BOMBA;
    static{
       MP_TIPO_BOMBA = new TreeMap<>();
       MP_TIPO_BOMBA.put(1, "De Eje Vertical");
       MP_TIPO_BOMBA.put(2, "A Motor Sumergido");
       MP_TIPO_BOMBA.put(3, "Con Inyección");
       MP_TIPO_BOMBA.put(4, "A Cilindro");
       MP_TIPO_BOMBA.put(5, "Centrífuga");
       MP_TIPO_BOMBA.put(6, "Otra");
    }     

    
    /**********************
     * Métodos de acceso **
     **********************/      
    public String getTipoBmb() {
        return MP_TIPO_BOMBA.get(tipoBomba);
    }       
    
    public float getPotenciaBomba() {
        return potenciaBomba;
    }

    public void setPotenciaBomba(float potenciaBomba) {
        this.potenciaBomba = potenciaBomba;
    }
      
    public float getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(float profundidad) {
        this.profundidad = profundidad;
    }
      
    public static Map<Integer, String> getMP_TIPO_BOMBA() {
        return MP_TIPO_BOMBA;
    } 

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isEnServicio() {
        return enServicio;
    }

    public void setEnServicio(boolean enServicio) {
        this.enServicio = enServicio;
    }

    public float getRendimiento() {
        return rendimiento;
    }

    public void setRendimiento(float rendimiento) {
        this.rendimiento = rendimiento;
    }

    public float getHorasFunc() {
        return horasFunc;
    }

    public void setHorasFunc(float horasFunc) {
        this.horasFunc = horasFunc;
    }

    public int getDiasFunc() {
        return diasFunc;
    }

    public void setDiasFunc(int diasFunc) {
        this.diasFunc = diasFunc;
    }

    public int getTipoBomba() {
        return tipoBomba;
    }

    public void setTipoBomba(int tipoBomba) {
        this.tipoBomba = tipoBomba;
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
        if (!(object instanceof Pozo)) {
            return false;
        }
        Pozo other = (Pozo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Pozo[ id=" + id + " ]";
    }
    
}
