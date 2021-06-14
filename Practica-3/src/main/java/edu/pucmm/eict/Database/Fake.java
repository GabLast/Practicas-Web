//package edu.pucmm.eict.Database;
//
//import edu.pucmm.eict.Models.Producto;
//import edu.pucmm.eict.Models.ProductoCompra;
//import edu.pucmm.eict.Models.Usuario;
//import edu.pucmm.eict.Models.Venta;
//import edu.pucmm.eict.Utils.ProductoException;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.sql.Date;
//import java.util.List;
//
//public class Fake {
//    private static Fake database;
//    private List<Usuario> usuarios = new ArrayList<>();
//    private List<Producto> productos = new ArrayList<>();
//    private List<Venta> ventas = new ArrayList<>();
//
//    public Fake() {
//        //Users
//        Usuario admin = new Usuario("admin", "admin", "Administrador", "Admin");
//        Usuario yo = new Usuario("gab", "123", "Cliente", "Gabriel");
//        usuarios.add(admin);
//        usuarios.add(yo);
//
//        //Productos iniciales
//        Producto a = new Producto(productos.size()+1, "Manzana", new BigDecimal(50.50));
//        productos.add(a);
//        Producto b = new Producto(productos.size()+1, "Pera", new BigDecimal(300.00));
//        productos.add(b);
//        Producto c = new Producto(productos.size()+1, "Laptop", new BigDecimal(10099.99));
//        productos.add(c);
//        Producto d = new Producto(productos.size()+1, "Corsair K70 RGB RapidFire", new BigDecimal(4999.99));
//        productos.add(d);
//
//        //Ventas Realizadas
//        List<ProductoCompra> ordenCompra = new ArrayList<>();
//        ordenCompra.add(new ProductoCompra(a.getId(), a.getNombre(), a.getPrecio(), 10));
//        ordenCompra.add(new ProductoCompra(b.getId(), b.getNombre(), b.getPrecio(), 5));
//        java.util.Date date1 = new java.util.Date();
//        java.sql.Date date = new java.sql.Date(date1.getTime());
//        ventas.add(new Venta(ventas.size()+1, admin.getNombre(), ordenCompra, date));
//
//        List<ProductoCompra> ordenCompra2 = new ArrayList<>();
//        ordenCompra2.add(new ProductoCompra(d.getId(), d.getNombre(), d.getPrecio(), 11));
//        ordenCompra2.add(new ProductoCompra(c.getId(), c.getNombre(), c.getPrecio(), 9));
//        ventas.add(new Venta(ventas.size()+1, yo.getNombre(), ordenCompra2, date));
//    }
//
//    public static Fake getInstancia(){
//        if(database==null){
//            database = new Fake();
//        }
//        return database;
//    }
//
//    public List<Usuario> getUsuarios() {
//        return usuarios;
//    }
//
//    public void setUsuarios(List<Usuario> usuarios) {
//        this.usuarios = usuarios;
//    }
//
//    public List<Producto> getProductos() {
//        return productos;
//    }
//
//    public void setProductos(List<Producto> productos) {
//        this.productos = productos;
//    }
//
//    public List<Venta> getVentas() {
//        return ventas;
//    }
//
//    public void setVentas(List<Venta> ventas) {
//        this.ventas = ventas;
//    }
//
//    public Producto getProductoByID(int id) {
//        return productos.stream().filter(pro -> pro.getId() == id).findFirst().orElse(null);
//    }
//
//    public Usuario getUserByUsername(String username) {
//        return usuarios.stream().filter(pro -> pro.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);
//    }
//
//    //Metodos de la tienda
//
//    public Producto agregarProducto(Producto producto) {
//        while (getProductoByID(producto.getId()) != null)
//        {
//            producto.setId(producto.getId()+1);
//        }
//        productos.add(producto);
//        return producto;
//    }
//
//    public Producto actualizarProducto(int id, String nombre, BigDecimal precio) {
//        Producto tmp = getProductoByID(id);
//        if(tmp == null) {
//            return null;
//        }
//        tmp.setNombre(nombre);
//        tmp.setPrecio(precio);
//        return tmp;
//    }
//
//    public boolean removerProducto(Producto producto) {
//
//        return productos.remove(producto);
//    }
//
//    public boolean agregarVenta(Venta venta) {
//        return ventas.add(venta);
//    }
//
//    public Usuario loginAuth(String username, String password){
//        Usuario user = getUserByUsername(username);
//        if (user == null)
//        {
//            return null;
//        }else {
//            if(!password.equals(user.getPassword()))
//            {
//                return null;
//            }
//            else{
//                return user;
//            }
//        }
//    }
//}
