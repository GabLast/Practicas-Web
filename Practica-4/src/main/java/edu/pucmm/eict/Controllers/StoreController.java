package edu.pucmm.eict.Controllers;

//import edu.pucmm.eict.Database.Fake;

import edu.pucmm.eict.Models.*;
import edu.pucmm.eict.Services.*;
import io.javalin.Javalin;
import org.jasypt.util.text.StrongTextEncryptor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;

public class StoreController {

    //Fake DB
    //Fake database = Fake.getInstancia();

    //App
    Javalin app;

    static String requestedURL = "";

    public StoreController(Javalin app) {
        this.app = app;
    }

    CarritoCompras micarrito;
    static Usuario usuario;

    private void verificarLogin(Usuario usuario, Map<String, Object> freeMarkerVars) {
        if (usuario != null) {
            freeMarkerVars.put("nombre", usuario.getNombre());
            if (usuario.getuserRole().equalsIgnoreCase("Administrador")) {
                freeMarkerVars.put("admin", true);
            } else {
                freeMarkerVars.put("admin", false);
            }
            freeMarkerVars.put("logged", true);
        } else {
            freeMarkerVars.put("admin", false);
            freeMarkerVars.put("logged", false);
        }
    }


    public void aplicarRutas() {
        app.routes(() -> {

            // Verificar si el carrito existe en la sesion antes de cargar
            before(ctx -> {
                micarrito = ctx.sessionAttribute("micarrito");
                ctx.header("acceso");
                //Inicializando carrito si es una sesion nueva
                if (micarrito == null) {
                    List<ProductoCompra> compras = new ArrayList<>();
                    micarrito = new CarritoCompras(1, compras);
                    ctx.sessionAttribute("micarrito", micarrito);
                }

                //Verificar cookie
                if (ctx.cookie("rememberme") != null) {
                    StrongTextEncryptor textEncryptor = new StrongTextEncryptor();
                    textEncryptor.setPassword("TH!SisTH3P@SSw0rD"); //this should be put in an enviroment variable?
                    String myDecryptedText = textEncryptor.decrypt(ctx.cookie("rememberme"));
                    usuario = UserService.getInstancia().getUserByUsername(myDecryptedText);
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

                    verificarLogin(usuario, freeMarkerVars);
                    freeMarkerVars.put("productos", ProductService.getInstancia().getAvailableProducts());

                    ctx.render("/public/templates/ComprarProductos.ftl", freeMarkerVars);
                });

                get("/listar?view_page=:page", ctx -> {
                    int page = ctx.pathParam("page", Integer.class).get();
                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    freeMarkerVars.put("title", "Comprar Productos");
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());

                    verificarLogin(usuario, freeMarkerVars);
                    freeMarkerVars.put("productos", ProductService.getInstancia().getAvailableProductsPaginated(page));

                    int pageSize = 1;
                    EntityManager em = ProductService.getInstancia().getEntityManager();
                    String count = "Select count (p.idproducto) from Producto p where p.borrado = 0";
                    Query countQuery = em.createQuery(count);
                    Long countResults = (Long) countQuery.getSingleResult();
                    int PageTotalNumber = (int) (Math.ceil(countResults / pageSize));

                    freeMarkerVars.put("cantPaginas", PageTotalNumber);
                    System.out.println("Paginas: " + PageTotalNumber);
                    ctx.render("/public/templates/ComprarProductos.ftl", freeMarkerVars);
                });

                get("/listar/view?:productid", ctx -> {
                    ctx.result("nice");
                });

                //Agregando productos al carrito de compras
                post("/listar/addtocart", ctx -> {
                    long id = ctx.formParam("idProduct", Long.class).get();

                    Producto producto = ProductService.getInstancia().find(id);
                    if (producto == null) {
                        ctx.redirect("/404.html");
                    }

                    int cantidad = Integer.parseInt(ctx.formParam("cantidad"));

                    ProductoCompra pro = new ProductoCompra(producto.getIdproducto(), producto.getNombre(), producto.getPrecio(), cantidad, producto.getDescripcion());
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
                    if (micarrito.getMiCarrito().size() > 0) {
                        freeMarkerVars.put("empty", false);
                    } else {
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

                    if (micarrito.getMiCarrito().size() > 0) {
                        String cliente = ctx.formParam("nombre");

                        Venta factura = new Venta(cliente);
                        StoreService.procesarVenta(factura, micarrito.getMiCarrito());

                        micarrito.limpiarCarrito();
                        ctx.sessionAttribute("micarrito", null);
                        ctx.redirect("/productos/comprar");
                    } else {
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
                    if (user == null) {
                        requestedURL = ctx.req.getRequestURI();
                        ctx.redirect("/Login.html");
                    }
                });

                get("/ventas", ctx -> {
                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    freeMarkerVars.put("title", "Historial de Ventas");
                    verificarLogin(usuario, freeMarkerVars);
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());

                    freeMarkerVars.put("ventas", StoreService.findAllVentas());
                    ctx.render("/public/templates/HistorialVentas.ftl", freeMarkerVars);
                });

            });

            path("/gestion", () -> {

                before(ctx -> {
                    Usuario user = ctx.sessionAttribute("usuario");
                    if (user == null) {
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
                    freeMarkerVars.put("productos", ProductService.getInstancia().getAvailableProducts());
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
                    String descripcion = ctx.formParam("descripcion");

                    Producto nuevo = new Producto(nombre, precio, descripcion);
                    ProductService.getInstancia().insert(nuevo);
                    ctx.uploadedFiles("fotos").forEach(uploadedFile -> {
                        try{
                            byte[] bytes = uploadedFile.getContent().readAllBytes();
                            String encondedString = Base64.getEncoder().encodeToString(bytes);
                            FotoProducto foto = new FotoProducto(uploadedFile.getContentType(), encondedString, nuevo);
                            FotoProduService.getInstancia().insert(foto);
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    });

                    ctx.redirect("/gestion/productos/agregar");
                });

                get("/productos/editar/:id", ctx -> {
                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    verificarLogin(usuario, freeMarkerVars);
                    freeMarkerVars.put("title", "Registrando Productos");
                    freeMarkerVars.put("cantidad", micarrito.contarProductos());

                    long id = Long.parseLong(ctx.pathParam("id"));
                    Producto produ = ProductService.getInstancia().find(id);

                    freeMarkerVars.put("producto", produ);

                    ctx.render("/public/templates/EditarProducto.ftl", freeMarkerVars);
                });

                post("/productos/editar/:id", ctx -> {

                    long id = Long.parseLong(ctx.pathParam("id"));
                    Producto produ = ProductService.getInstancia().find(id);

                    String nombre = ctx.formParam("nombre");
                    BigDecimal precio = new BigDecimal(ctx.formParam("precio"));
                    String descripcion = ctx.formParam("descripcion");

                    produ.setNombre(nombre);
                    produ.setPrecio(precio);
                    produ.setDescripcion(descripcion);

                    ProductService.getInstancia().update(produ);

                    ctx.redirect("/gestion/productos");
                });

                post("/productos/eliminar", ctx -> {
                    Map<String, Object> freeMarkerVars = new HashMap<>();
                    verificarLogin(usuario, freeMarkerVars);

                    long id = ctx.formParam("productID", Long.class).get();
                    Producto produ = ProductService.getInstancia().find(id);

                    if (produ != null) {
                        produ.setBorrado(1);
                        ProductService.getInstancia().update(produ);
                    }

                    ctx.redirect("/gestion/productos");
                });

            });

        });
    }
}





