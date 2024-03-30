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
                              action="${pageContext.request.contextPath}/vehicles/update">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="constructeur" class="col-sm-2 control-label">Marque</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="constructeur" name="constructeur"
                                               placeholder="Marque" value="${vehicle.constructeur}" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="model" class="col-sm-2 control-label">Model</label>

                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" id="model" name="model"
                                               placeholder="Modele" value="${vehicle.model}" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="nb_places" class="col-sm-2 control-label">Nombre de places</label>

                                    <div class="col-sm-10">
                                        <input type="number" class="form-control" id="nb_places" name="nb_places"
                                               placeholder="Nombre de places" value="${vehicle.nb_place}" required>
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Enregistrer les modifications
                                </button>
                            </div>
                            <label>
                                <input type="hidden" name="from_rents_create" value="${param.from_rents_create}">
                            </label>
                            <!-- /.box-footer -->
                            <input type="hidden" id="id_vehicle" name="id_vehicle" value="${param.id_vehicle}">
                        </form>
                        <% if (request.getAttribute("nbplaceError") != null) { %>
                        <span style="color: red;">*Le nombre de place doit être compris en 2 et 9</span>
                        <% } %>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
