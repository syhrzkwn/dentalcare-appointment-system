
<%-- 
    Document   : dashboard
    Created on : Dec 5, 2022, 12:17:25 AM
    Author     : syahir
--%>
<%@ page import = "java.io.*,java.util.*, javax.servlet.*" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% Date date = new java.util.Date(); %>
<!DOCTYPE html>
<html>
    <head>
        <!-- Head -->
        <%@ include file="../admin/component/head.jsp" %>
        <title>Admin - Dashboard</title>
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

                        <!-- Breadcrumb -->
                        <div class="row">
                            <div class="col">
                                <nav aria-label="breadcrumb" class="bg-light rounded-3 p-3 mb-4">
                                    <ol class="breadcrumb mb-0">
                                        <li class="breadcrumb-item active" aria-current="page">Dashboard</li>
                                    </ol>
                                </nav>
                            </div>
                        </div>

                        <div class="row">
                            <!-- Appointments Card -->
                            <sql:query var="results" dataSource="${myDatasource}">
                                SELECT COUNT(*) AS count FROM appointments
                            </sql:query>
                            <div class="col-sm-6 col-md-3 mb-3">
                                <div class="card bg-primary text-white shadow">
                                    <div class="card-body">
                                        <h5 class="card-title mb-3"><i class="fs-2 bi bi-calendar2-check"></i></h5>
                                        <div class="card-text">
                                            <div class="d-flex justify-content-between">
                                                <p class="align-self-center mb-0">Total Appointments <br> Booked</p>
                                                <c:forEach var = "result" items = "${results.rows}">
                                                    <p class="fs-2 fw-bold mb-0"><c:out value="${result.count}"/></p>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Treatments Card -->
                            <sql:query var="results" dataSource="${myDatasource}">
                                SELECT COUNT(*) AS count FROM treatments
                            </sql:query>
                            <div class="col-sm-6 col-md-3 mb-3">
                                <div class="card bg-primary text-white shadow">
                                    <div class="card-body">
                                        <h5 class="card-title mb-3"><i class="fs-2 bi bi-bandaid"></i></h5>
                                        <div class="card-text">
                                            <div class="d-flex justify-content-between">
                                                <p class="align-self-center mb-0">Total Treatments <br> Available</p>
                                                <c:forEach var = "result" items = "${results.rows}">
                                                    <p class="fs-2 fw-bold mb-0"><c:out value="${result.count}"/></p>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Patients Card -->
                            <sql:query var="results" dataSource="${myDatasource}">
                                SELECT COUNT(*) AS count FROM patients
                            </sql:query>
                            <div class="col-sm-6 col-md-3 mb-3">
                                <div class="card bg-primary text-white shadow">
                                    <div class="card-body">
                                        <h5 class="card-title mb-3"><i class="fs-2 bi bi bi-people"></i></h5>
                                        <div class="card-text">
                                            <div class="d-flex justify-content-between">
                                                <p class="align-self-center mb-0">Total Patients <br> Registered</p>
                                                <c:forEach var = "result" items = "${results.rows}">
                                                    <p class="fs-2 fw-bold mb-0"><c:out value="${result.count}"/></p>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Dentists Card -->
                            <sql:query var="results" dataSource="${myDatasource}">
                                SELECT COUNT(*) AS count FROM dentists WHERE dentist_status = 'Available' AND dentist_id != 0
                            </sql:query>
                            <div class="col-sm-6 col-md-3 mb-3">
                                <div class="card bg-primary text-white shadow">
                                    <div class="card-body">
                                        <h5 class="card-title mb-3"><i class="fs-2 bi bi-people"></i></h5>
                                        <div class="card-text">
                                            <div class="d-flex justify-content-between">
                                                <p class="align-self-center mb-0">Total Dentists <br> Available</p>
                                                <c:forEach var = "result" items = "${results.rows}">
                                                    <p class="fs-2 fw-bold mb-0"><c:out value="${result.count}"/></p>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <!-- Today's Appointments Card -->
                            <div class="col-md-9 mb-3">
                                <div class="card bg-light shadow">
                                    <div class="card-body">
                                        <div class="card-title d-flex justify-content-between">
                                            <h5><i class="fs-3 bi-calendar2-check align-middle me-2"></i><span>Today's Appointments</span></h5>
                                            <%-- Display today date with format Mon, 12 December 2022 --%>
                                            <%java.text.DateFormat df1 = new java.text.SimpleDateFormat("EEEE, dd MMMM yyyy"); %>
                                            <span><%= df1.format(date) %></span>
                                        </div>
                                        <div class="card-text">
                                            <div class="table-responsive">
                                                <table class="table">
                                                    <thead>
                                                        <tr>
                                                            <th class="text-center">#ID</th>
                                                            <th>Name</th>
                                                            <th>Phone</th>
                                                            <th>Treatment</th>
                                                            <th>Dentist</th>
                                                            <th class="text-center">Time</th>
                                                            <th class="text-center">Status</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    <%java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-M-dd"); %>
                                                    <c:set var="current_date" value="<%= df2.format(date) %>"/>
                                                    <sql:query var="results" dataSource="${myDatasource}">
                                                        SELECT appointments.aptmt_id, appointments.aptmt_date, appointments.aptmt_time, appointments.aptmt_status,
                                                        treatments.treat_title,
                                                        patients.patient_firstname, patients.patient_lastname, patients.patient_phone,
                                                        dentists.dentist_firstname, dentists.dentist_lastname, dentists.dentist_id
                                                        FROM appointments
                                                        JOIN treatments ON appointments.treat_id = treatments.treat_id
                                                        JOIN patients ON appointments.patient_id = patients.patient_id
                                                        JOIN dentists ON appointments.dentist_id = dentists.dentist_id
                                                        WHERE appointments.aptmt_date = ?
                                                        ORDER BY appointments.aptmt_time ASC
                                                        <sql:param value="${current_date}"/>
                                                    </sql:query>
                                                    <c:choose>
                                                        <c:when test="${empty results.rows}">
                                                            <tr>
                                                                <td colspan="7" class="align-middle text-center py-3">There is no data</td>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach var = "result" items = "${results.rows}">
                                                                <tr>
                                                                    <td class="align-middle text-center">${result.aptmt_id}</td>
                                                                    <td class="align-middle">${result.patient_firstname} ${result.patient_lastname}</td>
                                                                    <td class="align-middle"><a href="tel:${result.patient_phone}">${result.patient_phone}</a></td>
                                                                    <td class="align-middle">${result.treat_title}</td>
                                                                    <c:choose>
                                                                        <c:when test="${result.dentist_id == 0}">
                                                                            <td class="align-middle text-danger">${result.dentist_firstname}</td>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <td class="align-middle">Dr. ${result.dentist_firstname} ${result.dentist_lastname}</td>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                    <td class="align-middle text-center"><fmt:formatDate pattern = "h:mm a" value = "${result.aptmt_time}" /></td>
                                                                    <td class="align-middle text-center">
                                                                        <c:choose>
                                                                            <c:when test="${result.aptmt_status eq 'Booked'}">
                                                                                <span class="badge rounded-pill bg-primary">${result.aptmt_status}</span>
                                                                            </c:when>
                                                                            <c:when test="${result.aptmt_status eq 'In Progress'}">
                                                                                <span class="badge rounded-pill bg-warning text-dark">${result.aptmt_status}</span>
                                                                            </c:when>
                                                                            <c:when test="${result.aptmt_status eq 'Completed'}">
                                                                                <span class="badge rounded-pill bg-success">${result.aptmt_status}</span>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <span class="badge rounded-pill bg-danger">${result.aptmt_status}</span>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </td>
                                                                </tr>
                                                            </c:forEach>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <sql:query var="results" dataSource="${myDatasource}">
                                            SELECT COUNT(*) AS count FROM appointments WHERE aptmt_date=?
                                            <sql:param value="${current_date}"/>
                                        </sql:query>
                                        <c:forEach var = "result" items = "${results.rows}">
                                            <p class="mt-3"><strong><c:out value="${result.count}"/></strong> Appointment(s) today</p>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-3 mb-3">
                                <!-- Not Assigned Dentist Card -->
                                <sql:query var="results" dataSource="${myDatasource}">
                                    SELECT COUNT(*) AS count FROM appointments WHERE dentist_id = 0 AND aptmt_status != 'Cancelled'
                                </sql:query>
                                <c:forEach var = "result" items = "${results.rows}">
                                    <div class="card alert-primary shadow mb-3">
                                        <div class="card-body">
                                            <h5><i class="fs-3 bi bi-bell me-2"></i><span>Notifications</span></h5>
                                            <div class="card-text">
                                                <ul>
                                                    <c:choose>
                                                        <c:when test="${result.count != 0}">
                                                            <li>There is <strong><c:out value="${result.count}"/></strong> appointments that has not assigned the dentist yet. Please assign the dentist.
                                                                <a href="${pageContext.servletContext.contextPath}/admin/appointment/index.jsp" class="alert-link">Assign now</a>
                                                            </li>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <li>No notifications</li>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                
                                <!-- Patients not book yet Card -->
                                <div class="card bg-light shadow">
                                    <div class="card-body">
                                        <h5 class="card-title mb-3">
                                            <i class="fs-3 bi bi-person-exclamation align-middle me-2"></i>
                                            <span>Patients</span>
                                        </h5>
                                        <div class="card-text">
                                            <sql:query var="results" dataSource="${myDatasource}">
                                                SELECT patient_id, patient_firstname, patient_lastname, patient_phone FROM patients WHERE first_time_booked='N' AND patient_status='Active'
                                            </sql:query>
                                            <p>(not book appointment yet)</p>
                                            <div class="table-responsive">
                                                <table class="table">
                                                    <thead>
                                                        <tr>
                                                            <th class="text-center">#ID</th>
                                                            <th>Name</th>
                                                            <th>Contact</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:choose>
                                                            <c:when test="${empty results.rows}">
                                                                <tr>
                                                                    <td colspan="3" class="align-middle text-center py-3">There is no data</td>
                                                                </tr>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:forEach var="result" items="${results.rows}">
                                                                    <tr>
                                                                        <td class="text-center">${result.patient_id}</td>
                                                                        <td>${result.patient_firstname} ${result.patient_lastname}</td>
                                                                        <td class="text-center"><a href="https://wa.me/${result.patient_phone}" target="_blank" class="link-success"><i class="bi bi-whatsapp"></i></a></td>
                                                                    </tr>
                                                                </c:forEach>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
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
