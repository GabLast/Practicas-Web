package edu.pucmm.eict.Models;

import com.sun.istack.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;


public class ProductoCompra implements Serializable {


    private long id;

    private String nombre;

    private BigDecimal precio;

    private int cantidad;

    private String descripcion;

    public ProductoCompra(long id, String nombre, BigDecimal precio, int cantidad, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

    public ProductoCompra() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
