<%-- 
    Document   : navbar
    Created on : Dec 12, 2022, 4:25:40 PM
    Author     : syahir
--%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Navbar -->
<nav class="row navbar navbar-expand-md navbar-light bg-light shadow-sm sticky-top">
    <div class="container">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="{{ __('Toggle navigation') }}">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <!-- Left Side Of Navbar -->
            <ul class="navbar-nav me-auto">
            </ul>

            <!-- Right Side Of Navbar -->
            <ul class="navbar-nav ms-auto">
                <li class="nav-item dropdown">
                    <a id="navbarDropdown" class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" v-pre>
                        <c:set var="id" value="${staff.getId()}"/>
                        <sql:query var="results" dataSource="${myDatasource}">
                            SELECT * FROM staffs WHERE staff_id=?
                            <sql:param value="${id}"/>
                        </sql:query>
                        <c:forEach var="result" items="${results.rows}">
                            <span>
                                <i class="fs-4 bi bi-person-circle align-middle me-2 position-relative"></i>
                                <span>${result.staff_firstname}
                                    <c:choose>
                                        <c:when test="${result.staff_status eq 'Available'}">
                                            <i class="bi bi-check-circle-fill text-success"></i>
                                        </c:when>
                                        <c:when test="${result.staff_status eq 'On Leave'}">
                                            <i class="bi bi-x-circle-fill text-danger"></i>
                                        </c:when>
                                    </c:choose>
                                </span>
                        </c:forEach>
                    </a>
                    <div class="dropdown-menu dropdown-menu-lg-end" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="${pageContext.servletContext.contextPath}/admin/account.jsp">Account</a>
                        <hr class="dropdown-divider">
                        <a class="dropdown-item text-danger" href="${pageContext.servletContext.contextPath}/auth_logout.do">Logout</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>