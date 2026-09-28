<%-- 
    Document   : view
    Created on : Dec 22, 2022, 11:35:56 AM
    Author     : syahir
--%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                        
                        <c:set var="id" value="${param.aptmt_id}"/>
                        <sql:query var="results" dataSource="${myDatasource}">
                            SELECT appointments.aptmt_id, appointments.aptmt_date, appointments.aptmt_time, appointments.aptmt_status, appointments.aptmt_remark,
                            treatments.treat_title,
                            patients.patient_firstname, patients.patient_lastname, patients.patient_email, patients.patient_phone, patients.patient_id,
                            dentists.dentist_firstname, dentists.dentist_lastname, dentists.dentist_email, dentists.dentist_phone, dentists.dentist_id
                            FROM appointments
                            JOIN treatments ON appointments.treat_id = treatments.treat_id
                            JOIN patients ON appointments.patient_id = patients.patient_id
                            JOIN dentists ON appointments.dentist_id = dentists.dentist_id
                            WHERE appointments.aptmt_id=?
                            <sql:param value="${id}"/>
                        </sql:query>
                                                    
                        <!-- Breadcrumb -->
                        <div class="row">
                            <div class="col">
                                <nav aria-label="breadcrumb" class="bg-light rounded-3 p-3 mb-4">
                                    <ol class="breadcrumb mb-0">
                                        <li class="breadcrumb-item"><a href="${pageContext.servletContext.contextPath}/admin/dashboard.jsp" class="link-primary">Dashboard</a></li>
                                        <li class="breadcrumb-item"><a href="${pageContext.servletContext.contextPath}/admin/appointment/index.jsp" class="link-primary">Appointments</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">View</li>
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
                                            <h4>View Appointment</h4>
                                            <div>
                                                <a href="${pageContext.servletContext.contextPath}/admin/appointment/index.jsp" class="btn btn-secondary">Back</a>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-body p-5">
                                        <form method="post" action="${pageContext.servletContext.contextPath}#">
                                            <div class="row g-2">
                                                <c:forEach var = "result" items = "${results.rows}">
                                                    <h5>Appointment details</h5>
                                                    <div class="form-group col-md-6">
                                                        <label for="id">#ID</label>
                                                        <input type="text" class="form-control" id="id" value="${result.aptmt_id}" disabled>
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <label for="status">Status</label>
                                                        <input type="text" class="form-control" id="status" value = "${result.aptmt_status}" disabled>
                                                    </div>
                                                    <div class="form-group col-md-4">
                                                        <label for="treatment">Treatment</label>
                                                        <input type="text" class="form-control" id="treatment" value="${result.treat_title}" disabled>
                                                    </div>
                                                    <div class="form-group col-md-4">
                                                        <label for="date">Date</label>
                                                        <input type="text" class="form-control" id="date" value="<fmt:formatDate pattern = "dd/MM/yyyy (EEEE)" value = "${result.aptmt_date}" />" disabled>
                                                    </div>
                                                    <div class="form-group col-md-4">
                                                        <label for="time">Time</label>
                                                        <input type="text" class="form-control" id="time" value="<fmt:formatDate pattern = "h:mm a" value = "${result.aptmt_time}" />" disabled>
                                                    </div>
                                                    <div class="form-group col-md-12">
                                                        <label for="remark">Remark</label>
                                                        <textarea class="form-control" disabled>${result.aptmt_remark}</textarea>
                                                    </div>
                                                    <hr class="mt-5 mb-4">
                                                    <h5>Patient details</h5>
                                                    <div class="form-group col-md-6">
                                                        <label for="id">#ID</label>
                                                        <input type="text" class="form-control" id="id" value="${result.patient_id}" disabled>
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <label for="phone">Phone</label>
                                                        <input type="text" class="form-control" id="phone" value="${result.patient_phone}" disabled>
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <label for="name">Name</label>
                                                        <input type="text" class="form-control" id="name" value="${result.patient_firstname} ${result.patient_lastname}" disabled>
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <label for="email">Email</label>
                                                        <input type="text" class="form-control" id="email" value="${result.patient_email}" disabled>
                                                    </div>
                                                    <hr class="mt-5 mb-4">
                                                    <h5>Dentist details</h5>
                                                    <c:choose>
                                                        <c:when test="${result.dentist_id == 0}">
                                                            <p>There is no dentist assigned yet.<p>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="form-group col-md-6">
                                                                <label for="id">#ID</label>
                                                                <input type="text" class="form-control" id="id" value="${result.dentist_id}" disabled>
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="phone">Phone</label>
                                                                <input type="text" class="form-control" id="phone" value="${result.dentist_phone}" disabled>
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="name">Name</label>
                                                                <input type="text" class="form-control" id="name" value="Dr. ${result.dentist_firstname} ${result.dentist_lastname}" disabled>
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="email">Email</label>
                                                                <input type="text" class="form-control" id="email" value="${result.dentist_email}" disabled>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
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