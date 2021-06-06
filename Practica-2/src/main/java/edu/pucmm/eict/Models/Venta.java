package edu.pucmm.eict.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venta {
    private long id;
    private String cliente;
    private List<ProductoCompra> productosComprados;
    private Date fechaCompra;

    public Venta(long id, String cliente, List<ProductoCompra> productosComprados, Date fechaCompra) {
        this.id = id;
        this.cliente = cliente;
        this.productosComprados = productosComprados;
        this.fechaCompra = fechaCompra;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<ProductoCompra> getproductosComprados() {
        return productosComprados;
    }

    public void setproductosComprados(List<ProductoCompra> productosComprados) {
        this.productosComprados = productosComprados;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
}
