<#macro page_head>
    <title>Page title!</title>
</#macro>

<#macro page_body>
    <h1>Page body</h1>
</#macro>

<#macro display_page>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Carrito de Compras</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/Footer-Dark.css">
    <link rel="stylesheet" href="assets/css/Navigation-Clean.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <@page_head/>
</head>

<body class="text-center">
    <nav class="navbar navbar-dark navbar-expand-md bg-dark text-justify navigation-clean">
        <div class="container-fluid"><a class="navbar-brand text-light" href="/productos/listar">P2-Shop<img id="logo" src="assets/img/shopping-cart-304843_640.png" alt="Logo" ></a><button data-toggle="collapse" class="navbar-toggler" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navcol-1"><span class="navbar-text">Bienvenido, x</span>
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item"><a class="nav-link text-light" href="#">Comprar</a></li>
                    <li class="nav-item"><a class="nav-link text-light" href="#">Ventas Realizadas</a></li>
                    <li class="nav-item"><a class="nav-link text-light" href="#">Administrar Productos</a></li>
                    <li class="nav-item"><a class="nav-link text-light" href="#">Carrito (0)</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <@page_body/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.6.0/js/bootstrap.bundle.min.js"></script>
</body>
<footer class="footer-dark">
        <div class="container">
            <p class="copyright">Gabriel José Marte Lantigua - 2017-0388 - 1013-1315</p>
        </div>
</footer>
</html>
</#macro>