package edu.pucmm.eict;

import edu.pucmm.eict.Controllers.StoreController;
import edu.pucmm.eict.Controllers.UserController;
import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;


public class Main {

    public static void main(String[] args) {

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

        app.exception(NumberFormatException.class, (exception, ctx) -> {
            ctx.html("Ocurrió un error en la conversacion numerica: "+exception.getLocalizedMessage());
        });

        new UserController(app).aplicarRutas();
        new StoreController(app).aplicarRutas();
    }
}
