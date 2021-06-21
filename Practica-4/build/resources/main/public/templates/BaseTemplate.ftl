<#macro page_head>
    <title>Carrito de Compras</title>
</#macro>

<#macro page_body>
</#macro>

<#macro display_page>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="assets/bootstrap/css/Navigation-Clean.css">
    <link rel="stylesheet" type="text/css" href="assets/bootstrap/css/styles.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <@page_head/>
</head>

<body class="text-center">
    <nav class="navbar navbar-dark navbar-expand-md bg-dark text-justify navigation-clean">
        <div class="container-fluid"><a class="navbar-brand text-light" href="/">P2-Shop</a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse text-right" id="navcol-1" style="margin-top: 5px;">
                <#if logged == true>
                      <span class="navbar-text">Bienvenido, ${nombre}</span>
                <#else>
                      <span class="navbar-text">Bienvenido</span>
                </#if>
                <#if admin == true>
                    <ul class="navbar-nav ml-auto text-right">
                        <li class="nav-item"><a class="nav-link text-light" href="/productos/listar/view_page/1">Comprar</a></li>
                        <li class="nav-item"><a class="nav-link text-light" href="/historial/ventas">Ventas Realizadas</a></li>
                        <li class="nav-item"><a class="nav-link text-light" href="/gestion/productos">Administrar Productos</a></li>
                        <li class="nav-item"><a class="nav-link text-light" href="/productos/comprar">Carrito (${cantidad})</a></li>
                        <li class="nav-item"><a class="nav-link text-light" href="/logout">Log out</a></li>
                    </ul>
                <#else>
                    <ul class="navbar-nav ml-auto text-right">
                        <li class="nav-item"><a class="nav-link text-light" href="/productos/listar/view_page/1">Comprar</a></li>
                        <li class="nav-item"><a class="nav-link text-light" href="/productos/comprar">Carrito (${cantidad})</a></li>
                        <#if logged == true>
                            <li class="nav-item"><a class="nav-link text-light" href="/logout">Log Out</a></li>
                        <#else>
                            <li class="nav-item"><a class="nav-link text-light" href="/user/login">Log In</a></li>
                        </#if>
                    </ul>
                </#if>
            </div>
        </div>
    </nav>
    <@page_body/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>
</#macro>