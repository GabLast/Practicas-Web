package edu.pucmm.eict.Models;

//import edu.pucmm.eict.Database.Fake;

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

    public ProductoCompra getProductoByID(long id) {
        return miCarrito.stream().filter(pro -> pro.getId() == id).findFirst().orElse(null);
    }

    public void agregarProducto(ProductoCompra producto) {

        ProductoCompra existe = getProductoByID(producto.getId());

        if(existe == null)
        {
            this.miCarrito.add(producto);
        }else{
            existe.setCantidad(existe.getCantidad() + producto.getCantidad());
        }

    }
    public boolean removerProducto(ProductoCompra producto) {
        return miCarrito.remove(producto);
    }

    public boolean removerProductoByINDEX(int index) {
        ProductoCompra producto = miCarrito.remove(index);
        return (producto != null);
    }

    public Integer contarProductos() {
        int x = 0;
        for (ProductoCompra p: miCarrito) {
            x += p.getCantidad();
        }

        return x;
    }

    public void limpiarCarrito() {
        miCarrito = new ArrayList<>();
    }
}
