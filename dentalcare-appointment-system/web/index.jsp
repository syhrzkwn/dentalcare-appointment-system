<%-- 
    Document   : index
    Created on : Dec 13, 2022, 12:46:57 PM
    Author     : syahir
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Head -->
        <%@ include file="/component/head.jsp" %>
        <title>Home - Dental Care</title>
    </head>
    <body>
        <!-- Navbar -->
        <%@ include file="/component/navbar.jsp" %>
        
        <div class="bg-new">
            <div class="container">
                
                <!--Home section-->
                <div id="home" class="py-5">
                    <div class="row align-items-center">
                        <div class="col-md-6 ">
                            <h1 class="text-light">We're open and welcoming patients.</h1>
                            <div class ="row">
                                <div class="col-12 text-light mt-4">
                                    <p>Welcome to our Dental Care website. We have implemented a number of safety protocols and measures to ensure the safety and comfort of both our patients and our team.</p>
                                </div>
                            </div>
                            <div class="row mt-5">
                                <div class="col-12">
                                    <a href="${pageContext.servletContext.contextPath}/login.jsp" class="btn btn-new btn-light rounded-pill text-new py-3 px-5 me-5">Book Online</a>
                                    <a href="#" class="link-light">Contact Us</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 d-flex">
                            <div>
                                <img src="${pageContext.servletContext.contextPath}/images/hero.webp" class="img-fluid" alt="#image1">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                
        <main class="container">
            <!--Ads section-->
            <div class="pt-5 pb-4">
                <div class="row text-center">
                    <div class="col-4">
                        <h5 class="text-new">Free</h5>
                        <p>Dental Consultation</p>
                    </div>
                    <div class="col-4">
                        <h5 class="text-new">RM 200</h5>
                        <p>Custom Teeths Whitening Trays</p>
                    </div>
                    <div class="col-4">
                        <h5 class="text-new">RM 5,000</h5>
                        <p>Single Dental Implant & Crown</p>
                    </div>
                </div>
            </div>
            
            <hr class="bg-new">
            
            <!--Treatments section-->
            <div id="treatments" class="py-5">
                <div class="row">
                    <p class="mb-0 text-sec-new">Our Special</p>
                    <h1 class="text-new fw-semibold">Treatments</h1>
                </div>
                <div class="row align-items-center py-4">
                    <sql:query var="results" dataSource="${myDatasource}">
                        SELECT * FROM treatments
                    </sql:query>
                    <div class="col-6">
                        <div><img src="${pageContext.servletContext.contextPath}/images/home.webp" class="img-fluid" alt="#image2"></div>
                    </div>
                    <div class="col-6">
                        <div class="row row-cols-1 row-cols-md-2 g-2">
                            <c:forEach var = "result" items = "${results.rows}">
                                <div class="col">
                                    <div class="card border-0">
                                        <div class="card-body">
                                            <h5 class="card-title text-new">${result.treat_title}</h5>
                                            <p class="card-text">${result.treat_desc}</p>
                                            <a class="card-text link-sec-new" href="#">Learn more<i class="bi bi-arrow-right ms-2"></i></a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        
        <!-- Footer -->
        <%@ include file="/component/footer.jsp" %>
        
        <!-- Bootstrap 5 JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
