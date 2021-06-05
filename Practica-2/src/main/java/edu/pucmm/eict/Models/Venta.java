package edu.pucmm.eict.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venta {
    private long id;
    private String cliente;
    private List<ProductoCompra> ordenCompra;
    private Date fechaCompra;

    public Venta(long id, String cliente, List<ProductoCompra> ordenCompra, Date fechaCompra) {
        this.id = id;
        this.cliente = cliente;
        this.ordenCompra = ordenCompra;
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

    public List<ProductoCompra> getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(List<ProductoCompra> ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
}
