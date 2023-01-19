<%-- 
    Document   : appointment_history
    Created on : Jan 11, 2023, 8:24:27 PM
    Author     : Syahir
--%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Head -->
        <%@ include file="/component/head.jsp" %>
        <title>Appointment - Dental Care</title>
    </head>
    <body>
        <c:set var="id" value="${patient.getId()}"/>
        <sql:query var="results" dataSource="${myDatasource}">
            SELECT * FROM patients WHERE patient_id=?
            <sql:param value="${id}"/>
        </sql:query>
                            
        <!-- Navbar -->
        <%@ include file="/patient/component/navbar.jsp" %>
        
        <main class="container py-4">
            <!-- Error & Success message -->
            <%@ include file="/admin/component/error_success_msg.jsp" %>
            
            <div class="py-4">
                <sql:query var="results" dataSource="${myDatasource}">
                    SELECT appointments.aptmt_id, appointments.aptmt_date, appointments.aptmt_time, appointments.aptmt_status, appointments.aptmt_remark,
                    treatments.treat_title,
                    patients.patient_firstname, patients.patient_lastname,
                    dentists.dentist_firstname, dentists.dentist_lastname, dentists.dentist_id
                    FROM appointments
                    JOIN treatments ON appointments.treat_id = treatments.treat_id
                    JOIN patients ON appointments.patient_id = patients.patient_id
                    JOIN dentists ON appointments.dentist_id = dentists.dentist_id
                    WHERE patients.patient_id = ? AND appointments.aptmt_status = 'Completed' OR appointments.aptmt_status = 'Cancelled'
                    ORDER BY appointments.aptmt_date DESC, appointments.aptmt_time
                    <sql:param value="${id}"/>
                </sql:query>
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h3 class="text-new">Your Appointment History:</h3>
                    <a href="${pageContext.servletContext.contextPath}/patient/home.jsp" class="link-secondary"><i class="bi bi-arrow-left"></i> Back</a>
                </div>
                <c:choose>
                    <c:when test="${empty results.rows}">
                        <div class="card p-5 mb-3">
                            <div class="card-body">
                                <div class="card-text">
                                    <p class="text-center">You don't have any booked appointments history.</p>
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="result" items="${results.rows}">
                            <div class="card p-5 mb-3">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-4">
                                            <p> Booking ID: <span class="fw-bold text-new"># ${result.aptmt_id}</span></p>
                                            <p> Time: <span class="fw-bold"><fmt:formatDate pattern = "h:mm a" value = "${result.aptmt_time}" /></span></p>
                                            <p> Treatment: <span class="fw-bold">${result.treat_title}</span></p>
                                            <c:choose>
                                                <c:when test="${result.dentist_id == 0}">
                                                    <p> Dentist: <span class="text-danger" >Not Assigned Yet</span></p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p> Dentist: <span class="fw-bold" > Dr. ${result.dentist_firstname} ${result.dentist_lastname}</span></p>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="col-5">
                                            <p> Remark: <span class="fw-bold" >${result.aptmt_remark}</span></p>
                                            <c:choose>
                                                <c:when test="${result.aptmt_status eq 'Booked'}">
                                                    <p>Status: <span class="badge rounded-pill bg-primary fs-6">${result.aptmt_status}</span></p>
                                                </c:when>
                                                <c:when test="${result.aptmt_status eq 'In Progress'}">
                                                    <p>Status: <span class="badge rounded-pill bg-warning text-dark fs-6">${result.aptmt_status}</span></p>
                                                </c:when>
                                                <c:when test="${result.aptmt_status eq 'Completed'}">
                                                    <p>Status: <span class="badge rounded-pill bg-success fs-6">${result.aptmt_status}</span></p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p>Status: <span class="badge rounded-pill bg-danger fs-6">${result.aptmt_status}</span></p>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                         <div class="col-3">
                                            <h3 class="text-end text-new">
                                                <span><fmt:formatDate pattern = "dd MMM yyyy" value = "${result.aptmt_date}" /></span>
                                                <br>
                                                <span><fmt:formatDate pattern = "EEEE" value = "${result.aptmt_date}" /></span>
                                            </h3>
                                        </div>
                                    </div>
                                    <p class="text-center mt-4 mb-0">Note: Please arrive 10-15 mins earlier than your appointment time!</p>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
            
        <!-- Footer -->
        <%@ include file="/component/footer.jsp" %>
        
        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
