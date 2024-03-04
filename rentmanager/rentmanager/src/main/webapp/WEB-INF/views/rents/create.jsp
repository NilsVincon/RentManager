<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Votre titre</title>
    <%@include file="/WEB-INF/views/common/head.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                Reservations
            </h1>
        </section>

        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <form class="form-horizontal" method="post" action="./create">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="vehicle" class="col-sm-2 control-label">Voiture</label>
                                    <div class="col-sm-10">
                                        <select class="form-control" id="vehicle" name="vehicle">
                                            <c:forEach items="${rentsvehicles}" var="car" varStatus="loop">
                                                <option value="${car.ID_vehicle}">${car.constructeur} ${car.model}</option>
                                            </c:forEach>
                                            <option>
                                        </select>
                                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/vehicles/create?from_rents_create=${true}">
                                            Ajouter un vehicule
                                        </a>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="client" class="col-sm-2 control-label">Client</label>
                                    <div class="col-sm-10">
                                        <select class="form-control" id="client" name="client">
                                            <c:forEach items="${rentsusers}" var="user" varStatus="loop">
                                                <option value="${user.ID_client}">${user.nom} ${user.prenom}</option>
                                            </c:forEach>
                                        </select>

                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="debut" class="col-sm-2 control-label">Date de debut</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="debut" name="debut" required data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="fin" class="col-sm-2 control-label">Date de fin</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="fin" name="fin" required data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
                                    </div>
                                </div>
                            </div>
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Ajouter</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<script>
    function selectNewVehicle() {
        var newVehicleName = document.getElementById("new_Vehicle_name").value;

        if (newVehicleName !== "") {
            var vehicleSelect = document.getElementById('vehicle');
            var options = vehicleSelect.options;
            var lastOption = options[options.length - 2];
            lastOption.selected = true;
        }
    }

    $(function () {
        selectNewVehicle(); // Assurez-vous que la fonction est appelée lors du chargement de la page
        $('[data-mask]').inputmask();
    });
</script>
<label>
    <input type='hidden' id="new_Vehicle_name" value="${param.newVehicle_name}">
</label>
</body>
</html>
