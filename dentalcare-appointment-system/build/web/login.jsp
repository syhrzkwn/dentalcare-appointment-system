<%-- 
    Document   : login
    Created on : Dec 4, 2022, 2:47:39 PM
    Author     : syahir
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log In - Dental Care</title>
        <link rel="icon" href="favicon.ico">
        <!-- Bootstrap 5 CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container py-5">
        <div class="card">
            <div class="card-body m-4">
                <div class="row">
                    <div class="col-md-6 d-flex align-items-center">
                        <div>
                            <h3 class="card-title mb-5 text-primary">Login now to book your appointment today.</h3>
                            <form method="post" action="auth_login.do">
                                <div class="form-floating mb-3">
                                    <input type="email" class="form-control" id="email" name="email" placeholder="example@dentalcare.com">
                                    <label for="email">Email Address<span class="text-danger">*</span></label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                                    <label for="password">Password<span class="text-danger">*</span></label>
                                </div>
                                <div class="d-grid mx-auto mb-5">
                                    <button class="btn btn-primary py-3 rounded-pill" type="submit">Log In</button>
                                </div>
                                <input type="hidden" name="role" value="Patient">
                                <div>
                                    <p>Don't have an account to book your first appointment? <a href="signup.jsp">Signup</a> now!</p>
                                </div>
                            </form>
                            <!-- Errors message -->
                            <c:if test="${not empty errorMsgs}">
                                <div class="alert alert-danger alert-dismissible fade show mt-5" role="alert">
                                    <h5 class="alert-heading">Errors:</h5>
                                    <ul>
                                        <c:forEach var="message" items="${errorMsgs}">
                                            <li>${message}</li>
                                        </c:forEach>
                                    </ul>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>
                            <!-- Success message -->
                            <c:if test="${not empty successMsgs}">
                                <div class="alert alert-success alert-dismissible fade show mt-5" role="alert">
                                    <h5 class="alert-heading">Success:</h5>
                                    <p>${successMsgs}</p>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div class="col-md-6 d-flex align-items-center">
                        <div>
                            <img src="${pageContext.servletContext.contextPath}/images/login.webp" class="img-fluid" alt="login"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
