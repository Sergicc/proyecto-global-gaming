package com.globalgaming.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Pais.
 */
@Entity
@Table(name = "pais")
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Lob
    @Column(name = "bandera")
    private byte[] bandera;

    @Column(name = "bandera_content_type")
    private String banderaContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Pais nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getBandera() {
        return bandera;
    }

    public Pais bandera(byte[] bandera) {
        this.bandera = bandera;
        return this;
    }

    public void setBandera(byte[] bandera) {
        this.bandera = bandera;
    }

    public String getBanderaContentType() {
        return banderaContentType;
    }

    public Pais banderaContentType(String banderaContentType) {
        this.banderaContentType = banderaContentType;
        return this;
    }

    public void setBanderaContentType(String banderaContentType) {
        this.banderaContentType = banderaContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pais pais = (Pais) o;
        if (pais.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pais.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pais{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", bandera='" + bandera + "'" +
            ", banderaContentType='" + banderaContentType + "'" +
            '}';
    }
}
