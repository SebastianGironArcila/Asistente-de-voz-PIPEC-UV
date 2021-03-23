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
@Table(name = "prestadores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prestadores.findAll", query = "SELECT p FROM Prestadores p")
    , @NamedQuery(name = "Prestadores.findByCodigo", query = "SELECT p FROM Prestadores p WHERE p.codigo = :codigo")
    , @NamedQuery(name = "Prestadores.findBySigla", query = "SELECT p FROM Prestadores p WHERE p.sigla = :sigla")
    , @NamedQuery(name = "Prestadores.findByTipoid", query = "SELECT p FROM Prestadores p WHERE p.tipoid = :tipoid")
    , @NamedQuery(name = "Prestadores.findByIdentificacion", query = "SELECT p FROM Prestadores p WHERE p.identificacion = :identificacion")
    , @NamedQuery(name = "Prestadores.findByRazonsocial", query = "SELECT p FROM Prestadores p WHERE p.razonsocial = :razonsocial")
    , @NamedQuery(name = "Prestadores.findByDepartamento", query = "SELECT p FROM Prestadores p WHERE p.departamento = :departamento")
    , @NamedQuery(name = "Prestadores.findByMunicipio", query = "SELECT p FROM Prestadores p WHERE p.municipio = :municipio")
    , @NamedQuery(name = "Prestadores.findByDireccion", query = "SELECT p FROM Prestadores p WHERE p.direccion = :direccion")
    , @NamedQuery(name = "Prestadores.findByTelFijo", query = "SELECT p FROM Prestadores p WHERE p.telFijo = :telFijo")
    , @NamedQuery(name = "Prestadores.findByCelular", query = "SELECT p FROM Prestadores p WHERE p.celular = :celular")
    , @NamedQuery(name = "Prestadores.findByEmail", query = "SELECT p FROM Prestadores p WHERE p.email = :email")
    , @NamedQuery(name = "Prestadores.findByWeb", query = "SELECT p FROM Prestadores p WHERE p.web = :web")
    , @NamedQuery(name = "Prestadores.findByRepresentante", query = "SELECT p FROM Prestadores p WHERE p.representante = :representante")
    , @NamedQuery(name = "Prestadores.findByEnlacered", query = "SELECT p FROM Prestadores p WHERE p.enlacered = :enlacered")})
public class Prestadores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "sigla")
    private String sigla;
    @Column(name = "tipoid")
    private String tipoid;
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "razonsocial")
    private String razonsocial;
    @Column(name = "departamento")
    private String departamento;
    @Column(name = "municipio")
    private String municipio;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "tel_fijo")
    private String telFijo;
    @Column(name = "celular")
    private String celular;
    @Column(name = "email")
    private String email;
    @Column(name = "web")
    private String web;
    @Column(name = "representante")
    private String representante;
    @Column(name = "enlacered")
    private String enlacered;

    public Prestadores() {
    }

    public Prestadores(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getTipoid() {
        return tipoid;
    }

    public void setTipoid(String tipoid) {
        this.tipoid = tipoid;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelFijo() {
        return telFijo;
    }

    public void setTelFijo(String telFijo) {
        this.telFijo = telFijo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getEnlacered() {
        return enlacered;
    }

    public void setEnlacered(String enlacered) {
        this.enlacered = enlacered;
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
        if (!(object instanceof Prestadores)) {
            return false;
        }
        Prestadores other = (Prestadores) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BD.Prestadores[ codigo=" + codigo + " ]";
    }
    
}
