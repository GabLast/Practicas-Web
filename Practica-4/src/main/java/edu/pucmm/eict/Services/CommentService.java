package edu.pucmm.eict.Services;

import edu.pucmm.eict.Database.DBEntityManager;
import edu.pucmm.eict.Models.Comentario;
import edu.pucmm.eict.Models.Producto;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class CommentService extends DBEntityManager<Comentario> {
    private static CommentService instancia;

    private CommentService() {
        super(Comentario.class);
    }

    public static CommentService getInstancia() {
        if (instancia == null) {
            instancia = new CommentService();
        }
        return instancia;
    }

    public List<Producto> getNonDeletedCommentsByProduct(long idproduct){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select p from Comentario p where p.borrado = 0 and p.producto.idproducto =:id", Comentario.class);
        query.setParameter("id", idproduct);
        List<Producto> lista = query.getResultList();
        return lista;
    }
}
