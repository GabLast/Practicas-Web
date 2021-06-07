package edu.pucmm.eict.Controllers;

import edu.pucmm.eict.Database.Fake;
import edu.pucmm.eict.Models.*;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinFreemarker;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;
import io.javalin.plugin.rendering.template.JavalinVelocity;

import java.math.BigDecimal;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class StoreController {

    //Fake DB
    Fake database = Fake.getInstancia();
    //App
    Javalin app;

    static String requestedURL = "";

    public StoreController(Javalin app){
        this.app = app;
    }

    CarritoCompras micarrito;
    Usuario usuario;

    private void verificarLogin(Usuario usuario, Map<String, Object> freeMarkerVars)
    {
        if(usuario != null)
        {
            freeMarkerVars.put("nombre", usuario.getNombre());
            if (usuario.getuserRole().equalsIgnoreCase("Administrador"))
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
    }


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
            });

            //Control de productos

            path("/productos", () -> {

                get("/", ctx -> {
                    ctx.redirect("/productos/listar");
                });

                //Mostrando los productos disponibles al usuario
                get("/listar", ctx -> {

//                    for(Producto prods : database.getProductos()){
//                        System.out.println(prods.getId());
//                    }

                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    freeMarkerVars.put("title", "Comprar Productos");
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());
                    //System.out.println(micarrito.contarProductos());

                    verificarLogin(usuario, freeMarkerVars);
                    freeMarkerVars.put("productos", database.getProductos());

                    //ctx.result("nice job");
                    ctx.render("/public/templates/ComprarProductos.ftl", freeMarkerVars);
                });

                //Agregando productos al carrito de compras
                post("/listar/addtocart", ctx -> {
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
                get("/comprar", ctx -> {
                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    freeMarkerVars.put("title", "Carrito");
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());
                    verificarLogin(usuario, freeMarkerVars);
                    freeMarkerVars.put("productos", micarrito.getMiCarrito());
                    if(micarrito.getMiCarrito().size() > 0) {
                        freeMarkerVars.put("empty", false);
                    }else {
                        freeMarkerVars.put("empty", true);
                    }
                    ctx.render("/public/templates/ProcesarCompra.ftl", freeMarkerVars);

                });

                post("/comprar/remover", ctx -> {

                    int productIndex = Integer.parseInt(ctx.formParam("productIndex"));
                    micarrito.removerProductoByINDEX(productIndex);
                    ctx.redirect("/productos/comprar");
                });

                post("/comprar/limpiarcarrito", ctx -> {
                    micarrito.limpiarCarrito();
                    ctx.sessionAttribute("micarrito", null);
                    ctx.redirect("/productos/comprar");
                });

                post("/comprar/procesar", ctx -> {

                    if(micarrito.getMiCarrito().size() > 0)
                    {
                        String cliente = ctx.formParam("nombre");
                        Venta factura = new Venta(database.getVentas().get(database.getVentas().size() - 1).getId() + 1, cliente, micarrito.getMiCarrito(),new Date());
                        database.agregarVenta(factura);
                        //System.out.println(factura.getId());
                        micarrito.limpiarCarrito();
                        ctx.sessionAttribute("micarrito", null);
                        ctx.redirect("/productos/comprar");
                    }
                    else{
                        Map<String, Object> freeMarkerVars = new HashMap<>();
                        freeMarkerVars.put("title", "Carrito");
                        freeMarkerVars.put("empty", true);
                        verificarLogin(usuario, freeMarkerVars);
                        ctx.render("/public/templates/ProcesarCompra.ftl", freeMarkerVars);
                    }
                });

            });

            //Seccion de Ventas

            path("/historial", () -> {

                before(ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    if (user == null){
                        requestedURL = ctx.req.getRequestURI();
                        ctx.redirect("/Login.html");
                    }
                });

                get("/ventas", ctx -> {
                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    freeMarkerVars.put("title", "Historial de Ventas");
                    verificarLogin(usuario, freeMarkerVars);
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());
                    freeMarkerVars.put("ventas",database.getVentas());
                    ctx.render("/public/templates/HistorialVentas.ftl", freeMarkerVars);
                });

            });

            path("/gestion", () -> {

                before(ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    if (user == null){
                        requestedURL = ctx.req.getRequestURI();
                        ctx.redirect("/Login.html");
                    }
                });

                get("/", ctx -> {
                    ctx.redirect("/gestion/productos");
                });

                get("/productos", ctx -> {
                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    verificarLogin(usuario, freeMarkerVars);
                    freeMarkerVars.put("title", "Gestionar Productos");
                    freeMarkerVars.put("productos", database.getProductos());
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());
                    ctx.render("/public/templates/GestionProductos.ftl", freeMarkerVars);
                });

                get("/productos/agregar", ctx -> {
                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    verificarLogin(usuario, freeMarkerVars);
                    freeMarkerVars.put("title", "Registrando Productos");
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());
                    ctx.render("/public/templates/AgregarProducto.ftl", freeMarkerVars);
                });

                post("/productos/agregar", ctx -> {

                    String nombre = ctx.formParam("nombre");
                    BigDecimal precio = new BigDecimal(ctx.formParam("precio"));

                    Producto nuevo = new Producto(database.getProductos().get(database.getProductos().size()-1).getId()+1, nombre, precio);

                    //System.out.println(nuevo.getId() + " " +  nuevo.getNombre() + " " + nuevo.getPrecio());

                    database.agregarProducto(nuevo);

                    ctx.redirect("/gestion/productos/agregar");
                });

                get("/productos/editar/:id", ctx -> {
                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    verificarLogin(usuario, freeMarkerVars);
                    freeMarkerVars.put("title", "Registrando Productos");
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());

                    //int id = Integer.parseInt(ctx.pathParam("id"));
                    Producto produ = database.getProductoByID(ctx.pathParam("id", Integer.class).get());
                    //System.out.println(produ.getId() + "----" + produ.getNombre());

                    freeMarkerVars.put("producto", produ);

                    ctx.render("/public/templates/EditarProducto.ftl", freeMarkerVars);
                });

                post("/productos/editar/:id", ctx -> {

                    //int id = Integer.parseInt(ctx.pathParam("id"));
                    Producto produ = database.getProductoByID(ctx.pathParam("id", Integer.class).get());
                    //System.out.println(produ.getId() + "----" + produ.getNombre());
                    String nombre = ctx.formParam("nombre");
                    BigDecimal precio = new BigDecimal(ctx.formParam("precio"));
                    database.actualizarProducto(produ.getId(),nombre,precio);

                    ctx.redirect("/gestion/productos");
                });

                post("/productos/eliminar", ctx -> {
                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    verificarLogin(usuario, freeMarkerVars);

                    int id = ctx.formParam("productID", Integer.class).get();
                    Producto produ = database.getProductoByID(id);
                    if (produ != null)
                    {
                        database.removerProducto(produ);
                    }
                    freeMarkerVars.put("title", "Gestionar Productos");
                    freeMarkerVars.put("productos", database.getProductos());
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());
                    ctx.render("/public/templates/GestionProductos.ftl", freeMarkerVars);
                });
            });

        });
    }
}





