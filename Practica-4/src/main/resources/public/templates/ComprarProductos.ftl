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
      <h2 style="padding: 2rem"><span>Productos Disponibles</span></h2>
    </div>
  </div>
  <div class="row align-items-center">
    <div class="col">
      <div class="table-responsive" style="height: 300px">
                <table class="table table-striped table-bordered">
                    <thead class="thead-dark text-center">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Producto</th>
                        <th scope="col">Precio</th>
                        <th scope="col">Cantidad</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody class="text-center table-bordered">
                    <#if productos?has_content>
                    <#list productos as p>
                        <tr>
                            <form method="POST" action="/productos/listar/addtocart/">
                                <td>${p.idproducto}<input hidden value ="${p.idproducto}" name="idProduct"></td>
                                <td>${p.nombre}</td>
                                <td>RD$${p.precio}</td>
                                <td>
                                    <input type="number" class="form-control form-control-sm" name="cantidad" min="1" value="1" />
                                </td>
                                <td>
                                    <div class="row justify-content-evenly">
                                        <div class="col-6">
                                            <a href=""><#--/productos/view?${p.id}-->
                                                <button class="btn btn-sm" style="background-color: dodgerblue; color: whitesmoke"><span class="material-icons">visibility</span></button>
                                            </a>
                                        </div>
                                        <div class="col-6">
                                            <button class="btn btn-success btn-sm"  type="submit"><span class="material-icons">add_circle</span></button>
                                        </div>
                                    </div>
                                </td>
                            </form>
                        </tr>
                    </#list>
                    </#if>
                    </tbody>
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-right">
                            <#if cantPaginas?has_content>
                                <#list 0..cantPaginas as index>
                                    <li class="page-item">
                                    <a class="page-link" href="/productos/listar?${hola}">${index}</a>
                                </li>
                                </#list>
                            </#if>
                        </ul>
                    </nav>
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
</#macro>
<@display_page/>

