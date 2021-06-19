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

    public Venta(long id, String cliente, List<ProductoVentas> listaproductos) {
        this.idventa = id;
        this.cliente = cliente;
        this.listaproductos = listaproductos;
        this.fechaCompra = new Date();
    }

    public Venta() {

    }

    public long getId() {
        return idventa;
    }

    public void setId(int id) {
        this.idventa = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public List<ProductoVentas> getproductosComprados() {
        return listaproductos;
    }

    public void setproductosComprados(List<ProductoVentas> productosComprados) {
        this.listaproductos = productosComprados;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
}
