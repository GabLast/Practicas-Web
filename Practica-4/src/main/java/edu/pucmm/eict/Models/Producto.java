package edu.pucmm.eict.Models;

import com.sun.istack.NotNull;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idproducto;
    @NotNull
    @Column(unique = true)
    private String nombre;
    @NotNull
    private BigDecimal precio;
    @NotNull
    @Column(columnDefinition = "int default 0")
    private int borrado;
    @NotNull @Column(columnDefinition = "varchar(255) default 'No se digitó una descripción'")
    private String descripcion;

    @OneToMany(mappedBy = "venta")
    private List<ProductoVentas> listaventas;

    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
    private Set<FotoProducto> listafotos;

    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
    private Set<Comentario> listacomentarios;

    public Producto() {

    }

    public Producto(String nombre, BigDecimal precio, String descripcion) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public Producto(long idproducto, String nombre, BigDecimal precio, String descripcion) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public long getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(long idproducto) {
        this.idproducto = idproducto;
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

    public int getBorrado() {
        return borrado;
    }

    public void setBorrado(int borrado) {
        this.borrado = borrado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<FotoProducto> getListafotos() {
        return listafotos;
    }

    public void setListafotos(Set<FotoProducto> listafotos) {
        this.listafotos = listafotos;
    }

    public Set<Comentario> getListacomentarios() {
        return listacomentarios;
    }

    public void setListacomentarios(Set<Comentario> listacomentarios) {
        this.listacomentarios = listacomentarios;
    }
}
