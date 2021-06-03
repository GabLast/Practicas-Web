package edu.pucmm.eict.Controllers;

import edu.pucmm.eict.Database.Fake;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;

import static io.javalin.apibuilder.ApiBuilder.path;

public class UserController {
    //Fake DB
    Fake database = Fake.getInstancia();
    //App
    Javalin app;
    public UserController(Javalin app){
        this.app = app;
        registrandoPlantillas();
    }

    private void registrandoPlantillas(){
        JavalinRenderer.register(JavalinFreemarker.INSTANCE, ".ftl");
    }

    public void routes() {

        app.error(404, ctx -> {
            ctx.redirect("/404.html");
        });

        app.routes(() -> {
            path("/user", () -> {

                app.get("/user", ctx -> {
                    ctx.redirect("/user/login");
                });

                app.get("/user/login", ctx -> {
                    ctx.result("/login");
                });

            });
        });
    }
}
