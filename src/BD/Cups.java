/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Miguel Askar
 */
@Entity
@Table(name = "cups")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cups.findAll", query = "SELECT c FROM Cups c")
    , @NamedQuery(name = "Cups.findByCodigo", query = "SELECT c FROM Cups c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "Cups.findByNombre", query = "SELECT c FROM Cups c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Cups.findByGenero", query = "SELECT c FROM Cups c WHERE c.genero = :genero")
    , @NamedQuery(name = "Cups.findByEdadminanos", query = "SELECT c FROM Cups c WHERE c.edadminanos = :edadminanos")
    , @NamedQuery(name = "Cups.findByEdadmaxanos", query = "SELECT c FROM Cups c WHERE c.edadmaxanos = :edadmaxanos")
    , @NamedQuery(name = "Cups.findByMaxvecesdia", query = "SELECT c FROM Cups c WHERE c.maxvecesdia = :maxvecesdia")
    , @NamedQuery(name = "Cups.findByMaxvecesvida", query = "SELECT c FROM Cups c WHERE c.maxvecesvida = :maxvecesvida")
    , @NamedQuery(name = "Cups.findByTiempolimitedia", query = "SELECT c FROM Cups c WHERE c.tiempolimitedia = :tiempolimitedia")
    , @NamedQuery(name = "Cups.findByQuirurgico", query = "SELECT c FROM Cups c WHERE c.quirurgico = :quirurgico")
    , @NamedQuery(name = "Cups.findByTipoatencion", query = "SELECT c FROM Cups c WHERE c.tipoatencion = :tipoatencion")
    , @NamedQuery(name = "Cups.findByTipoprocedimiento", query = "SELECT c FROM Cups c WHERE c.tipoprocedimiento = :tipoprocedimiento")
    , @NamedQuery(name = "Cups.findByRes008", query = "SELECT c FROM Cups c WHERE c.res008 = :res008")
    , @NamedQuery(name = "Cups.findByRes029", query = "SELECT c FROM Cups c WHERE c.res029 = :res029")
    , @NamedQuery(name = "Cups.findByRes5521", query = "SELECT c FROM Cups c WHERE c.res5521 = :res5521")
    , @NamedQuery(name = "Cups.findByRes5592", query = "SELECT c FROM Cups c WHERE c.res5592 = :res5592")})
public class Cups implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "genero")
    private String genero;
    @Column(name = "edadminanos")
    private Integer edadminanos;
    @Column(name = "edadmaxanos")
    private Integer edadmaxanos;
    @Column(name = "maxvecesdia")
    private Integer maxvecesdia;
    @Column(name = "maxvecesvida")
    private Integer maxvecesvida;
    @Column(name = "tiempolimitedia")
    private Integer tiempolimitedia;
    @Column(name = "quirurgico")
    private String quirurgico;
    @Column(name = "tipoatencion")
    private String tipoatencion;
    @Column(name = "tipoprocedimiento")
    private String tipoprocedimiento;
    @Column(name = "res008")
    private Integer res008;
    @Column(name = "res029")
    private Integer res029;
    @Column(name = "res5521")
    private Integer res5521;
    @Column(name = "res5592")
    private Integer res5592;

    public Cups() {
    }

    public Cups(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getEdadminanos() {
        return edadminanos;
    }

    public void setEdadminanos(Integer edadminanos) {
        this.edadminanos = edadminanos;
    }

    public Integer getEdadmaxanos() {
        return edadmaxanos;
    }

    public void setEdadmaxanos(Integer edadmaxanos) {
        this.edadmaxanos = edadmaxanos;
    }

    public Integer getMaxvecesdia() {
        return maxvecesdia;
    }

    public void setMaxvecesdia(Integer maxvecesdia) {
        this.maxvecesdia = maxvecesdia;
    }

    public Integer getMaxvecesvida() {
        return maxvecesvida;
    }

    public void setMaxvecesvida(Integer maxvecesvida) {
        this.maxvecesvida = maxvecesvida;
    }

    public Integer getTiempolimitedia() {
        return tiempolimitedia;
    }

    public void setTiempolimitedia(Integer tiempolimitedia) {
        this.tiempolimitedia = tiempolimitedia;
    }

    public String getQuirurgico() {
        return quirurgico;
    }

    public void setQuirurgico(String quirurgico) {
        this.quirurgico = quirurgico;
    }

    public String getTipoatencion() {
        return tipoatencion;
    }

    public void setTipoatencion(String tipoatencion) {
        this.tipoatencion = tipoatencion;
    }

    public String getTipoprocedimiento() {
        return tipoprocedimiento;
    }

    public void setTipoprocedimiento(String tipoprocedimiento) {
        this.tipoprocedimiento = tipoprocedimiento;
    }

    public Integer getRes008() {
        return res008;
    }

    public void setRes008(Integer res008) {
        this.res008 = res008;
    }

    public Integer getRes029() {
        return res029;
    }

    public void setRes029(Integer res029) {
        this.res029 = res029;
    }

    public Integer getRes5521() {
        return res5521;
    }

    public void setRes5521(Integer res5521) {
        this.res5521 = res5521;
    }

    public Integer getRes5592() {
        return res5592;
    }

    public void setRes5592(Integer res5592) {
        this.res5592 = res5592;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cups)) {
            return false;
        }
        Cups other = (Cups) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BD.Cups[ codigo=" + codigo + " ]";
    }
    
}
