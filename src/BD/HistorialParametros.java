/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Miguel Askar
 */
@Entity
@Table(name = "historial_parametros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistorialParametros.findAll", query = "SELECT h FROM HistorialParametros h")
    , @NamedQuery(name = "HistorialParametros.findByTipoid", query = "SELECT h FROM HistorialParametros h WHERE h.historialParametrosPK.tipoid = :tipoid")
    , @NamedQuery(name = "HistorialParametros.findByIdentificacion", query = "SELECT h FROM HistorialParametros h WHERE h.historialParametrosPK.identificacion = :identificacion")
    , @NamedQuery(name = "HistorialParametros.findByFecha", query = "SELECT h FROM HistorialParametros h WHERE h.historialParametrosPK.fecha = :fecha")
    , @NamedQuery(name = "HistorialParametros.findByHora", query = "SELECT h FROM HistorialParametros h WHERE h.hora = :hora")
    , @NamedQuery(name = "HistorialParametros.findByHrEcg", query = "SELECT h FROM HistorialParametros h WHERE h.hrEcg = :hrEcg")
    , @NamedQuery(name = "HistorialParametros.findByHrSpo2", query = "SELECT h FROM HistorialParametros h WHERE h.hrSpo2 = :hrSpo2")
    , @NamedQuery(name = "HistorialParametros.findBySpo2oxi", query = "SELECT h FROM HistorialParametros h WHERE h.spo2oxi = :spo2oxi")
    , @NamedQuery(name = "HistorialParametros.findByFrecResp", query = "SELECT h FROM HistorialParametros h WHERE h.frecResp = :frecResp")
    , @NamedQuery(name = "HistorialParametros.findByPresSist", query = "SELECT h FROM HistorialParametros h WHERE h.presSist = :presSist")
    , @NamedQuery(name = "HistorialParametros.findByPresDiast", query = "SELECT h FROM HistorialParametros h WHERE h.presDiast = :presDiast")
    , @NamedQuery(name = "HistorialParametros.findByPresMedia", query = "SELECT h FROM HistorialParametros h WHERE h.presMedia = :presMedia")
    , @NamedQuery(name = "HistorialParametros.findByPresRate", query = "SELECT h FROM HistorialParametros h WHERE h.presRate = :presRate")
    , @NamedQuery(name = "HistorialParametros.findByTemperatura", query = "SELECT h FROM HistorialParametros h WHERE h.temperatura = :temperatura")
    , @NamedQuery(name = "HistorialParametros.findByPeso", query = "SELECT h FROM HistorialParametros h WHERE h.peso = :peso")
    , @NamedQuery(name = "HistorialParametros.findByPorcAgua", query = "SELECT h FROM HistorialParametros h WHERE h.porcAgua = :porcAgua")
    , @NamedQuery(name = "HistorialParametros.findByGrasaCorporal", query = "SELECT h FROM HistorialParametros h WHERE h.grasaCorporal = :grasaCorporal")
    , @NamedQuery(name = "HistorialParametros.findByMasaMuscular", query = "SELECT h FROM HistorialParametros h WHERE h.masaMuscular = :masaMuscular")
    , @NamedQuery(name = "HistorialParametros.findByImc", query = "SELECT h FROM HistorialParametros h WHERE h.imc = :imc")})
public class HistorialParametros implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected HistorialParametrosPK historialParametrosPK;
    @Basic(optional = false)
    @Column(name = "hora")
    private double hora;
    @Basic(optional = false)
    @Column(name = "hr_ecg")
    private int hrEcg;
    @Basic(optional = false)
    @Column(name = "hr_spo2")
    private int hrSpo2;
    @Basic(optional = false)
    @Column(name = "spo2oxi")
    private int spo2oxi;
    @Basic(optional = false)
    @Column(name = "frec_resp")
    private int frecResp;
    @Basic(optional = false)
    @Column(name = "pres_Sist")
    private int presSist;
    @Basic(optional = false)
    @Column(name = "pres_diast")
    private int presDiast;
    @Basic(optional = false)
    @Column(name = "pres_media")
    private int presMedia;
    @Basic(optional = false)
    @Column(name = "pres_rate")
    private int presRate;
    @Basic(optional = false)
    @Column(name = "temperatura")
    private int temperatura;
    @Basic(optional = false)
    @Column(name = "peso")
    private double peso;
    @Basic(optional = false)
    @Column(name = "porc_agua")
    private double porcAgua;
    @Basic(optional = false)
    @Column(name = "grasa_corporal")
    private double grasaCorporal;
    @Basic(optional = false)
    @Column(name = "masa_muscular")
    private double masaMuscular;
    @Basic(optional = false)
    @Column(name = "imc")
    private double imc;

    public HistorialParametros() {
    }

    public HistorialParametros(HistorialParametrosPK historialParametrosPK) {
        this.historialParametrosPK = historialParametrosPK;
    }

    public HistorialParametros(HistorialParametrosPK historialParametrosPK, double hora, int hrEcg, int hrSpo2, int spo2oxi, int frecResp, int presSist, int presDiast, int presMedia, int presRate, int temperatura, double peso, double porcAgua, double grasaCorporal, double masaMuscular, double imc) {
        this.historialParametrosPK = historialParametrosPK;
        this.hora = hora;
        this.hrEcg = hrEcg;
        this.hrSpo2 = hrSpo2;
        this.spo2oxi = spo2oxi;
        this.frecResp = frecResp;
        this.presSist = presSist;
        this.presDiast = presDiast;
        this.presMedia = presMedia;
        this.presRate = presRate;
        this.temperatura = temperatura;
        this.peso = peso;
        this.porcAgua = porcAgua;
        this.grasaCorporal = grasaCorporal;
        this.masaMuscular = masaMuscular;
        this.imc = imc;
    }

    public HistorialParametros(String tipoid, String identificacion, Date fecha) {
        this.historialParametrosPK = new HistorialParametrosPK(tipoid, identificacion, fecha);
    }

    public HistorialParametrosPK getHistorialParametrosPK() {
        return historialParametrosPK;
    }

    public void setHistorialParametrosPK(HistorialParametrosPK historialParametrosPK) {
        this.historialParametrosPK = historialParametrosPK;
    }

    public double getHora() {
        return hora;
    }

    public void setHora(double hora) {
        this.hora = hora;
    }

    public int getHrEcg() {
        return hrEcg;
    }

    public void setHrEcg(int hrEcg) {
        this.hrEcg = hrEcg;
    }

    public int getHrSpo2() {
        return hrSpo2;
    }

    public void setHrSpo2(int hrSpo2) {
        this.hrSpo2 = hrSpo2;
    }

    public int getSpo2oxi() {
        return spo2oxi;
    }

    public void setSpo2oxi(int spo2oxi) {
        this.spo2oxi = spo2oxi;
    }

    public int getFrecResp() {
        return frecResp;
    }

    public void setFrecResp(int frecResp) {
        this.frecResp = frecResp;
    }

    public int getPresSist() {
        return presSist;
    }

    public void setPresSist(int presSist) {
        this.presSist = presSist;
    }

    public int getPresDiast() {
        return presDiast;
    }

    public void setPresDiast(int presDiast) {
        this.presDiast = presDiast;
    }

    public int getPresMedia() {
        return presMedia;
    }

    public void setPresMedia(int presMedia) {
        this.presMedia = presMedia;
    }

    public int getPresRate() {
        return presRate;
    }

    public void setPresRate(int presRate) {
        this.presRate = presRate;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getPorcAgua() {
        return porcAgua;
    }

    public void setPorcAgua(double porcAgua) {
        this.porcAgua = porcAgua;
    }

    public double getGrasaCorporal() {
        return grasaCorporal;
    }

    public void setGrasaCorporal(double grasaCorporal) {
        this.grasaCorporal = grasaCorporal;
    }

    public double getMasaMuscular() {
        return masaMuscular;
    }

    public void setMasaMuscular(double masaMuscular) {
        this.masaMuscular = masaMuscular;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historialParametrosPK != null ? historialParametrosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialParametros)) {
            return false;
        }
        HistorialParametros other = (HistorialParametros) object;
        if ((this.historialParametrosPK == null && other.historialParametrosPK != null) || (this.historialParametrosPK != null && !this.historialParametrosPK.equals(other.historialParametrosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BD.HistorialParametros[ historialParametrosPK=" + historialParametrosPK + " ]";
    }
    
}
