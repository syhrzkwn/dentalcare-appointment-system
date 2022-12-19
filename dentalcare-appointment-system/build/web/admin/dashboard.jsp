
<%-- 
    Document   : dashboard
    Created on : Dec 5, 2022, 12:17:25 AM
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
                                SELECT COUNT(*) AS count FROM dentists WHERE dentist_status = 'Available'
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
                                            <%java.text.DateFormat df = new java.text.SimpleDateFormat("EEEE, dd MMMM yyyy"); %>
                                            <span><%= df.format(new java.util.Date()) %></span>
                                        </div>
                                        <div class="card-text">
                                            <!-- <p>You don't have any orders today.</p> -->
                                            <div class="table-responsive">
                                                <table class="table">
                                                    <thead>
                                                        <tr>
                                                            <th class="text-center">#</th>
                                                            <th>Name</th>
                                                            <th>Phone</th>
                                                            <th>Treatment</th>
                                                            <th class="text-center">Time</th>
                                                            <th class="text-center">Status</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td class="text-center">4</td>
                                                            <td>John Doe</td>
                                                            <td>0123456789</td>
                                                            <td>Extractions</td>
                                                            <td class="text-center">3:00PM</td>
                                                            <td class="text-center"><span class="badge rounded-pill bg-danger">Cancelled</span></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="text-center">3</td>
                                                            <td>Luqman Hakim</td>
                                                            <td>0123456789</td>
                                                            <td>Cosmetic Braces</td>
                                                            <td class="text-center">11:00AM</td>
                                                            <td class="text-center"><span class="badge rounded-pill bg-primary">Booked</span></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="text-center">2</td>
                                                            <td>Syahir Zakwan</td>
                                                            <td>0196021939</td>
                                                            <td>Cosmetic Braces</td>
                                                            <td class="text-center">10:00AM</td>
                                                            <td class="text-center"><span class="badge rounded-pill bg-warning text-dark">In Progress</span></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="text-center">1</td>
                                                            <td>Khairul Afnan</td>
                                                            <td>0123456789</td>
                                                            <td>General Dentistary</td>
                                                            <td class="text-center">9:00AM</td>
                                                            <td class="text-center"><span class="badge rounded-pill bg-success">Completed</span></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div>
                                            <a href="#" class="btn btn-primary btn-sm mt-3">View All Appointments</a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Patients not book yet Card -->
                            <div class="col-md-3 mb-3">
                                <div class="card bg-light shadow">
                                    <div class="card-body">
                                        <h5 class="card-title mb-3">
                                            <i class="fs-3 bi bi-person-exclamation align-middle me-2"></i>
                                            <span>Patients</span>
                                        </h5>
                                        <div class="card-text">
                                            <p>(not book appointment yet)</p>
                                            <div class="table-responsive">
                                                <table class="table">
                                                    <thead>
                                                        <tr>
                                                            <th class="text-center">#</th>
                                                            <th>Name</th>
                                                            <th>Contact</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td class="text-center">1</td>
                                                            <td>Johan Nazrin</td>
                                                            <td class="text-center"><a href="#" target="_blank" class="link-success"><i class="bi bi-whatsapp"></i></a></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="text-center">2</td>
                                                            <td>Danial Aqeem</td>
                                                            <td class="text-center"><a href="#" target="_blank" class="link-success"><i class="bi bi-whatsapp"></i></a></td>
                                                        </tr>
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
