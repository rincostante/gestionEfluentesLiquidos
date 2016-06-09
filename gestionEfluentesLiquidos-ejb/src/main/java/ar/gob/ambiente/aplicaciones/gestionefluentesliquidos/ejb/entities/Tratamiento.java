

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
 * Entidad que encapsula los distintos tratamientos previos que puede sufrir la descarga del efluente líquido
 * @author rincostante
 */
@Entity
public class Tratamiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarda la clave del nombre del tratamiento.
     * Con el método getNombre, se accede a la descripción correpondiente a la clave
     */
    @Column (nullable=false)
    @NotNull(message = "El campo claveNombre no puede ser nulo")
    private int claveNombre;
    
    /**
     * Campo que guarda el valor del tipo de tratamiento
     * Podrá ser entre 1 y 6
     */
    @Column (nullable=false)
    @NotNull(message = "El campo valor no puede ser nulo")
    private int valor;
    
    /**
     * Listado para seleccionar el nombre del tratamiento
     */
    @Transient
    private static final Map<Integer, String> MP_NOMBRES;
    static{
       MP_NOMBRES = new TreeMap<>();
       MP_NOMBRES.put(1, "PRETRATAMIENTO");
       MP_NOMBRES.put(2, "PRIMARIO FISICO");
       MP_NOMBRES.put(3, "PRIMARIO QUIMICO");
       MP_NOMBRES.put(4, "SECUNDARIO");
       MP_NOMBRES.put(5, "DESINFECCION");
    }      
    
    /**
     * Listado para seleccionar el tipo de Pretratamiento
     */
    @Transient
    private static final Map<Integer, String> MP_TIPOPRET;
    static{
       MP_TIPOPRET = new TreeMap<>();
       MP_TIPOPRET.put(1, "Rejas");
       MP_TIPOPRET.put(2, "Tamices");
       MP_TIPOPRET.put(3, "Desarenador");
       MP_TIPOPRET.put(4, "Tanque compensación");
       MP_TIPOPRET.put(5, "Desengrasador");
       MP_TIPOPRET.put(6, "Otro");
    }     
    
    /**
     * Listado para seleccionar el tipo de Tratamiento primario físico
     */
    @Transient
    private static final Map<Integer, String> MP_TIPOTRATPRIMFIS;
    static{
       MP_TIPOTRATPRIMFIS = new TreeMap<>();
       MP_TIPOTRATPRIMFIS.put(1, "Sedimentación");
       MP_TIPOTRATPRIMFIS.put(2, "Flotación");
       MP_TIPOTRATPRIMFIS.put(3, "Otro");
    }      
    
    /**
     * Listado para seleccionar el tipo de Tratamiento primario químico
     */
    @Transient
    private static final Map<Integer, String> MP_TIPOTRATPRIMQUIM;
    static{
       MP_TIPOTRATPRIMQUIM = new TreeMap<>();
       MP_TIPOTRATPRIMQUIM.put(1, "Coagulación");
       MP_TIPOTRATPRIMQUIM.put(2, "Neutralización");
       MP_TIPOTRATPRIMQUIM.put(3, "Oxidorreducción");
       MP_TIPOTRATPRIMQUIM.put(4, "Otro");
    }        
    
    /**
     * Listado para seleccionar el tipo de Tratamiento secundario
     */
    @Transient
    private static final Map<Integer, String> MP_TIPOTRATSEC;
    static{
       MP_TIPOTRATSEC = new TreeMap<>();
       MP_TIPOTRATSEC.put(1, "Lecho percolador");
       MP_TIPOTRATSEC.put(2, "Barro activado");
       MP_TIPOTRATSEC.put(3, "Zanja de oxidación");
       MP_TIPOTRATSEC.put(4, "Laguna de estabilización");
       MP_TIPOTRATSEC.put(5, "Laguna aireada");
       MP_TIPOTRATSEC.put(6, "Otro");
    }       
    
    /**
     * Listado para seleccionar el tipo de Desinfección
     */
    @Transient
    private static final Map<Integer, String> MP_TIPODESINF;
    static{
       MP_TIPODESINF = new TreeMap<>();
       MP_TIPODESINF.put(1, "Cloración");
       MP_TIPODESINF.put(2, "Otro");
    }       

    /**********************
     * Métodos de acceso **
     **********************/   
    
    /***** Map nombre del Tratamiento ******
     * clave: claveNombre
     * valor: nombre
     * map: MP_NOMBRES
     */    
    public String getNombre() {
        return MP_NOMBRES.get(claveNombre);
    }    
    
    public static Map<Integer, String> getMP_NOMBRES() {
        return MP_NOMBRES;
    }  
    
    public int getClaveNombre() {
        return claveNombre;
    }

    public void setClaveNombre(int claveNombre) {
        this.claveNombre = claveNombre;
    }
    
    /***** Map Tipo Pretratamiento ******
     * clave: valor (para todos)
     * Valor: tipoPretrat
     * map: MP_TIPOPRET
     */ 
    public String getTipoPretrat() {
        return MP_TIPOPRET.get(valor);
    }    
    
    public static Map<Integer, String> getMP_TIPOPRET() {
        return MP_TIPOPRET;
    }  
    
    /***** Map Tipo Tratamiento primario físico ******
     * clave: valor (para todos)
     * Valor: tipoTratPrimFisico
     * map: MP_TIPOTRATPRIMFIS
     */  
    public String getTipoTratPrimFisico() {
        return MP_TIPOTRATPRIMFIS.get(valor);
    }    
    
    public static Map<Integer, String> getMP_TIPOTRATPRIMFIS() {
        return MP_TIPOTRATPRIMFIS;
    }    
    
    /***** Map Tipo Tratamiento primario químico ******
     * clave: valor (para todos)
     * Valor: tipoTratPrimQuimico
     * map: MP_TIPOTRATPRIMQUIM
     */  
    public String getTipoTratPrimQuimico() {
        return MP_TIPOTRATPRIMQUIM.get(valor);
    }    
    
    public static Map<Integer, String> getMP_TIPOTRATPRIMQUIM() {
        return MP_TIPOTRATPRIMQUIM;
    }   
    
    /***** Map Tipo Tratamiento secundario ******
     * clave: valor (para todos)
     * Valor: tipoTratSec
     * map: MP_TIPOTRATSEC
     */  
    public String getTipoTratSec() {
        return MP_TIPOTRATSEC.get(valor);
    }    
    
    public static Map<Integer, String> getMP_TIPOTRATSEC() {
        return MP_TIPOTRATSEC;
    } 
    
    /***** Map Tipo Desinfección ******
     * clave: valor (para todos)
     * Valor: tipoDesinf
     * map: MP_TIPODESINF
     */  
    public String getTipoDesinf() {
        return MP_TIPODESINF.get(valor);
    }    
    
    public static Map<Integer, String> getMP_TIPODESINF() {
        return MP_TIPODESINF;
    }     

    ///////////////////////////////////////////////////////////////////
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    
    /**
     * Método que devuelve el valor del tratado según el tipo de tratamiento.
     * @return 
     */
    public String getTratado(){
        String tratado;
        switch (claveNombre){
            case 1: 
                tratado = this.getTipoPretrat();
                break;
            case 2:
                tratado = this.getTipoTratPrimFisico();
                break;
            case 3:
                tratado = this.getTipoTratPrimQuimico();
                break;
            case 4:
                tratado = this.getTipoTratSec();
                break;
            default:
                tratado = this.getTipoDesinf();
                break;
        }
        return tratado;
    }
    ///////////////////////////////////////////////////////////////////
    
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
        if (!(object instanceof Tratamiento)) {
            return false;
        }
        Tratamiento other = (Tratamiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Tratamiento[ id=" + id + " ]";
    }
    
}
