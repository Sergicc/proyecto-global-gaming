package com.globalgaming.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Articulo.
 */
@Entity
@Table(name = "articulo")
public class Articulo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "url")
    private String url;

    @Column(name = "visitas")
    private Integer visitas;

    @Column(name = "comentarios")
    private String comentarios;

    @Lob
    @Column(name = "texto")
    private String texto;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @ManyToMany
    @JoinTable(name = "articulo_etiqueta",
               joinColumns = @JoinColumn(name="articulos_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="etiquetas_id", referencedColumnName="ID"))
    private Set<Etiqueta> etiquetas = new HashSet<>();

    @OneToMany(mappedBy = "articulo")
    @JsonIgnore
    private Set<Foto> fotos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Articulo titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Articulo fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getUrl() {
        return url;
    }

    public Articulo url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getVisitas() {
        return visitas;
    }

    public Articulo visitas(Integer visitas) {
        this.visitas = visitas;
        return this;
    }

    public void setVisitas(Integer visitas) {
        this.visitas = visitas;
    }

    public String getComentarios() {
        return comentarios;
    }

    public Articulo comentarios(String comentarios) {
        this.comentarios = comentarios;
        return this;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getTexto() {
        return texto;
    }

    public Articulo texto(String texto) {
        this.texto = texto;
        return this;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Articulo foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Articulo fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public Set<Etiqueta> getEtiquetas() {
        return etiquetas;
    }

    public Articulo etiquetas(Set<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
        return this;
    }

    public Articulo addEtiqueta(Etiqueta etiqueta) {
        etiquetas.add(etiqueta);
        etiqueta.getArticulos().add(this);
        return this;
    }

    public Articulo removeEtiqueta(Etiqueta etiqueta) {
        etiquetas.remove(etiqueta);
        etiqueta.getArticulos().remove(this);
        return this;
    }

    public void setEtiquetas(Set<Etiqueta> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public Set<Foto> getFotos() {
        return fotos;
    }

    public Articulo fotos(Set<Foto> fotos) {
        this.fotos = fotos;
        return this;
    }

    public Articulo addFoto(Foto foto) {
        fotos.add(foto);
        foto.setArticulo(this);
        return this;
    }

    public Articulo removeFoto(Foto foto) {
        fotos.remove(foto);
        foto.setArticulo(null);
        return this;
    }

    public void setFotos(Set<Foto> fotos) {
        this.fotos = fotos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Articulo articulo = (Articulo) o;
        if (articulo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, articulo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Articulo{" +
            "id=" + id +
            ", titulo='" + titulo + "'" +
            ", fecha='" + fecha + "'" +
            ", url='" + url + "'" +
            ", visitas='" + visitas + "'" +
            ", comentarios='" + comentarios + "'" +
            ", texto='" + texto + "'" +
            ", foto='" + foto + "'" +
            ", fotoContentType='" + fotoContentType + "'" +
            '}';
    }
}
