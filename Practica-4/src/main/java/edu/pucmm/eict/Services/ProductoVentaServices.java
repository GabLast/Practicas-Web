package edu.pucmm.eict.Services;

import edu.pucmm.eict.Database.DBEntityManager;
import edu.pucmm.eict.Models.Producto;
import edu.pucmm.eict.Models.ProductoVentas;
import edu.pucmm.eict.Models.Usuario;
import edu.pucmm.eict.Models.Venta;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ProductoVentaServices extends DBEntityManager<ProductService> {

    private static ProductoVentaServices instancia;

    private ProductoVentaServices() {
        super(ProductService.class);
    }

    public static ProductoVentaServices getInstancia() {
        if (instancia == null) {
            instancia = new ProductoVentaServices();
        }
        return instancia;
    }

    public void insertProductoVenta(ProductoVentas venta){
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("insert into Productoventas (cantidad, precio, producto_idproducto, venta_idventa) VALUES (?, ?, ?, ?)")
                .setParameter(1, venta.getCantidad())
                .setParameter(2, venta.getPrecio())
                .setParameter(3, venta.getProducto().getIdproducto())
                .setParameter(4, venta.getVenta().getIdventa());

        try {

            em.getTransaction().begin();
            query.executeUpdate();
            em.getTransaction().commit();

        }finally {
            em.close();
        }
    }

    public List<ProductoVentas> findProductsOFVentaByID(long id)
    {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM ProductoVentas u where u.productoVentasID.idventa = :id", ProductoVentas.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
