<%-- 
    Document   : login
    Created on : Dec 5, 2022, 12:20:38 AM
    Author     : syahir
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.time.LocalDate"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <c:if test="${param.secret_key != 'dn3@ZDt8UJ8l'}">
            <jsp:forward page="../login.jsp"></jsp:forward>
        </c:if>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin - Login</title>
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">
        <div class="d-flex justify-content-center">
            <div class="card text-center w-50">
                <div class="card-body p-4">
                    <h3 class="card-title mt-4 mb-5 text-primary">Admin Login</h3>
                    <form method="post" action="${pageContext.servletContext.contextPath}/auth_login.do">
                        <div class="form-floating mb-3">
                            <input type="email" class="form-control" id="email" name="email" placeholder="example@dentalcare.com">
                            <label for="email">Email Address<span class="text-danger">*</span></label>
                        </div>
                        <div class="form-floating mb-4">
                            <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                            <label for="password">Password<span class="text-danger">*</span></label>
                        </div>
                        <div class="d-grid mb-4 mx-auto">
                            <button class="btn btn-primary py-3 rounded-pill" type="submit">Log In</button>
                        </div>
                        <input type="hidden" name="user_type" value="08y*6M">
                    </form>
                </div>
            </div>
        </div>
        <div class="text-center my-5">
            <small>&copy; <%= LocalDate.now().getYear() %> Dental Care. All Right Reserved.</small>
        </div>
        
        <!-- Errors message -->
        <c:if test="${not empty errorMsgs}">
            <div class="d-flex justify-content-center">
                <div class="alert alert-danger alert-dismissible fade show w-50" role="alert">
                    <h5 class="alert-heading">Errors:</h5>
                    <ul>
                        <c:forEach var="message" items="${errorMsgs}">
                            <li>${message}</li>
                        </c:forEach>
                    </ul>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </div>
        </c:if>
        <!-- Success message -->
        <c:if test="${not empty successMsgs}">
            <div class="d-flex justify-content-center">
                <div class="alert alert-success alert-dismissible fade show w-50" role="alert">
                    <h5 class="alert-heading">Success:</h5>
                    <p>${successMsgs}</p>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </div>
        </c:if>
        
        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
