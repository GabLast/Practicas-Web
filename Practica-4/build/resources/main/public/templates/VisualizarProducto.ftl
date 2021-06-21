<#include "BaseTemplate.ftl">
<#macro page_head>
    <#if title?has_content>
        <title>${title}</title>
    </#if>
</#macro>

<#macro page_body>
    <body>
    <div class="container-fluid">
        <div class="row row-cols-md-2">
            <div class="col-md-6">
                <div class="container-fluid">
                    <div class="modal-dialog modal-dialog-scrollable" role="img">
                        <div class="modal-content">
                            <div class="modal-header" style="background: #080808;color:rgb(255, 255, 255)">
                                <h5 class="modal-title">Producto</h5>
                            </div>
                            <div class="modal-body">
                                <#if producto?has_content>
                                    <div class="mb-3">
                                        <label for="nombre" class="form-label">Nombre:</label>
                                        <input type="text" class="form-control" id="nombre" name="nombre"
                                               value="${producto.nombre}" placeholder="Producto" readonly>
                                    </div>
                                    <div class="mb-3">
                                        <label for="precio" class="form-label">Precio:</label>
                                        <input type="number" value="${producto.precio}" min="0.000001" step="any"
                                               class="form-control" id="precio" name="precio" placeholder="(RD$)"
                                               readonly>
                                    </div>
                                    <div class="mb-3">
                                        <label for="descripcion" class="form-label">Descripci&oacuten:</label>
                                        <input type="text" class="form-control" id="descripcion" name="descripcion"
                                               value="${producto.descripcion}" placeholder="Detalles" readonly>
                                    </div>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="container-fluid">
                    <div class="modal-dialog modal-dialog-scrollable" role="img" style="max-height: 356px;">
                        <div class="modal-content">
                            <div class="modal-header" style="background: #080808;color:rgb(255, 255, 255)">
                                <h5 class="modal-title">Im&aacutegenes</h5>
                            </div>
                            <div class="modal-body">
                                <#if fotos?has_content>
                                    <#list fotos as foto>
                                        <img src="data:${foto.mimeType};base64,${foto.fotoBase64}" class="card-img-top"
                                             alt="picture" width="auto" height="auto">
                                        <div>
                                            <br>
                                        </div>
                                    </#list>
                                </#if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <br>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="container-fluid">
                    <div class="modal-dialog modal-dialog-scrollable" role="document" style="max-height: 500px;">
                        <div class="modal-content">
                            <div class="modal-header" style="background: #080808;color:rgb(255, 255, 255)">
                                <h5 class="modal-title">Escribir un comentario</h5>
                            </div>
                            <#if logged == true>
                                <div class="modal-body">
                                    <form method="POST" id="comentar" action="/productos/comentar">
                                        <#if nombre?has_content>
                                            <input hidden value="${nombre}" name="originalposter">
                                        </#if>
                                         <#if producto?has_content>
                                             <input hidden value="${producto.idproducto}" name="idproduct">
                                         </#if>
                                        <textarea name="comentario" style="width: 100%; height: 100%;" maxlength="255" placeholder="Escriba un comentario"></textarea>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" form="comentar" class="btn btn-primary btn-dark">Publicar</button>
                                </div>
                            <#else>
                                <div class="modal-body">
                                    <h5 class="modal-title">Necesita iniciar sesi&oacuten para poder escribir un
                                        comentario</h5>
                                </div>
                            </#if>

                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="container-fluid">
                    <div class="modal-dialog modal-dialog-scrollable" role="document" style="max-height: 500px;">
                        <div class="modal-content">
                            <div class="modal-header" style="background: #080808;color:rgb(255, 255, 255)">
                                <h5 class="modal-title">Comentarios</h5>
                            </div>
                            <div class="modal-body" id="commentbody">
                                <div class="table-responsive" style="height: 300px">
                                    <table class="table table-striped table-bordered">
                                        <thead class="thead-dark text-center">
                                        <tr>
                                            <th scope="col">Usuario</th>
                                            <th scope="col">Comentario</th>
                                            <#if admin == true>
                                                <th scope="col"></th>
                                            </#if>
                                        </tr>
                                        </thead>
                                        <tbody class="text-center table-bordered">
                                        <tr>
                                            <form method="POST" action="">
                                                <#if comentarios?has_content>
                                                    <#list comentarios as comment>
                                                        <td>${comment.originalposter}<input hidden
                                                                                            value="${comment.idcomentario}"
                                                                                            name="productIndex"></td>
                                                        <td>${comment.descripcion}</td>
                                                        <td>
                                                            <#if admin == true>
                                                                <button class="btn btn-sm" type="submit"
                                                                        style="background-color: darkred; color: whitesmoke">
                                                                    <span class="material-icons">delete</span></button>
                                                            </#if>
                                                        </td>
                                                    </#list>
                                                </#if>
                                            </form>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</#macro>
<@display_page/>