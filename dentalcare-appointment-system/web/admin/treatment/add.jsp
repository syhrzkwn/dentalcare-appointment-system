<%-- 
    Document   : add
    Created on : Dec 20, 2022, 12:23:43 AM
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
                                        <li class="breadcrumb-item"><a href="${pageContext.servletContext.contextPath}/admin/treatment/index.jsp" class="link-primary">Treatments</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">Add</li>
                                    </ol>
                                </nav>
                            </div>
                        </div>
                                    
                        <div class="row justify-content-center">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-between">
                                            <h4>Add Treatment</h4>
                                            <div>
                                                <a href="${pageContext.servletContext.contextPath}/admin/treatment/index.jsp" class="btn btn-secondary">Back</a>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-body p-5">
                                        <form method="post" action="${pageContext.servletContext.contextPath}/treatment_add.do">
                                            <div class="form-group col-md-6 mb-3">
                                                <label for="title">Title <span class="text-danger">*</span></label>
                                                <input type="text" class="form-control" id="title" name="title">
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label for="desc">Description <span class="text-danger">*</span></label>
                                                <textarea class="form-control" id="desc" rows="3" name="desc"></textarea>
                                            </div>
                                            <div class="form-group col-md-6 mt-4">
                                                <button type="submit" class="btn btn-primary">Add</button>
                                                <a href="${pageContext.servletContext.contextPath}/admin/treatment/index.jsp" class="btn btn-link link-danger">Cancel</a>
                                            </div>
                                        </form>
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
