<%-- 
    Document   : add
    Created on : Dec 22, 2022, 10:14:54 AM
    Author     : syahir
--%>
<%@page import="java.util.Date"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Date date = new java.util.Date(); %>
<!DOCTYPE html>
<html>
    <head>
        <!-- Head -->
        <%@ include file="../component/head.jsp" %>
        <title>Admin - Appointments</title>
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
                            
                        <!-- Breadcrumb -->
                        <div class="row">
                            <div class="col">
                                <nav aria-label="breadcrumb" class="bg-light rounded-3 p-3 mb-4">
                                    <ol class="breadcrumb mb-0">
                                        <li class="breadcrumb-item"><a href="${pageContext.servletContext.contextPath}/admin/dashboard.jsp" class="link-primary">Dashboard</a></li>
                                        <li class="breadcrumb-item"><a href="${pageContext.servletContext.contextPath}/admin/appointment/index.jsp" class="link-primary">Appointments</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">Add</li>
                                    </ol>
                                </nav>
                            </div>
                        </div>
                                    
                        <div class="row justify-content-center">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-between">
                                            <h4>Add Appointment</h4>
                                            <div>
                                                <a href="${pageContext.servletContext.contextPath}/admin/appointment/index.jsp" class="btn btn-secondary">Back</a>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-body p-5">
                                        <form method="post" action="${pageContext.servletContext.contextPath}/appointment_add.do">
                                            <div class="row g-2">
                                                <h5 class="mb-4">Appointment Details</h5>
                                                <div class="form-group col-md-6">
                                                    <sql:query var="results" dataSource="${myDatasource}">
                                                        SELECT * FROM treatments
                                                    </sql:query>
                                                    <label for="treatment">Treatment <span class="text-danger">*</span></label>
                                                    <select class="form-select" name="treatment">
                                                        <c:forEach var = "result" items = "${results.rows}">
                                                            <option value="${result.treat_id}">${result.treat_title}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <%java.text.DateFormat df1 = new java.text.SimpleDateFormat("yyyy-MM-dd"); %>
                                                    <label for="date">Select Date <span class="text-danger">*</span></label>
                                                    <input type="date" min="<%= df1.format(date) %>" class="form-control" id="date" name="date">
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="remark">Remark <span class="text-danger">*</span></label>
                                                    <textarea class="form-control" name="remark"></textarea>
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="time">Select Time <span class="text-danger">*</span></label>
                                                    <select class="form-select" name="time">
                                                        <option value="09:00:00">9:00 AM</option>
                                                        <option value="10:00:00">10:00 AM</option>
                                                        <option value="11:00:00">11:00 AM</option>
                                                        <option value="14:00:00">2:00 PM</option>
                                                        <option value="15:00:00">3:00 PM</option>
                                                        <option value="16:00:00">4:00 PM</option>
                                                        <option value="17:00:00">5:00 PM</option>
                                                        <option value="18:00:00">6:00 PM</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <hr class="my-5">
                                            <div class="row g-2">
                                                <div class="form-group col-md-6">
                                                    <h5 class="mb-4">Patient</h5>
                                                    <sql:query var="results" dataSource="${myDatasource}">
                                                        SELECT * FROM patients WHERE patient_status = 'Active'
                                                    </sql:query>
                                                    <label for="patient">Select Patient <span class="text-danger">*</span></label>
                                                    <select class="form-select" name="patient">
                                                        <c:forEach var = "result" items = "${results.rows}">
                                                            <option value="${result.patient_id}">#ID${result.patient_id} - ${result.patient_firstname} ${result.patient_lastname}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group col-md-6 mt-5">
                                                <input type="hidden" value="08y*6M" name="user_type">
                                                <button type="submit" class="btn btn-primary">Book</button>
                                                <a href="${pageContext.servletContext.contextPath}/admin/appointment/index.jsp" class="btn btn-link link-danger">Cancel</a>
                                            </div>
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
