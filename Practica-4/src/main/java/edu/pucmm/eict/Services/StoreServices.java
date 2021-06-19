package edu.pucmm.eict.Services;

import edu.pucmm.eict.Database.DBConnection;
import edu.pucmm.eict.Models.Producto;
import edu.pucmm.eict.Models.ProductoCompra;
import edu.pucmm.eict.Models.Usuario;
import edu.pucmm.eict.Models.Venta;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreServices {
//    public static void init() {
//        //Productos iniciales
//        Producto a = new Producto(1, "Manzana", new BigDecimal(50.50));
//        Producto b = new Producto(2, "Pera", new BigDecimal(300.00));
//        Producto c = new Producto(3, "Laptop", new BigDecimal(10099.99));
//        Producto d = new Producto(4, "Corsair K70 RGB RapidFire", new BigDecimal(4999.99));
//        crearProducto(a);
//        crearProducto(b);
//        crearProducto(c);
//        crearProducto(d);
//
//        //Ventas iniciales
//        List<ProductoCompra> ordenCompra = new ArrayList<>();
//        ordenCompra.add(new ProductoCompra(a.getId(), a.getNombre(), a.getPrecio(), 10));
//        ordenCompra.add(new ProductoCompra(b.getId(), b.getNombre(), b.getPrecio(), 5));
//        java.util.Date date1 = new java.util.Date();
//        java.sql.Date date = new java.sql.Date(date1.getTime());
//        Venta venta1 = new Venta(1, "Administrador", ordenCompra, date);
//        crearVenta(venta1);
//
//        List<ProductoCompra> ordenCompra2 = new ArrayList<>();
//        ordenCompra2.add(new ProductoCompra(d.getId(), d.getNombre(), d.getPrecio(), 11));
//        ordenCompra2.add(new ProductoCompra(c.getId(), c.getNombre(), c.getPrecio(), 9));
//        Venta venta2 = new Venta(2, "Gabriel", ordenCompra2, date);
//        crearVenta(venta2);
//
//    }

    public static boolean crearProducto(Producto produ){
        boolean ok = false;
        Connection con = null;

        try {
            String query = "insert into producto(nombre, precio) values (?,?)";
            con = DBConnection.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, produ.getNombre());
            prepareStatement.setBigDecimal(2, produ.getPrecio());

            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ok;
    }

    public static Producto getProductoByID(int id){
        Producto producto = null;
        Connection con = null;

        try {
            String query = "select * from producto where idproducto = ?";
            con = DBConnection.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                producto = new Producto();
                producto.setId(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getBigDecimal("precio"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return producto;
    }

    public static boolean actualizarProducto(Producto produ){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "update producto set nombre=?, precio=? where idProducto = ?";
            con = DBConnection.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, produ.getNombre());
            prepareStatement.setBigDecimal(2, produ.getPrecio());
            //Indica el where...
            prepareStatement.setInt(3, produ.getId());
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ok;
    }

    public static boolean borrarProducto(int id){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "update producto set borrado = 1 where idproducto = ?";
            con = DBConnection.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);

            //Indica el where...
            prepareStatement.setInt(1, id);
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ok;
    }

    public static List<Producto> getListaProductos(){
        Producto producto;
        Connection con = null;
        List<Producto> lista = new ArrayList<>();
        try {
            String query = "select * from producto where borrado = 0";
            con = DBConnection.getInstancia().getConexion();

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                producto = new Producto();
                producto.setId(rs.getInt("idProducto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getBigDecimal("precio"));
                lista.add(producto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }

    public static List<Venta> getListaVentas(){
        List<Venta> ventas = new ArrayList<>();
        Venta venta;
        ProductoCompra produ;
        Connection con = null;

        try {
            String query = "select * from venta";
            con = DBConnection.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                venta = new Venta();
                venta.setId(rs.getInt("idventa"));
                venta.setCliente(rs.getString("cliente"));
                venta.setFechaCompra(rs.getDate("fechaventa"));
                List<ProductoCompra> listaProd = new ArrayList<>();
                //venta.setproductosComprados(listaProd);
                ventas.add(venta);
            }

            for(Venta ven : ventas)
            {
                String query2 = "select * from productocompra where idventa = ?";
                ps = con.prepareStatement(query2);
                ps.setLong(1, ven.getId());
                rs = ps.executeQuery();
                while (rs.next())
                {
                    produ = new ProductoCompra();
                    produ.setCantidad(rs.getInt("cantidad"));
                    Producto aux = getProductoByID(rs.getInt("idproducto"));
                    produ.setNombre(aux.getNombre());
                    produ.setPrecio(rs.getBigDecimal("precio"));
                    produ.setId(aux.getId());
                    ven.getproductosComprados().add(produ);
                }
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return ventas;
    }


    public static boolean crearVenta(Venta venta){
        boolean ok = false;

        Connection con = null;

        try {
            String query = "insert into venta(fechaventa, cliente) values (?,?)";
            con = DBConnection.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setDate(1, venta.getFechaCompra());
            prepareStatement.setString(2, venta.getCliente());

            prepareStatement.executeUpdate();

            int idVenta = -1;
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                idVenta = generatedKeys.getInt(1);
            }
//            System.out.println("ID Venta generado:" + idVenta);
//            System.out.println("Total Productos: " + venta.getproductosComprados().get(0).getNombre());

            String query2;
            for(ProductoCompra produ : venta.getproductosComprados())
            {
                query2 = "insert into PRODUCTOCOMPRA(idventa, idproducto, cantidad, precio) values (?,?,?,?)";
                prepareStatement = con.prepareStatement(query2);
                prepareStatement.setInt(1, idVenta);
                prepareStatement.setInt(2, produ.getId());
                prepareStatement.setInt(3, produ.getCantidad());
                prepareStatement.setBigDecimal(4, produ.getPrecio());
                prepareStatement.executeUpdate();
            }

            ok = idVenta > -1;

        } catch (SQLException ex) {
            Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Venta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ok;
    }
}
