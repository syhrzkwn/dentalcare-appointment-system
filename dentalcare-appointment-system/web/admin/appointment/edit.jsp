<%-- 
    Document   : edit
    Created on : Dec 23, 2022, 8:31:10 PM
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
                            dentists.dentist_firstname, dentists.dentist_lastname, dentists.dentist_id
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
                                            <h4>Edit Appointment</h4>
                                            <div>
                                                <a href="${pageContext.servletContext.contextPath}/admin/appointment/index.jsp" class="btn btn-secondary">Back</a>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-body p-5">
                                        <form method="post" action="${pageContext.servletContext.contextPath}/appointment_update.do">
                                            <c:forEach var = "result" items = "${results.rows}">
                                                <div class="row g-2">
                                                    <h5>Appointment details</h5>
                                                    <div class="form-group col-md-6">
                                                        <label for="id">#ID</label>
                                                        <input type="text" class="form-control" id="id" value="${result.aptmt_id}"disabled>
                                                        <input type="hidden" value="${result.aptmt_id}" name="aptmt_id">
                                                        <input type="hidden" value="${result.dentist_id}" name="dentist_id">
                                                    </div>
                                                    <div class="form-group col-md-6">
                                                        <label for="status">Status <span class="text-danger">*</span></label>
                                                        <select class="form-select" id="status" name="status">
                                                            <option value="${result.aptmt_status}" selected>${result.aptmt_status}</option>
                                                            <option disabled>───────</option>
                                                            <option value="Booked">Booked</option>
                                                            <option value="In Progress">In Progress</option>
                                                            <option value="Completed">Completed</option>
                                                            <option value="Cancelled">Cancelled</option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group col-md-4">
                                                        <label for="treatment">Treatment</label>
                                                        <input type="text" class="form-control" id="treatment" value="${result.treat_title}" disabled>
                                                    </div>
                                                    <div class="form-group col-md-4">
                                                        <label for="date">Date <span class="text-danger">*</span></label>
                                                        <input type="date" class="form-control" id="date" name="date" value="${result.aptmt_date}">
                                                    </div>
                                                    <div class="form-group col-md-4">
                                                        <label for="time">Time <span class="text-danger">*</span></label>
                                                        <select class="form-select" id="time" name="time">
                                                            <option value="${result.aptmt_time}" selected><fmt:formatDate pattern = "h:mm a" value = "${result.aptmt_time}" /></option>
                                                            <option disabled>───────</option>
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
                                                    <div class="form-group col-md-12">
                                                        <label for="remark">Remark <span class="text-danger">*</span></label>
                                                        <textarea class="form-control" id="remark" name="remark">${result.aptmt_remark}</textarea>
                                                    </div>
                                                    <div class="form-group col-md-6 mt-4">
                                                        <input type="hidden" value="form1" name="form">
                                                        <button type="submit" class="btn btn-primary">Update Appointment</button>
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
                                                </div>
                                            </c:forEach>
                                        </form>
                                                
                                        <hr class="mt-5 mb-4">

                                        <form method="post" action="${pageContext.servletContext.contextPath}/appointment_update.do">
                                            <h5>Dentist</h5>
                                            <div class="form-group col-md-6">
                                                <sql:query var="results" dataSource="${myDatasource}">
                                                    SELECT appointments.aptmt_id, appointments.aptmt_date, appointments.aptmt_time, appointments.aptmt_status, appointments.aptmt_remark,
                                                    dentists.dentist_firstname, dentists.dentist_lastname, dentists.dentist_id
                                                    FROM appointments
                                                    JOIN dentists ON appointments.dentist_id = dentists.dentist_id
                                                    WHERE aptmt_id=?
                                                    <sql:param value="${id}"/>
                                                </sql:query>
                                                <c:forEach var = "result" items = "${results.rows}">
                                                    <input type="hidden" value="${result.aptmt_id}" name="aptmt_id">
                                                    <input type="hidden" value="${result.aptmt_date}" name="date">
                                                    <input type="hidden" value="${result.aptmt_time}" name="time">
                                                </c:forEach>
                                                <label for="dentist">Select a dentist to be assigned <span class="text-danger">*</span></label>
                                                <select class="form-select" id="dentist" name="dentist_id">
                                                    <c:forEach var = "result" items = "${results.rows}">
                                                        <c:choose>
                                                            <c:when test="${result.dentist_id == 0}">
                                                                <option value="${result.dentist_id}" selected>${result.dentist_firstname}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${result.dentist_id}" selected>Dr. ${result.dentist_firstname} ${result.dentist_lastname}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                    <option disabled>───────</option>
                                                    <sql:query var="results" dataSource="${myDatasource}">
                                                        SELECT * FROM dentists WHERE dentist_status='Available' OR dentist_id=0
                                                    </sql:query>
                                                    <c:forEach var = "result" items = "${results.rows}">
                                                        <c:choose>
                                                            <c:when test="${result.dentist_id == 0}">
                                                                <option value="${result.dentist_id}">${result.dentist_firstname}</option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${result.dentist_id}">Dr. ${result.dentist_firstname} ${result.dentist_lastname}</option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach> 
                                                </select>
                                            </div>
                                            <div class="row">
                                                <div class="form-group col-md-6 mt-4">
                                                    <input type="hidden" value="form2" name="form">
                                                    <button type="submit" class="btn btn-primary">Assign Dentist</button>
                                                </div>
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
