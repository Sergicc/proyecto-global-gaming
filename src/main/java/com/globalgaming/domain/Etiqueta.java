package com.globalgaming.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Etiqueta.
 */
@Entity
@Table(name = "etiqueta")
public class Etiqueta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToMany(mappedBy = "etiquetas")
    @JsonIgnore
    private Set<Articulo> articulos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Etiqueta nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Articulo> getArticulos() {
        return articulos;
    }

    public Etiqueta articulos(Set<Articulo> articulos) {
        this.articulos = articulos;
        return this;
    }

    public Etiqueta addArticulo(Articulo articulo) {
        articulos.add(articulo);
        articulo.getEtiquetas().add(this);
        return this;
    }

    public Etiqueta removeArticulo(Articulo articulo) {
        articulos.remove(articulo);
        articulo.getEtiquetas().remove(this);
        return this;
    }

    public void setArticulos(Set<Articulo> articulos) {
        this.articulos = articulos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Etiqueta etiqueta = (Etiqueta) o;
        if (etiqueta.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, etiqueta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Etiqueta{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
