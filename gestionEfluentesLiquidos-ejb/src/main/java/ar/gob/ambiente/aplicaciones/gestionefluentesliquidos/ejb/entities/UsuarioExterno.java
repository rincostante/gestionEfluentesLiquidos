/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entidad para gestionar los datos de los usuarios externos
 * @author rincostante
 */
@Entity
public class UsuarioExterno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo utilizado como nombre de usuario, es único
     */
    @Column (nullable=false, length=20, unique=true)
    @NotNull(message = "El campo cude no puede ser nulo")
    @Size(message = "El campo cude no puede contener más de 20 caracteres", min = 1, max = 20)    
    private String cude;
    
    /**
     * Contraseña que deberá cambiarse al iniciar sesión por primera vez
     */
    @Column (nullable=false, length=100, unique=true)
    @NotNull(message = "El campo clave no puede ser nulo")
    @Size(message = "el campo clave no puede tener más de 100 caracteres", min = 1, max = 100)
    private String clave;
    
    /**
     * Correo electrónico del usuario que operará en representación del Establecimiento.
     * Este campo es ingresado por el usuario al momento de solicitar la cuenta de acceso.
     */
    @Column (nullable=false, length=50)
    @NotNull(message = "El campo email no puede ser nulo")
    @Size(message = "El campo email no puede tener más de 50 caracteres", min = 1, max = 50)
    private String email;
    
    /**
     * Este campo solo está en true cuando el usuario es registrado o
     * su contraseña blanqueada. El sistema validará este campo en cada 
     * inicio de sesión, en caso de ser verdadero redirecciona al cambio de contraseña,
     * la que una vez actualizada permitirá al usuario reiniciar sesión y operar normalemente el sistema.
     */
    @Column(nullable=false)
    @NotNull(message = "El campo primeraVez no puede quedar vacío")
    private boolean primeraVez = true;    
    
    /**
     * Campo que se completa al momento de la creación del usuario, con los datos correspondientes del RUP
     */
    @Column (nullable=false, length=50)
    @NotNull(message = "El campo cude no puede ser nulo")
    @Size(message = "El campo cude no puede contener más de 50 caracteres", min = 1, max = 50)      
    private String razonSocial;
    
    /**
     * Campo que se completa al momento de la creación del usuario, con los datos correspondientes del RUP
     */
    @Column (nullable=false)
    @NotNull(message = "El campo cuit no puede ser nulo")  
    private Long cuit;
    
    /**
     * Campo que se completa al momento de la creación del usuario, con los datos correspondientes del RUP
     */
    @Column (length=100)
    @Size(message = "El campo Tipo de Establecimiento no puede contener más de 100 caracteres", max = 100)        
    private String tipoEst;
    
    /**
     * Campo que se completa al momento de la creación del usuario, con los datos correspondientes del RUP
     */
    @Column (nullable=false, length=50)
    @NotNull(message = "El campo Calle no puede ser nulo")
    @Size(message = "El campo Calle no puede contener más de 50 caracteres", min = 1, max = 50)   
    private String domCalle;
    
    /**
     * Campo que se completa al momento de la creación del usuario, con los datos correspondientes del RUP
     */
    @Column (nullable=false, length=10)
    @NotNull(message = "El campo Número no puede ser nulo")
    @Size(message = "El campo Número no puede contener más de 10 caracteres", min = 1, max = 10)   
    private String numero;
    
    /**
     * Campo que se completa al momento de la creación del usuario, con los datos correspondientes del RUP
     */
    @Column (length=10)
    @Size(message = "El campo Piso no puede contener más de 10 caracteres", max = 10)      
    private String piso; 
    
    /**
     * Campo que se completa al momento de la creación del usuario, con los datos correspondientes del RUP
     */
    @Column (length=10)
    @Size(message = "El campo Departamento no puede contener más de 10 caracteres", max = 10)  
    private String dpto;  
    
    /**
     * Campo que se completa al momento de la creación del usuario, con los datos correspondientes del RUP
     */
    @Column (length=50)
    @Size(message = "El campo Localidad no puede contener más de 50 caracteres", max = 50)      
    private String localidad;
    
    /**
     * Campo de tipo AdmEntidad que encapsula los datos propios para su trazabilidad.
     */
    @OneToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @NotNull(message = "El campo admin no puede quedar nulo") 
    private AdminEntidad admin;     
    
    /**
     * Campo que muestra el cuit con "-"
     */
    @Transient
    String strCuit;    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AdminEntidad getAdmin() {
        return admin;
    }

    public void setAdmin(AdminEntidad admin) {
        this.admin = admin;
    }

    public String getCude() {
        return cude;
    }

    public void setCude(String cude) {
        this.cude = cude;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isPrimeraVez() {
        return primeraVez;
    }

    public void setPrimeraVez(boolean primeraVez) {
        this.primeraVez = primeraVez;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Long getCuit() {
        return cuit;
    }

    public void setCuit(Long cuit) {
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDpto() {
        return dpto;
    }

    public void setDpto(String dpto) {
        this.dpto = dpto;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getStrCuit() {
        return String.valueOf(cuit).substring(0, 2) + "-" + String.valueOf(cuit).substring(2, 9) + "-" + String.valueOf(cuit).charAt(10);
    }

    public void setStrCuit(String strCuit) {
        this.strCuit = strCuit;
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
        if (!(object instanceof UsuarioExterno)) {
            return false;
        }
        UsuarioExterno other = (UsuarioExterno) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.modelo.UsuarioExterno[ id=" + id + " ]";
    }
    
}
