/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.dentalcare.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class AccountEmailUpdate extends HttpServlet {

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
        String id_param = request.getParameter("id");
        int id = Integer.parseInt(id_param);

        try {
            if(email.isEmpty()) {
                errorMsgs.add("Email is required");
            }

            if(isEmailExists(email) != null) {
                errorMsgs.add("Email address is already registered with other account");
            }
            
            if(!errorMsgs.isEmpty()) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/admin/account.jsp");
                view.forward(request, response);
                return;
            }
            
            updateEmail(email, id);
            request.setAttribute("successMsgs", "Email has been updated");
            RequestDispatcher view = request.getRequestDispatcher("/admin/account.jsp");
            view.forward(request, response);
            
        } catch(IOException | SQLException | ServletException ex) {
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
            
            //check email exists in db query
            pstmt1 = conn.prepareStatement(
             "SELECT * FROM staffs WHERE staff_email=?"
            );
            
            //update staff email
            pstmt2 = conn.prepareStatement(
             "UPDATE staffs SET staff_email=? WHERE staff_id=?"
            );
            
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }
    
    //method for email exists checking
    protected String isEmailExists(String email) throws SQLException {
        pstmt1.setString(1,email);
        ResultSet rs = pstmt1.executeQuery(); //for execute select query
        
        if(rs.next()) {
            String data;
            data = rs.getString(1);
            return data;
        }
        else {
            return null;
        }
    }
    
    //method to update staff email
    private void updateEmail(String email, int id) throws SQLException {
        pstmt2.setString(1,email);
        pstmt2.setInt(2,id);
        pstmt2.executeUpdate();
    }
}
