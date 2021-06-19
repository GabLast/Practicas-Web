<#include "BaseTemplate.ftl">
<#macro page_head>
    <title>${title}</title>
</#macro>

<#macro page_body>
    <#if admin == true>
        <body style="background-color: whitesmoke">
            <div class="container-sm">
                <div class="row">
                    <div class="col-md-4">
                    </div>
                    <div class="col-md-4">
                    </div>
                    <div class="col-md-4">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                    </div>
                    <div class="col-md-4" style="padding-top: 25%">
                        <form method="post" action="/gestion/productos/agregar">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Registro de Productos</h5>
                                </div>
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <label for="nombre" class="form-label">Nombre:</label>
                                        <input type="text" class="form-control" name="nombre" placeholder="Producto" aria-describedby="emailHelp">
                                    </div>
                                    <div class="mb-3">
                                        <label for="precio" class="form-label">Precio:</label>
                                        <input type="number" min="1" value="1" class="form-control" name="precio" placeholder="(RD$)">
                                    </div>
                                    <div class="mb-3 form-check">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-primary btn-dark">Registrar</button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="col-md-4">
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-4">
                    </div>
                    <div class="col-md-4">
                    </div>
                    <div class="col-md-4">
                    </div>
                </div>
            </div>
        </body>
    <#else>
        <body>
            <div class="central-body ">
                <h5>Usted no tiene permisos para acceder a este recurso</h5>
                <a href="/productos/listar" class="btn btn-go-home btn-dark"><button>VOLVER AL INICIO</button></a>
            </div>
        </body>
    </#if>
</#macro>
<@display_page/>