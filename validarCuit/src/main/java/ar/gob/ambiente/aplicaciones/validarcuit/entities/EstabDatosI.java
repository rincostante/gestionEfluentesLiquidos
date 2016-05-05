/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.gob.ambiente.aplicaciones.validarcuit.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rincostante
 */
@Entity
@Table(name = "estab_datos_i")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstabDatosI.findAll", query = "SELECT e FROM EstabDatosI e")})
public class EstabDatosI implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EstabDatosIPK estabDatosIPK;
    @Column(name = "razon_soc")
    private String razonSoc;
    @Column(name = "calle1")
    private String calle1;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nro1")
    private Double nro1;
    @Column(name = "cod_pos1")
    private Double codPos1;
    @Column(name = "local1")
    private String local1;
    @Column(name = "part_inmob")
    private String partInmob;
    @Column(name = "nomen_catas")
    private String nomenCatas;
    @Column(name = "longitud")
    private Integer longitud;
    @Column(name = "latitud")
    private Integer latitud;
    @Column(name = "calle2")
    private String calle2;
    @Column(name = "nro2")
    private Double nro2;
    @Column(name = "cod_pos2")
    private Double codPos2;
    @Column(name = "local2")
    private String local2;
    @Column(name = "parti2")
    private String parti2;
    @Column(name = "cuit")
    private String cuit;
    @Column(name = "tel")
    private String tel;
    @Column(name = "fax")
    private String fax;
    @Column(name = "email")
    private String email;
    @Column(name = "act1")
    private String act1;
    @Column(name = "act2")
    private String act2;
    @Column(name = "fe_ins_est")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feInsEst;
    @Column(name = "fe_ini_act")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feIniAct;
    @Column(name = "fe_ces_act")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feCesAct;
    @Column(name = "fe_cam_rs")
    @Temporal(TemporalType.TIMESTAMP)
    private Date feCamRs;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "validado")
    private Boolean validado;
    @Column(name = "rs_validada")
    private String rsValidada;

    public EstabDatosI() {
    }

    public EstabDatosI(EstabDatosIPK estabDatosIPK) {
        this.estabDatosIPK = estabDatosIPK;
    }

    public EstabDatosI(int partido, int numero, int cRS) {
        this.estabDatosIPK = new EstabDatosIPK(partido, numero, cRS);
    }

    public EstabDatosIPK getEstabDatosIPK() {
        return estabDatosIPK;
    }

    public void setEstabDatosIPK(EstabDatosIPK estabDatosIPK) {
        this.estabDatosIPK = estabDatosIPK;
    }

    public String getRazonSoc() {
        return razonSoc;
    }

    public void setRazonSoc(String razonSoc) {
        this.razonSoc = razonSoc;
    }

    public String getCalle1() {
        return calle1;
    }

    public void setCalle1(String calle1) {
        this.calle1 = calle1;
    }

    public Double getNro1() {
        return nro1;
    }

    public void setNro1(Double nro1) {
        this.nro1 = nro1;
    }

    public Double getCodPos1() {
        return codPos1;
    }

    public void setCodPos1(Double codPos1) {
        this.codPos1 = codPos1;
    }

    public String getLocal1() {
        return local1;
    }

    public void setLocal1(String local1) {
        this.local1 = local1;
    }

    public String getPartInmob() {
        return partInmob;
    }

    public void setPartInmob(String partInmob) {
        this.partInmob = partInmob;
    }

    public String getNomenCatas() {
        return nomenCatas;
    }

    public void setNomenCatas(String nomenCatas) {
        this.nomenCatas = nomenCatas;
    }

    public Integer getLongitud() {
        return longitud;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }

    public Integer getLatitud() {
        return latitud;
    }

    public void setLatitud(Integer latitud) {
        this.latitud = latitud;
    }

    public String getCalle2() {
        return calle2;
    }

    public void setCalle2(String calle2) {
        this.calle2 = calle2;
    }

    public Double getNro2() {
        return nro2;
    }

    public void setNro2(Double nro2) {
        this.nro2 = nro2;
    }

    public Double getCodPos2() {
        return codPos2;
    }

    public void setCodPos2(Double codPos2) {
        this.codPos2 = codPos2;
    }

    public String getLocal2() {
        return local2;
    }

    public void setLocal2(String local2) {
        this.local2 = local2;
    }

    public String getParti2() {
        return parti2;
    }

    public void setParti2(String parti2) {
        this.parti2 = parti2;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAct1() {
        return act1;
    }

    public void setAct1(String act1) {
        this.act1 = act1;
    }

    public String getAct2() {
        return act2;
    }

    public void setAct2(String act2) {
        this.act2 = act2;
    }

    public Date getFeInsEst() {
        return feInsEst;
    }

    public void setFeInsEst(Date feInsEst) {
        this.feInsEst = feInsEst;
    }

    public Date getFeIniAct() {
        return feIniAct;
    }

    public void setFeIniAct(Date feIniAct) {
        this.feIniAct = feIniAct;
    }

    public Date getFeCesAct() {
        return feCesAct;
    }

    public void setFeCesAct(Date feCesAct) {
        this.feCesAct = feCesAct;
    }

    public Date getFeCamRs() {
        return feCamRs;
    }

    public void setFeCamRs(Date feCamRs) {
        this.feCamRs = feCamRs;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public String getRsValidada() {
        return rsValidada;
    }

    public void setRsValidada(String rsValidada) {
        this.rsValidada = rsValidada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estabDatosIPK != null ? estabDatosIPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstabDatosI)) {
            return false;
        }
        EstabDatosI other = (EstabDatosI) object;
        if ((this.estabDatosIPK == null && other.estabDatosIPK != null) || (this.estabDatosIPK != null && !this.estabDatosIPK.equals(other.estabDatosIPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ar.gob.ambiente.aplicaciones.validarcuit.entities.EstabDatosI[ estabDatosIPK=" + estabDatosIPK + " ]";
    }
    
}
