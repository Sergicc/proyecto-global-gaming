package com.globalgaming.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Juego.
 */
@Entity
@Table(name = "juego")
public class Juego implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Lob
    @Column(name = "portada")
    private byte[] portada;

    @Column(name = "portada_content_type")
    private String portadaContentType;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "trailer")
    private String trailer;

    @Column(name = "desarrollador")
    private String desarrollador;

    @Column(name = "genero")
    private String genero;

    @Column(name = "edad_recomendada")
    private Integer edadRecomendada;

    @Column(name = "fecha_lanzamiento")
    private LocalDate fechaLanzamiento;

    @Column(name = "capacidad_jugadores")
    private Integer capacidadJugadores;

    @Column(name = "valoracion_web")
    private Double valoracionWeb;

    @Column(name = "valoracion_users")
    private Double valoracionUsers;

    @OneToMany(mappedBy = "juego")
    @JsonIgnore
    private Set<Sala> salas = new HashSet<>();

    @OneToMany(mappedBy = "juego")
    @JsonIgnore
    private Set<Idioma> idiomas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Juego titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public byte[] getPortada() {
        return portada;
    }

    public Juego portada(byte[] portada) {
        this.portada = portada;
        return this;
    }

    public void setPortada(byte[] portada) {
        this.portada = portada;
    }

    public String getPortadaContentType() {
        return portadaContentType;
    }

    public Juego portadaContentType(String portadaContentType) {
        this.portadaContentType = portadaContentType;
        return this;
    }

    public void setPortadaContentType(String portadaContentType) {
        this.portadaContentType = portadaContentType;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Juego descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTrailer() {
        return trailer;
    }

    public Juego trailer(String trailer) {
        this.trailer = trailer;
        return this;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public Juego desarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
        return this;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public String getGenero() {
        return genero;
    }

    public Juego genero(String genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getEdadRecomendada() {
        return edadRecomendada;
    }

    public Juego edadRecomendada(Integer edadRecomendada) {
        this.edadRecomendada = edadRecomendada;
        return this;
    }

    public void setEdadRecomendada(Integer edadRecomendada) {
        this.edadRecomendada = edadRecomendada;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public Juego fechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
        return this;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Integer getCapacidadJugadores() {
        return capacidadJugadores;
    }

    public Juego capacidadJugadores(Integer capacidadJugadores) {
        this.capacidadJugadores = capacidadJugadores;
        return this;
    }

    public void setCapacidadJugadores(Integer capacidadJugadores) {
        this.capacidadJugadores = capacidadJugadores;
    }

    public Double getValoracionWeb() {
        return valoracionWeb;
    }

    public Juego valoracionWeb(Double valoracionWeb) {
        this.valoracionWeb = valoracionWeb;
        return this;
    }

    public void setValoracionWeb(Double valoracionWeb) {
        this.valoracionWeb = valoracionWeb;
    }

    public Double getValoracionUsers() {
        return valoracionUsers;
    }

    public Juego valoracionUsers(Double valoracionUsers) {
        this.valoracionUsers = valoracionUsers;
        return this;
    }

    public void setValoracionUsers(Double valoracionUsers) {
        this.valoracionUsers = valoracionUsers;
    }

    public Set<Sala> getSalas() {
        return salas;
    }

    public Juego salas(Set<Sala> salas) {
        this.salas = salas;
        return this;
    }

    public Juego addSala(Sala sala) {
        salas.add(sala);
        sala.setJuego(this);
        return this;
    }

    public Juego removeSala(Sala sala) {
        salas.remove(sala);
        sala.setJuego(null);
        return this;
    }

    public void setSalas(Set<Sala> salas) {
        this.salas = salas;
    }

    public Set<Idioma> getIdiomas() {
        return idiomas;
    }

    public Juego idiomas(Set<Idioma> idiomas) {
        this.idiomas = idiomas;
        return this;
    }

    public Juego addIdioma(Idioma idioma) {
        idiomas.add(idioma);
        idioma.setJuego(this);
        return this;
    }

    public Juego removeIdioma(Idioma idioma) {
        idiomas.remove(idioma);
        idioma.setJuego(null);
        return this;
    }

    public void setIdiomas(Set<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Juego juego = (Juego) o;
        if (juego.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, juego.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Juego{" +
            "id=" + id +
            ", titulo='" + titulo + "'" +
            ", portada='" + portada + "'" +
            ", portadaContentType='" + portadaContentType + "'" +
            ", descripcion='" + descripcion + "'" +
            ", trailer='" + trailer + "'" +
            ", desarrollador='" + desarrollador + "'" +
            ", genero='" + genero + "'" +
            ", edadRecomendada='" + edadRecomendada + "'" +
            ", fechaLanzamiento='" + fechaLanzamiento + "'" +
            ", capacidadJugadores='" + capacidadJugadores + "'" +
            ", valoracionWeb='" + valoracionWeb + "'" +
            ", valoracionUsers='" + valoracionUsers + "'" +
            '}';
    }
}
