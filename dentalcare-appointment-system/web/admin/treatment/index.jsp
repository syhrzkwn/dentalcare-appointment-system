<%-- 
    Document   : index
    Created on : Dec 20, 2022, 12:13:36 AM
    Author     : syahir
--%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Head -->
        <%@ include file="../component/head.jsp" %>
        <title>Admin - Treatments</title>
    </head>
    <body style="background-color: #eee;">
        <div class="container-fluid">
            <div class="row flex-nowrap">
                
                <!-- Sidebar -->
                <%@ include file="../component/sidebar.jsp" %>

                <div class="col pb-3">

                    <!-- Navbar -->
                    <%@ include file="../component/navbar.jsp" %>

                    <!-- Main -->
                    <main class="container py-4">
                        <!-- Error & Success message -->
                        <%@ include file="../component/error_success_msg.jsp" %>

                        <!-- Breadcrumb -->
                        <div class="row">
                            <div class="col">
                                <nav aria-label="breadcrumb" class="bg-light rounded-3 p-3 mb-4">
                                    <ol class="breadcrumb mb-0">
                                        <li class="breadcrumb-item"><a href="${pageContext.servletContext.contextPath}/admin/dashboard.jsp" class="link-primary">Dashboard</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">Treatments</li>
                                    </ol>
                                </nav>
                            </div>
                        </div>
                        
                        <div class="row justify-content-center">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-between">
                                            <h4>Treatments</h4>
                                            <div>
                                                <a href="${pageContext.servletContext.contextPath}/admin/treatment/add.jsp" class="btn btn-success"><i class="bi bi-plus-lg"></i> Treatment</a>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="w-100 table">
                                                <thead class="table-light">
                                                    <tr>
                                                        <th class="text-center">#ID</th>
                                                        <th>Title</th>
                                                        <th>Description</th>
                                                        <th class="text-center">Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <sql:query var="results" dataSource="${myDatasource}">
                                                        SELECT * FROM APP.TREATMENTS
                                                    </sql:query>
                                                    <c:choose>
                                                        <c:when test="${empty results.rows}">
                                                            <tr>
                                                                <td colspan="4" class="align-middle text-center py-3">There is no data</td>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach var = "result" items = "${results.rows}">
                                                                <tr>
                                                                    <td class="align-middle text-center">${result.treat_id}</td>
                                                                    <td class="align-middle">${result.treat_title}</td>
                                                                    <td class="align-middle">${result.treat_desc}</td>
                                                                    <td class="align-middle text-center">
                                                                        <div class="btn-group">
                                                                            <button type="button" class="btn" data-bs-toggle="dropdown" data-bs-display="static" aria-expanded="false">
                                                                                <i class="bi bi-three-dots"></i>
                                                                            </button>
                                                                            <ul class="dropdown-menu dropdown-menu-end">
                                                                                <li><a href="${pageContext.servletContext.contextPath}/admin/treatment/view.jsp?treat_id=${result.treat_id}" class="dropdown-item" type="button">View</a></li>
                                                                                <li><a href="${pageContext.servletContext.contextPath}/admin/treatment/edit.jsp?treat_id=${result.treat_id}" class="dropdown-item" type="button">Edit</a></li>
                                                                                <li><hr class="dropdown-divider"></li>
                                                                                <li>
                                                                                    <form method="post" action="${pageContext.servletContext.contextPath}/treatment_delete.do">
                                                                                        <input type="hidden" value="${result.treat_id}" name="id">
                                                                                        <button type="submit" class="btn text-danger dropdown-item" data-bs-toggle="tooltip" data-bs-placement="left" title="This process cannot be undone.">Delete</button>
                                                                                    </form>
                                                                                </li>
                                                                            </ul>
                                                                        </div>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </main>
                </div>
            </div>
        </div>
                        
        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

