/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.dentalcare.controller;

import com.dentalcare.dao.DentistDAO;
import com.dentalcare.model.Dentist;
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
public class DentistUpdate extends HttpServlet {
    
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
                        RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/edit.jsp?dentist_id="+id);
                        view.forward(request, response);
                        return;
                    }

                    Dentist dentist = new Dentist();
                    
                    dentist.setFirstName(firstname);
                    dentist.setLastName(lastname);
                    dentist.setPhone(phone);
                    dentist.setStatus(status);
                    dentist.setId(id);
                    
                    DentistDAO dentistDAO = new DentistDAO();
                    
                    dentistDAO.updateProfile(dentist);
                    
                    request.setAttribute("successMsgs", "Profile has been updated");
                    RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/edit.jsp?dentist_id="+id);
                    view.forward(request, response);
                    break;
                }
                case "form2":
                {
                    if(email.isEmpty()) {
                        errorMsgs.add("Email is required");
                    }

                    Dentist dentist = new Dentist();
                    
                    dentist.setEmail(email);
                    
                    DentistDAO dentistDAO = new DentistDAO();
                    
                    dentist = dentistDAO.isEmailExist(dentist);
                    
                    if(dentist != null) {
                        errorMsgs.add("Email address is already registered with other account");
                    }

                    if(!errorMsgs.isEmpty()) {
                        request.setAttribute("errorMsgs", errorMsgs);
                        RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/edit.jsp?dentist_id="+id);
                        view.forward(request, response);
                        return;
                    }

                    Dentist new_dentist = new Dentist();
                    
                    new_dentist.setEmail(email);
                    new_dentist.setId(id);
                    
                    dentistDAO.updateEmail(new_dentist);
                    
                    request.setAttribute("successMsgs", "Email has been updated");
                    RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/edit.jsp?dentist_id="+id);
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
                        RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/edit.jsp?dentist_id="+id);
                        view.forward(request, response);
                        return;
                    }

                    //hashing current password to check password in db
                    String hashPassword = AuthLogin.doHashing(new_password);
                    
                    Dentist dentist = new Dentist();
                    
                    dentist.setPassword(hashPassword);
                    dentist.setId(id);
                    
                    DentistDAO dentistDAO = new DentistDAO();
                    
                    dentistDAO.updatePassword(dentist);
                    
                    request.setAttribute("successMsgs", "Password has been updated");
                    RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/edit.jsp?dentist_id="+id);
                    view.forward(request, response);
                    break;
                }
                case "form4":
                { 
                    if(email_for_delete.isEmpty()) {
                        errorMsgs.add("Email is required");
                    }
                    
                    if(!errorMsgs.isEmpty()) {
                        request.setAttribute("errorMsgs", errorMsgs);
                        RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/edit.jsp?dentist_id="+id);
                        view.forward(request, response);
                        return;
                    }
                    
                    Dentist dentist = new Dentist();
                    
                    dentist.setEmail(email_for_delete);
                    dentist.setId(id);
                    
                    DentistDAO dentistDAO = new DentistDAO();
                    
                    dentist = dentistDAO.checkEmailForDelete(dentist);
                    
                    if(dentist == null) {
                        request.setAttribute("errorMsgs", "Opps! Email doesn't match. Please re-type the patient email");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/edit.jsp?dentist_id="+id);
                        view.forward(request, response);
                    }
                    else {
                        Dentist new_dentist = new Dentist();

                        new_dentist.setEmail(email_for_delete);
                        new_dentist.setId(id);

                        dentistDAO.deleteUser(new_dentist);

                        request.setAttribute("successMsgs", "Patient account has been deleted");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/index.jsp");
                        view.forward(request, response);
                    }

                    break;
                }
                default:
                {
                    request.setAttribute("errorMsgs", "Something went wrong!");
                    RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/edit.jsp?dentist_id="+id);
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
