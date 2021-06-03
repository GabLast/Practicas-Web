package edu.pucmm.eict.Controllers;

import edu.pucmm.eict.Database.Fake;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import io.javalin.plugin.rendering.template.JavalinVelocity;

import static io.javalin.apibuilder.ApiBuilder.before;
import static io.javalin.apibuilder.ApiBuilder.path;

public class StoreController {
    //Fake DB
    Fake database = Fake.getInstancia();
    //App
    Javalin app;
    public StoreController(Javalin app){
        this.app = app;
        registrandoPlantillas();
    }

    //registrando los sistemas de plantilla.
    private void registrandoPlantillas(){
        JavalinRenderer.register(JavalinFreemarker.INSTANCE, ".ftl");
    }

    public void routes() {

        before(ctx -> {

        });

        app.routes(() -> {
            path("/productos", () -> {

                app.get("/productos", ctx -> {
                    ctx.redirect("/productos/listar");
                });

                app.get("/productos/listar", ctx -> {
                    ctx.result("/listar");
                });

            });
        });
    }
}
