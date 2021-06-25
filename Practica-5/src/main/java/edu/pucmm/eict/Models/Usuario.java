package edu.pucmm.eict.Models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Usuario implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //crear el ID de forma automatica
    private long iduser;
    @NotNull @Column(unique=true)
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String userRole;
    @NotNull
    private String nombre;

    public Usuario(String username, String password, String userRole, String nombre) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.nombre = nombre;
    }

    public Usuario() {

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setuserRole(String userRole) {
        this.userRole = userRole;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getuserRole() {
        return userRole;
    }

    public String getNombre() {
        return nombre;
    }

    public long getIduser() {
        return iduser;
    }

    public void setIduser(long iduser) {
        this.iduser = iduser;
    }
}
