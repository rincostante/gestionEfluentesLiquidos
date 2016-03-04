/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.gestionefluentesliquidos.webext.modelo;

import java.io.Serializable;

/**
 * Clase que encapsula los datos la solicitud de cuenta de usuario
 * @author rincostante
 */
public class SolicitudCuenta implements Serializable{
        private String cude;
        private String codigoRecibo;
        private Long cuitEstablecimiento;
        private Long cuitFirmante;
        private String correoElectronico;

        public String getCude() {
            return cude;
        }

        public void setCude(String cude) {
            this.cude = cude;
        }

        public String getCodigoRecibo() {
            return codigoRecibo;
        }

        public void setCodigoRecibo(String codigoRecibo) {
            this.codigoRecibo = codigoRecibo;
        }

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

        public String getCorreoElectronico() {
            return correoElectronico;
        }

        public void setCorreoElectronico(String correoElectronico) {
            this.correoElectronico = correoElectronico;
        }    
}
