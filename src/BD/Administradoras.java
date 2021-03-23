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
@Table(name = "administradoras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Administradoras.findAll", query = "SELECT a FROM Administradoras a")
    , @NamedQuery(name = "Administradoras.findByCodigo", query = "SELECT a FROM Administradoras a WHERE a.codigo = :codigo")
    , @NamedQuery(name = "Administradoras.findByNit", query = "SELECT a FROM Administradoras a WHERE a.nit = :nit")
    , @NamedQuery(name = "Administradoras.findByNombre", query = "SELECT a FROM Administradoras a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Administradoras.findByRegimen", query = "SELECT a FROM Administradoras a WHERE a.regimen = :regimen")})
public class Administradoras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "nit")
    private String nit;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "regimen")
    private String regimen;

    public Administradoras() {
    }

    public Administradoras(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
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
        if (!(object instanceof Administradoras)) {
            return false;
        }
        Administradoras other = (Administradoras) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BD.Administradoras[ codigo=" + codigo + " ]";
    }
    
}
