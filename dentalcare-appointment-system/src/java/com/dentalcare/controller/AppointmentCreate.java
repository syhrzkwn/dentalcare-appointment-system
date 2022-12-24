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
public class AppointmentCreate extends HttpServlet {

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
        
        String user_type = request.getParameter("user_type");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String remark = request.getParameter("remark");
        String treatment_param = request.getParameter("treatment");
        String patient_param = request.getParameter("patient");
        
        int treatment = Integer.parseInt(treatment_param);
        int patient = Integer.parseInt(patient_param);
        
        try {
            if(date.isEmpty()) {
                errorMsgs.add("Date is required");
            }
            
            if(time.isEmpty()) {
                errorMsgs.add("Time is required");
            }
            
            if(remark.isEmpty()) {
                errorMsgs.add("Remark is required");
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("$B9f86")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/"); //Change path later
                view.forward(request, response);
                return;
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("08y*6M")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/admin/appointment/add.jsp");
                view.forward(request, response);
                return;
            }
            
            switch(user_type) {
                case "$B9f86":
                {
                    //can book appointment with same date and time up to 3 times only!
                    //start with 0
                    if(checkDatetime(date, time) >= 3) {
                        request.setAttribute("errorMsgs", "The number of appointments booked on the selected date and time has exceeded the limit");
                        RequestDispatcher view = request.getRequestDispatcher("/"); //change the path later
                        view.forward(request, response);
                    }
                    else {
                        //store data in db
                        storeAppointment(date, time, remark, patient, treatment);

                        request.setAttribute("successMsgs", "Appointment booked successfully");
                        RequestDispatcher view = request.getRequestDispatcher("/"); //change the path later
                        view.forward(request,response);
                    }
                    break;
                }   
                case "08y*6M":
                {
                    //can book appointment with same date and time up to 3 times only!
                    //start with 0
                    if(checkDatetime(date, time) >= 3) {
                        request.setAttribute("errorMsgs", "The number of appointments booked on the selected date and time has exceeded the limit");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/appointment/add.jsp");
                        view.forward(request, response);
                    }
                    else {
                        //store data in db
                        storeAppointment(date, time, remark, patient, treatment);

                        request.setAttribute("successMsgs", "Appointment booked successfully");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/appointment/add.jsp");
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
            
            //store appointment data in db query
            pstmt1 = conn.prepareStatement(
             "INSERT INTO appointments(aptmt_date, aptmt_time, aptmt_remark, patient_id, treat_id) VALUES(?,?,?,?,?)"
            );
            
            //check count appointment date and time choosen
            pstmt2 = conn.prepareStatement(
             "SELECT COUNT(*) AS count FROM appointments WHERE aptmt_date=? AND aptmt_time=? AND aptmt_status != 'Cancelled'"
            );
            
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }
    
    //method for store appointment data
    private void storeAppointment(String date, String time, String remark, int patient, int treat) throws SQLException {
        pstmt1.setString(1,date);
        pstmt1.setString(2,time);
        pstmt1.setString(3,remark);
        pstmt1.setInt(4,patient);
        pstmt1.setInt(5,treat);
        pstmt1.executeUpdate();
    }
    
    private int checkDatetime(String date, String time) throws SQLException {
        pstmt2.setString(1,date);
        pstmt2.setString(2,time);
        ResultSet rs = pstmt2.executeQuery(); //for execute select query
        
        int data = 0;
        if(rs.next()) {
            data = rs.getInt("count");
        }
        return data;
    }
}
