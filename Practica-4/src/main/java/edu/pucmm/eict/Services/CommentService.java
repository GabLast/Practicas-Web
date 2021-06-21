package edu.pucmm.eict.Services;

import edu.pucmm.eict.Database.DBEntityManager;
import edu.pucmm.eict.Models.Comentario;

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
}
