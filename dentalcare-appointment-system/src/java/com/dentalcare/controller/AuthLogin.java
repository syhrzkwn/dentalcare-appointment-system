package com.dentalcare.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import com.dentalcare.model.Patient;
import com.dentalcare.model.Dentist;
import com.dentalcare.model.Staff;
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

    private PreparedStatement pstmt1, pstmt2, pstmt3;
    
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
        String user_type = request.getParameter("user_type");
        
        try {
            if(email.isEmpty()) {
                errorMsgs.add("Email is required");
            }
            
            if(password.isEmpty()) {
                errorMsgs.add("Password is required");
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("$B9f86")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
                view.forward(request, response);
                return;
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("8Dv46$")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/login.jsp"); //change the path later
                view.forward(request, response);
                return;
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("08y*6M")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/admin/login.jsp?secret_key=dn3@ZDt8UJ8l");
                view.forward(request, response);
                return;
            }

            //hashing password
            String hashPassword = doHashing(password);
            
            switch (user_type) {
                case "$B9f86":
                {
                    if(checkPatientAccount(email, hashPassword) == null) {
                        request.setAttribute("errorMsgs", "Email or Password is invalid");
                        RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
                        view.forward(request, response);
                    }
                    else {
                        HttpSession session = request.getSession();
                        session.setAttribute("patient",checkPatientAccount(email, hashPassword));
                        request.setAttribute("successMsgs", "You have login successfully");

                        RequestDispatcher view = request.getRequestDispatcher("/patient/home.jsp");
                        view.forward(request,response);
                    }
                    
                    break;
                }
                case "8Dv46$":
                {
                    if(checkDentistAccount(email, hashPassword) == null) {
                        request.setAttribute("errorMsgs", "Email or Password is invalid");
                        RequestDispatcher view = request.getRequestDispatcher("/login.jsp"); //change the path later
                        view.forward(request, response);
                    }
                    else {
                        HttpSession session = request.getSession();
                        session.setAttribute("dentist",checkDentistAccount(email, hashPassword));
                        request.setAttribute("successMsgs", "You have login successfully");

                        RequestDispatcher view = request.getRequestDispatcher("/login.jsp"); //change the path later
                        view.forward(request,response);
                    }
                    
                    break;
                }
                case "08y*6M":
                {
                    if(checkStaffAccount(email, hashPassword) == null) {
                        request.setAttribute("errorMsgs", "Email or Password is invalid");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/login.jsp?secret_key=dn3@ZDt8UJ8l");
                        view.forward(request, response);
                    }
                    else {
                        HttpSession session = request.getSession();
                        session.setAttribute("staff",checkStaffAccount(email, hashPassword));
                        request.setAttribute("successMsgs", "You have login successfully");

                        RequestDispatcher view = request.getRequestDispatcher("/admin/dashboard.jsp");
                        view.forward(request,response);
                    }
                    
                    break;
                }
                default:
                {
                    request.setAttribute("errorMsgs", "You don't have an access!");
                    RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
                    view.forward(request,response);
                    break;
                }
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

    //method for hashing password
    protected static String doHashing(String password) {
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
            
            //sql query to check and get data active patient account
            pstmt1 = conn.prepareStatement(
             "SELECT * FROM patients WHERE patient_email=? AND patient_password=? AND patient_status='Active'"
            );
            
            //sql query to check and get data admin account
            pstmt2 = conn.prepareStatement(
             "SELECT * FROM dentists WHERE dentist_email=? AND dentist_password=?"
            );
            
            //sql query to check and get data admin account
            pstmt3 = conn.prepareStatement(
             "SELECT * FROM staffs WHERE staff_email=? AND staff_password=?"
            );
            
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }
    
    //method for check and get data patient account
    private Patient checkPatientAccount(String email, String password) throws SQLException {
        pstmt1.setString(1,email);
        pstmt1.setString(2,password);
        ResultSet rs = pstmt1.executeQuery(); //for execute select query
        
        if(rs.next()) {
            Patient data = new Patient();
            data.setId(rs.getInt("patient_id"));
            data.setFirstName(rs.getString("patient_firstname"));
            data.setLastName(rs.getString("patient_lastname"));
            data.setPhone(rs.getString("patient_phone"));
            data.setEmail(rs.getString("patient_email"));
            data.setStatus(rs.getString("patient_status"));
            return data;
        }
        else {
            return null;
        }
    }
    
    //method for check and get data dentist account
    private Dentist checkDentistAccount(String email, String password) throws SQLException {
        pstmt2.setString(1,email);
        pstmt2.setString(2,password);
        ResultSet rs = pstmt2.executeQuery(); //for execute select query
        
        if(rs.next()) {
            Dentist data = new Dentist();
            data.setId(rs.getInt("dentist_id"));
            data.setFirstName(rs.getString("dentist_firstname"));
            data.setLastName(rs.getString("dentist_lastname"));
            data.setPhone(rs.getString("dentist_phone"));
            data.setEmail(rs.getString("dentist_email"));
            data.setStatus(rs.getString("dentist_status"));
            return data;
        }
        else {
            return null;
        }
    }
    
    //method for check and get data staff account
    private Staff checkStaffAccount(String email, String password) throws SQLException {
        pstmt3.setString(1,email);
        pstmt3.setString(2,password);
        ResultSet rs = pstmt3.executeQuery(); //for execute select query
        
        if(rs.next()) {
            Staff data = new Staff();
            data.setId(rs.getInt("staff_id"));
            data.setFirstName(rs.getString("staff_firstname"));
            data.setLastName(rs.getString("staff_lastname"));
            data.setPhone(rs.getString("staff_phone"));
            data.setEmail(rs.getString("staff_email"));
            data.setStatus(rs.getString("staff_status"));
            return data;
        }
        else {
            return null;
        }
    }
}
