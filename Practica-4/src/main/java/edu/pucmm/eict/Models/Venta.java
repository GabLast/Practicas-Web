package edu.pucmm.eict.Models;

import com.sun.istack.NotNull;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Venta implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idventa;
    @NotNull
    private String cliente;
    @NotNull
    private Date fechaCompra;

    @OneToMany(mappedBy = "producto")
    private List<ProductoVentas> listaproductos;

    public Venta(String cliente) {
        this.cliente = cliente;
        this.fechaCompra = new Date();
    }

    public Venta() {

    }

    public long getIdventa() {
        return idventa;
    }

    public void setIdventa(long idventa) {
        this.idventa = idventa;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public List<ProductoVentas> getListaproductos() {
        return listaproductos;
    }

    public void setListaproductos(List<ProductoVentas> listaproductos) {
        this.listaproductos = listaproductos;
    }
}
