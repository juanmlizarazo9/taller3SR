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

<div class="container">
    <form name="t3_peliculas_recomendacion" action="t3_peliculas_recomendacion" method="POST">
        <div class="form-row">

            <div class="col-md-4 mb-3">
                <label for="user">User</label>
                <input type="text" class="form-control" name="user" id="user" placeholder="User" required>
            </div>

            <div class="col-md-4 mb-3">

                <label for="Genero">Género</label>
                <select name="Genero" id="Genero" class="custom-select" required>

                    <option value="">...</option>
                    <option value="OH"> Ohio </option>
                    <option value="NC"> Carolina del Norte </option>
                    <option value="PA"> Pensilvania </option>
                    <option value="QC"> Quebec </option>
                    <option value="AB"> Alabama </option>
                    <option value="WI"> Wisconsin</option>

                </select>

            </div>






        </div>

        <div class="col-md-4 mb-3">
            <button class="btn btn-primary" type="submit">Recomendar</button>
            <div>


    </form>
</div>

<#if recommendations ??>
    <div class="container">
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">Item</th>
                <th scope="col">Rating</th>
            </tr>
            </thead>
            <tbody>
            <#list recommendations as recommendation>
                <tr class="success">
                    <td>${recommendation.item}</td>
                    <td>${recommendation.rating}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</#if>

</body>
</html>