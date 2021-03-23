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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "entrenamiento_asistente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntrenamientoAsistente.findAll", query = "SELECT e FROM EntrenamientoAsistente e")
    , @NamedQuery(name = "EntrenamientoAsistente.findById", query = "SELECT e FROM EntrenamientoAsistente e WHERE e.id = :id")})
public class EntrenamientoAsistente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Lob
    @Column(name = "frase")
    private String frase;

    public EntrenamientoAsistente() {
    }

    public EntrenamientoAsistente(Integer id) {
        this.id = id;
    }

    public EntrenamientoAsistente(Integer id, String frase) {
        this.id = id;
        this.frase = frase;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
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
        if (!(object instanceof EntrenamientoAsistente)) {
            return false;
        }
        EntrenamientoAsistente other = (EntrenamientoAsistente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BD.EntrenamientoAsistente[ id=" + id + " ]";
    }
    
}
