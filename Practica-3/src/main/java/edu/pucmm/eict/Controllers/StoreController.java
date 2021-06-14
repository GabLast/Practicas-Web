package edu.pucmm.eict.Controllers;

//import edu.pucmm.eict.Database.Fake;
import edu.pucmm.eict.Models.*;
import edu.pucmm.eict.Services.StoreServices;
import edu.pucmm.eict.Services.UserService;
import io.javalin.Javalin;
import org.jasypt.util.text.StrongTextEncryptor;

import java.math.BigDecimal;
import java.util.*;
import java.sql.Date;

import static io.javalin.apibuilder.ApiBuilder.*;

public class StoreController {

    //Fake DB
    //Fake database = Fake.getInstancia();

    //App
    Javalin app;

    static String requestedURL = "";

    public StoreController(Javalin app){
        this.app = app;
    }

    CarritoCompras micarrito;
    static Usuario usuario;

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
//                    micarrito = new CarritoCompras(database.getVentas().get(database.getVentas().size()-1).getId()+1, compras);
                    micarrito = new CarritoCompras(1, compras);
                    ctx.sessionAttribute("micarrito", micarrito);
                }

                //Verificar cookie
                if(ctx.cookie("rememberme") != null){
                    StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
                    textEncryptor.setPassword("TH!SisTH3P@SSw0rD"); //this should be put in an enviroment variable?
                    String myDecryptedText = textEncryptor.decrypt(ctx.cookie("rememberme"));
                    usuario = UserService.getUserByUsername(myDecryptedText);
                    ctx.sessionAttribute("usuario", usuario);
                }
                usuario = ctx.sessionAttribute("usuario");
            });

            //Control de productos

            path("/productos", () -> {

                get("/", ctx -> {
                    ctx.redirect("/productos/listar");
                });

                //Mostrando los productos disponibles al usuario
                get("/listar", ctx -> {

                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    freeMarkerVars.put("title", "Comprar Productos");
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());
                    //System.out.println(micarrito.contarProductos());

                    verificarLogin(usuario, freeMarkerVars);
//                    freeMarkerVars.put("productos", database.getProductos());
                    freeMarkerVars.put("productos", StoreServices.getListaProductos());

                    //ctx.result("nice job");
                    ctx.render("/public/templates/ComprarProductos.ftl", freeMarkerVars);
                });

                //Agregando productos al carrito de compras
                post("/listar/addtocart", ctx -> {
                    int id = ctx.formParam("idProduct", Integer.class).get();

//                    Producto producto = database.getProductoByID(id);
                    Producto producto = StoreServices.getProductoByID(id);
                    if (producto == null)
                    {
                        ctx.redirect("/404.html");
                    }

                    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));
                    ProductoCompra pro = new ProductoCompra(producto.getId(), producto.getNombre(), producto.getPrecio(), cantidad);
                    micarrito.agregarProducto(pro);

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
//                        Venta factura = new Venta(database.getVentas().get(database.getVentas().size() - 1).getId() + 1, cliente, micarrito.getMiCarrito(),new Date());
//                        database.agregarVenta(factura);
                        java.util.Date date1 = new java.util.Date();
                        java.sql.Date date = new java.sql.Date(date1.getTime());
                        Venta factura = new Venta(-1, cliente, micarrito.getMiCarrito(), date);
                        StoreServices.crearVenta(factura);
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
//                    freeMarkerVars.put("ventas",database.getVentas());
                    freeMarkerVars.put("ventas",StoreServices.getListaVentas());
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
                    //freeMarkerVars.put("productos", database.getProductos());
                    freeMarkerVars.put("productos", StoreServices.getListaProductos());
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


//                    Producto nuevo = new Producto(database.getProductos().get(database.getProductos().size()-1).getId()+1, nombre, precio);
                    Producto nuevo = new Producto(StoreServices.getListaProductos().get(StoreServices.getListaProductos().size()-1).getId()+1, nombre, precio);

                    //database.agregarProducto(nuevo);
                    StoreServices.crearProducto(nuevo);

                    ctx.redirect("/gestion/productos/agregar");
                });

                get("/productos/editar/:id", ctx -> {
                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    verificarLogin(usuario, freeMarkerVars);
                    freeMarkerVars.put("title", "Registrando Productos");
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());

                    //int id = Integer.parseInt(ctx.pathParam("id"));
//                    Producto produ = database.getProductoByID(ctx.pathParam("id", Integer.class).get());
                    Producto produ = StoreServices.getProductoByID(ctx.pathParam("id", Integer.class).get());
                    //System.out.println(produ.getId() + "----" + produ.getNombre());

                    freeMarkerVars.put("producto", produ);

                    ctx.render("/public/templates/EditarProducto.ftl", freeMarkerVars);
                });

                post("/productos/editar/:id", ctx -> {

                    //int id = Integer.parseInt(ctx.pathParam("id"));
                    //Producto produ = database.getProductoByID(ctx.pathParam("id", Integer.class).get());
                    Producto produ = StoreServices.getProductoByID(ctx.pathParam("id", Integer.class).get());

                    //System.out.println(produ.getId() + "----" + produ.getNombre());
                    String nombre = ctx.formParam("nombre");
                    BigDecimal precio = new BigDecimal(ctx.formParam("precio"));

                    //database.actualizarProducto(produ.getId(),nombre,precio);
                    produ.setNombre(nombre);
                    produ.setPrecio(precio);
                    StoreServices.actualizarProducto(produ);

                    ctx.redirect("/gestion/productos");
                });

                post("/productos/eliminar", ctx -> {
                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    verificarLogin(usuario, freeMarkerVars);

                    int id = ctx.formParam("productID", Integer.class).get();
//                    Producto produ = database.getProductoByID(id);
                    Producto produ = StoreServices.getProductoByID(id);
                    if (produ != null)
                    {
                        //database.removerProducto(produ);
                        StoreServices.borrarProducto(produ.getId());
                    }
                    freeMarkerVars.put("title", "Gestionar Productos");
//                    freeMarkerVars.put("productos", database.getProductos());
                    freeMarkerVars.put("productos", StoreServices.getListaProductos());
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());
                    ctx.render("/public/templates/GestionProductos.ftl", freeMarkerVars);
                });
            });

        });
    }
}





