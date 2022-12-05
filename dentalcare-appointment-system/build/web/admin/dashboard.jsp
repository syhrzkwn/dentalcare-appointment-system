<%-- 
    Document   : dashboard
    Created on : Dec 5, 2022, 12:17:25 AM
    Author     : syahir
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:if test="${empty sessionScope.admin}">
            <jsp:forward page="../login.jsp"></jsp:forward>
        </c:if>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin - Dashboard</title>
                <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container">
        <h1>Dashboard</h1>
        <a href="auth_logout.do">Log Out</a>
        
        <!-- Success message -->
        <c:if test="${not empty successMsgs}">
            <div class="position-relative">
                <div class="position-fixed top-50 start-50 translate-middle">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <h5 class="alert-heading">Success:</h5>
                        <p>${successMsgs}</p>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </div>
            </div>
        </c:if>
        
        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
