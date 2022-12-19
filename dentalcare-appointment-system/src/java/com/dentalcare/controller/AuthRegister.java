package com.dentalcare.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import com.dentalcare.model.Patient;
import java.io.IOException;
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
public class AuthRegister extends HttpServlet {
    
    private PreparedStatement pstmt1, pstmt2, pstmt3, pstmt4, pstmt5, pstmt6, pstmt7;
    
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
        
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm_password = request.getParameter("confirm_password");
        String user_type = request.getParameter("user_type");
            
        try {
            if(firstname.isEmpty()) {
                errorMsgs.add("First name is required");
            }
            
            if(lastname.isEmpty()) {
                errorMsgs.add("Last name is required");
            }
            
            if(phone.isEmpty()) {
                errorMsgs.add("Phone number is required");
            }
            
            if(email.isEmpty()) {
                errorMsgs.add("Email is required");
            }
            
            if(password.isEmpty()) {
                errorMsgs.add("Password is required");
            }
            
            if(!password.equals(confirm_password)) {
                errorMsgs.add("Password does not match");
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("$B9f861")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/signup.jsp");
                view.forward(request, response);
                return;
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("$B9f862")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/admin/patient/add.jsp");
                view.forward(request, response);
                return;
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("8Dv46$")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/add.jsp");
                view.forward(request, response);
                return;
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("08y*6M")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/admin/staff/add.jsp");
                view.forward(request, response);
                return;
            }
            
            switch (user_type) {
                case "$B9f861":
                {
                    if(isPatientEmailExists(email) != null) {
                        request.setAttribute("errorMsgs", "Email address is already registered with other account");
                        RequestDispatcher view = request.getRequestDispatcher("/signup.jsp");
                        view.forward(request, response);
                    }
                    else {
                        //hashing password
                        String hashPassword = AuthLogin.doHashing(password);

                        //store data in db
                        storePatient(firstname, lastname, phone, email, hashPassword);

                        HttpSession session = request.getSession();
                        session.setAttribute("patient", getPatientData(email,hashPassword));
                        request.setAttribute("successMsgs", "You have signup successfully");
                        RequestDispatcher view = request.getRequestDispatcher("/patient/home.jsp");
                        view.forward(request,response);
                    }
                    
                    break;
                }
                case "$B9f862":
                {
                    if(isPatientEmailExists(email) != null) {
                        request.setAttribute("errorMsgs", "Email address is already registered with other account");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/patient/add.jsp");
                        view.forward(request, response);
                    }
                    else {
                        //hashing password
                        String hashPassword = AuthLogin.doHashing(password);

                        //store data in db
                        storePatient(firstname, lastname, phone, email, hashPassword);

                        HttpSession session = request.getSession();
                        session.setAttribute("patient", getPatientData(email,hashPassword));
                        request.setAttribute("successMsgs", "You have signup successfully");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/patient/add.jsp");
                        view.forward(request,response);
                    }
                    
                    break;
                }
                case "8Dv46$":
                {
                    if(isDentistEmailExists(email) != null) {
                        request.setAttribute("errorMsgs", "Email address is already registered with other account");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/add.jsp");
                        view.forward(request, response);
                    }
                    else {
                        //hashing password
                        String hashPassword = AuthLogin.doHashing(password);

                        //store data in db
                        storeDentist(firstname, lastname, phone, email, hashPassword);

                        request.setAttribute("successMsgs", "You have signup successfully");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/add.jsp");
                        view.forward(request,response);
                    }
                    
                    break;
                }
                case "08y*6M":
                {
                    if(isStaffEmailExists(email) != null) {
                        request.setAttribute("errorMsgs", "Email address is already registered with other account");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/staff/add.jsp");
                        view.forward(request, response);
                    }
                    else {
                        //hashing password
                        String hashPassword = AuthLogin.doHashing(password);

                        //store data in db
                        storeStaff(firstname, lastname, phone, email, hashPassword);

                        request.setAttribute("successMsgs", "You have signup successfully");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/staff/add.jsp");
                        view.forward(request,response);
                    }
                    
                    break;
                }
                default:
                {
                    request.setAttribute("errorMsgs", "Opps! Sorry, something went wrong");
                    RequestDispatcher view = request.getRequestDispatcher("/signup.jsp");
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
            
            //check email exists in patient, dentist, staff db query
            pstmt1 = conn.prepareStatement(
             "SELECT * FROM patients WHERE patient_email=?"
            );
            pstmt2 = conn.prepareStatement(
             "SELECT * FROM dentists WHERE dentist_email=?"
            );
            pstmt3 = conn.prepareStatement(
             "SELECT * FROM staffs WHERE staff_email=?"
            );
            
            //store patient, dentist, staff data in db query
            pstmt4 = conn.prepareStatement(
             "INSERT INTO patients(patient_firstname, patient_lastname, patient_phone, patient_email, patient_password) VALUES(?,?,?,?,?)"
            );
            pstmt5 = conn.prepareStatement(
             "INSERT INTO dentists(dentist_firstname, dentist_lastname, dentist_phone, dentist_email, dentist_password) VALUES(?,?,?,?,?)"
            );
            pstmt6 = conn.prepareStatement(
             "INSERT INTO staffs(staff_firstname, staff_lastname, staff_phone, staff_email, staff_password) VALUES(?,?,?,?,?)"
            );
            
            //sql query to get patient, dentist, staff data
            pstmt7 = conn.prepareStatement(
             "SELECT * FROM patients WHERE patient_email=? AND patient_password=?"
            );
            
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }
    
    //method for patient email exists checking
    private String isPatientEmailExists(String email) throws SQLException {
        pstmt1.setString(1,email);
        ResultSet rs = pstmt1.executeQuery(); //for execute select query
        
        if(rs.next()) {
            String data = rs.getString(1);
            return data;
        }
        else {
            return null;
        }
    }
    
    //method for patient email exists checking
    private String isDentistEmailExists(String email) throws SQLException {
        pstmt2.setString(1,email);
        ResultSet rs = pstmt2.executeQuery(); //for execute select query
        
        if(rs.next()) {
            String data = rs.getString(1);
            return data;
        }
        else {
            return null;
        }
    }
    
    //method for patient email exists checking
    private String isStaffEmailExists(String email) throws SQLException {
        pstmt3.setString(1,email);
        ResultSet rs = pstmt3.executeQuery(); //for execute select query
        
        if(rs.next()) {
            String data = rs.getString(1);
            return data;
        }
        else {
            return null;
        }
    }
    
    //method for store patient data
    private void storePatient(String firstname, String lastname, String phone, String email, String password) throws SQLException {
        pstmt4.setString(1,firstname);
        pstmt4.setString(2,lastname);
        pstmt4.setString(3,phone);
        pstmt4.setString(4,email);
        pstmt4.setString(5,password);
        pstmt4.executeUpdate();
    }
    
    //method for store dentist data
    private void storeDentist(String firstname, String lastname, String phone, String email, String password) throws SQLException {
        pstmt5.setString(1,firstname);
        pstmt5.setString(2,lastname);
        pstmt5.setString(3,phone);
        pstmt5.setString(4,email);
        pstmt5.setString(5,password);
        pstmt5.executeUpdate();
    }
    
    //method for store staff data
    private void storeStaff(String firstname, String lastname, String phone, String email, String password) throws SQLException {
        pstmt6.setString(1,firstname);
        pstmt6.setString(2,lastname);
        pstmt6.setString(3,phone);
        pstmt6.setString(4,email);
        pstmt6.setString(5,password);
        pstmt6.executeUpdate();
    }
    
    //method for check and get data patient account
    private Patient getPatientData(String email, String password) throws SQLException {
        pstmt7.setString(1,email);
        pstmt7.setString(2,password);
        ResultSet rs = pstmt7.executeQuery(); //for execute select query

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
}
