<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Votre titre</title>
    <%@include file="/WEB-INF/views/common/head.jsp" %>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                Reservations
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/rents/create">Ajouter</a>
            </h1>
        </section>

        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body no-padding">
                            <table class="table table-striped">
                                <tr>
                                    <th style="width: 10px">#</th>
                                    <th>Voiture</th>
                                    <th>Client</th>
                                    <th>Debut</th>
                                    <th>Fin</th>
                                    <th>Action</th>
                                </tr>
                                <c:forEach items="${rents}" var="resa" varStatus="loop">
                                    <tr>
                                        <td>${resa.ID_reservation}</td>
                                        <td>${resa.clientName}</td>
                                        <td>${resa.vehicleName}</td>
                                        <td>${resa.debut}</td>
                                        <td>${resa.fin}</td>
                                        <td>
                                            <form method="post">
                                                <a class="btn btn-success disabled" href="#">
                                                    <i class="fa fa-edit"></i>
                                                </a>
                                                <input type="hidden" name="reservationId"
                                                       value="${resa.ID_reservation}">
                                                <button type="submit" class="btn btn-danger">
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
<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
