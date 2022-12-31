<%-- 
    Document   : navbar
    Created on : Dec 27, 2022, 11:02:59 AM
    Author     : syahir
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg bg-new sticky-top">
    <div class="container">
        <a href="${pageContext.servletContext.contextPath}/patient/home.jsp"><img src="${pageContext.servletContext.contextPath}/images/logo.webp" width="20%" class="p-1" alt="logo"></a>
        <div class="collapse navbar-collapse m-2" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="d-flex">
                    <a class="nav-link active text-light me-5" href="${pageContext.servletContext.contextPath}/patient/home.jsp">Appointment</a>
                </li>
                <li class="d-flex">
                    <a class="nav-link active text-light me-5" href="${pageContext.servletContext.contextPath}/patient/account.jsp">Account</a>
                </li>
            </ul>
            <a href="${pageContext.servletContext.contextPath}/auth_logout.do" class="btn btn-new btn-light rounded-pill text-danger py-3 px-5">Log Out</a>
        </div>
    </div>
</nav>
