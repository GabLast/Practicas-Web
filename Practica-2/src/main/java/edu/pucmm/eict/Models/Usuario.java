package edu.pucmm.eict.Models;

public class Usuario {
    private String username;
    private String password;
    private String userRole;
    private String nombre;

    public Usuario(String username, String password, String userRole, String nombre) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.nombre = nombre;
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
}
