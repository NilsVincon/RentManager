<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                Voitures
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <!-- form start -->
                        <!-- Le  type de methode http qui sera appel� lors de action submit du formulaire -->
                        <!-- est d�crit an l'attribut "method" de la balise forme -->
                        <!-- action indique � quel "cible" sera envoyr la requ�te, ici notre Servlet qui sera bind sur -->
                        <!-- /vehicles/create -->
                        <form class="form-horizontal" method="post"
                              action="${pageContext.request.contextPath}/vehicles/create">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="constructeur" class="col-sm-2 control-label">Marque</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="constructeur" name="constructeur"
                                               value="${constructeurencours}" placeholder="Marque" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="model" class="col-sm-2 control-label">Model</label>

                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="model" name="model"
                                               placeholder="Modele" value="${modelencours}" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="nb_places" class="col-sm-2 control-label">Nombre de places</label>

                                    <div class="col-sm-10">
                                        <input type="number" class="form-control" id="nb_places" name="nb_places"
                                               placeholder="Nombre de places" required>
                                    </div>
                                </div>
                                <!--
                                <div class="form-group">
                                    <label for="owner" class="col-sm-2 control-label">Propriétaire</label>

                                    <div class="col-sm-10">
                                        <select class="form-control" id="owner" name="owner">
                                            <option value="1">John Doe</option>
                                            <option value="2">Jane Doe</option>
                                        </select>
                                    </div>
                                </div>
                                -->
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Ajouter</button>
                            </div>
                            <label>
                                <input type="hidden" name="from_rents_create" value="${param.from_rents_create}">
                            </label>
                            <!-- /.box-footer -->
                        </form>
                        <% if (request.getAttribute("nbplaceError") != null) { %>
                        <span style="color: red;">*Le nombre de place doit être compris en 2 et 9</span>
                        <% } %>
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
</body>
</html>