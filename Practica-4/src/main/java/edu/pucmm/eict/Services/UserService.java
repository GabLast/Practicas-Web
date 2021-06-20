package edu.pucmm.eict.Services;

import edu.pucmm.eict.Database.DBEntityManager;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import edu.pucmm.eict.Models.Usuario;
import org.jasypt.util.password.StrongPasswordEncryptor;

import java.util.List;


public class UserService extends DBEntityManager<Usuario> {

    private static UserService instancia;

    private UserService() {
        super(Usuario.class);
    }

    public static UserService getInstancia() {
        if (instancia == null) {
            instancia = new UserService();
        }
        return instancia;
    }

    public static void init() {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword("admin");
        Usuario admin = new Usuario("admin", encryptedPassword, "Administrador", "Admin");
        encryptedPassword = passwordEncryptor.encryptPassword("123");
        Usuario yo = new Usuario("gab", encryptedPassword, "Cliente", "Gabriel");
        UserService.getInstancia().insert(admin);
        UserService.getInstancia().insert(yo);
    }

    public Usuario getUserByUsername(String username) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT u FROM Usuario u where u.username = :username");
        query.setParameter("username", username);
        return (Usuario) query.getResultList().get(0);
    }

    public static Usuario login(String username, String password) {
        Usuario usuario;
        usuario = UserService.getInstancia().getUserByUsername(username);
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        if (usuario != null) {
            if (passwordEncryptor.checkPassword(password, usuario.getPassword())) {
                return usuario;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
