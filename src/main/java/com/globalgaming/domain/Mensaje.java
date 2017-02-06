package com.globalgaming.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Mensaje.
 */
@Entity
@Table(name = "mensaje")
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "hora_envio")
    private ZonedDateTime horaEnvio;

    @Column(name = "contenido")
    private String contenido;

    @Lob
    @Column(name = "adjunto")
    private byte[] adjunto;

    @Column(name = "adjunto_content_type")
    private String adjuntoContentType;

    @ManyToOne
    private Sala sala;

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

    public ZonedDateTime getHoraEnvio() {
        return horaEnvio;
    }

    public Mensaje horaEnvio(ZonedDateTime horaEnvio) {
        this.horaEnvio = horaEnvio;
        return this;
    }

    public void setHoraEnvio(ZonedDateTime horaEnvio) {
        this.horaEnvio = horaEnvio;
    }

    public String getContenido() {
        return contenido;
    }

    public Mensaje contenido(String contenido) {
        this.contenido = contenido;
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public byte[] getAdjunto() {
        return adjunto;
    }

    public Mensaje adjunto(byte[] adjunto) {
        this.adjunto = adjunto;
        return this;
    }

    public void setAdjunto(byte[] adjunto) {
        this.adjunto = adjunto;
    }

    public String getAdjuntoContentType() {
        return adjuntoContentType;
    }

    public Mensaje adjuntoContentType(String adjuntoContentType) {
        this.adjuntoContentType = adjuntoContentType;
        return this;
    }

    public void setAdjuntoContentType(String adjuntoContentType) {
        this.adjuntoContentType = adjuntoContentType;
    }

    public Sala getSala() {
        return sala;
    }

    public Mensaje sala(Sala sala) {
        this.sala = sala;
        return this;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public User getEmisor() {
        return emisor;
    }

    public Mensaje emisor(User user) {
        this.emisor = user;
        return this;
    }

    public void setEmisor(User user) {
        this.emisor = user;
    }

    public User getReceptor() {
        return receptor;
    }

    public Mensaje receptor(User user) {
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
        Mensaje mensaje = (Mensaje) o;
        if (mensaje.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mensaje.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Mensaje{" +
            "id=" + id +
            ", horaEnvio='" + horaEnvio + "'" +
            ", contenido='" + contenido + "'" +
            ", adjunto='" + adjunto + "'" +
            ", adjuntoContentType='" + adjuntoContentType + "'" +
            '}';
    }
}
