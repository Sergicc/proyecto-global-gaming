package com.globalgaming.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Idioma.
 */
@Entity
@Table(name = "idioma")
public class Idioma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    private Juego juego;

    @ManyToOne
    private Sala sala;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Idioma nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Juego getJuego() {
        return juego;
    }

    public Idioma juego(Juego juego) {
        this.juego = juego;
        return this;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public Sala getSala() {
        return sala;
    }

    public Idioma sala(Sala sala) {
        this.sala = sala;
        return this;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Idioma idioma = (Idioma) o;
        if (idioma.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, idioma.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Idioma{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
