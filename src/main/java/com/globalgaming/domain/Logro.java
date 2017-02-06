package com.globalgaming.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Logro.
 */
@Entity
@Table(name = "logro")
public class Logro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "logro")
    @JsonIgnore
    private Set<UserLogro> userLogroes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Logro nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Logro descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<UserLogro> getUserLogroes() {
        return userLogroes;
    }

    public Logro userLogroes(Set<UserLogro> userLogroes) {
        this.userLogroes = userLogroes;
        return this;
    }

    public Logro addUserLogro(UserLogro userLogro) {
        userLogroes.add(userLogro);
        userLogro.setLogro(this);
        return this;
    }

    public Logro removeUserLogro(UserLogro userLogro) {
        userLogroes.remove(userLogro);
        userLogro.setLogro(null);
        return this;
    }

    public void setUserLogroes(Set<UserLogro> userLogroes) {
        this.userLogroes = userLogroes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Logro logro = (Logro) o;
        if (logro.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, logro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Logro{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", descripcion='" + descripcion + "'" +
            '}';
    }
}
