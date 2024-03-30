<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
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
                Utilisateurs
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/users/create">Ajouter</a>
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
                                    <th>Nom</th>
                                    <th>Prenom</th>
                                    <th>Email</th>
                                    <th>Naissance</th>
                                    <th>Action</th>
                                </tr>
                                <c:forEach items="${users}" var="user" varStatus="loop">
                                    <tr>
                                        <td>${user.ID_client}</td>
                                        <td>${fn:toUpperCase(user.nom)}</td>
                                        <td>${user.prenom}</td>
                                        <td>${user.email}</td>
                                        <td>${user.naissance}</td>
                                        <td>
                                            <form id="deleteForm_${loop.index}" method="post">
                                                <a class="btn btn-primary"
                                                   href="${pageContext.request.contextPath}/users/details?client_id=${user.ID_client}">
                                                    <i class="fa fa-play"></i>
                                                </a>
                                                <a class="btn btn-success"
                                                   href="${pageContext.request.contextPath}/users/update?client_id=${user.ID_client}">
                                                    <i class="fa fa-edit"></i>
                                                </a>
                                                <input type="hidden" name="clientID" value="${user.ID_client}">
                                                <input type="hidden" name="nom" value="${user.nom}">
                                                <input type="hidden" name="prenom" value="${user.prenom}">
                                                <input type="hidden" id="deleteornot" name="deleteornot" value="false">
                                                <input type="hidden" name="action" value="">
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
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
<c:if test="${not empty requestScope.successMessage}">
    <div class="alert alert-success" role="alert">
            ${requestScope.successMessage}
    </div>
</c:if>
</body>
<script>
    function afficherAlerte(index) {
        var confirmation = confirm("La suppression de cette voiture va supprimer toutes les réservations associées.\nÊtes-vous sûr de vouloir continuer ?");
        if (confirmation) {
            document.getElementsByName("deleteornot")[index].value = "true";
            document.getElementsByName("action")[index].value = "delete_client";
            document.getElementById("deleteForm_" + index).submit();
            alert("Action confirmée!");
        } else {
            document.getElementsByName("action")[index].value = "dont_delete_client";
            document.getElementById("deleteForm_" + index).submit();
            alert("Action annulée.");
        }
    }
</script>
</html>