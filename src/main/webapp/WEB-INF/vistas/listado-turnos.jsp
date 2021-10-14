<%@ taglib prefix="th" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file = "partial/header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Veterinaria</title>
    </head>
    <body>
        <div class="row d-flex text-center mt-4 mb-4">
            <h4><span>Turnos Disponibles</span></h4>
        </div>

        <c:if test="${empty turnos}">
            <div class="text-center mt-5 mb-5 p-5">
                <h4><span>No tenés turnos</span></h4>
                <p>Para reservar presiona en "Reservar Turno"</p>
                <br>
            </div>
        </c:if>

        <form:form action="listado-turnos" method="post" modelAttribute="datosTurno">
            <div class="m-3">
                <label class="text-dark">Seleccione fecha para un turno:</label><br>
                <form:input type="date" id="fecha" value="${datosTurno.fechaDesde}" min="${datosTurno.fechaDesde}" max="${datosTurno.fechaHasta}" path="fecha"/>
                <button class="btn btn-primary" type="submit">Buscar</button>
            </div>
        </form:form>

        <c:forEach var="turno" items="${turnos}">
            <div class="row container-fluid d-flex align-items-center mt-3">
                <div class="card-turno d-flex col-8" >
                    <img
                            src="<c:url value="/images/pelu-1.jpeg"/>"
                            class="img-fluid"
                            alt="..."
                            height="200px"
                            width="200px"
                    />
                    <div class="card-body">
                        <h5 class="card-title">Fecha: <fmt:formatDate value="${turno.fecha}" pattern="dd MMMM" /> ${turno.hora} hs</h5>
<%--                        <button class="btn" style="background-color: #a4ebf3" href="#">Reservar</button>--%>
                    </div>
                </div>

                <div class="col-4 text-right">
                    <h3 class="card-title">$${turno.precio}</h3>
                </div>
            </div><hr>
        </c:forEach>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
        <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
        <%@ include file = "partial/footer.jsp" %>
    </body>
</html>
