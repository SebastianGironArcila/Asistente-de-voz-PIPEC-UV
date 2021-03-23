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
@Table(name = "cie10")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cie10.findAll", query = "SELECT c FROM Cie10 c")
    , @NamedQuery(name = "Cie10.findByCodigo", query = "SELECT c FROM Cie10 c WHERE c.codigo = :codigo")
    , @NamedQuery(name = "Cie10.findByNombre", query = "SELECT c FROM Cie10 c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Cie10.findByGenero", query = "SELECT c FROM Cie10 c WHERE c.genero = :genero")
    , @NamedQuery(name = "Cie10.findByEdadminanos", query = "SELECT c FROM Cie10 c WHERE c.edadminanos = :edadminanos")
    , @NamedQuery(name = "Cie10.findByEdadmaxanos", query = "SELECT c FROM Cie10 c WHERE c.edadmaxanos = :edadmaxanos")
    , @NamedQuery(name = "Cie10.findByMortalidad", query = "SELECT c FROM Cie10 c WHERE c.mortalidad = :mortalidad")
    , @NamedQuery(name = "Cie10.findByMorbilidad", query = "SELECT c FROM Cie10 c WHERE c.morbilidad = :morbilidad")
    , @NamedQuery(name = "Cie10.findByA", query = "SELECT c FROM Cie10 c WHERE c.a = :a")
    , @NamedQuery(name = "Cie10.findByA1", query = "SELECT c FROM Cie10 c WHERE c.a1 = :a1")
    , @NamedQuery(name = "Cie10.findByA2", query = "SELECT c FROM Cie10 c WHERE c.a2 = :a2")
    , @NamedQuery(name = "Cie10.findByA3", query = "SELECT c FROM Cie10 c WHERE c.a3 = :a3")
    , @NamedQuery(name = "Cie10.findByA4", query = "SELECT c FROM Cie10 c WHERE c.a4 = :a4")})
public class Cie10 implements Serializable {

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
    @Column(name = "mortalidad")
    private Integer mortalidad;
    @Column(name = "morbilidad")
    private Integer morbilidad;
    @Column(name = "2002")
    private Integer a;
    @Column(name = "2005")
    private Integer a1;
    @Column(name = "2009")
    private Integer a2;
    @Column(name = "2012")
    private Integer a3;
    @Column(name = "2014")
    private Integer a4;

    public Cie10() {
    }

    public Cie10(String codigo) {
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

    public Integer getMortalidad() {
        return mortalidad;
    }

    public void setMortalidad(Integer mortalidad) {
        this.mortalidad = mortalidad;
    }

    public Integer getMorbilidad() {
        return morbilidad;
    }

    public void setMorbilidad(Integer morbilidad) {
        this.morbilidad = morbilidad;
    }

    public Integer getA() {
        return a;
    }

    public void setA(Integer a) {
        this.a = a;
    }

    public Integer getA1() {
        return a1;
    }

    public void setA1(Integer a1) {
        this.a1 = a1;
    }

    public Integer getA2() {
        return a2;
    }

    public void setA2(Integer a2) {
        this.a2 = a2;
    }

    public Integer getA3() {
        return a3;
    }

    public void setA3(Integer a3) {
        this.a3 = a3;
    }

    public Integer getA4() {
        return a4;
    }

    public void setA4(Integer a4) {
        this.a4 = a4;
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
        if (!(object instanceof Cie10)) {
            return false;
        }
        Cie10 other = (Cie10) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BD.Cie10[ codigo=" + codigo + " ]";
    }
    
}
