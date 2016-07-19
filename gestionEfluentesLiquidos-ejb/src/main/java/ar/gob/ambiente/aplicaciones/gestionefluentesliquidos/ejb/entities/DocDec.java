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
import javax.validation.constraints.Size;

/**
 *
 * @author rincostante
 */
@Entity
public class DocDec implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarada la descripción el tipo de documentación presentada con la Declaración
     */
    @Column (nullable=false, length=50)
    @NotNull(message = "El campo descripcion no puede ser nulo")
    @Size(message = "El campo descripcion no puede tener más de 50 caracteres", min = 1, max = 50)
    private String descripcion; 
    
    /**
     * Campo que guarada el número correspondiente a la documentación presentada con la Declaración
     */
    @Column (nullable=true)
    private int numero;  
    
    /**
     * Campo que guarda la clave del visado.
     * Con el método getVisado, se accede a la descripción correpondiente a la clave
     */
    @Column (nullable=true)
    private int tipoVisado;      
    
    /**
     * Listado para seleccionar la descripción de la fecha
     */
    @Transient
    private static final Map<Integer, String> MP_VISADOS;
    static{
       MP_VISADOS = new TreeMap<>();
       MP_VISADOS.put(1, "OBRA EXISTENTE");
       MP_VISADOS.put(2, "DOC. TEC. DE OBRA A EJECUTAR");
       MP_VISADOS.put(3, "ESTABLECIMIENTO SIN VUELCO");
       MP_VISADOS.put(4, "CALIDAD DE VERTIDO");
       MP_VISADOS.put(5, "NO PRESENTADA");
       MP_VISADOS.put(6, "EN ESTUDIO");
    }    

    public int getTipoVisado() {
        return tipoVisado;
    }

    public void setTipoVisado(int tipoVisado) {
        this.tipoVisado = tipoVisado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public String getVisado() {
        return MP_VISADOS.get(tipoVisado);
    }    
    
    public static Map<Integer, String> getMP_VISADOS() {
        return MP_VISADOS;
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
        if (!(object instanceof DocDec)) {
            return false;
        }
        DocDec other = (DocDec) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.DocDec[ id=" + id + " ]";
    }
    
}
