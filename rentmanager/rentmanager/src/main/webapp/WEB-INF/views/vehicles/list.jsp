<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp" %>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Voitures
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/vehicles/create">Ajouter</a>
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body no-padding">
                            <table class="table table-striped">
                                <tr>
                                    <th style="width: 10px">#</th>
                                    <th>Marque</th>
                                    <th>Modele</th>
                                    <th>Nombre de places</th>
                                    <!--<th>Propriétaire</th>-->
                                    <th>Action</th>
                                </tr>
                                <!-- Utilisation de la boucle forEach -->
                                <c:forEach items="${vehicles}" var="vehicle" varStatus="loop">
                                    <tr>
                                        <td>${vehicle.ID_vehicle}</td>
                                        <td>${vehicle.constructeur}</td>
                                        <td>${vehicle.model}</td>
                                        <td>${vehicle.nb_place}</td>
                                        <td>
                                            <form id="deleteForm_${loop.index}" method="post">
                                                <a class="btn btn-success "
                                                   href="${pageContext.request.contextPath}/vehicles/update?id_vehicle=${vehicle.ID_vehicle}">
                                                    <i class="fa fa-edit"></i>
                                                </a>
                                                <input type="hidden" name="vehicleId" value="${vehicle.ID_vehicle}">
                                                <input type="hidden" name="constructeur"
                                                       value="${vehicle.constructeur}">
                                                <input type="hidden" name="model" value="${vehicle.model}">
                                                <input type="hidden" id="deleteornot" name="deleteornot" value="false">
                                                <button type="button" class="btn btn-danger" onclick="afficherAlerte(${loop.index})" title="Supprimer">
                                                    <i class="fa fa-trash"></i>
                                                </button>
                                            </form>

                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
<script>
    function afficherAlerte(index) {
        var confirmation = confirm("La suppression de cette voiture va supprimer toutes les réservations associées.\nÊtes-vous sûr de vouloir continuer ?");
        if (confirmation) {
            document.getElementsByName("deleteornot")[index].value = "true";
            document.getElementById("deleteForm_" + index).submit();
            alert("Action confirmée!");
        } else {
            document.getElementById("deleteForm_" + index).submit();
            alert("Action annulée.");
        }
    }
</script>
</html>
