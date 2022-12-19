<%-- 
    Document   : error_success_msg
    Created on : Dec 12, 2022, 4:38:43 PM
    Author     : syahir
--%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Success message -->
<c:if test="${not empty successMsgs}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <i class="fs-5 bi bi-check-circle-fill align-middle"></i>
        <span>${successMsgs}</span>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<!-- Error message -->
<c:if test="${not empty errorMsgs}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="fs-5 bi bi-exclamation-circle-fill align-middle"></i>
        <span>Errors:</span>
        <ul>
            <c:forEach var="message" items="${errorMsgs}">
                <li>${message}</li>
            </c:forEach>
        </ul>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>