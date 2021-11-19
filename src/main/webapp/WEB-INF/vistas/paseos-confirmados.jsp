<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="partial/header.jsp" %>

<html>
<head>
    <title>Veterinaria</title>
</head>
<body>
    <div class="container mt-3">
        <div class="d-flex justify-content-between my-2 align-items-center">
            <h2>Paseos</h2>
            <a href="/paseador/nuevo-paseo" class="btn btn-success">
                Nuevo Paseo
            </a>
        </div>
        <div>
            <h3>A confirmar</h3>
            <table class="table">
                <thead>
                <tr>
                    <th>Mascota</th>
                    <th>Paseador</th>
                    <th>Ruta del paseador</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Firulais</td>
                    <td>Marcelo</td>
                    <td>
                        <a href="#" data-toggle="modal" data-target="#modalImage" data-title="Ruta de Paseador a mi casa" data-image="mock-routing">
                            <img src="<c:url value="/images/mock-routing.jpg"/>" width="100" alt="ruta">
                        </a>
                    </td>
                    <td>
                        <button class="btn btn-success" type="button">Comenzar Paseo</button>
                        <button class="btn btn-danger" type="button">Cancelar</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div>
            <h3>Activos</h3>
            <table class="table">
                <thead>
                <tr>
                    <th>Mascota</th>
                    <th>Paseador</th>
                    <th>Tiempo restante</th>
                    <th>Posición del paseador</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Firulais</td>
                    <td>Marcelo</td>
                    <td>23 minutos</td>
                    <td>
                        <a href="#" data-toggle="modal" data-target="#modalImage" data-title="¿Dónde está mi mascota?" data-image="mock-routing">
                            <img src="<c:url value="/images/mock-routing.jpg"/>" width="100" alt="ruta">
                        </a>
                    </td>
                    <td>
                        <button class="btn btn-success" type="button">Finalizar Paseo</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div>
            <h3>Finalizados</h3>
            <table class="table">
                <thead>
                <tr>
                    <th>Mascota</th>
                    <th>Paseador</th>
                    <th>Hora de inicio</th>
                    <th>Hora de fin</th>
                    <th>Estado</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Firulais</td>
                    <td>Marcelo</td>
                    <td>11-02-2021 12:00</td>
                    <td>11-02-2021 13:00</td>
                    <td>Pago</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="modal fade" id="modalImage" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel"></h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body text-center"></div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF" crossorigin="anonymous"></script>
<script src="<c:url value="/js/paseos-modal-img.js" />"></script>
</body>
</html>
