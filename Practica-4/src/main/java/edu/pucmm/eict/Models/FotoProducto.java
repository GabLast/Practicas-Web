package edu.pucmm.eict.Models;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class FotoProducto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idfotoproducto;
    @NotNull
    private String mimeType;
    @Lob @NotNull
    private String fotoBase64;

    @ManyToOne
    private Producto producto;

    public FotoProducto(int idfotoproducto, @NotNull String mimeType, @NotNull String fotoBase64, Producto producto) {
        this.idfotoproducto = idfotoproducto;
        this.mimeType = mimeType;
        this.fotoBase64 = fotoBase64;
        this.producto = producto;
    }

    public FotoProducto() {

    }

    public int getIdfotoproducto() {
        return idfotoproducto;
    }

    public void setIdfotoproducto(int idfotoproducto) {
        this.idfotoproducto = idfotoproducto;
    }

    @NotNull
    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(@NotNull String mimeType) {
        this.mimeType = mimeType;
    }

    @NotNull
    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(@NotNull String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}