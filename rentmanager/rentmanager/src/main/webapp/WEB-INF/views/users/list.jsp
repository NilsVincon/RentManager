<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                                            <form method="post">
                                                <a class="btn btn-primary"
                                                   href="${pageContext.request.contextPath}/users/details?client_id=${user.ID_client}">
                                                    <i class="fa fa-play"></i>
                                                </a>
                                                <button type="submit" name="action" value="modif_client" class="btn btn-success">
                                                    <i class="fa fa-edit"></i>
                                                </button>

                                                <input type="hidden" name="clientID" value="${user.ID_client}">
                                                <input type="hidden" name="nom" value="${user.nom}">
                                                <input type="hidden" name="prenom" value="${user.prenom}">
                                                <button type="submit" name="action" value="delete_client" class="btn btn-danger">
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
</html>
