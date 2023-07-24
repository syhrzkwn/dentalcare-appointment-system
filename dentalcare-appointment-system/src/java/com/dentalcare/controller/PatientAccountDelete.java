/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.dentalcare.controller;

import com.dentalcare.dao.PatientDAO;
import com.dentalcare.model.Patient;
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
public class PatientAccountDelete extends HttpServlet {
    
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
        
        String email_for_delete = request.getParameter("email_for_delete");
        String id_param = request.getParameter("id");
        int id = Integer.parseInt(id_param);
                
        try {
            Patient patient = new Patient();
            
            patient.setEmail(email_for_delete);
            patient.setId(id);
            
            PatientDAO patientDAO = new PatientDAO();
            
            if(email_for_delete.isEmpty()) {
                errorMsgs.add("Email is required");
            }
            
            if(!errorMsgs.isEmpty()) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/patient/account.jsp");
                view.forward(request, response);
                return;
            }
            
            patient = patientDAO.checkEmailForDelete(patient);
            
            if(patient == null) {
                request.setAttribute("errorMsgs", "Opps! Email doesn't match. Please re-type the patient email");
                RequestDispatcher view = request.getRequestDispatcher("/patient/account.jsp");
                view.forward(request, response);
            }
            else {
                Patient new_patient = new Patient();

                new_patient.setEmail(email_for_delete);
                new_patient.setId(id);

                String deleteUser = patientDAO.deleteUser(new_patient);

                if(deleteUser == null) {
                    request.setAttribute("successMsgs", "Your account has been deleted");
                    RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
                    view.forward(request, response);
                }
                // Fixed on delete cascade on appointments table
                // It will also delete appointment linked to patient
                /*
                else {
                    request.setAttribute("errorMsgs", "Sorry but we cannot delete your account because you still have a booked appointment with us!");
                    RequestDispatcher view = request.getRequestDispatcher("/patient/account.jsp");
                    view.forward(request, response);
                }*/
            }
            
        } catch (IOException | ServletException ex) {
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
