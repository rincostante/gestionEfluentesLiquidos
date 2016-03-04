

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.modelo;

import java.io.Serializable;

/**
 * Clase que encapsula los datos de la consulta del CUDE
 * @author rincostante
 */
public class ConsultaCude implements Serializable{
    private Long cuitEstablecimiento;
    private Long cuitFirmante;

    public Long getCuitEstablecimiento() {
        return cuitEstablecimiento;
    }

    public void setCuitEstablecimiento(Long cuitEstablecimiento) {
        this.cuitEstablecimiento = cuitEstablecimiento;
    }

    public Long getCuitFirmante() {
        return cuitFirmante;
    }

    public void setCuitFirmante(Long cuitFirmante) {
        this.cuitFirmante = cuitFirmante;
    }
}
