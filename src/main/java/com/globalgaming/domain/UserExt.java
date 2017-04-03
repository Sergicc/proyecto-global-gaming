package com.globalgaming.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserExt.
 */
@Entity
@Table(name = "user_ext")
public class UserExt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    @Column(name = "avatar")
    private byte[] avatar;

    @Column(name = "avatar_content_type")
    private String avatarContentType;

    @Column(name = "nick")
    private String nick;

    @Column(name = "id_battlenet")
    private String idBattlenet;

    @Column(name = "id_steam")
    private String idSteam;

    @Column(name = "id_origin")
    private String idOrigin;

    @Column(name = "id_lol")
    private String idLol;

    @Column(name = "pais")
    private String pais;

    @Column(name = "puntos")
    private Integer puntos;

    @OneToOne
    @JoinColumn(unique = true)

    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public UserExt avatar(byte[] avatar) {
        this.avatar = avatar;
        return this;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getAvatarContentType() {
        return avatarContentType;
    }

    public UserExt avatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
        return this;
    }

    public void setAvatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
    }

    public String getNick() {
        return nick;
    }

    public UserExt nick(String nick) {
        this.nick = nick;
        return this;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIdBattlenet() {
        return idBattlenet;
    }

    public UserExt idBattlenet(String idBattlenet) {
        this.idBattlenet = idBattlenet;
        return this;
    }

    public void setIdBattlenet(String idBattlenet) {
        this.idBattlenet = idBattlenet;
    }

    public String getIdSteam() {
        return idSteam;
    }

    public UserExt idSteam(String idSteam) {
        this.idSteam = idSteam;
        return this;
    }
    public void setIdSteam(String idSteam) {
        this.idSteam = idSteam;
    }

    public String getIdOrigin() {
        return idOrigin;
    }

    public UserExt idOrigin(String idOrigin) {
        this.idOrigin = idOrigin;
        return this;
    }

    public void setIdOrigin(String idOrigin) {
        this.idOrigin = idOrigin;
    }

    public String getIdLol() {
        return idLol;
    }

    public UserExt idLol(String idLol) {
        this.idLol = idLol;
        return this;
    }

    public void setIdLol(String idLol) {
        this.idLol = idLol;
    }

    public String getPais() {
        return pais;
    }

    public UserExt pais(String pais) {
        this.pais = pais;
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public UserExt puntos(Integer puntos) {
        this.puntos = puntos;
        return this;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public User getUser() {
        return user;
    }

    public UserExt user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserExt userExt = (UserExt) o;
        if (userExt.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userExt.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserExt{" +
            "id=" + id + "'"+
            ", avatar='" + avatar + "'" +
            ", avatarContentType='" + avatarContentType + "'" +
            ", nick='" + nick + "'" +
            ", idBattlenet='" + idBattlenet + "'" +
            ", idSteam='" + idSteam + "'" +
            ", idOrigin='" + idOrigin + "'" +
            ", idLol='" + idLol + "'" +
            ", pais='" + pais + "'" +
            ", puntos='" + puntos + "'" +
            '}';
    }
}
