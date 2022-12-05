package com.dentalcare.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import com.dentalcare.model.Patient;
import com.dentalcare.model.Admin;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author syahir
 */
public class AuthLogin extends HttpServlet {

    private PreparedStatement pstmt1, pstmt2;
    
    @Override
    public void init() throws ServletException {
        initializeJdbc();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List errorMsgs = new LinkedList();
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        
        try {
            if(email.isEmpty()) {
                errorMsgs.add("Email is required");
            }
            
            if(password.isEmpty()) {
                errorMsgs.add("Password is required");
            }
            
            if(!errorMsgs.isEmpty() && role.equalsIgnoreCase("Patient")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
                view.forward(request, response);
                return;
            }
            else if(!errorMsgs.isEmpty() && role.equalsIgnoreCase("Admin")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/admin/login.jsp?secret_key=dn3@ZDt8UJ8l");
                view.forward(request, response);
                return;
            }

            String hashPassword = doHashing(password);
            
            if(role.equalsIgnoreCase("Patient")) {
                if(checkPatientAccount(email, hashPassword) == null) {
                    request.setAttribute("errorMsgs", "Email or Password is invalid");
                    RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
                    view.forward(request, response);
                }
                else {
                    Patient Patient = new Patient();
                    Patient.setEmail(email);
                    Patient.setRole(role);
        
                    HttpSession session = request.getSession();
                    session.setAttribute("patient",Patient);
                    request.setAttribute("successMsgs", "You have login successfully");
                    
                    RequestDispatcher view = request.getRequestDispatcher("/patient/appointment.jsp");
                    view.forward(request,response);
                }
            }
            else if(role.equalsIgnoreCase("Admin")) {    
                if(checkAdminAccount(email, hashPassword) == null) {
                    request.setAttribute("errorMsgs", "Email or Password is invalid");
                    RequestDispatcher view = request.getRequestDispatcher("/admin/login.jsp?secret_key=dn3@ZDt8UJ8l");
                    view.forward(request, response);
                }
                else {
                    Admin Admin = new Admin();
                    Admin.setEmail(email);
                    Admin.setRole(role);
                
                    HttpSession session = request.getSession();
                    session.setAttribute("admin",Admin);
                    request.setAttribute("successMsgs", "You have login successfully");
                    
                    RequestDispatcher view = request.getRequestDispatcher("/admin/dashboard.jsp");
                    view.forward(request,response);
                }
            }
            else {
                request.setAttribute("errorMsgs", "You don't have an access!");
                RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
                view.forward(request,response);
            }
                        
        } catch (IOException | SQLException | ServletException ex) {
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    //Hashing Function
    private static String doHashing(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            
            messageDigest.update(password.getBytes());
            
            byte[] resultByteArray = messageDigest.digest();
            
            StringBuilder sb = new StringBuilder();
            
            for(byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
            
        } catch(NoSuchAlgorithmException ex) {
            //ex.printStackTrace();
        }
        return "";
    }
    
    //connect with db and query to run
    private void initializeJdbc() {
        try {
            //declare driver and connection string
            String driver = "org.apache.derby.jdbc.ClientDriver";
            String connectionString = "jdbc:derby://localhost:1527/DentalcareDB;create=true;user=app;password=app";
            
            //load the driver
            Class.forName(driver);
            
            //connect to the database
            Connection conn = DriverManager.getConnection(connectionString);
            
            //sql query to check patient email and password exist in database
            pstmt1 = conn.prepareStatement(
             "SELECT email, password FROM USERS WHERE email=? AND password=? AND role='Patient'"
            );
            
            //sql query to check admin email and password exist in database
            pstmt2 = conn.prepareStatement(
             "SELECT email, password FROM USERS WHERE email=? AND password=? AND role='Admin'"
            );
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }
    
    //method for user account validation
    private String checkPatientAccount(String email, String password) throws SQLException {
        pstmt1.setString(1,email);
        pstmt1.setString(2,password);
        ResultSet rs = pstmt1.executeQuery(); //for execute select query
        String data = null;
        
        if(rs.next()) {
            data = rs.getString(1);
        }
        if(data == null) {
            data = null;
        }
        
        return data;
    }
    
    //method for admin account validation
    private String checkAdminAccount(String email, String password) throws SQLException {
        pstmt2.setString(1,email);
        pstmt2.setString(2,password);
        ResultSet rs = pstmt2.executeQuery(); //for execute select query
        String data = null;
        
        if(rs.next()) {
            data = rs.getString(1);
        }
        if(data == null) {
            data = null;
        }
        
        return data;
    }
}
