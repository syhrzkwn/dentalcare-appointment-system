<%-- 
    Document   : index
    Created on : Dec 12, 2022, 4:47:26 PM
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
        <title>Admin - Dentists</title>
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
                                        <li class="breadcrumb-item active" aria-current="page">Dentists</li>
                                    </ol>
                                </nav>
                            </div>
                        </div>
                        
                        <div class="row justify-content-center">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-between">
                                            <h4>Dentists</h4>
                                            <div>
                                                <a href="${pageContext.servletContext.contextPath}/admin/dentist/add.jsp" class="btn btn-success"><i class="bi bi-plus-lg"></i> Dentist</a>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="w-100 table">
                                                <thead class="table-light">
                                                    <tr>
                                                        <th class="text-center">#ID</th>
                                                        <th>Name</th>
                                                        <th>Phone</th>
                                                        <th>Email</th>
                                                        <th class="text-center">Status</th>
                                                        <th class="text-center">Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <sql:query var="results" dataSource="${myDatasource}">
                                                        SELECT * FROM APP.DENTISTS WHERE dentist_id != 0
                                                    </sql:query>
                                                    <c:choose>
                                                        <c:when test="${empty results.rows}">
                                                            <tr>
                                                                <td colspan="6" class="align-middle text-center py-3">There is no data</td>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach var = "result" items = "${results.rows}">
                                                                <tr>
                                                                    <td class="align-middle text-center">${result.dentist_id}</td>
                                                                    <td class="align-middle">Dr. ${result.dentist_firstname} ${result.dentist_lastname}</td>
                                                                    <td class="align-middle"><a href="tel:${result.dentist_phone}" class="link-primary">${result.dentist_phone}</a></td>
                                                                    <td class="align-middle"><a href="mailto:${result.dentist_email}" class="link-primary">${result.dentist_email}</a></td>
                                                                    <td class="align-middle text-center">
                                                                        <c:choose>
                                                                            <c:when test="${result.dentist_status eq 'Available'}">
                                                                                <span class="badge rounded-pill bg-success">${result.dentist_status}</span>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <span class="badge rounded-pill bg-danger">${result.dentist_status}</span>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </td>
                                                                    <td class="align-middle text-center">
                                                                        <div class="btn-group">
                                                                            <button type="button" class="btn" data-bs-toggle="dropdown" data-bs-display="static" aria-expanded="false">
                                                                                <i class="bi bi-three-dots"></i>
                                                                            </button>
                                                                            <ul class="dropdown-menu dropdown-menu-end">
                                                                                <li><a href="${pageContext.servletContext.contextPath}/admin/dentist/view.jsp?dentist_id=${result.dentist_id}" class="dropdown-item" type="button">View</a></li>
                                                                                <li><a href="${pageContext.servletContext.contextPath}/admin/dentist/edit.jsp?dentist_id=${result.dentist_id}" class="dropdown-item" type="button">Edit</a></li>
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
