<%-- 
    Document   : edit
    Created on : Dec 19, 2022, 11:50:06 PM
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
        <title>Admin - Dentists</title>
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
                        
                        <c:set var="id" value="${param.dentist_id}"/>
                        <sql:query var="results" dataSource="${myDatasource}">
                            SELECT * FROM APP.DENTISTS WHERE dentist_id=? AND dentist_id != 0
                            <sql:param value="${id}"/>
                        </sql:query>
                            
                        <!-- Breadcrumb -->
                        <div class="row">
                            <div class="col">
                                <nav aria-label="breadcrumb" class="bg-light rounded-3 p-3 mb-4">
                                    <ol class="breadcrumb mb-0">
                                        <li class="breadcrumb-item"><a href="${pageContext.servletContext.contextPath}/admin/dashboard.jsp" class="link-primary">Dashboard</a></li>
                                        <li class="breadcrumb-item"><a href="${pageContext.servletContext.contextPath}/admin/dentist/index.jsp" class="link-primary">Dentists</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">Edit</li>
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
                                            <h4>Edit Patient</h4>
                                            <div>
                                                <a href="${pageContext.servletContext.contextPath}/admin/dentist/index.jsp" class="btn btn-secondary">Back</a>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-body p-5">
                                        <c:forEach var="result" items="${results.rows}">
                                            <form method="post" action="${pageContext.servletContext.contextPath}/dentist_update.do">
                                                <div class="row g-2 mb-4">
                                                    <h5 class="mb-4">Profile</h5>
                                                    <div class="form-group col-md-6 mb-2">
                                                        <label for="firstname">First Name <span class=text-danger>*</span></label>
                                                        <input type="text" value="${result.dentist_firstname}" class="form-control" id="firstname" name="firstname">
                                                    </div>
                                                    <div class="form-group col-md-6 mb-2">
                                                        <label for="lastname">Last Name <span class=text-danger>*</span></label>
                                                        <input type="text" value="${result.dentist_lastname}" class="form-control" id="lastname" name="lastname">
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <label for="phone">Phone <span class=text-danger>*</span></label>
                                                        <input type="tel" value="${result.dentist_phone}" class="form-control" id="phone" name="phone">
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <label for="status">Status <span class=text-danger>*</span></label>
                                                        <select class="form-select" id="status" name="status">
                                                            <option value="${result.dentist_status}" selected>${result.dentist_status}</option>
                                                            <option disabled>───────</option>
                                                            <option value="Available">Available</option>
                                                            <option value="On Leave">On Leave</option>
                                                        </select>
                                                    </div>
                                                    <input type="hidden" value="form1" name="form">
                                                    <input type="hidden" value="${result.dentist_id}" name="id">
                                                </div>
                                                <button type="submit" class="btn btn-primary">Update Profile</button>
                                            </form>
                                            <hr class="my-5">
                                            <form method="post" action="${pageContext.servletContext.contextPath}/dentist_update.do">
                                                <h5 class="mb-4">Update Email</h5>
                                                <div class="form-group col-md-6 mb-4">
                                                    <label for="email">Email <span class=text-danger>*</span></label>
                                                    <input type="email" value="${result.dentist_email}" class="form-control" id="email" name="email">
                                                </div>
                                                <input type="hidden" value="form2" name="form">
                                                <input type="hidden" value="${result.dentist_id}" name="id">
                                                <button type="submit" class="btn btn-primary">Update Email</button>
                                            </form>
                                           
                                        </c:forEach>
                                        <hr class="my-5">
                                        <form method="post" action="${pageContext.servletContext.contextPath}/dentist_update.do">
                                            <c:forEach var="result" items="${results.rows}">
                                                <h5 class="mb-4">Change Password</h5>
                                                <p>Please notify the dentist once you has change their password.<p>
                                                <div class="form-group col-md-6 mb-3">
                                                    <label for="password">New Password <span class=text-danger>*</span></label>
                                                    <input type="password" class="form-control " id="password" name="new_password">
                                                </div>
                                                <div class="form-group col-md-6 mb-4">
                                                    <label for="password-confirm">New Password Confirmation <span class=text-danger>*</span></label>
                                                    <input type="password" class="form-control" id="password-confirm" name="confirm_new_password">
                                                </div>
                                                <input type="hidden" value="form3" name="form">
                                                <input type="hidden" value="${result.dentist_id}" name="id">
                                                <button type="submit" class="btn btn-primary">Change Password</button>
                                            </c:forEach>
                                        </form>
                                        <hr class="my-5">
                                        <form method="post" action="${pageContext.servletContext.contextPath}/dentist_update.do">
                                            <c:forEach var="result" items="${results.rows}">
                                                <h5 class="mb-4">Danger Zone</h5>
                                                <div class="form-group col-md-12 mb-3">
                                                    <p>This action cannot be undone. This will permanently delete the dentist account. Please type <strong>${result.dentist_email}</strong> to confirm.</p>
                                                    <input type="text" class="form-control w-50 mb-4" name="email_for_delete">
                                                </div>
                                                <input type="hidden" value="form4" name="form">
                                                <input type="hidden" value="${result.dentist_id}" name="id">
                                                <button type="submit" class="btn btn-outline-danger">Delete Account</button>
                                            </c:forEach>
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
