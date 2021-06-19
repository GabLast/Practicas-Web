package edu.pucmm.eict.Models;

import com.sun.istack.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Producto implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idproducto;
    @NotNull @Column(unique=true)
    private String nombre;
    @NotNull
    private BigDecimal precio;
    @NotNull @ColumnDefault("0")
    private int borrado;

    @OneToMany(mappedBy = "venta")
    private List<ProductoVentas> listaventas;

    @OneToMany(mappedBy = "producto")
    private List<FotoProducto> listafotos;

    @OneToMany(mappedBy = "producto")
    private List<Comentario> comentarios;



    public Producto(int id, String nombre, BigDecimal precio) {
        this.idproducto = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Producto() {

    }

    public int getId() {
        return idproducto;
    }

    public void setId(int id) {
        this.idproducto = id;
    }

    public int getBorrado() {
        return borrado;
    }

    public void setBorrado(int borrado) {
        this.borrado = borrado;
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

//    public List<Venta> getVentas() {
//        return ventas;
//    }
//
//    public void setVentas(List<Venta> ventas) {
//        this.ventas = ventas;
//    }
}
