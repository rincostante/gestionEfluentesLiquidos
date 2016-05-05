

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 * Clase que encapsula los horarios y turnos de cada día laborable de la semana
 * @author rincostante
 */
@Entity
public class Dia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Campo que guarda la clave del día
     * Con el método getDia, se accede al Día correpondiente a la clave
     */
    private int codDia;        
    
    /**
     * Listado para seleccionar los días de la semana
     */
    @Transient
    private static final Map<Integer, String> MP_DIAS;
    static{
       MP_DIAS = new TreeMap<>();
       MP_DIAS.put(1, "DOMINGO");
       MP_DIAS.put(2, "LUNES");
       MP_DIAS.put(3, "MARTES");
       MP_DIAS.put(4, "MIERCOLES");
       MP_DIAS.put(5, "JUEVES");
       MP_DIAS.put(6, "VIERNES");
       MP_DIAS.put(7, "SABADO");
    } 
    
    /**
     * Campo que guarda la hora de inicio de la descarga del efluente para el día correspondiente
     */
    private int horasInicDesc;
    
    /**
     * Campo que guarda los minutos inicio de la descarga del efluente para el día correspondiente
     */
    private int minInicDesc;
    
    /**
     * Campo que guarda la hora de finalización de la descarga del efluente para el día correspondiente
     */
    private int horasFinDesc;
    
    /**
     * Campo que guarda los minutos finalizazión de la descarga del efluente para el día correspondiente
     */
    private int minFinDesc;    
    
    /**
     * Campo que muestra los turnos de trabajo que componen el día laboral
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dia_id", referencedColumnName = "id")
    private List<Turno> turnos;       
    
    /**********************
     * Métodos de acceso **
     **********************/    

    public int getHorasInicDesc() {
        return horasInicDesc;
    }

    public void setHorasInicDesc(int horasInicDesc) {
        this.horasInicDesc = horasInicDesc;
    }

    public int getMinInicDesc() {
        return minInicDesc;
    }

    public void setMinInicDesc(int minInicDesc) {
        this.minInicDesc = minInicDesc;
    }

    public int getHorasFinDesc() {
        return horasFinDesc;
    }

    public void setHorasFinDesc(int horasFinDesc) {
        this.horasFinDesc = horasFinDesc;
    }

    public int getMinFinDesc() {
        return minFinDesc;
    }

    public void setMinFinDesc(int minFinDesc) {
        this.minFinDesc = minFinDesc;
    }
    

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }
    
    public static Map<Integer, String> getMP_DIAS() {
        return MP_DIAS;
    }     
    
    public int getCodDia() {
        return codDia;
    }

    public void setCodDia(int codDia) {
        this.codDia = codDia;
    }
    

    public String getNombre() {
        return MP_DIAS.get(codDia);
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
        if (!(object instanceof Dia)) {
            return false;
        }
        Dia other = (Dia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.ejb.entities.Dia[ id=" + id + " ]";
    }
    
}
