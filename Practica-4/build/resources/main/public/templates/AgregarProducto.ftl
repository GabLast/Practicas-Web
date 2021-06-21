<#include "BaseTemplate.ftl">
<#macro page_head>
    <#if title?has_content>
        <title>${title}</title>
    </#if>
</#macro>

<#macro page_body>
    <#if admin == true>
        <body style="background-color: whitesmoke">
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                </div>
                <div class="col-md-4">
                </div>
                <div class="col-md-4">
                </div>
            </div>
            <div class="container" style="margin-bottom: 3rem">
                <div class="row">
                    <div class="col-md-3">
                    </div>
                    <div class="col-md-6" style="padding-top: 25%">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title">Registro de Productos</h5>
                            </div>
                            <div class="modal-body">
                                <form id="addform" method="post" action="/gestion/productos/agregar"
                                      enctype="multipart/form-data">
                                    <div class="mb-3 form-group">
                                        <label for="nombre" class="form-label">Nombre:</label>
                                        <input type="text" class="form-control" id="nombre" name="nombre"
                                               placeholder="Producto" required>
                                    </div>
                                    <div class="mb-3 form-group">
                                        <label for="precio" class="form-label">Precio:</label>
                                        <input type="number" min="1" value="1" class="form-control" id="precio"
                                               name="precio" placeholder="(RD$)" required>
                                    </div>
                                    <div class="mb-3 form-group">
                                        <label for="descripcion" class="form-label">Descripci&oacuten:</label>
                                        <input type="text" class="form-control" id="descripcion" name="descripcion"
                                               placeholder="Detalles" required>
                                    </div>
                                    <div class="mb-3 form-group">
                                        <label class="form-label">Fotos:</label>
                                        <br>
                                        <label for="fotos"
                                               class="form-label btn btn-primary material-icons custom-file-upload"
                                               style="background-color: dodgerblue; color: whitesmoke">
                                            image
                                        </label>
                                        <input type="file" class="form-control" id="fotos" name="fotos" multiple
                                               required>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" form="addform" class="btn btn-primary btn-dark">Registrar</button>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                    </div>
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
        <div class="central-body">
            <h5>Usted no tiene permisos para acceder a este recurso</h5>
            <a href="/">
                <button class="btn btn-go-home btn-dark">VOLVER AL INICIO</button>
            </a>
        </div>
        </body>
    </#if>
</#macro>
<@display_page/>
