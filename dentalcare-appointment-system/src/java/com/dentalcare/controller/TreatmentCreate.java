/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.dentalcare.controller;

import com.dentalcare.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
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
public class TreatmentCreate extends HttpServlet {

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
        
        String title = request.getParameter("title");
        String desc = request.getParameter("desc");
        
        try {
            if(title.isEmpty()) {
                errorMsgs.add("Title is required");
            }
            
            if(desc.isEmpty()) {
                errorMsgs.add("Description is required");
            }
            
            if(!errorMsgs.isEmpty()) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/admin/treatment/add.jsp");
                view.forward(request, response);
                return;
            }
            
            //store data in db
            storeTreatment(title, desc);

            request.setAttribute("successMsgs", "Treatment added successfully");
            RequestDispatcher view = request.getRequestDispatcher("/admin/treatment/add.jsp");
            view.forward(request,response);
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

            //connect to the database
            Connection conn = DBConnection.createConnection();
            
            //store treatment query
            pstmt = conn.prepareStatement(
             "INSERT INTO treatments(treat_title, treat_desc) VALUES(?,?)"
            );
        } catch (SQLException ex) {
        }
    }
    
    //method for store treatment data
    private void storeTreatment(String title, String desc) throws SQLException {
        pstmt.setString(1,title);
        pstmt.setString(2,desc);
        pstmt.executeUpdate();
    }
}
