package com.globalgaming.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A RelacionUsuarios.
 */
@Entity
@Table(name = "relacion_usuarios")
public class RelacionUsuarios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fecha_emision")
    private ZonedDateTime fechaEmision;

    @Column(name = "fecha_resolucion")
    private ZonedDateTime fechaResolucion;

    @Column(name = "resultado")
    private Boolean resultado;

    @ManyToOne
    private User emisor;

    @ManyToOne
    private User receptor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFechaEmision() {
        return fechaEmision;
    }

    public RelacionUsuarios fechaEmision(ZonedDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
        return this;
    }

    public void setFechaEmision(ZonedDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public ZonedDateTime getFechaResolucion() {
        return fechaResolucion;
    }

    public RelacionUsuarios fechaResolucion(ZonedDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
        return this;
    }

    public void setFechaResolucion(ZonedDateTime fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public Boolean isResultado() {
        return resultado;
    }

    public RelacionUsuarios resultado(Boolean resultado) {
        this.resultado = resultado;
        return this;
    }

    public void setResultado(Boolean resultado) {
        this.resultado = resultado;
    }

    public User getEmisor() {
        return emisor;
    }

    public RelacionUsuarios emisor(User user) {
        this.emisor = user;
        return this;
    }

    public void setEmisor(User user) {
        this.emisor = user;
    }

    public User getReceptor() {
        return receptor;
    }

    public RelacionUsuarios receptor(User user) {
        this.receptor = user;
        return this;
    }

    public void setReceptor(User user) {
        this.receptor = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelacionUsuarios relacionUsuarios = (RelacionUsuarios) o;
        if (relacionUsuarios.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, relacionUsuarios.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RelacionUsuarios{" +
            "id=" + id +
            ", fechaEmision='" + fechaEmision + "'" +
            ", fechaResolucion='" + fechaResolucion + "'" +
            ", resultado='" + resultado + "'" +
            '}';
    }
}
