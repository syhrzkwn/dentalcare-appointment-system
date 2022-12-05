<%-- 
    Document   : appointment
    Created on : Dec 4, 2022, 3:33:35 PM
    Author     : syahir
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:if test="${empty sessionScope.patient}">
            <jsp:forward page="../login.jsp"></jsp:forward>
        </c:if>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Appointment - Dental Care</title>
        <link rel="icon" href="favicon.ico">
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container">
        <h1>Hi, ${patient.getFirstName()} ${patient.getLastName()}</h1>
        <a href="auth_logout.do">Log Out</a>
        
        <!-- Success message -->
        <c:if test="${not empty successMsgs}">
            <div class="position-relative">
                <div class="position-absolute top-0 end-0 translate-middle">
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

