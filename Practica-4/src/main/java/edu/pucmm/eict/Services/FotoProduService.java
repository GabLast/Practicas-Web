package edu.pucmm.eict.Services;


import edu.pucmm.eict.Database.DBEntityManager;
import edu.pucmm.eict.Models.FotoProducto;

public class FotoProduService extends DBEntityManager<FotoProducto> {
    private static FotoProduService instance;

    public FotoProduService() {
        super(FotoProducto.class);
    }

    public static FotoProduService getInstance() {
        if (instance == null) {
            instance = new FotoProduService();
        }
        return instance;
    }
}
