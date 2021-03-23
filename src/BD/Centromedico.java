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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author migma
 */
@Entity
@Table(name = "centromedico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Centromedico.findAll", query = "SELECT c FROM Centromedico c")
    , @NamedQuery(name = "Centromedico.findById", query = "SELECT c FROM Centromedico c WHERE c.id = :id")
    , @NamedQuery(name = "Centromedico.findByNombre", query = "SELECT c FROM Centromedico c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Centromedico.findByZona", query = "SELECT c FROM Centromedico c WHERE c.zona = :zona")
    , @NamedQuery(name = "Centromedico.findByDireccion", query = "SELECT c FROM Centromedico c WHERE c.direccion = :direccion")
    , @NamedQuery(name = "Centromedico.findByTelefono", query = "SELECT c FROM Centromedico c WHERE c.telefono = :telefono")
    , @NamedQuery(name = "Centromedico.findByGelocalizacion", query = "SELECT c FROM Centromedico c WHERE c.gelocalizacion = :gelocalizacion")
    , @NamedQuery(name = "Centromedico.findByServicios", query = "SELECT c FROM Centromedico c WHERE c.servicios = :servicios")})
public class Centromedico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "zona")
    private String zona;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "gelocalizacion")
    private String gelocalizacion;
    @Basic(optional = false)
    @Column(name = "servicios")
    private String servicios;

    public Centromedico() {
    }

    public Centromedico(Integer id) {
        this.id = id;
    }

    public Centromedico(Integer id, String nombre, String zona, String direccion, String telefono, String gelocalizacion, String servicios) {
        this.id = id;
        this.nombre = nombre;
        this.zona = zona;
        this.direccion = direccion;
        this.telefono = telefono;
        this.gelocalizacion = gelocalizacion;
        this.servicios = servicios;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGelocalizacion() {
        return gelocalizacion;
    }

    public void setGelocalizacion(String gelocalizacion) {
        this.gelocalizacion = gelocalizacion;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Centromedico)) {
            return false;
        }
        Centromedico other = (Centromedico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BD.Centromedico[ id=" + id + " ]";
    }
    
}
