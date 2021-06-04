package edu.pucmm.eict.Models;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompras {
    private long id;
    private List<ProductoCompra> miCarrito;

    public CarritoCompras(long id, List<ProductoCompra> miCarrito) {
        this.id = id;
        this.miCarrito = miCarrito;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ProductoCompra> getMiCarrito() {
        return miCarrito;
    }

    public void setMiCarrito(List<ProductoCompra> miCarrito) {
        this.miCarrito = miCarrito;
    }

    public void agregarProducto(ProductoCompra producto) {

        this.miCarrito.add(producto);
    }
    public boolean removerProducto(ProductoCompra producto) {
        return miCarrito.remove(producto);
    }

    public Integer contarProductos() {
        int x = 0;
        for (ProductoCompra p: miCarrito) {
            x += p.getCantidad();
        }

        return x;
    }
}
