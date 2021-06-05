package edu.pucmm.eict.Controllers;

import edu.pucmm.eict.Database.Fake;
import edu.pucmm.eict.Models.*;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import io.javalin.plugin.rendering.template.JavalinVelocity;

import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class StoreController {

    //Fake DB
    Fake database = Fake.getInstancia();
    //App
    Javalin app;
    public StoreController(Javalin app){
        this.app = app;
    }

    CarritoCompras micarrito;
    String usuario;
    String role;
    String nombre;


    public void aplicarRutas() {
        app.routes(() -> {

            // Verificar si el carrito existe en la sesion antes de cargar
            before(ctx -> {
                micarrito = ctx.sessionAttribute("micarrito");

                //Inicializando carrito si es una sesion nueva
                if(micarrito == null)
                {
                    List<ProductoCompra> compras = new ArrayList<>();
                    micarrito = new CarritoCompras(database.getVentas().get(database.getVentas().size()-1).getId()+1, compras);
                    ctx.sessionAttribute("micarrito", micarrito);
                }

                //System.out.println("ID Carrito: "+ micarrito.getId());

                //Revisando si ha hecho log in
                usuario = ctx.sessionAttribute("usuario");
                if(usuario != null) {
                    Usuario aux = Fake.getInstancia().getUserByUsername(usuario);
                    nombre = aux.getNombre();
                    role = aux.getuserRole();
                }
            });

            //Control de productos

            path("/productos", () -> {

                app.get("/productos", ctx -> {
                    ctx.redirect("/productos/listar");
                });

                //Mostrando los productos disponibles al usuario
                app.get("/productos/listar", ctx -> {

//                    for(Producto prods : database.getProductos()){
//                        System.out.println(prods.getId());
//                    }

                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    freeMarkerVars.put("title", "Comprar Productos");
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());
                    //System.out.println(micarrito.contarProductos());
                    if(usuario != null)
                    {
                        freeMarkerVars.put("nombre", nombre);
                        if (role.equalsIgnoreCase("Administrador"))
                        {
                            freeMarkerVars.put("admin", true);
                        }
                        else{
                            freeMarkerVars.put("admin", false);
                        }
                        freeMarkerVars.put("logged", true);
                    }else
                    {
                        freeMarkerVars.put("admin", false);
                        freeMarkerVars.put("logged", false);
                    }
                    freeMarkerVars.put("productos", database.getProductos());

                    //ctx.result("nice job");
                    ctx.render("/public/templates/ComprarProductos.ftl", freeMarkerVars);
                });

                //Agregando productos al carrito de compras
                app.post("/productos/listar/addtocart", ctx -> {
                    int id = ctx.formParam("idProduct", Integer.class).get();

                    Producto producto = database.getProductoByID(id);
                    if (producto == null)
                    {
                        ctx.redirect("/404.html");
                    }
                    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
                    ProductoCompra pro = new ProductoCompra(producto.getId(), producto.getNombre(), producto.getPrecio(), cantidad);
                    micarrito.agregarProducto(pro);

//                    for (ProductoCompra p: micarrito.getMiCarrito())
//                    {
//                        System.out.println(p.getNombre());
//                    }

                    ctx.redirect("/productos/listar");
                });

                //Seccion de compras
                app.get("/productos/comprar", ctx -> {
                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    freeMarkerVars.put("title", "Carrito");
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());

                    if(usuario != null)
                    {
                        freeMarkerVars.put("nombre", nombre);
                        if (role.equalsIgnoreCase("Administrador"))
                        {
                            freeMarkerVars.put("admin", true);
                        }
                        else{
                            freeMarkerVars.put("admin", false);
                        }
                        freeMarkerVars.put("logged", true);
                    }else
                    {
                        freeMarkerVars.put("admin", false);
                        freeMarkerVars.put("logged", false);
                    }
                    freeMarkerVars.put("productos", micarrito.getMiCarrito());
                    ctx.render("/public/templates/ProcesarCompra.ftl", freeMarkerVars);
                });

                app.post("/productos/comprar/remover", ctx -> {

                    int productIndex = Integer.parseInt(ctx.formParam("productIndex"));
                    micarrito.removerProductoByINDEX(productIndex);
                    ctx.redirect("/productos/comprar");
                });

                app.post("/productos/comprar/limpiarcarrito", ctx -> {
                    micarrito.limpiarCarrito();
                    ctx.sessionAttribute("micarrito", null);
                    ctx.redirect("/productos/comprar");
                });

                app.post("/productos/comprar/procesar", ctx -> {
                    String cliente = ctx.formParam("nombre");
                    Venta factura = new Venta(database.getVentas().get(database.getVentas().size() - 1).getId() + 1, cliente, micarrito.getMiCarrito(),new Date());
                    database.agregarVenta(factura);
                    System.out.println(factura.getId());
                    micarrito.limpiarCarrito();
                    ctx.sessionAttribute("micarrito", null);
                    ctx.redirect("/productos/comprar");
                });
            });
        });
    }
}





