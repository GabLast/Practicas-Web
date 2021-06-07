<#include "BaseTemplate.ftl">
<#macro page_head>
<title>${title}</title>
</#macro>

<#macro page_body>
<#--  body here  -->
<#if admin == true>
<body>
<div class="container">
    <div class="row align-items-start">
        <div class="col">
        </div>
        <div class="col">
            <h2 style="padding: 2rem"><span>Gestionar Productos</span></h2>
        </div>
        <div class="col">

        </div>
    </div>
    <a href="/gestion/productos/agregar"><button type="button" class="btn btn-primary btn-dark">Agregar Producto</button></a>
    <div class="row align-items-center" style="padding: 2rem">
        <div class="col">
            <div class="table-responsive" style="height: 300px">
                <table class="table table-striped table-bordered">
                    <thead class="thead-dark text-center">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Producto</th>
                        <th scope="col">Precio</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody class="text-center table-bordered">
                    <#if productos?size gt 0>
                    <#list productos as p>
                    <tr>
                        <td>${p.id}</td>
                        <td>${p.nombre}</td>
                        <td>RD$${p.precio}</td>
                        <td>
                            <div class="container">
                                <div class="row justify-content-evenly">
                                    <div class="col-6">
                                        <a href="/gestion/productos/editar/${p.id}">
                                            <button class="btn btn-sm" style="background-color: dodgerblue; color: whitesmoke"><span class="material-icons">edit</span></button>
                                        </a>
                                    </div>
                                    <div class="col-6">
                                        <form method="post" action="/gestion/productos/eliminar">
                                            <input hidden value ="${p.id}" name="productID">
                                            <button class="btn btn-sm btn-danger" type="submit" style="background-color: darkred; color: whitesmoke"><span class="material-icons">delete</span></button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </td>
                    </tr>
                    </#list>
                </#if>
                </tbody>
                </table>
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
<#else>
<body>
<div class="central-body">
    <h5>Usted no tiene permisos para acceder a este recurso</h5>
    <a href="/productos/listar"><button class="btn btn-dark btn-go-home">VOLVER AL INICIO</button></a>
</div>
</body>
</#if>
</#macro>
<@display_page/>