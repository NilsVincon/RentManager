<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->
                        <form class="form-horizontal" method="post" action="./create">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="last_name" class="col-sm-2 control-label">Nom</label>

                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="last_name" name="last_name"
                                               value="${nom}" required
                                               placeholder="Nom">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="first_name" class="col-sm-2 control-label">Prenom</label>

                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="first_name" name="first_name"
                                               value="${prenom}"
                                               required
                                               placeholder="Prenom">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="col-sm-2 control-label">Email</label>

                                    <div class="col-sm-10">
                                        <input type="email" class="form-control" id="email" name="email" value="${mail}"
                                               required
                                               placeholder="Email">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="naissance" class="col-sm-2 control-label">Naissance</label>
                                    <div class="col-sm-10">
                                        <%-- Utilisez DateTimeFormatter pour formater la date --%>
                                        <input type="text" class="form-control" id="naissance" name="naissance"
                                               value="<%= request.getAttribute("naissance") != null ? ((LocalDate)request.getAttribute("naissance")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "" %>"
                                               required data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
                                    </div>
                                </div>
                            </div>
                            <% if (request.getAttribute("ageError") != null) { %>
                            <span style="color: red;">Le client doit avoir 18 ans ou plus</span>
                            <% } %>
                            <% if (request.getAttribute("mailError") != null) { %>
                            <span style="color: red;">Cet email est deja associé à un client</span>
                            <% } %>
                            <% if (request.getAttribute("nameError") != null) { %>
                            <span style="color: red;">Le nom et le prenom doivent faire au moin 3 caractères</span>
                            <% } %>

                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Ajouter</button>
                            </div>
                            <label>
                                <input type="hidden" name="from_rents_create" value="${param.from_rents_create}">
                            </label>
                            <!-- /.box-footer -->
                        </form>
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
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugins/input-mask/jquery.inputmask.extensions.js"></script>
<script>
    $(function () {
        $('[data-mask]').inputmask()
    });
</script>
</body>
</html>