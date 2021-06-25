package edu.pucmm.eict.Services;

import edu.pucmm.eict.Database.DBEntityManager;
import edu.pucmm.eict.Models.Venta;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class VentaService extends DBEntityManager<Venta> {
    private static VentaService instancia;

    private VentaService() {
        super(Venta.class);
    }

    public static VentaService getInstancia(){
        if(instancia==null){
            instancia = new VentaService();
        }
        return instancia;
    }

//    public Venta getVentaByID(int id){
//        EntityManager em = getEntityManager();
//        Query query = em.createQuery("select p from Venta p where p.idventa = :id");
//        query.setParameter("id", id);
//        return (Venta) query.getResultList().get(0);
//    }
}
