package edu.pucmm.eict.Services;

import edu.pucmm.eict.Database.DBConnection;
import edu.pucmm.eict.Models.Producto;
import edu.pucmm.eict.Models.Usuario;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {
    public static void init() {
        Usuario admin = new Usuario("admin", "admin", "Administrador", "Admin");
        Usuario yo = new Usuario("gab", "123", "Cliente", "Gabriel");
        crearUser(admin);
        crearUser(yo);
    }

    public static boolean crearUser(Usuario user){
        boolean ok = false;
        Connection con = null;
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword());

        try {
            String query = "insert into usuario(username, nombre, password, rol) values (?,?,?,?)";
            con = DBConnection.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, user.getUsername());
            prepareStatement.setString(2, user.getNombre());
            prepareStatement.setString(3, encryptedPassword);
            prepareStatement.setString(4, user.getuserRole());
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ok;
    }

    public static Usuario getUserByUsername(String username){
        Usuario usuario = null;
        Connection con = null;

        try {
            String query = "select * from usuario where username = ?";
            con = DBConnection.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                usuario = new Usuario();
                usuario.setUsername(rs.getString("username"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPassword(rs.getString("password"));
                usuario.setuserRole(rs.getString("rol"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        //System.out.println(usuario.getNombre());
        return usuario;
    }

    public static Usuario login(String username, String password){
        Usuario usuario;
        usuario = getUserByUsername(username);
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

        if(usuario != null)
        {
            if (passwordEncryptor.checkPassword(password, usuario.getPassword()))
            {
                return usuario;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    public static Boolean addCookie(Usuario user, String cookie){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "update usuario set cookieencrypted = ? where username = ?";
            con = DBConnection.getInstancia().getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, cookie);
            prepareStatement.setString(2, user.getUsername());
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ok;
    }

    public static List<Usuario> getListaUsuarios(){
        Usuario usuario;
        Connection con = null;
        List<Usuario> lista = new ArrayList<>();

        try {
            String query = "select * from usuario";
            con = DBConnection.getInstancia().getConexion();
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                usuario = new Usuario();
                usuario.setUsername(rs.getString("username"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setPassword(rs.getString("password"));
                usuario.setuserRole(rs.getString("rol"));
                lista.add(usuario);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }
}
