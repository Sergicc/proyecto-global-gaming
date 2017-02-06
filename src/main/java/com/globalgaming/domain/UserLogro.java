package com.globalgaming.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UserLogro.
 */
@Entity
@Table(name = "user_logro")
public class UserLogro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fecha")
    private ZonedDateTime fecha;

    @ManyToOne
    private User user;

    @ManyToOne
    private Logro logro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public UserLogro fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public User getUser() {
        return user;
    }

    public UserLogro user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Logro getLogro() {
        return logro;
    }

    public UserLogro logro(Logro logro) {
        this.logro = logro;
        return this;
    }

    public void setLogro(Logro logro) {
        this.logro = logro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserLogro userLogro = (UserLogro) o;
        if (userLogro.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userLogro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserLogro{" +
            "id=" + id +
            ", fecha='" + fecha + "'" +
            '}';
    }
}
