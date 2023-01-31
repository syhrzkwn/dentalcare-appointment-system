<%-- 
    Document   : head
    Created on : Dec 12, 2022, 4:34:15 PM
    Author     : syahir
--%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${empty sessionScope.staff}">
    <jsp:forward page="../login.jsp"></jsp:forward>
</c:if>
<sql:setDataSource var="myDatasource"
           driver="org.apache.derby.jdbc.ClientDriver"
           url="jdbc:derby://localhost:1527/DentalcareDB"
           user="app" password="app"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="${pageContext.servletContext.contextPath}/favicon.ico">
<!-- Bootstrap 5 CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"/>
<!-- Bootstrap Icon -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css"/>
