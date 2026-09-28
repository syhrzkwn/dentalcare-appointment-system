<%-- 
    Document   : view
    Created on : Dec 19, 2022, 3:08:08 AM
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
        <title>Admin - Staffs</title>
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

                        <c:set var="id" value="${param.staff_id}"/>
                        <sql:query var="results" dataSource="${myDatasource}">
                            SELECT * FROM staffs WHERE staff_id=?
                            <sql:param value="${id}"/>
                        </sql:query>
                            
                        <!-- Breadcrumb -->
                        <div class="row">
                            <div class="col">
                                <nav aria-label="breadcrumb" class="bg-light rounded-3 p-3 mb-4">
                                    <ol class="breadcrumb mb-0">
                                        <li class="breadcrumb-item"><a href="${pageContext.servletContext.contextPath}/admin/dashboard.jsp" class="link-primary">Dashboard</a></li>
                                        <li class="breadcrumb-item"><a href="${pageContext.servletContext.contextPath}/admin/staff/index.jsp" class="link-primary">Staffs</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">${id}</li>
                                    </ol>
                                </nav>
                            </div>
                        </div>
                                    
                        <div class="row justify-content-center">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-between">
                                            <h4>View Staff</h4>
                                            <div>
                                                <a href="${pageContext.servletContext.contextPath}/admin/staff/index.jsp" class="btn btn-secondary">Back</a>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-body p-5">
                                        <c:forEach var="result" items="${results.rows}">
                                            <div class="row g-2">
                                                <div class="form-group col-md-6">
                                                    <label for="id">#ID</label>
                                                    <input type="number" value="${result.staff_id}" class="form-control" id="id" name="id" disabled>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="status">Status</label>
                                                    <input type="text" value="${result.staff_status}" class="form-control" id="status" name="status" disabled>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="firstname">First Name</label>
                                                    <input type="text" value="${result.staff_firstname}" class="form-control" id="firstname" name="firstname" disabled>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="lastname">Last Name</label>
                                                    <input type="text" value="${result.staff_lastname}" class="form-control" id="lastname" name="lastname" disabled>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="phone">Phone</label>
                                                    <input type="tel" value="${result.staff_phone}" class="form-control" id="phone" name="phone" disabled>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="email">Email</label>
                                                    <input type="email" value="${result.staff_email}" class="form-control" id="email" name="email" disabled>
                                                </div>
                                            </div>
                                        </c:forEach>
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
