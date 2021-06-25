package edu.pucmm.eict.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import com.sun.istack.NotNull;

@Entity(name = "ProductoVentas")
@Table(name = "ProductoVentas")
public class ProductoVentas implements Serializable {

    @EmbeddedId
    private ProductoVentasID productoVentasID;

    @ManyToOne
    @MapsId("idventa")
    private Venta venta;

    @ManyToOne
    @MapsId("idproducto")
    private Producto producto;

    @NotNull
    private BigDecimal precio;
    @NotNull
    private int cantidad;

    public ProductoVentas() {
    }

    public ProductoVentas(Venta venta, Producto producto, BigDecimal precio, int cantidad) {
        this.venta = venta;
        this.producto = producto;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public ProductoVentasID getProductoVentasID() {
        return productoVentasID;
    }

    public void setProductoVentasID(ProductoVentasID productoVentasID) {
        this.productoVentasID = productoVentasID;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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
}

@Embeddable
class ProductoVentasID implements Serializable {

    @Column(name = "idventa")
    private long idventa;

    @Column(name = "idproducto")
    private long idproducto;

    public long getVentaid() {
        return idproducto;
    }

    public void setVentaid(long idventa) {
        this.idventa = idventa;
    }

    public long getProductoid() {
        return idproducto;
    }

    public void setProductoid(long idproducto) {
        this.idproducto = idproducto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductoVentasID that = (ProductoVentasID) o;

        if (idventa != that.idventa) return false;
        return idproducto == that.idproducto;
    }

    @Override
    public int hashCode() {
        int result = (int) (idventa ^ (idventa >>> 32));
        result = 31 * result + (int) (idproducto ^ (idproducto >>> 32));
        return result;
    }
}
