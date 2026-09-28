<%-- 
    Document   : edit
    Created on : Dec 12, 2022, 9:18:09 PM
    Author     : syahir
--%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Head -->
        <%@ include file="../admin/component/head.jsp" %>
        <title>Admin - Account</title>
    </head>
    <body style="background-color: #eee;">
        <div class="container-fluid">
            <div class="row flex-nowrap">
                
                <!-- Sidebar -->
                <%@ include file="../admin/component/sidebar.jsp" %>

                <div class="col pb-3">

                    <!-- Navbar -->
                    <%@ include file="../admin/component/navbar.jsp" %>

                    <!-- Main -->
                    <main class="container py-4">
                        <!-- Error & Success message -->
                        <%@ include file="../admin/component/error_success_msg.jsp" %>

                        <c:set var="id" value="${staff.getId()}"/>
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
                                        <li class="breadcrumb-item active">Account</li>
                                        <c:forEach var="result" items="${results.rows}">
                                            <li class="breadcrumb-item active" aria-current="page">${result.staff_firstname}</li>
                                        </c:forEach>
                                    </ol>
                                </nav>
                            </div>
                        </div>
                        
                        <div class="row justify-content-center">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-between">
                                            <h4>Account</h4>
                                            <div>
                                                <a href="${pageContext.servletContext.contextPath}/admin/dashboard.jsp" class="btn btn-secondary">Back</a>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-body p-5">
                                        <c:forEach var="result" items="${results.rows}">
                                            <form method="post" action="${pageContext.servletContext.contextPath}/account_profile_update.do">
                                                <div class="row g-2 mb-4">
                                                    <h5 class="mb-4">Profile</h5>
                                                    <div class="form-group col-md-6 mb-2">
                                                        <label for="firstname">First Name <span class=text-danger>*</span></label>
                                                        <input type="text" value="${result.staff_firstname}" class="form-control" id="firstname" name="firstname">
                                                    </div>
                                                    <div class="form-group col-md-6 mb-2">
                                                        <label for="lastname">Last Name <span class=text-danger>*</span></label>
                                                        <input type="text" value="${result.staff_lastname}" class="form-control" id="lastname" name="lastname">
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <label for="phone">Phone <span class=text-danger>*</span></label>
                                                        <input type="tel" value="${result.staff_phone}" class="form-control" id="phone" name="phone">
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <label for="status">Status <span class=text-danger>*</span></label>
                                                        <select class="form-select" id="status" name="status">
                                                            <option value="${result.staff_status}" selected>${result.staff_status}</option>
                                                            <option disabled>───────</option>
                                                            <option value="Available">Available</option>
                                                            <option value="On Leave">On Leave</option>
                                                        </select>
                                                    </div>
                                                    <input type="hidden" value="${staff.getId()}" name="id">
                                                </div>
                                                <button type="submit" class="btn btn-primary">Update Profile</button>
                                            </form>
                                            <hr class="my-5">
                                            <form method="post" action="${pageContext.servletContext.contextPath}/account_email_update.do">
                                                <h5 class="mb-4">Update Email</h5>
                                                <div class="form-group col-md-6 mb-4">
                                                    <label for="email">Email <span class=text-danger>*</span></label>
                                                    <input type="email" value="${result.staff_email}" class="form-control" id="email" name="email">
                                                </div>
                                                <input type="hidden" value="${staff.getId()}" name="id">
                                                <button type="submit" class="btn btn-primary">Update Email</button>
                                            </form>
                                        </c:forEach>
                                        <hr class="my-5">
                                        <form method="post" action="${pageContext.servletContext.contextPath}/account_password_update.do">
                                            <c:forEach var="result" items="${results.rows}">
                                                <h5 class="mb-4">Change Password</h5>
                                                <input type="hidden" value="${result.staff_email}" class="form-control" id="email" name="email">
                                                <div class="form-group col-md-6 mb-3">
                                                    <label for="current-password">Current Password <span class=text-danger>*</span></label>
                                                    <input type="password" class="form-control " id="current-password" name="current_password">
                                                </div>
                                                <div class="form-group col-md-6 mb-3">
                                                    <label for="password">New Password <span class=text-danger>*</span></label>
                                                    <input type="password" class="form-control " id="password" name="new_password">
                                                </div>
                                                <div class="form-group col-md-6 mb-4">
                                                    <label for="password-confirm">New Password Confirmation <span class=text-danger>*</span></label>
                                                    <input type="password" class="form-control" id="password-confirm" name="confirm_new_password">
                                                </div>
                                                <input type="hidden" value="${staff.getId()}" name="id">
                                            </c:forEach>
                                            <button type="submit" class="btn btn-primary mb-4">Change Password</button>
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

