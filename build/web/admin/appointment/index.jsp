<%-- 
    Document   : index
    Created on : Dec 22, 2022, 8:25:33 AM
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

                        <sql:query var="results" dataSource="${myDatasource}">
                            SELECT COUNT(*) AS count FROM appointments WHERE dentist_id = 0 AND aptmt_status != 'Cancelled'
                        </sql:query>
                        <c:forEach var = "result" items = "${results.rows}">
                            <c:if test="${result.count != 0}">
                                <div class="alert alert-warning" role="alert">
                                    <i class="fs-5 bi bi-exclamation-triangle-fill align-middle me-2"></i>
                                    <span>There is <strong><c:out value="${result.count}"/></strong> appointments that has not assigned the dentist yet. Please assign the dentist.</span>
                                </div>
                            </c:if>
                        </c:forEach>
                            
                        <!-- Breadcrumb -->
                        <div class="row">
                            <div class="col">
                                <nav aria-label="breadcrumb" class="bg-light rounded-3 p-3 mb-4">
                                    <ol class="breadcrumb mb-0">
                                        <li class="breadcrumb-item"><a href="${pageContext.servletContext.contextPath}/admin/dashboard.jsp" class="link-primary">Dashboard</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">Appointments</li>
                                    </ol>
                                </nav>
                            </div>
                        </div>
                            
                        <div class="row justify-content-center">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-between">
                                            <h4>Appointments</h4>
                                            <div>
                                                <a href="${pageContext.servletContext.contextPath}/admin/appointment/add.jsp" class="btn btn-success"><i class="bi bi-plus-lg"></i> Appointment</a>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="w-100 table">
                                                <thead class="table-light">
                                                    <tr>
                                                        <th class="text-center">#ID</th>
                                                        <th>Date</th>
                                                        <th>Time</th>
                                                        <th>Treatment</th>
                                                        <th>Patient</th>
                                                        <th>Dentist</th>
                                                        <th class="text-center">Status</th>
                                                        <th class="text-center">Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <sql:query var="results" dataSource="${myDatasource}">
                                                        SELECT appointments.aptmt_id, appointments.aptmt_date, appointments.aptmt_time, appointments.aptmt_status,
                                                        treatments.treat_title,
                                                        patients.patient_firstname, patients.patient_lastname,
                                                        dentists.dentist_id, dentists.dentist_firstname, dentists.dentist_lastname
                                                        FROM appointments
                                                        JOIN treatments ON appointments.treat_id = treatments.treat_id
                                                        JOIN patients ON appointments.patient_id = patients.patient_id
                                                        JOIN dentists ON appointments.dentist_id = dentists.dentist_id
                                                        ORDER BY appointments.aptmt_date DESC, appointments.aptmt_time
                                                    </sql:query>
                                                    <c:choose>
                                                        <c:when test="${empty results.rows}">
                                                            <tr>
                                                                <td colspan="8" class="align-middle text-center py-3">There is no data</td>
                                                            </tr>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach var = "result" items = "${results.rows}">
                                                                <tr>
                                                                    <td class="align-middle text-center">${result.aptmt_id}</td>
                                                                    <td class="align-middle"><fmt:formatDate pattern = "dd/MM/yyyy (EEEE)" value = "${result.aptmt_date}" /></td>
                                                                    <td class="align-middle"><fmt:formatDate pattern = "h:mm a" value = "${result.aptmt_time}" /></td>
                                                                    <td class="align-middle">${result.treat_title}</td>
                                                                    <td class="align-middle">${result.patient_firstname} ${result.patient_lastname}</td>
                                                                    <c:choose>
                                                                        <c:when test="${result.dentist_id == 0}">
                                                                            <td class="align-middle text-danger">${result.dentist_firstname}</td>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <td class="align-middle">Dr. ${result.dentist_firstname} ${result.dentist_lastname}</td>
                                                                        </c:otherwise>
                                                                    </c:choose>
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
                                                                    <td class="align-middle text-center">
                                                                        <div class="btn-group">
                                                                            <button type="button" class="btn" data-bs-toggle="dropdown" data-bs-display="static" aria-expanded="false">
                                                                                <i class="bi bi-three-dots"></i>
                                                                            </button>
                                                                            <ul class="dropdown-menu dropdown-menu-end">
                                                                                <li><a href="${pageContext.servletContext.contextPath}/admin/appointment/view.jsp?aptmt_id=${result.aptmt_id}" class="dropdown-item" type="button">View</a></li>
                                                                                <li><a href="${pageContext.servletContext.contextPath}/admin/appointment/edit.jsp?aptmt_id=${result.aptmt_id}" class="dropdown-item" type="button">Edit</a></li>
                                                                                <li><hr class="dropdown-divider"></li>
                                                                                <li>
                                                                                    <form method="post" action="${pageContext.servletContext.contextPath}/appointment_delete.do">
                                                                                        <input type="hidden" value="${result.aptmt_id}" name="id">
                                                                                        <button type="submit" class="btn text-danger dropdown-item" data-bs-toggle="tooltip" data-bs-placement="left" title="This process cannot be undone.">Delete</button>
                                                                                    </form>
                                                                                </li>
                                                                            </ul>
                                                                        </div>
                                                                    </td>
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
                    </main>
                </div>
            </div>
        </div>
                        
        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
