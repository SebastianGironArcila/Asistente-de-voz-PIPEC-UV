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
@Table(name = "medicamentos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medicamentos.findAll", query = "SELECT m FROM Medicamentos m")
    , @NamedQuery(name = "Medicamentos.findByCodigo", query = "SELECT m FROM Medicamentos m WHERE m.codigo = :codigo")
    , @NamedQuery(name = "Medicamentos.findByNombregenerico", query = "SELECT m FROM Medicamentos m WHERE m.nombregenerico = :nombregenerico")
    , @NamedQuery(name = "Medicamentos.findByFormafarmaceutica", query = "SELECT m FROM Medicamentos m WHERE m.formafarmaceutica = :formafarmaceutica")
    , @NamedQuery(name = "Medicamentos.findByConcentracion", query = "SELECT m FROM Medicamentos m WHERE m.concentracion = :concentracion")
    , @NamedQuery(name = "Medicamentos.findByUnidadmedida", query = "SELECT m FROM Medicamentos m WHERE m.unidadmedida = :unidadmedida")
    , @NamedQuery(name = "Medicamentos.findByRes008", query = "SELECT m FROM Medicamentos m WHERE m.res008 = :res008")
    , @NamedQuery(name = "Medicamentos.findByRes029", query = "SELECT m FROM Medicamentos m WHERE m.res029 = :res029")
    , @NamedQuery(name = "Medicamentos.findByRes5521", query = "SELECT m FROM Medicamentos m WHERE m.res5521 = :res5521")
    , @NamedQuery(name = "Medicamentos.findByRes5926", query = "SELECT m FROM Medicamentos m WHERE m.res5926 = :res5926")
    , @NamedQuery(name = "Medicamentos.findByRes5592", query = "SELECT m FROM Medicamentos m WHERE m.res5592 = :res5592")})
public class Medicamentos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "nombregenerico")
    private String nombregenerico;
    @Column(name = "formafarmaceutica")
    private String formafarmaceutica;
    @Column(name = "concentracion")
    private String concentracion;
    @Column(name = "unidadmedida")
    private Integer unidadmedida;
    @Column(name = "res008")
    private Integer res008;
    @Column(name = "res029")
    private Integer res029;
    @Column(name = "res5521")
    private Integer res5521;
    @Column(name = "res5926")
    private Integer res5926;
    @Column(name = "res5592")
    private Integer res5592;

    public Medicamentos() {
    }

    public Medicamentos(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombregenerico() {
        return nombregenerico;
    }

    public void setNombregenerico(String nombregenerico) {
        this.nombregenerico = nombregenerico;
    }

    public String getFormafarmaceutica() {
        return formafarmaceutica;
    }

    public void setFormafarmaceutica(String formafarmaceutica) {
        this.formafarmaceutica = formafarmaceutica;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public Integer getUnidadmedida() {
        return unidadmedida;
    }

    public void setUnidadmedida(Integer unidadmedida) {
        this.unidadmedida = unidadmedida;
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

    public Integer getRes5926() {
        return res5926;
    }

    public void setRes5926(Integer res5926) {
        this.res5926 = res5926;
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
        if (!(object instanceof Medicamentos)) {
            return false;
        }
        Medicamentos other = (Medicamentos) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BD.Medicamentos[ codigo=" + codigo + " ]";
    }
    
}
