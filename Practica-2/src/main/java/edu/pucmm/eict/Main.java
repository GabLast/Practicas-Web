package edu.pucmm.eict;

import edu.pucmm.eict.Controllers.UserController;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;

public class Main {

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/rutas"));
            config.enableCorsForAllOrigins();

        }).start(7000);

        app.get("/", ctx -> {
            ctx.redirect("/producto/listar");
        });

        app.error(404, ctx -> {
            ctx.redirect("/404.html");
        });

        new UserController(app).routes();
    }
}
