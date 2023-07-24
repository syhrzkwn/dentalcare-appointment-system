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
public class PatientUpdate extends HttpServlet {
    
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
        String status = request.getParameter("status");
        String email = request.getParameter("email");
        String email_for_delete = request.getParameter("email_for_delete");
        String new_password = request.getParameter("new_password");
        String confirm_new_password = request.getParameter("confirm_new_password");
        String form = request.getParameter("form");
        String id_param = request.getParameter("id");
        int id = Integer.parseInt(id_param);
        
        try {
            switch(form) {
                case "form1":
                {
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
                        RequestDispatcher view = request.getRequestDispatcher("/admin/patient/edit.jsp?patient_id="+id);
                        view.forward(request, response);
                        return;
                    }
                    
                    Patient patient = new Patient();
                    
                    patient.setFirstName(firstname);
                    patient.setLastName(lastname);
                    patient.setPhone(phone);
                    patient.setStatus(status);
                    patient.setId(id);
                    
                    PatientDAO patientDAO = new PatientDAO();
                    
                    patientDAO.updateProfileInAdmin(patient);
                    
                    request.setAttribute("successMsgs", "Profile has been updated");
                    RequestDispatcher view = request.getRequestDispatcher("/admin/patient/edit.jsp?patient_id="+id);
                    view.forward(request, response);
                    break;
                }
                case "form2":
                {
                    if(email.isEmpty()) {
                        errorMsgs.add("Email is required");
                    }

                    Patient patient = new Patient();
                    
                    patient.setEmail(email);
                    
                    PatientDAO patientDAO = new PatientDAO();
                    
                    patient = patientDAO.isEmailExist(patient);
                    
                    if(patient != null) {
                        errorMsgs.add("Email address is already registered with other account");
                    }

                    if(!errorMsgs.isEmpty()) {
                        request.setAttribute("errorMsgs", errorMsgs);
                        RequestDispatcher view = request.getRequestDispatcher("/admin/patient/edit.jsp?patient_id="+id);
                        view.forward(request, response);
                        return;
                    }

                    Patient new_patient = new Patient();
                    
                    new_patient.setEmail(email);
                    new_patient.setId(id);
                    
                    patientDAO.updateEmail(new_patient);
                    
                    request.setAttribute("successMsgs", "Email has been updated");
                    RequestDispatcher view = request.getRequestDispatcher("/admin/patient/edit.jsp?patient_id="+id);
                    view.forward(request, response);
                    break;
                }
                case "form3":
                {
                    if(new_password.isEmpty()) {
                        errorMsgs.add("New password is required");
                    }
                    
                    if(confirm_new_password.isEmpty()) {
                        errorMsgs.add("Confirm new password is required");
                    }

                    if(!new_password.equals(confirm_new_password)) {
                        errorMsgs.add("Password does not match");
                    }

                    if(!errorMsgs.isEmpty()) {
                        request.setAttribute("errorMsgs", errorMsgs);
                        RequestDispatcher view = request.getRequestDispatcher("/admin/patient/edit.jsp?patient_id="+id);
                        view.forward(request, response);
                        return;
                    }

                    //hashing current password to check password in db
                    String hashPassword = AuthLogin.doHashing(new_password);
                    
                    Patient patient = new Patient();
                    
                    patient.setPassword(hashPassword);
                    patient.setId(id);
                    
                    PatientDAO patientDAO = new PatientDAO();
                    
                    patientDAO.updatePassword(patient);
                    
                    request.setAttribute("successMsgs", "Password has been updated");
                    RequestDispatcher view = request.getRequestDispatcher("/admin/patient/edit.jsp?patient_id="+id);
                    view.forward(request, response);
                    break;
                }
                case "form4":
                {
                    if(email_for_delete.isEmpty()) {
                        errorMsgs.add("Email is required");
                    }
                    
                    Patient patient = new Patient();
                    
                    patient.setEmail(email_for_delete);
                    patient.setId(id);
                    
                    PatientDAO patientDAO = new PatientDAO();
                    
                    patient = patientDAO.checkEmailForDelete(patient);
                    
                    if(patient == null) {
                        errorMsgs.add("Opps! Email doesn't match. Please re-type the patient email");
                    }

                    if(!errorMsgs.isEmpty()) {
                        request.setAttribute("errorMsgs", errorMsgs);
                        RequestDispatcher view = request.getRequestDispatcher("/admin/patient/edit.jsp?patient_id="+id);
                        view.forward(request, response);
                        return;
                    }
                    
                    Patient new_patient = new Patient();

                    new_patient.setEmail(email_for_delete);
                    new_patient.setId(id);

                    String deleteUser = patientDAO.deleteUser(new_patient);
                    
                    if(deleteUser == null) {
                        request.setAttribute("successMsgs", "Patient account has been deleted");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/patient/index.jsp");
                        view.forward(request, response);
                    }
                    // Fixed on delete cascade on appointments table
                    // It will also delete appointment linked to patient
                    /*
                    else {
                        request.setAttribute("errorMsgs", "Patient account cannot be deleted because the patient have a booked appointment!");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/patient/edit.jsp?patient_id="+id);
                        view.forward(request, response);
                    }*/
                    
                    break;
                }
                default:
                {
                    request.setAttribute("errorMsgs", "Something went wrong!");
                    RequestDispatcher view = request.getRequestDispatcher("/admin/patient/edit.jsp?patient_id="+id);
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
