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
public class PatientAccountPasswordUpdate extends HttpServlet {
    
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
        String password = request.getParameter("current_password");
        String new_password = request.getParameter("new_password");
        String confirm_new_password = request.getParameter("confirm_new_password");
        String id_param = request.getParameter("id");
        int id = Integer.parseInt(id_param);
                
        try {
            if(email.isEmpty()) {
                errorMsgs.add("Email is required");
            }

            if(password.isEmpty()) {
                errorMsgs.add("Current password is required");
            }

            if(new_password.isEmpty()) {
                errorMsgs.add("New password is required");
            }

            if(!new_password.equals(confirm_new_password)) {
                errorMsgs.add("Password does not match");
            }

            if(!errorMsgs.isEmpty()) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/patient/account.jsp");
                view.forward(request, response);
                return;
            }
                
            //hashing current password to check password in db
            String checkPassword = AuthLogin.doHashing(password);

            Patient patient = new Patient();
            
            patient.setEmail(email);
            patient.setPassword(checkPassword);
            
            PatientDAO patientDAO = new PatientDAO();
            
            patient = patientDAO.authenticateUser(patient);
            
            if(patient == null) {
                request.setAttribute("errorMsgs", "Current password is invalid");
                RequestDispatcher view = request.getRequestDispatcher("/patient/account.jsp");
                view.forward(request, response);
            }
            else {
                //hashing new password to store in db
                String hashPassword = AuthLogin.doHashing(new_password);
                
                Patient new_patient = new Patient();
                
                new_patient.setId(id);
                new_patient.setPassword(hashPassword);
                
                patientDAO.updatePassword(new_patient);
                
                request.setAttribute("successMsgs", "Password has been updated");
                RequestDispatcher view = request.getRequestDispatcher("/patient/account.jsp");
                view.forward(request, response);
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
