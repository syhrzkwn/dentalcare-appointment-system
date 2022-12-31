<%-- 
    Document   : navbar
    Created on : Dec 27, 2022, 12:57:11 AM
    Author     : johan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-expand-lg bg-new sticky-top">
    <div class="container">
        <a href="${pageContext.servletContext.contextPath}/index.jsp"><img src="${pageContext.servletContext.contextPath}/images/logo.webp" width="20%" class="p-1" alt="logo"></a>
        <div class="collapse navbar-collapse m-2" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="d-flex">
                    <a class="nav-link active text-light me-5" href="${pageContext.servletContext.contextPath}/index.jsp">Home</a>
                </li>
                <li class="d-flex">
                    <a class="nav-link active text-light me-5" href="${pageContext.servletContext.contextPath}/index.jsp#treatments">Treatments</a>
                </li>
            </ul>
            <a href="${pageContext.servletContext.contextPath}/login.jsp" class="btn btn-new btn-light rounded-pill text-new py-3 px-4">Book Appointment</a>
        </div>
    </div>
</nav>
