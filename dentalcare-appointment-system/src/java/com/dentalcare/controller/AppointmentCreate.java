/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.dentalcare.controller;

import com.dentalcare.dao.AppointmentDAO;
import com.dentalcare.model.Appointment;
import java.io.IOException;
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
        
        // treatment id and patient id
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
                RequestDispatcher view = request.getRequestDispatcher("/patient/book_appointment.jsp");
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
                    
                    Appointment appointment = new Appointment();
                    
                    appointment.setDate(date);
                    appointment.setTime(time);
                    appointment.setPatientId(patient);
                    
                    AppointmentDAO appointmentDAO = new AppointmentDAO();
                    
                    int checkDatetime = appointmentDAO.checkDatetime(appointment);
                    
                    if(checkDatetime >= 3) {
                        request.setAttribute("errorMsgs", "The number of appointments booked on the selected date and time has exceeded the limit");
                        RequestDispatcher view = request.getRequestDispatcher("/patient/book_appointment.jsp");
                        view.forward(request, response);
                    }
                    else {
                        //update patient first time booked status
                        String checkFirstAppointment = appointmentDAO.checkFirstAppointment(appointment);
                        
                        if(checkFirstAppointment.equals("N")) {
                            appointmentDAO.updateFirstAppointment(appointment);
                        }
                        
                        Appointment new_aptmt = new Appointment();
                        
                        new_aptmt.setDate(date);
                        new_aptmt.setTime(time);
                        new_aptmt.setRemark(remark);
                        new_aptmt.setPatientId(patient);
                        new_aptmt.setTreatId(treatment);
                        
                        appointmentDAO.storeAppointment(new_aptmt);

                        request.setAttribute("successMsgs", "Appointment booked successfully");
                        RequestDispatcher view = request.getRequestDispatcher("/patient/home.jsp");
                        view.forward(request,response);
                    }
                    break;
                }   
                case "08y*6M":
                {
                    //can book appointment with same date and time up to 3 times only!
                    //start with 0
                    
                    Appointment appointment = new Appointment();
                    
                    appointment.setDate(date);
                    appointment.setTime(time);
                    appointment.setPatientId(patient);
                    
                    AppointmentDAO appointmentDAO = new AppointmentDAO();
                    
                    int checkDatetime = appointmentDAO.checkDatetime(appointment);
                    
                    if(checkDatetime >= 3) {
                        request.setAttribute("errorMsgs", "The number of appointments booked on the selected date and time has exceeded the limit");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/appointment/add.jsp");
                        view.forward(request, response);
                    }
                    else {
                        //update patient first time booked status
                        String checkFirstAppointment = appointmentDAO.checkFirstAppointment(appointment);
                        
                        if(checkFirstAppointment.equals("N")) {
                            appointmentDAO.updateFirstAppointment(appointment);
                        }
                        
                        Appointment new_aptmt = new Appointment();
                        
                        new_aptmt.setDate(date);
                        new_aptmt.setTime(time);
                        new_aptmt.setRemark(remark);
                        new_aptmt.setPatientId(patient);
                        new_aptmt.setTreatId(treatment);
                        
                        appointmentDAO.storeAppointment(new_aptmt);
                        
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
        } catch(IOException | ServletException ex) {
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
}
