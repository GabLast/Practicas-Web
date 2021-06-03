package edu.pucmm.eict.Database;

import edu.pucmm.eict.Models.Producto;
import edu.pucmm.eict.Models.ProductoCompra;
import edu.pucmm.eict.Models.Usuario;
import edu.pucmm.eict.Models.Venta;
import edu.pucmm.eict.Utils.ProductoException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Fake {
    private static Fake database;
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Producto> productos = new ArrayList<>();
    private List<Venta> ventas = new ArrayList<>();

    public Fake() {
        //Users
        Usuario admin = new Usuario("admin", "admin", "Administrador", "Admin");
        usuarios.add(admin);
        usuarios.add(new Usuario("gab", "123", "Cliente", "Gabriel"));

        //Productos iniciales
        Producto a = new Producto(productos.size()+1, "Manzana", new BigDecimal(50.50));
        Producto b = new Producto(productos.size()+1, "Pera", new BigDecimal(300.00));
        Producto c = new Producto(productos.size()+1, "Laptop", new BigDecimal(10099.99));
        Producto d = new Producto(productos.size()+1, "Corsair K70 RGB RapidFire", new BigDecimal(4999.99));

        productos.add(a);
        productos.add(b);
        productos.add(c);
        productos.add(d);

        //Ventas Realizadas
        List<ProductoCompra> ordenCompra = new ArrayList<>();
        ordenCompra.add(new ProductoCompra(a.getId(), a.getNombre(), a.getPrecio(), 10));
        ordenCompra.add(new ProductoCompra(b.getId(), b.getNombre(), b.getPrecio(), 5));
        ventas.add(new Venta(ventas.size()+1, admin, ordenCompra, new Date()));
    }

    public static Fake getInstancia(){
        if(database==null){
            database = new Fake();
        }
        return database;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public Producto getProductoByID(int id) {
        return productos.stream().filter(pro -> pro.getId() == id).findFirst().orElse(null);
    }

    //Metodos de la tienda

    public Producto agregarProducto(Producto producto) {
        if(getProductoByID(producto.getId()) != null) {
            System.out.println("Este producto ya existe. Registre un producto nuevo.");
            return null;
        }
        productos.add(producto);
        return producto;
    }

    public Producto actualizarProducto(Producto producto) {
        Producto tmp = getProductoByID(producto.getId());
        if(tmp == null) {
            throw new ProductoException("El producto de ID [" + producto.getId() + "] no existe." );
        }
        tmp.setNombre(producto.getNombre());
        tmp.setPrecio(producto.getPrecio());
        return tmp;
    }

    public boolean removerProducto(Producto producto) {

        return productos.remove(producto);
    }
}
