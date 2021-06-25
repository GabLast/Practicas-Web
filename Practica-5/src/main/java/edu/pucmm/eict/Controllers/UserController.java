package edu.pucmm.eict.Controllers;

//import edu.pucmm.eict.Database.Fake;
import edu.pucmm.eict.Models.CarritoCompras;
import edu.pucmm.eict.Models.Usuario;
import edu.pucmm.eict.Services.UserService;
import io.javalin.Javalin;

import org.jasypt.util.text.StrongTextEncryptor;


import static io.javalin.apibuilder.ApiBuilder.*;

public class UserController {
    //Fake DB
//    Fake database = Fake.getInstancia();

    //App
    Javalin app;
    public UserController(Javalin app){
        this.app = app;;
    }
    CarritoCompras micarrito;

    public void aplicarRutas() {
        app.routes(() -> {

            path("/user", () -> {

                get("/", ctx -> {
                    ctx.redirect("/user/login");
                });

                get("/login", ctx -> {
                    ctx.redirect("/Login.html");
                });

                post("/login/auth", ctx -> {
                    String username = ctx.formParam("username");
                    String password = ctx.formParam("password");
                    Usuario user = UserService.login(ctx.formParam("username"),ctx.formParam("password"));
                    String remember = ctx.formParam("remember");

                    if (user == null)
                    {
                        System.out.println("User is NULL");
                        ctx.redirect("/user/login");
                    }else{
                        ctx.sessionAttribute("usuario", user);
                        if(remember != null)
                        {
                            if (remember.equals("true"))
                            {
                                String cookie = user.getUsername();
                                StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
                                textEncryptor.setPassword("TH!SisTH3P@SSw0rD"); //this should be put in an enviroment variable?
                                String myEncryptedText = textEncryptor.encrypt(cookie);
                                ctx.cookie("rememberme", myEncryptedText , 604800);
                            }
                        }

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

            });

            path("/logout", () -> {

                get("/", ctx -> {
                    //invalidando la sesion.
                    if (ctx.cookie("rememberme") != null)
                    {
                        ctx.removeCookie("rememberme");
                        StoreController.usuario = null;
                    }
                    ctx.req.getSession().invalidate();
                    ctx.redirect("/user/login");
                });

            });
        });
    }
}
