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
@Table(name = "municipios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Municipios.findAll", query = "SELECT m FROM Municipios m")
    , @NamedQuery(name = "Municipios.findByCodigo", query = "SELECT m FROM Municipios m WHERE m.codigo = :codigo")
    , @NamedQuery(name = "Municipios.findByCodmun", query = "SELECT m FROM Municipios m WHERE m.codmun = :codmun")
    , @NamedQuery(name = "Municipios.findByNombre", query = "SELECT m FROM Municipios m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "Municipios.findByCoddpto", query = "SELECT m FROM Municipios m WHERE m.coddpto = :coddpto")
    , @NamedQuery(name = "Municipios.findByNomdpto", query = "SELECT m FROM Municipios m WHERE m.nomdpto = :nomdpto")})
public class Municipios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "codmun")
    private String codmun;
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "coddpto")
    private String coddpto;
    @Column(name = "nomdpto")
    private String nomdpto;

    public Municipios() {
    }

    public Municipios(String codigo) {
        this.codigo = codigo;
    }

    public Municipios(String codigo, String codmun, String coddpto) {
        this.codigo = codigo;
        this.codmun = codmun;
        this.coddpto = coddpto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodmun() {
        return codmun;
    }

    public void setCodmun(String codmun) {
        this.codmun = codmun;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCoddpto() {
        return coddpto;
    }

    public void setCoddpto(String coddpto) {
        this.coddpto = coddpto;
    }

    public String getNomdpto() {
        return nomdpto;
    }

    public void setNomdpto(String nomdpto) {
        this.nomdpto = nomdpto;
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
        if (!(object instanceof Municipios)) {
            return false;
        }
        Municipios other = (Municipios) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BD.Municipios[ codigo=" + codigo + " ]";
    }
    
}
