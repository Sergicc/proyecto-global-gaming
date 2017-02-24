package com.globalgaming.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Sala.
 */
@Entity
@Table(name = "sala")
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Column(name = "limite_usuarios")
    private Integer limiteUsuarios;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;
//añadido el nuevo atributo descripcion como CLOB
    @ManyToOne
    private Juego juego;

    @OneToMany(mappedBy = "sala")
    @JsonIgnore
    private Set<Mensaje> mensajes = new HashSet<>();

    @OneToMany(mappedBy = "sala")
    @JsonIgnore
    private Set<Idioma> idiomas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Sala nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Sala imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Sala imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public Integer getLimiteUsuarios() {
        return limiteUsuarios;
    }

    public Sala limiteUsuarios(Integer limiteUsuarios) {
        this.limiteUsuarios = limiteUsuarios;
        return this;
    }

    public void setLimiteUsuarios(Integer limiteUsuarios) {
        this.limiteUsuarios = limiteUsuarios;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Sala descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Juego getJuego() {
        return juego;
    }

    public Sala juego(Juego juego) {
        this.juego = juego;
        return this;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    public Set<Mensaje> getMensajes() {
        return mensajes;
    }

    public Sala mensajes(Set<Mensaje> mensajes) {
        this.mensajes = mensajes;
        return this;
    }

    public Sala addMensaje(Mensaje mensaje) {
        mensajes.add(mensaje);
        mensaje.setSala(this);
        return this;
    }

    public Sala removeMensaje(Mensaje mensaje) {
        mensajes.remove(mensaje);
        mensaje.setSala(null);
        return this;
    }

    public void setMensajes(Set<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public Set<Idioma> getIdiomas() {
        return idiomas;
    }

    public Sala idiomas(Set<Idioma> idiomas) {
        this.idiomas = idiomas;
        return this;
    }

    public Sala addIdioma(Idioma idioma) {
        idiomas.add(idioma);
        idioma.setSala(this);
        return this;
    }

    public Sala removeIdioma(Idioma idioma) {
        idiomas.remove(idioma);
        idioma.setSala(null);
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
        Sala sala = (Sala) o;
        if (sala.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sala.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sala{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", imagen='" + imagen + "'" +
            ", imagenContentType='" + imagenContentType + "'" +
            ", limiteUsuarios='" + limiteUsuarios + "'" +
            ", descripcion='" + descripcion + "'" +
            '}';
    }
}
