<p>&nbsp;</p>
<p>&lt;#if errorMessage??&gt;</p>
<div style="color: #ff0000; font-style: italic;">${errorMessage}</div>
<p>&lt;/#if&gt;</p>
<div class="container">
    <h2>Taller 3 - Recomendador de Pel&iacute;culas</h2>
    <a class="btn btn-warning btn-lg" role="button" href="/sr">Volver</a></div>
<p>&nbsp;</p>
<div class="container"><form action="t1_restaurantes_recomendacion" method="POST" name="t1_restaurantes_recomendacion">
        <div class="form-row">
            <div class="col-md-4 mb-3"><label for="user">User</label> <input id="user" class="form-control" name="user" required="" type="text" placeholder="User" /></div>
            <div class="col-md-4 mb-3"><label for="restaurant">Película</label> <input id="restaurant" class="form-control" name="restaurant" required="" type="text" placeholder="Restaurante" /></div>
            <div class="col-md-4 mb-3"><label for="Rating">Rating</label><select id="Rating" class="custom-select" name="Rating" required="">
                    <option value="">...</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select></div>
        </div>
        <div class="col-md-4 mb-3"><button class="btn btn-primary" type="submit">Recomendar</button>
            <div>&nbsp;</div>
        </div>
    </form></div>
<p>&lt;#if recommendations ??&gt;</p>
<div class="container">&lt;#list recommendations as recommendation&gt;&lt;/#list&gt;
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Item</th>
            <th scope="col">Rating</th>
        </tr>
        </thead>
        <tbody>
        <tr class="success">
            <td>${recommendation.item}</td>
            <td>${recommendation.rating}</td>
        </tr>
        </tbody>
    </table>
</div>
<p>&lt;/#if&gt;</p>