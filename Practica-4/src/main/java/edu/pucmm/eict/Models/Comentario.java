package edu.pucmm.eict.Models;

import org.hibernate.annotations.ColumnDefault;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Comentario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idcomentario;
    @NotNull
    private int descripcion;
    @NotNull
    private Date fechaCreacion;
    @NotNull @ColumnDefault("0")
    private int borrado;
    @NotNull
    private String originalposter;

    @ManyToOne
    private Producto producto;

    public Comentario(int idcomentario, int descripcion, int borrado, @NotNull String originalposter) {
        this.idcomentario = idcomentario;
        this.descripcion = descripcion;
        this.borrado = borrado;
        this.originalposter = originalposter;
        this.fechaCreacion = new Date();
    }

    public Comentario() { }

    public int getIdcomentario() {
        return idcomentario;
    }

    public void setIdcomentario(int idcomentario) {
        this.idcomentario = idcomentario;
    }

    public int getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(int descripcion) {
        this.descripcion = descripcion;
    }

    @NotNull
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(@NotNull Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getBorrado() {
        return borrado;
    }

    public void setBorrado(int borrado) {
        this.borrado = borrado;
    }

    @NotNull
    public String getOriginalposter() {
        return originalposter;
    }

    public void setOriginalposter(@NotNull String originalposter) {
        this.originalposter = originalposter;
    }
}
