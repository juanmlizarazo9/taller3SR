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
<#if infoMessage??>
    <div style="color:green;font-style:italic;">
        ${infoMessage}
    </div>
</#if>
<div class="container">
    <h2>Taller 3 - Agregar usuarios y ratings</h2>
    <a class="btn btn-warning btn-lg" href="taller3" role="button">VOLVER</a>
</div>
<br/>
<div class="container">
</div>

<div class="container">
    <h2>Ratings</h2>
    <form name="addUserRating" action="addUserRating" method="POST">
        <div class="form-row">

            <div class="col-md-4 mb-3">
                <label for="movie">Movie</label>
                <input type="text" class="form-control" name="movie" id="movie" placeholder="movie" required>
            </div>


            <div class="col-md-4 mb-3">
                 <label for="user">User</label>
                 <input type="text" class="form-control" name="user" id="user" placeholder="user" required>
            </div>

                <div class="col-md-4 mb-3">

                    <label for="Rating">Rating</label>
                    <select name="Rating" id="Rating" class="custom-select" required>

                        <option value="0.0"> 0.0 </option>
                        <option value="0.5"> 0.5 </option>
                        <option value="1.0"> 1.0 </option>
                        <option value="1.5"> 1.5 </option>
                        <option value="2.0"> 2.0 </option>
                        <option value="2.5"> 2.5 </option>
                        <option value="3.0"> 3.0 </option>
                        <option value="3.5"> 3.5 </option>
                        <option value="4.0"> 4.0 </option>
                        <option value="4.5"> 4.5 </option>
                        <option value="5.0"> 5.0 </option>

                    </select>
            </div>
        </div>
        <button class="btn btn-primary" href="show_rating_list" role="button" type="submit">Agregar</button>
    </form>
</div>

<br/>

<div class="container">
    <h2>Ratings</h2>
    <form name="show_rating_list" action="show_rating_list" method="POST">
        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label for="user">User</label>
                <input type="text" class="form-control" name="user" id="user" placeholder="User" required>
            </div>
        </div>
        <button class="btn btn-primary" href="show_rating_list" role="button" type="submit">Mostrar</button>
    </form>
</div>

<#if ratings ??>
    <div class="container">
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">Usuario</th>
                <th scope="col">Item id</th>
                <th scope="col">Item Name</th>
                <th scope="col">Rating</th>
            </tr>
            </thead>
            <tbody>
            <#list ratings as rating>
                <tr class="success">
                    <td>${rating.userId}</td>
                    <td>${rating.movieId}</td>
                    <td>${rating.title}</td>
                    <td>${rating.rating}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</#if>

</body>
</html>
