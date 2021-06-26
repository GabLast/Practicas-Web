package edu.pucmm.eict.Database;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConfig {
    private static Server server;

    public static void startDb()  {
        try {
            server = Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers", "-tcpDaemon", "-ifNotExists").start();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public static void stopDb() throws SQLException {
        if(server!=null) {
            server.stop();
        }
    }

    public static void crearTablas() throws  SQLException{
        String producto = "CREATE TABLE IF NOT EXISTS PRODUCTO\n" +
                "(\n" +
                "  idProducto INT auto_increment PRIMARY KEY NOT NULL,\n" +
                "  nombre varchar(100) NOT NULL,\n" +
                "  precio DECIMAL NOT NULL,\n" +
                "  borrado int default 0 NOT NULL\n" +
                ");";
        String usuario = "CREATE TABLE IF NOT EXISTS USUARIO\n" +
                "(\n" +
                "  iduser int auto_increment PRIMARY KEY NOT NULL,\n" +
                "  username VARCHAR(100) NOT NULL,\n" +
                "  nombre VARCHAR(100) NOT NULL,\n" +
                "  password VARCHAR(100) NOT NULL,\n" +
                "  rol varchar(100) NOT NULL,\n" +

                "  constraint usernameunique unique (username)\n" +
                ");";
        String venta = "CREATE TABLE IF NOT EXISTS VENTA\n" +
                "(\n" +
                "  idVenta int auto_increment PRIMARY KEY NOT NULL,\n" +
                "  fechaVenta timestamp NOT NULL,\n" +
                "  cliente VARCHAR(100) NOT NULL\n" +
                ");";
        String productoComprado = "CREATE TABLE IF NOT EXISTS PRODUCTOCOMPRA\n" +
                "(\n" +
                "  idVenta int NOT NULL,\n" +
                "  idProducto int NOT NULL,\n" +
                "  cantidad int NOT NULL,\n" +
                "  precio decimal NOT NULL,\n" +
                "  primary key (idVenta, idProducto),\n" +
                "  constraint FK_venta foreign key (idVenta) references VENTA (idVenta),\n" +
                "  constraint FK_producto foreign key (idProducto) references PRODUCTO (idProducto)\n" +
                ");";
        Connection con = DBConnection.getInstancia().getConexion();
        Statement statement = con.createStatement();
        statement.execute(producto);
        statement.execute(usuario);
        statement.execute(venta);
        statement.execute(productoComprado);
        statement.close();
        con.close();
    }

}
