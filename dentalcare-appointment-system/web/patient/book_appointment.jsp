<%-- 
    Document   : book
    Created on : Dec 4, 2022, 6:57:32 PM
    Author     : syahir
--%>
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
        
        <main class="container py-5">
            <!-- Error & Success message -->
            <%@ include file="/admin/component/error_success_msg.jsp" %>
            
            <h2 class="text-new my-4">Book Appointment</h2>
            <div class="card p-5 mb-4">
                <form method="post" action="${pageContext.servletContext.contextPath}/appointment_add.do">
                    <div class="mb-4">
                        <sql:query var="results" dataSource="${myDatasource}">
                            SELECT * FROM treatments
                        </sql:query>
                        <p>Select Treatment <span class="text-danger">*</span></p>
                        <c:forEach var = "result" items = "${results.rows}">
                            <div class="button">
                                <input type="radio" name="treatment" value="${result.treat_id}" id="cosmetic-outlined" autocomplete="off" checked>
                                <label for="cosmetic-outlined">${result.treat_title}</label>
                            </div>
                        </c:forEach>
                    </div>
                    <br><br><br><br><br>
                    <div class="col-6 my-4">
                        <div class="form-group">
                            <p>Remark for Dentist <span class="text-danger">*</span></p>
                            <textarea class="form-control" name="remark" rows="4" placeholder="Example: My wisdom tooth is pushing my front teeth."></textarea>
                        </div>
                    </div>

                    <p>Select Date and Time <span class="text-danger">*</span></p>
                    <div class="row mb-5">
                        <div class="col-6 pe-0">
                            <input type="date" name="date" class="form-control py-3">
                        </div>
                        <div class="col-6">
                            <div class="button">
                                <input type="radio" name="time" value="09:00:00" id="option1" autocomplete="off" checked>
                                <label class="text-nowrap" for="option1">9:00 AM</label>
                            </div>
                            <div class="button">
                                <input type="radio" name="time" value="10:00:00" id="option2" autocomplete="off">
                                <label class="text-nowrap" for="option2">10:00 AM</label>
                            </div>
                            <div class="button">
                                <input type="radio" name="time" value="11:00:00" id="option3" autocomplete="off">
                                <label class="text-nowrap" for="option3">11:00 AM</label>
                            </div>
                            <div class="button">
                                <input type="radio" name="time" value="14:00:00" id="option4" autocomplete="off">
                                <label class="text-nowrap" for="option4">2:00 PM</label>
                            </div>
                            <div class="button">
                                <input type="radio" name="time" value="15:00:00" id="option5" autocomplete="off">
                                <label class="text-nowrap" for="option5">3:00 PM</label>
                            </div>
                            <div class="button">
                                <input type="radio" name="time" value="16:00:00" id="option6" autocomplete="off">
                                <label class="text-nowrap" for="option6">4:00 PM</label>
                            </div>
                            <div class="button">
                                <input type="radio" name="time" value="17:00:00" id="option7" autocomplete="off">
                                <label class="text-nowrap" for="option7">5:00 PM</label>
                            </div>
                            <div class="button">
                                <input type="radio" name="time" value="18:00:00" id="option8" autocomplete="off">
                                <label class="text-nowrap" for="option8">6:00 PM</label>
                            </div>
                        </div>
                    </div>
                    <div class="d-flex justify-content-end align-items-center">
                        <c:set var="id" value="${patient.getId()}"/>
                        <sql:query var="results" dataSource="${myDatasource}">
                            SELECT patient_id FROM patients WHERE patient_id=?
                            <sql:param value="${id}"/>
                        </sql:query>
                        <input type="hidden" name="user_type" value="$B9f86">
                        <c:forEach var = "result" items = "${results.rows}">
                            <input type="hidden" name="patient" value="${result.patient_id}">
                        </c:forEach>
                        <a href="${pageContext.servletContext.contextPath}/patient/home.jsp" class="me-5 link-danger">Cancel</a>
                        <input type="submit" class="btn btn-new-1 bg-new text-light py-3 px-5 rounded-pill" value="Book">
                    </div>
                </form>
            </div>
        </main>
        
        <!-- Footer -->
        <%@ include file="/component/footer.jsp" %>
        
        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
