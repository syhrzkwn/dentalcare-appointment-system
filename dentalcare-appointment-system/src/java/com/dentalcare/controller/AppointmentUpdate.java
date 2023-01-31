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
public class AppointmentUpdate extends HttpServlet {
    
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

                    Appointment appointment = new Appointment();
                    
                    appointment.setDate(date);
                    appointment.setTime(time);
                    appointment.setStatus(status);
                    appointment.setRemark(remark);
                    appointment.setId(aptmt_id);
                    
                    AppointmentDAO appointmentDAO = new AppointmentDAO();
                    
                    appointmentDAO.updateAppointment(appointment);
                    
                    request.setAttribute("successMsgs", "Appointment details has been updated");
                    RequestDispatcher view = request.getRequestDispatcher("/admin/appointment/edit.jsp?aptmt_id="+aptmt_id);
                    view.forward(request, response);
                    
                    break;
                }
                case "form2":
                {
                    Appointment appointment = new Appointment();
                    
                    appointment.setDate(date);
                    appointment.setTime(time);
                    appointment.setDentistId(dentist_id);
                    appointment.setId(aptmt_id);
                    
                    AppointmentDAO appointmentDAO = new AppointmentDAO();
                    
                    String isDentistAssigned = appointmentDAO.isDentistAssigned(appointment);
                            
                    if(isDentistAssigned != null) {
                        request.setAttribute("errorMsgs", "The chosen dentist has been assigned for other appointment on selected date and time. Select other dentist.");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/appointment/edit.jsp?aptmt_id="+aptmt_id);
                        view.forward(request, response);
                    }
                    else {
                        appointmentDAO.assignDentist(appointment);
                        
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
