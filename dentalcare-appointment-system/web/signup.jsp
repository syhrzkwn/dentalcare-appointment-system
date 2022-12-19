<%-- 
    Document   : signup
    Created on : Dec 4, 2022, 12:29:54 AM
    Author     : syahir
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up - Dental Care</title>
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
                            <h3 class="card-title mb-5 text-primary">Signup now to book your appointment today.</h3>
                            <form method="post" action="auth_register.do">
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="firstname" name="firstname" placeholder="Syahir">
                                    <label for="firstname">First Name <span class="text-danger">*</span></label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="text" class="form-control" id="lastname" name="lastname" placeholder="Zakwan">
                                    <label for="lastname">Last Name <span class="text-danger">*</span></label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="tel" class="form-control" id="phone" name="phone" placeholder="0123456789">
                                    <label for="phone">Phone <span class="text-danger">*</span></label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="email" class="form-control" id="email" name="email" placeholder="example@dentalcare.com">
                                    <label for="email">Email Address <span class="text-danger">*</span></label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                                    <label for="password">Password <span class="text-danger">*</span></label>
                                </div>
                                <div class="form-floating mb-4">
                                    <input type="password" class="form-control" id="confirm_password" name="confirm_password" placeholder="Password">
                                    <label for="confirm-password">Confirm Password <span class="text-danger">*</span></label>
                                </div>
                                <input type="hidden" name="user_type" value="$B9f861">
                                <div class="d-grid mx-auto mb-5">
                                    <button class="btn btn-primary py-3 rounded-pill" type="submit">Sign Up</button>
                                </div>
                                <div>
                                    <p>Already have an account? <a href="${pageContext.servletContext.contextPath}/login.jsp">Login</a></p>
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
                        </div>
                    </div>
                    <div class="col-md-6 d-flex align-items-center">
                        <div>
                            <img src="${pageContext.servletContext.contextPath}/images/signup.webp" class="img-fluid" alt="signup"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                        
        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
