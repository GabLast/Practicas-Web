package edu.pucmm.eict.Controllers;

import edu.pucmm.eict.Database.Fake;
import edu.pucmm.eict.Models.CarritoCompras;
import edu.pucmm.eict.Models.ProductoCompra;
import edu.pucmm.eict.Models.Usuario;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class UserController {
    //Fake DB
    Fake database = Fake.getInstancia();
    //App
    Javalin app;
    public UserController(Javalin app){
        this.app = app;;
    }
    CarritoCompras micarrito;

    public void aplicarRutas() {
        app.routes(() -> {

            path("/user", () -> {
                before(ctx -> {
                    micarrito = ctx.sessionAttribute("micarrito");

                    //Inicializando carrito si es una sesion nueva
                    if(micarrito == null)
                    {
                        List<ProductoCompra> compras = new ArrayList<>();
                        micarrito = new CarritoCompras(database.getVentas().get(database.getVentas().size()-1).getId()+1, compras);
                        ctx.sessionAttribute("micarrito", micarrito);
                    }
                });

                get("/", ctx -> {
                    ctx.redirect("/user/login");
                });

                get("/login", ctx -> {
                    ctx.redirect("/Login.html");
                });

                post("/login/auth", ctx -> {
                    String username = ctx.formParam("username");
                    String password = ctx.formParam("password");
                    Usuario user = database.loginAuth(username, password);

                    if (user == null)
                    {
                        System.out.println("Usuario no existe");
                        ctx.redirect("/user/login");
                    }else{
                        ctx.sessionAttribute("usuario", user);

                        if(StoreController.requestedURL.equalsIgnoreCase(""))
                        {
                            ctx.redirect("/");
                        }
                        else {
                            String aux = StoreController.requestedURL;
                            StoreController.requestedURL = "";
                            ctx.redirect(aux);
                        }

                    }
                });

                get("/logout", ctx -> {
                    //invalidando la sesion.
                    ctx.req.getSession().invalidate();
                    ctx.redirect("/user/login");
                });

            });
        });
    }
}
