<%-- 
    Document   : sidebar
    Created on : Dec 12, 2022, 4:26:47 PM
    Author     : syahir
--%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Sidebar -->
<div class="col-auto col-md-3 col-xl-2 px-sm-2 px-0 bg-primary">
    <div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 text-white min-vh-100 sticky-top">
        <a href="${pageContext.servletContext.contextPath}/admin/dashboard.jsp" class="mx-auto d-block my-3 text-decoration-none text-center">
            <img src="${pageContext.servletContext.contextPath}/images/logo.webp" class="w-25" alt="Dental Care Logo">
        </a>

        <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start" id="menu">
            <li>
                <a href="${pageContext.servletContext.contextPath}/admin/dashboard.jsp" class="nav-link px-0 align-middle link-light">
                    <i class="fs-4 bi-speedometer2"></i> <span class="ms-1 d-none d-sm-inline">Dashboard</span>
                </a>
            </li>
            <li>
                <a href="#" class="nav-link px-0 align-middle link-light">
                    <i class="fs-4 bi bi-calendar2-check"></i> <span class="ms-1 d-none d-sm-inline">Appointments</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.servletContext.contextPath}/admin/treatment/index.jsp" class="nav-link px-0 align-middle link-light">
                    <i class="fs-4 bi bi-bandaid"></i> <span class="ms-1 d-none d-sm-inline">Treatments</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.servletContext.contextPath}/admin/patient/index.jsp" class="nav-link px-0 align-middle link-light">
                    <i class="fs-4 bi bi-people"></i> <span class="ms-1 d-none d-sm-inline">Patients</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.servletContext.contextPath}/admin/dentist/index.jsp" class="nav-link px-0 align-middle link-light">
                    <i class="fs-4 bi bi-people"></i> <span class="ms-1 d-none d-sm-inline">Dentists</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.servletContext.contextPath}/admin/staff/index.jsp" class="nav-link px-0 align-middle link-light">
                    <i class="fs-4 bi bi-person-badge"></i> <span class="ms-1 d-none d-sm-inline">Staffs</span>
                </a>
            </li>
        </ul>
    </div>
</div>
