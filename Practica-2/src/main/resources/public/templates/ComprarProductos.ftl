<#include "BaseTemplate.ftl">
<#macro page_head>
    <title>${title}</title>
</#macro>

<#macro page_body>
<#--  body here  -->
<body style="background: whitesmoke">
<div class="container">
  <div class="row align-items-start">
    <div class="col">
      <h2 style="padding: 2rem; width: 100%"><span style="background: #343a40; color:#fff">Productos Disponibles</span></h2>
    </div>
  </div>
  <div class="row align-items-center">
    <div class="col">
      <div class="table-responsive" style="height: 300px">
                <table class="table table-striped table-bordered">
                    <thead class="thead-dark text-center">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Nombre</th>
                        <th scope="col">Precio</th>
                        <th scope="col">Cantidad</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody class="text-center table-bordered">
                    <#list productos as p>
                        <tr>
                            <form method="post" action="/productos/listar/addtocart">
                                <td>${p.id}<input hidden value ="${p.id}" name="idProduct"></td>
                                <td>${p.nombre}</td>
                                <td>RD$${p.precio}</td>
                                <td>
                                    <input type="number" class="form-control form-control-sm" id="cantidad" name="cantidad" min="1" value="1" />
                                </td>
                                <td>
                                    <button class="btn btn-success  btn-sm" type="submit"><span class="material-icons">add_circle</span></button>
                                </td>
                            </form>
                        </tr>
                    </#list>
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
</#macro>
<@display_page/>

