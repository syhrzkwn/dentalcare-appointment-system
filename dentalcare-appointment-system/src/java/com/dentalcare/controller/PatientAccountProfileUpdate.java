/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.dentalcare.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author syahir
 */
public class PatientAccountProfileUpdate extends HttpServlet {

    private PreparedStatement pstmt;
    
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
        String id_param = request.getParameter("id");
        int id = Integer.parseInt(id_param);
        
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

            if(!errorMsgs.isEmpty()) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/patient/account.jsp");
                view.forward(request, response);
                return;
            }

            updateProfile(firstname, lastname, phone, id);
            request.setAttribute("successMsgs", "Profile has been updated");
            RequestDispatcher view = request.getRequestDispatcher("/patient/account.jsp");
            view.forward(request, response);

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
            
            //update patient profile
            pstmt = conn.prepareStatement(
             "UPDATE patients SET patient_firstname=?, patient_lastname=?, patient_phone=? WHERE patient_id=?"
            );
            
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }
    
    //method to update patient profile
    private void updateProfile(String firstname, String lastname, String phone, int id) throws SQLException {
        pstmt.setString(1,firstname);
        pstmt.setString(2,lastname);
        pstmt.setString(3,phone);
        pstmt.setInt(4,id);
        pstmt.executeUpdate();
    }
}
