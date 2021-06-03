package edu.pucmm.eict.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venta {
    private int id;
    private Usuario cliente;
    private List<ProductoCompra> ordenCompra;
    private Date fechaCompra;

    public Venta(int id, Usuario cliente, List<ProductoCompra> ordenCompra, Date fechaCompra) {
        this.id = id;
        this.cliente = cliente;
        this.ordenCompra = ordenCompra;
        this.fechaCompra = fechaCompra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
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
