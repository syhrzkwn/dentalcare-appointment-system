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
public class AuthRegister extends HttpServlet {
    
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
        
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm_password = request.getParameter("confirm_password");
        String role_code = request.getParameter("role_code");
            
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
            
            if(isEmailExists(email) != null) {
                errorMsgs.add("Email address is already registered with other account");
            }
            
            if(!errorMsgs.isEmpty()) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/signup.jsp");
                view.forward(request, response);
                return;
            }
            
            switch (role_code) {
                case "$B9f86":
                {
                    Patient Patient = new Patient(firstname, lastname, phone, email, password, "Patient");
                    Patient.setFirstName(firstname);
                    Patient.setLastName(lastname);
                    Patient.setPhone(phone);
                    Patient.setEmail(email);
                    Patient.setRole("Patient");
                    
                    String hashPassword = doHashing(password);
                    
                    Patient.setPassword(hashPassword);
                    
                    storeUser(firstname, lastname, phone, email, hashPassword, "Patient");
                    
                    HttpSession session = request.getSession();
                    session.setAttribute("patient",Patient);
                    request.setAttribute("successMsgs", "You have signup successfully");
                    RequestDispatcher view = request.getRequestDispatcher("/patient/appointment.jsp");
                    view.forward(request,response);
                    break;
                }
                case "8Dv46$":
                {
                    Admin Admin = new Admin(firstname, lastname, phone, email, password, "Admin");
                    Admin.setFirstName(firstname);
                    Admin.setLastName(lastname);
                    Admin.setPhone(phone);
                    Admin.setEmail(email);
                    Admin.setRole("Admin");
                    
                    String hashPassword = doHashing(password);
                    
                    Admin.setPassword(hashPassword);
                    
                    storeUser(firstname, lastname, phone, email, hashPassword, "Admin");
                    
                    HttpSession session = request.getSession();
                    session.setAttribute("admin",Admin);
                    request.setAttribute("successMsgs", "You have signup successfully");
                    RequestDispatcher view = request.getRequestDispatcher("/admin/register.jsp");
                    view.forward(request,response);
                    break;
                }
                default:
                {
                    request.setAttribute("errorMsgs", "Something went wrong");
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
            
            //check email exists in db query
            pstmt1 = conn.prepareStatement(
             "SELECT * FROM USERS WHERE email=?"
            );
            
            //store user data in db query
            pstmt2 = conn.prepareStatement(
             "INSERT INTO USERS(firstname, lastname, phone, email, password, role) VALUES(?,?,?,?,?,?)"
            );
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }
    
    //method for email exists checking
    private String isEmailExists(String email) throws SQLException {
        pstmt1.setString(1,email);
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
    
    //method for store user data
    private void storeUser(String firstname, String lastname, String phone, String email, String password, String role) throws SQLException {
        pstmt2.setString(1,firstname);
        pstmt2.setString(2,lastname);
        pstmt2.setString(3,phone);
        pstmt2.setString(4,email);
        pstmt2.setString(5,password);
        pstmt2.setString(6,role);
        pstmt2.executeUpdate();
    }
}
