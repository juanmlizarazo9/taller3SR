<!DOCTYPE html>
<html lang="es">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Taller3 - GRUPO 11</title>
    <link rel="stylesheet" href="/sr/webjars/bootstrap/4.0.0/css/bootstrap.min.css" />
    <script src="/sr/webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="/sr/webjars/bootstrap/4.0.0/js/bootstrap.min.js"></script>

</head>

<body>
<#if errorMessage??>
    <div style="color:red;font-style:italic;">
        ${errorMessage}
    </div>
</#if>

<div class="container">
    <h2>Taller 3 - Recomendador de películas</h2>
    <a class="btn btn-warning btn-lg" href="/sr" role="button">Volver</a>
</div>

<br/>
<br/>

            <div class="container">
               <h2>Agregar ratings y usuarios</h2>
               <div class="col-md-4 mb-3">
                 <a class="btn btn-primary" href="t3_add_user_rating" role="button">Agregar ratings</a>
               </div>

            </div>

<br/>
<br/>


<div class="container">
    <h2>Recomendación usuario</h2>
</div>

<div class="container">
    <form name="t3_peliculas_recomendacion" action="t3_peliculas_recomendacion" method="POST">

            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <input type="text" class="form-control" name="user" id="user" placeholder="Usuario" required>
                </div>
            </div>

            <div class="col-md-4 mb-3">
                <button class="btn btn-primary" type="submit">Recomendar</button>
            </div>

    </form>
</div>

<br/>
<br/>

<#if recommendations ??>
     <div class="container">
        <h2>Recomendaciones</h2>
     </div>
    <div class="container">
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">Nombre</th>
                <th scope="col">Generos</th>
                <th scope="col">Actores</th>
                <th scope="col">Directores</th>
                <th scope="col">Tags</th>
            </tr>
            </thead>
            <tbody>
            <#list recommendations as recommendation>
                <tr class="success">
                    <td>${recommendation.title}</td>
                    <td>${recommendation.genres}</td>
                    <td>${recommendation.actors}</td>
                    <td>${recommendation.directors}</td>
                    <td>${recommendation.tags}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</#if>

</body>
</html>