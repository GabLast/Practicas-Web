package edu.pucmm.eict.Services;

import edu.pucmm.eict.Database.DBEntityManager;
import edu.pucmm.eict.Models.FotoProducto;

public class FotoProduService extends DBEntityManager<FotoProducto> {
    private static FotoProduService instancia;

    private FotoProduService() {
        super(FotoProducto.class);
    }

    public static FotoProduService getInstancia() {
        if (instancia == null) {
            instancia = new FotoProduService();
        }
        return instancia;
    }
}
