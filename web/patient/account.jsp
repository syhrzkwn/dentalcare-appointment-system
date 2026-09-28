<%-- 
    Document   : account
    Created on : Dec 4, 2022, 3:33:59 PM
    Author     : syahir
--%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Head -->
        <%@ include file="/component/head.jsp" %>
        <title>Book Appointment - Dental Care</title>
    </head>
    <body>
        <!-- Navbar -->
        <%@ include file="/patient/component/navbar.jsp" %>
        
        <c:set var="id" value="${patient.getId()}"/>
        <sql:query var="results" dataSource="${myDatasource}">
            SELECT * FROM patients WHERE patient_id=?
            <sql:param value="${id}"/>
        </sql:query>
                            
        <main class="container py-5">
            <!-- Error & Success message -->
            <%@ include file="/admin/component/error_success_msg.jsp" %>
            
            <c:forEach var="result" items="${results.rows}">
                <form method="post" action="${pageContext.servletContext.contextPath}/patient_account_profile_update.do">
                    <div class="row g-2 mb-3">
                        <h2 class="text-new my-4">User Details</h2>
                        <div class="form-group col-md-6 mb-2">
                            <label for="firstname">First Name <span class=text-danger>*</span></label>
                            <input type="text" value="${result.patient_firstname}" class="form-control" id="firstname" name="firstname">
                        </div>
                        <div class="form-group col-md-6 mb-2">
                            <label for="lastname">Last Name <span class=text-danger>*</span></label>
                            <input type="text" value="${result.patient_lastname}" class="form-control" id="lastname" name="lastname">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="phone">Phone <span class=text-danger>*</span></label>
                            <input type="tel" value="${result.patient_phone}" class="form-control" id="phone" name="phone">
                        </div>
                        <input type="hidden" value="${result.patient_id}" name="id">
                    </div>
                    <div class="d-flex justify-content-end">
                        <button type="submit" class="btn btn-new-1 bg-new text-light py-3 px-5 rounded-pill">Update Profile</button>
                    </div>
                </form>
                    
                <form method="post" action="${pageContext.servletContext.contextPath}/patient_account_email_update.do">
                    <h2 class="text-new my-4">Update Email</h2>
                    <div class="form-group col-md-6 mb-3">
                        <label for="email">Email <span class=text-danger>*</span></label>
                        <input type="email" value="${result.patient_email}" class="form-control" id="email" name="email">
                    </div>
                    <input type="hidden" value="${result.patient_id}" name="id">
                    <div class="d-flex justify-content-end">
                        <button type="submit" class="btn btn-new-1 bg-new text-light py-3 px-5 rounded-pill">Update Email</button>
                    </div>
                </form>
                    
                <form method="post" action="${pageContext.servletContext.contextPath}/patient_account_password_update.do">
                    <h2 class="text-new my-4">Change Password</h2>
                    <input type="hidden" value="${result.patient_email}" name="email">
                    <div class="form-group col-md-6 mb-3">
                        <label for="current-password">Current Password <span class=text-danger>*</span></label>
                        <input type="password" class="form-control " id="current-password" name="current_password">
                    </div>
                    <div class="row g-2 mb-4">
                        <div class="form-group col-md-6 mb-3">
                            <label for="password">New Password <span class=text-danger>*</span></label>
                            <input type="password" class="form-control " id="password" name="new_password">
                        </div>
                        <div class="form-group col-md-6 mb-3">
                            <label for="password-confirm">Confirm New Password <span class=text-danger>*</span></label>
                            <input type="password" class="form-control" id="password-confirm" name="confirm_new_password">
                        </div>
                    </div>
                    <input type="hidden" value="${result.patient_id}" name="id">
                    <div class="d-flex justify-content-end">
                        <input type="hidden" value="form3" name="form">
                        <button type="submit" class="btn btn-new-1 bg-new text-light py-3 px-4 rounded-pill">Change Password</button>
                    </div>
                </form>
                    
                <form method="post" action="${pageContext.servletContext.contextPath}/patient_account_delete.do">
                    <h2 class="text-new my-4">Delete Account</h2>
                    <div class="form-group col-md-12 mb-3">
                        <p class="mb-0">We sad to see you go. Hope you can use our service again. Bye!</p>
                        <p>This action cannot be undone. This will permanently delete the patient account. Please type your email <strong>${result.patient_email}</strong> to confirm.</p>
                        <input type="text" class="form-control w-50 mb-4" name="email_for_delete">
                    </div>
                    <input type="hidden" value="${result.patient_id}" name="id">
                    <div class="d-flex justify-content-end mb-4">
                        <button type="submit" class="btn btn-outline-danger py-3 px-4 rounded-pill">Delete Account</button>
                    </div>
                </form>
            </c:forEach>
        </main>
        
        <!-- Footer -->
        <%@ include file="/component/footer.jsp" %>
        
        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
