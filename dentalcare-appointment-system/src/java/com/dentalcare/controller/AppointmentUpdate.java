/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.dentalcare.controller;

import com.dentalcare.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
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
public class AppointmentUpdate extends HttpServlet {

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
        
        String status = request.getParameter("status");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String remark = request.getParameter("remark");
        String form = request.getParameter("form");
        
        int dentist_id = Integer.parseInt(request.getParameter("dentist_id"));
        int aptmt_id = Integer.parseInt(request.getParameter("aptmt_id"));
        
        try {
            switch(form) {
                case "form1":
                {                    
                    if(remark.isEmpty()) {
                            errorMsgs.add("Remark is required");
                        }

                    if(!errorMsgs.isEmpty()) {
                        request.setAttribute("errorMsgs", errorMsgs);
                        RequestDispatcher view = request.getRequestDispatcher("/admin/appointment/edit.jsp?aptmt_id="+aptmt_id);
                        view.forward(request, response);
                        return;
                    }

                    updateAppointmentAdmin(date, time, status, remark, aptmt_id);
                    request.setAttribute("successMsgs", "Appointment details has been updated");
                    RequestDispatcher view = request.getRequestDispatcher("/admin/appointment/edit.jsp?aptmt_id="+aptmt_id);
                    view.forward(request, response);
                    
                    break;
                }
                case "form2":
                {
                    if(isDentistAssigned(date, time, dentist_id) != null) {
                        request.setAttribute("errorMsgs", "The chosen dentist has been assigned for other appointment on selected date and time. Select other dentist.");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/appointment/edit.jsp?aptmt_id="+aptmt_id);
                        view.forward(request, response);
                    }
                    else {
                        assignDentist(dentist_id, aptmt_id);
                        request.setAttribute("successMsgs", "Dentist has been assigned successfully");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/appointment/edit.jsp?aptmt_id="+aptmt_id);
                        view.forward(request, response);
                    }
                    
                    break;
                }
                default:
                {
                    request.setAttribute("errorMsgs", "Something went wrong!");
                    RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
                    view.forward(request, response);
                    break;
                }
            }

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
            
            //update appointment data in db query
            pstmt1 = conn.prepareStatement(
             "UPDATE appointments SET aptmt_date=?, aptmt_time=?, aptmt_status=?, aptmt_remark=? WHERE aptmt_id=?"
            );
            
            //assign dentist in appointment data in db query
            pstmt2 = conn.prepareStatement(
             "UPDATE appointments SET dentist_id=? WHERE aptmt_id=?"
            );
            
            //check the dentist has been assigned for selected date and time query
            pstmt3 = conn.prepareStatement(
             "SELECT * FROM appointments WHERE aptmt_date=? AND aptmt_time=? AND dentist_id=? AND aptmt_status != 'Cancelled'"
            );
            
        } catch (SQLException ex) {
        }
    }
    
    //method for update appointment data - staff
    private void updateAppointmentAdmin(String date, String time, String status, String remark, int aptmt) throws SQLException {
        pstmt1.setString(1,date);
        pstmt1.setString(2,time);
        pstmt1.setString(3,status);
        pstmt1.setString(4,remark);
        pstmt1.setInt(5,aptmt);
        pstmt1.executeUpdate();
    }
    
    //method for update appointment data - assign dentist
    private void assignDentist(int dentist, int aptmt) throws SQLException {
        pstmt2.setInt(1,dentist);
        pstmt2.setInt(2,aptmt);
        pstmt2.executeUpdate();
    }
    
    //check if the dentist has been assigned for selected date and time
    private String isDentistAssigned(String date, String time, int dentist) throws SQLException {
        pstmt3.setString(1,date);
        pstmt3.setString(2,time);
        pstmt3.setInt(3,dentist);
        ResultSet rs = pstmt3.executeQuery(); //for execute select query
        
        if(rs.next()) {
            String data = rs.getString(1);
            return data;
        }
        else {
            return null;
        }
    }
}
