package edu.pucmm.eict.Services;

import edu.pucmm.eict.Database.DBEntityManager;
import edu.pucmm.eict.Models.Producto;

public class FotoProduService extends DBEntityManager<Producto> {
    private static FotoProduService instancia;

    private FotoProduService() {
        super(Producto.class);
    }

    public static FotoProduService getInstancia() {
        if (instancia == null) {
            instancia = new FotoProduService();
        }
        return instancia;
    }
}
