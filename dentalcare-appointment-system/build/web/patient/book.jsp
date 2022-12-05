<%-- 
    Document   : book
    Created on : Dec 4, 2022, 6:57:32 PM
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
        <title>Book Appointment - Dental Care</title>
        <link rel="icon" href="favicon.ico">
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container">
        <h1>Hello World!</h1>
        
        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
