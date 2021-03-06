package edu.pucmm.eict;

import edu.pucmm.eict.Controllers.StoreController;
import edu.pucmm.eict.Controllers.UserController;
import edu.pucmm.eict.Database.DBConfig;
import edu.pucmm.eict.Database.DBConnection;
import edu.pucmm.eict.Services.StoreServices;
import edu.pucmm.eict.Services.UserService;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;

import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException {
        //*********************************************************************
        //Base de datos
        //Iniciando el servicio
        DBConfig.startDb();

        //Prueba de Conexión.
        DBConnection.getInstancia().testConexion();

        DBConfig.crearTablas();
        UserService.init();
        StoreServices.init();

        //*********************************************************************

         Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("rutas"));
            config.enableCorsForAllOrigins();

        }).start(7000);

        JavalinRenderer.register(JavalinFreemarker.INSTANCE, ".ftl");

        app.get("/", ctx -> {
            ctx.redirect("/productos/listar");
        });

        app.error(404, ctx -> {
            ctx.redirect("/404.html");
        });

        new UserController(app).aplicarRutas();
        new StoreController(app).aplicarRutas();
    }
}
