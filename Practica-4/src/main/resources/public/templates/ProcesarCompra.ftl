<#include "BaseTemplate.ftl">
<#macro page_head>
<title>${title}</title>
</#macro>

<#macro page_body>
<#--  body here  -->
<body>
<div class="container">
    <div class="row align-items-start">
        <div class="col">
            <h2 style="padding: 2rem"><span>Realizar Compra</span></h2>
            <form method="POST" action="/productos/comprar/procesar" id="datos-cliente" style="padding: 2rem">
                <div class="shadow p-4 rounded bg-black js-active card border border-3" data-animation="scaleIn">
                    <h3 class="text-left">Datos del Cliente</h3>
                    <div>
                        <div class="form-row mt-4">
                            <label style="padding-top: 0.4rem" for="nombre">Nombre del Cliente:</label>
                            <#if logged == true>
                            <div class="col-12 col-sm-6"><input class="form-control" type="text" name="nombre" placeholder="Digite su nombre" value="${nombre}" required/></div>
                            <#else>
                            <div class="col-12 col-sm-6"><input class="form-control" type="text" name="nombre" placeholder="Digite su nombre" required/></div>
                        </#if>
                    </div>
                </div>
                </div>
            </form>
        </div>
    </div>

<div class="row align-items-center">
    <div class="col" >
        <div class="table-responsive" style="height: 300px">
            <table class="table table-striped table-bordered">
                <thead class="thead-dark text-center">
                <tr>
                    <th scope="col">Producto</th>
                    <th scope="col">Precio</th>
                    <th scope="col">Cantidad</th>
                    <th scope="col">Total</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody class="text-center table-bordered">
                <#assign totalfactura = 0>
                <#if productos?has_content>
                <#list productos as p>
                <tr>
                    <form method="POST" action="/productos/comprar/remover">
                        <td>${p.nombre}<input hidden value ="${p?index}" name="productIndex"></td>
                        <td>RD$${p.precio}</td>
                        <td>${p.cantidad}</td>
                        <#assign totalProducto = p.cantidad * p.precio>
                        <td>RD$${totalProducto}</td>
                        <#assign totalfactura += totalProducto>
                        <td>
                            <button class="btn btn-sm" type="submit" style="background-color: darkred; color: whitesmoke"><span class="material-icons">delete</span></button>
                        </td>
                    </form>
                </tr>
                </#list>
            </#if>
            <tr style="background: #343a40; color:#fff">
                <td colspan="5">Total: RD$${totalfactura}</td>
            </tr>
            </tbody>
            </table>
        </div>
        <div class="button-row d-flex">
            <form method="POST" action="/productos/comprar/limpiarcarrito">
                <button class="btn btn btn-primary ml-auto btn-danger" type="submit">Limpiar Carro</button></a>
            </form>
            <div></div>
            <#if empty == true>
            <button class="btn btn btn-primary ml-auto" form="datos-cliente" type="button" id="procesar" disabled>Agregue productos al carro antes de realizar una compra</button>
            <#else>
            <button class="btn btn btn-primary ml-auto" form="datos-cliente" type="submit" id="procesar">Procesar Compra</button>
        </#if>
    </div>
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
</#macro>
<@display_page/>

