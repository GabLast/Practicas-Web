package edu.pucmm.eict.Services;

import edu.pucmm.eict.Database.DBEntityManager;
import edu.pucmm.eict.Models.Producto;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ProductService extends DBEntityManager<Producto> {
    private static ProductService instancia;

    private ProductService() {
        super(Producto.class);
    }

    public static ProductService getInstancia() {
        if (instancia == null) {
            instancia = new ProductService();
        }
        return instancia;
    }

    public List<Producto> getAvailableProducts(){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select p from Producto p where p.borrado = :borrado", Producto.class);
        query.setParameter("borrado", 0);
        List<Producto> lista = query.getResultList();
        return lista;
    }

    public List<Producto> getAvailableProductsPaginated(int page){
        EntityManager em = getEntityManager();

        int rowsPerPage = 10;
        int selectedPage = page;

        Query selectQuery = em.createQuery("select p From Producto p where p.borrado = 0");
        selectQuery.setFirstResult((selectedPage - 1) * rowsPerPage);
        selectQuery.setMaxResults(rowsPerPage);

        List<Producto> lista = selectQuery.getResultList();

        return lista;
    }

}
