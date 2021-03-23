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
 * @author Miguel Askar
 */
@Entity
@Table(name = "farmacias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Farmacias.findAll", query = "SELECT f FROM Farmacias f")
    , @NamedQuery(name = "Farmacias.findById", query = "SELECT f FROM Farmacias f WHERE f.id = :id")
    , @NamedQuery(name = "Farmacias.findByNombre", query = "SELECT f FROM Farmacias f WHERE f.nombre = :nombre")
    , @NamedQuery(name = "Farmacias.findByZona", query = "SELECT f FROM Farmacias f WHERE f.zona = :zona")
    , @NamedQuery(name = "Farmacias.findByDireccion", query = "SELECT f FROM Farmacias f WHERE f.direccion = :direccion")
    , @NamedQuery(name = "Farmacias.findByTelefono", query = "SELECT f FROM Farmacias f WHERE f.telefono = :telefono")})
public class Farmacias implements Serializable {

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

    public Farmacias() {
    }

    public Farmacias(Integer id) {
        this.id = id;
    }

    public Farmacias(Integer id, String nombre, String zona, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.zona = zona;
        this.direccion = direccion;
        this.telefono = telefono;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Farmacias)) {
            return false;
        }
        Farmacias other = (Farmacias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BD.Farmacias[ id=" + id + " ]";
    }
    
}
