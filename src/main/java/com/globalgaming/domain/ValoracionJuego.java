package com.globalgaming.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A ValoracionJuego.
 */
@Entity
@Table(name = "valoracion_juego")
public class ValoracionJuego implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "5.0")
    @Column(name = "valoracion", nullable = false)
    private Double valoracion;

    @Column(name = "time_stamp")
    private ZonedDateTime timeStamp;

    @ManyToOne
    private User user;

    @ManyToOne
    private Juego juego;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValoracion() {
        return valoracion;
    }

    public ValoracionJuego valoracion(Double valoracion) {
        this.valoracion = valoracion;
        return this;
    }

    public void setValoracion(Double valoracion) {
        this.valoracion = valoracion;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public ValoracionJuego timeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }

    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public User getUser() {
        return user;
    }

    public ValoracionJuego user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Juego getJuego() {
        return juego;
    }

    public ValoracionJuego juego(Juego juego) {
        this.juego = juego;
        return this;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ValoracionJuego valoracionJuego = (ValoracionJuego) o;
        if (valoracionJuego.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, valoracionJuego.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ValoracionJuego{" +
            "id=" + id +
            ", valoracion='" + valoracion + "'" +
            ", timeStamp='" + timeStamp + "'" +
            '}';
    }
}
