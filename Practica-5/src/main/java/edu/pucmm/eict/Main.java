package edu.pucmm.eict;

import edu.pucmm.eict.Controllers.StoreController;
import edu.pucmm.eict.Controllers.UserController;
import edu.pucmm.eict.Database.DBConfig;
import edu.pucmm.eict.Database.DBConnection;
import edu.pucmm.eict.Services.ProductService;
import edu.pucmm.eict.Services.UserService;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;

import java.sql.SQLException;


public class Main {

    //indica el modo de operacion para la base de datos.
    private static String modoConexion = "";

    public static void main(String[] args) throws SQLException {
        //*********************************************************************
        //Base de Datos
        if(args.length >= 1){
            modoConexion = args[0];
            System.out.println("Modo de Operacion: "+modoConexion);
        }

        if(modoConexion.isEmpty()) {
            DBConfig.startDb();
        }

        //Prueba de Conexión.
        DBConnection.getInstancia().testConexion();
        UserService.init();
        //*********************************************************************

         Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("rutas"));
            config.enableCorsForAllOrigins();

        }).start(getHerokuAssignedPort());

        JavalinRenderer.register(JavalinFreemarker.INSTANCE, ".ftl");

//        app.get("/", ctx -> {
//            ctx.redirect("/productos/listar");
//        });

        app.get("/", ctx -> {
            ctx.redirect("/productos/listar/view_page/1");
        });

        app.error(404, ctx -> {
            ctx.redirect("/404.html");
        });

        app.exception(Exception.class, (exception, ctx) -> {
            ctx.status(500);
            ctx.html("<h1>Error no recuperado</h1>");
            exception.printStackTrace();
        });

        new UserController(app).aplicarRutas();
        new StoreController(app).aplicarRutas();
    }

    /**
     * Metodo para indicar el puerto en Heroku
     * @return
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000; //Retorna el puerto por defecto en caso de no estar en Heroku.
    }

    /**
     * Nos
     * @return
     */
    public static String getModoConexion(){
        return modoConexion;
    }
}
