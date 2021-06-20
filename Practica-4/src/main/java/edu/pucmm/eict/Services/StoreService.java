package edu.pucmm.eict.Services;

import edu.pucmm.eict.Database.DBConnection;
import edu.pucmm.eict.Database.DBEntityManager;
import edu.pucmm.eict.Models.*;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreService {

    public static void procesarVenta(Venta venta, List<ProductoCompra> productosComprados)
    {
        VentaService.getInstancia().insert(venta);
        for(ProductoCompra produ : productosComprados)
        {
            Producto temp = new Producto(produ.getId(), produ.getNombre(), produ.getPrecio(), produ.getDescripcion());
            ProductoVentas factura = new ProductoVentas(venta, temp, produ.getPrecio(), produ.getCantidad());
            ProductoVentaServices.getInstancia().insertProductoVenta(factura);
        }
    }

    public static List<Venta> findAllVentas() {
        List<Venta> ventaList = VentaService.getInstancia().findAll();
        for(Venta venta : ventaList)
        {
            List<ProductoVentas> detallesVenta = ProductoVentaServices.getInstancia().findProductsOFVentaByID(venta.getIdventa());

            List<ProductoVentas> listaproductos = new ArrayList<>();
            //Por cada producto que se obtuvo de la venta:
            for(ProductoVentas producto : detallesVenta)
            {
                Producto produ = ProductService.getInstancia().find(producto.getProducto().getIdproducto());
                ProductoVentas aux = new ProductoVentas(venta, produ, producto.getPrecio(), producto.getCantidad());
                listaproductos.add(aux);
            }
            venta.setListaproductos(listaproductos);
        }

        return ventaList;
    }
}
