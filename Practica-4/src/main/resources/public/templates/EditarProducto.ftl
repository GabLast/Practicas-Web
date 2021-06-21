<#include "BaseTemplate.ftl">
<#macro page_head>
    <#if title?has_content>
        <title>${title}</title>
    </#if>
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
                    <#if producto?has_content>
                        <form method="post" action="/gestion/productos/editar/${producto.idproducto}">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Editar Productos</h5>
                                </div>
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <label for="nombre" class="form-label">Nombre:</label>
                                        <input type="text" class="form-control" id="nombre" name="nombre" value="${producto.nombre}" placeholder="Producto" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="precio" class="form-label">Precio:</label>
                                        <input type="number" value="${producto.precio}" min="0.000001" step="any" class="form-control" id="precio" name="precio" placeholder="(RD$)" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="descripcion" class="form-label">Descripci&oacuten:</label>
                                        <input type="text" class="form-control" id="descripcion" name="descripcion" value="${producto.descripcion}" placeholder="Detalles" required>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <#--<input hidden value ="${producto.id}" name="id">-->
                                    <button type="submit" class="btn btn-primary btn-dark">Actualizar</button>
                                </div>
                            </div>
                        </form>
                    <#else>
                        <h5>Error: El producto no fue cargado apropiadamente</h5>
                        <a href="/" class="btn btn-go-home btn-dark"><button>VOLVER AL INICIO</button></a>
                    </#if>
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
            <a href="/"><button class="btn btn-go-home btn-dark">VOLVER AL INICIO</button></a>
        </div>
        </body>
    </#if>
</#macro>
<@display_page/>