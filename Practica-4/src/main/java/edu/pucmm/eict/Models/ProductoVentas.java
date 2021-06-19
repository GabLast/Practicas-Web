package edu.pucmm.eict.Models;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

import com.sun.istack.NotNull;

@Entity
public class ProductoVentas implements Serializable{

    @EmbeddedId
    private ProductoVentasID productoVentasID;
    @NotNull
    private BigDecimal precio;
    @NotNull
    private int cantidad;

}

@Embeddable
class ProductoVentasID implements Serializable {

    @OneToOne
    private Venta venta;
    @OneToOne
    private Producto producto;

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
}
