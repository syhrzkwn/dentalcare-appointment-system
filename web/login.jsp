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
        <!-- Head -->
        <%@ include file="/component/head.jsp" %>
        <title>Log In - Dental Care</title>
    </head>
        
    <body>
        <!-- Navbar -->
        <%@ include file="/component/navbar.jsp" %>
        
        <main class="container py-5">
            <div class="card">
                <div class="card-body m-4">
                    <div class="row">
                        <div class="col-md-6 d-flex align-items-center">
                            <div>
                                <h3 class="card-title mb-5 text-new">Login now to book your appointment today.</h3>
                                <form method="post" action="auth_login.do">
                                    <div class="form-floating mb-3">
                                        <input type="email" class="form-control" id="email" name="email" placeholder="example@dentalcare.com">
                                        <label for="email">Email Address <span class="text-danger">*</span></label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                                        <label for="password">Password <span class="text-danger">*</span></label>
                                    </div>
                                    <div class="mb-3">
                                        <!-- Button trigger modal -->
                                        <button type="button" class="btn link-new btn-link" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                                            Forgot Password?
                                        </button>

                                        <!-- Modal -->
                                        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                          <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <h5 class="modal-title" id="staticBackdropLabel">Forgot your password?</h5>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <p>Please contact our Admin at <a href="mailto:admin@dentalcare.com" class="link-new">admin@dentalcare.com</a> to reset your password. We are truly sorry for any inconvenience.<p>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Cancel</button>
                                                </div>
                                            </div>
                                          </div>
                                        </div>
                                    </div>
                                    <div class="d-grid mx-auto mb-5">
                                        <button class="btn btn-new-1 bg-new text-light py-3 rounded-pill" type="submit">Log In</button>
                                    </div>
                                    <input type="hidden" name="user_type" value="$B9f86">
                                    <div>
                                        <p>Don't have an account to book your first appointment? <a href="${pageContext.servletContext.contextPath}/signup.jsp" class="link-new">Signup</a> now!</p>
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
        </main>
        
        <!-- Footer -->
        <%@ include file="/component/footer.jsp" %>
        
        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
