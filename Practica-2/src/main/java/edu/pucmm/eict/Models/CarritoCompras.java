package edu.pucmm.eict.Models;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompras {
    private long id;
    private List<ProductoCompra> miCarrito = new ArrayList<>();

    public CarritoCompras(long id) {
        this.id = id;
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
}
