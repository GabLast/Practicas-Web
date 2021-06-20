<#include "BaseTemplate.ftl">
<#macro page_head>
    <title>${title}</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/404-NOT-FOUND-animated.css">
    <link rel="stylesheet" type="text/css" href="assets/css/styles.css">
</#macro>

<#macro page_body>
<#--  body here  -->
    <#if admin == true>
    <body>
        <div class="container">
            <div class="row align-items-end">
                <div class="col">

                </div>
                <div class="col">

                </div>
                <div class="col">

                </div>
            </div>
            <div class="row align-items-center justify-content-center">
                <div class="col">
                    <#if ventas?has_content>
                        <#list ventas as venta>
                            <div class="table-responsive" style="height: 300px">
                                <table class="table table-striped table-bordered">
                                    <thead class="thead-dark text-center">
                                    <tr>
                                        <th scope="col">Producto</th>
                                        <th scope="col">Precio</th>
                                        <th scope="col">Cantidad</th>
                                        <th scope="col">Total</th>
                                    </tr>
                                    </thead>
                                    <tbody class="text-center table-bordered">
                                    <h5 style="padding: 1rem" class="text-left">Cliente: ${venta.cliente} - Fecha: ${venta.fechaCompra?datetime}</h5>
                                    <#assign totalfactura = 0>
                                    <#if venta.listaproductos?has_content>
                                        <#list venta.listaproductos as p>
                                            <tr>
                                                <td>${p.producto.nombre}</td>
                                                <td>RD$${p.producto.precio}</td>
                                                <td>${p.cantidad}</td>
                                                <#assign totalProducto = p.cantidad * p.precio>
                                                <td>RD$${totalProducto}</td>
                                                <#assign totalfactura += totalProducto>
                                            </tr>
                                        </#list>
                                    </#if>
                                    <tr style="background: #343a40; color:#fff">
                                        <td colspan="5">Total: RD$${totalfactura}</td>
                                    </tr>
                                    </tbody>
                                </table>
                                <div>
                                </div>
                            </div>
                        </#list>
                    <#else>
                        <h5>No se ha podido obtener las ventas. Por lo cual, no hay datos.</h5>
                        <a href="/" class="btn btn-go-home btn-dark"><button>VOLVER AL INICIO</button></a>
                    </#if>
                </div>
            </div>
            <div class="row align-items-end">
                <div class="col">

                </div>
                <div class="col">

                </div>
                <div class="col">

                </div>
            </div>
        </div>
    </body>
    <#else>
    <body>
    <div class="central-body ">
        <h5>Usted no tiene permisos para acceder a este recurso</h5>
        <a href="/" class="btn btn-go-home btn-dark"><button>VOLVER AL INICIO</button></a>
    </div>
    </body>
    </#if>
</#macro>
<@display_page/>

