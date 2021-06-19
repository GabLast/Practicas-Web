package edu.pucmm.eict.Models;

import com.sun.istack.NotNull;

import java.sql.Date;
import java.util.List;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Venta implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String cliente;
    @NotNull
    private Date fechaCompra;

    @OneToMany(mappedBy = "ventas")
    private Set<Producto> productosComprados;

    public Venta(long id, String cliente, Set<Producto> productosComprados, Date fechaCompra) {
        this.id = id;
        this.cliente = cliente;
        this.productosComprados = productosComprados;
        this.fechaCompra = fechaCompra;
    }

    public Venta() {

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

    public Set<Producto> getproductosComprados() {
        return productosComprados;
    }

    public void setproductosComprados(Set<Producto> productosComprados) {
        this.productosComprados = productosComprados;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }
}
