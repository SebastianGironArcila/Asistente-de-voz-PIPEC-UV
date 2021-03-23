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
@Table(name = "especialistas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Especialistas.findAll", query = "SELECT e FROM Especialistas e")
    , @NamedQuery(name = "Especialistas.findById", query = "SELECT e FROM Especialistas e WHERE e.id = :id")
    , @NamedQuery(name = "Especialistas.findByNombre", query = "SELECT e FROM Especialistas e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Especialistas.findByEspecialidad", query = "SELECT e FROM Especialistas e WHERE e.especialidad = :especialidad")
    , @NamedQuery(name = "Especialistas.findByDireccion", query = "SELECT e FROM Especialistas e WHERE e.direccion = :direccion")
    , @NamedQuery(name = "Especialistas.findByTelefono", query = "SELECT e FROM Especialistas e WHERE e.telefono = :telefono")})
public class Especialistas implements Serializable {

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
    @Column(name = "especialidad")
    private String especialidad;
    @Basic(optional = false)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;

    public Especialistas() {
    }

    public Especialistas(Integer id) {
        this.id = id;
    }

    public Especialistas(Integer id, String nombre, String especialidad, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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
        if (!(object instanceof Especialistas)) {
            return false;
        }
        Especialistas other = (Especialistas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BD.Especialistas[ id=" + id + " ]";
    }
    
}
