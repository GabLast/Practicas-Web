package edu.pucmm.eict.Models;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Comentario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idcomentario;
    @NotNull
    private String descripcion;
    @NotNull
    private Date fechaCreacion;
    @NotNull @Column(columnDefinition = "int default 0")
    private int borrado;
    @NotNull
    private String originalposter;

    @ManyToOne(fetch = FetchType.EAGER)
    private Producto producto;

    public Comentario(@NotNull String descripcion, @NotNull String originalposter, Producto producto) {
        this.descripcion = descripcion;
        this.originalposter = originalposter;
        this.producto = producto;
        this.fechaCreacion = new Date();
    }

    public Comentario() { }

    public long getIdcomentario() {
        return idcomentario;
    }

    public void setIdcomentario(long idcomentario) {
        this.idcomentario = idcomentario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
