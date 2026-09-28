package com.dentalcare.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import com.dentalcare.dao.PatientDAO;
import com.dentalcare.dao.StaffDAO;
import com.dentalcare.dao.DentistDAO;
import com.dentalcare.model.Patient;
import com.dentalcare.model.Staff;
import com.dentalcare.model.Dentist;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author syahir
 */
public class AuthRegister extends HttpServlet {
    
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
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm_password = request.getParameter("confirm_password");
        String user_type = request.getParameter("user_type");
            
        try {
            if(firstname.isEmpty()) {
                errorMsgs.add("First name is required");
            }
            
            if(lastname.isEmpty()) {
                errorMsgs.add("Last name is required");
            }
            
            if(phone.isEmpty()) {
                errorMsgs.add("Phone number is required");
            }
            
            if(email.isEmpty()) {
                errorMsgs.add("Email is required");
            }
            
            if(password.isEmpty()) {
                errorMsgs.add("Password is required");
            }
            
            if(!password.equals(confirm_password)) {
                errorMsgs.add("Password does not match");
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("$B9f861")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/signup.jsp");
                view.forward(request, response);
                return;
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("$B9f862")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/admin/patient/add.jsp");
                view.forward(request, response);
                return;
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("8Dv46$")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/add.jsp");
                view.forward(request, response);
                return;
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("08y*6M")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/admin/staff/add.jsp");
                view.forward(request, response);
                return;
            }
            
            switch (user_type) {
                case "$B9f861":
                {
                    Patient patient = new Patient();
        
                    patient.setEmail(email);
                    
                    PatientDAO patientDAO = new PatientDAO();
                    
                    patient = patientDAO.isEmailExist(patient);
                    
                    if(patient != null) {
                        request.setAttribute("errorMsgs", "Email address is already registered with other account");
                        RequestDispatcher view = request.getRequestDispatcher("/signup.jsp");
                        view.forward(request, response);
                    }
                    else {
                        //hashing password
                        String hashPassword = AuthLogin.doHashing(password);

                        Patient new_patient = new Patient();
                        
                        new_patient.setFirstName(firstname);
                        new_patient.setLastName(lastname);
                        new_patient.setPhone(phone);
                        new_patient.setEmail(email);
                        new_patient.setPassword(hashPassword);
                        
                        new_patient = patientDAO.storeUser(new_patient);
                        
                        HttpSession session = request.getSession();
                        session.setAttribute("patient", new_patient);
                        request.setAttribute("successMsgs", "You have signup successfully");
                        RequestDispatcher view = request.getRequestDispatcher("/patient/book_appointment.jsp");
                        view.forward(request,response);
                    }
                    
                    break;
                }
                case "$B9f862":
                {
                    Patient patient = new Patient();
        
                    patient.setEmail(email);
                    
                    PatientDAO patientDAO = new PatientDAO();
                    
                    patient = patientDAO.isEmailExist(patient);
                    
                    if(patient != null) {
                        request.setAttribute("errorMsgs", "Email address is already registered with other account");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/patient/add.jsp");
                        view.forward(request, response);
                    }
                    else {
                        //hashing password
                        String hashPassword = AuthLogin.doHashing(password);

                        Patient new_patient = new Patient();
                        
                        new_patient.setFirstName(firstname);
                        new_patient.setLastName(lastname);
                        new_patient.setPhone(phone);
                        new_patient.setEmail(email);
                        new_patient.setPassword(hashPassword);
                        
                        patientDAO.storeUser(new_patient);

                        request.setAttribute("successMsgs", "You have signup successfully");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/patient/add.jsp");
                        view.forward(request,response);
                    }
                    
                    break;
                }
                case "8Dv46$":
                {
                    Dentist dentist = new Dentist();
        
                    dentist.setEmail(email);
                    
                    DentistDAO dentistDAO = new DentistDAO();
                    
                    dentist = dentistDAO.isEmailExist(dentist);
                    
                    if(dentist != null) {
                        request.setAttribute("errorMsgs", "Email address is already registered with other account");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/add.jsp");
                        view.forward(request, response);
                    }
                    else {
                        //hashing password
                        String hashPassword = AuthLogin.doHashing(password);

                        Dentist new_dentist = new Dentist();
                        
                        new_dentist.setFirstName(firstname);
                        new_dentist.setLastName(lastname);
                        new_dentist.setPhone(phone);
                        new_dentist.setEmail(email);
                        new_dentist.setPassword(hashPassword);
                        
                        dentistDAO.storeUser(new_dentist);

                        request.setAttribute("successMsgs", "You have signup successfully");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/add.jsp");
                        view.forward(request,response);
                    }
                    
                    break;
                }
                case "08y*6M":
                {
                    Staff staff = new Staff();
        
                    staff.setEmail(email);
                    
                    StaffDAO staffDAO = new StaffDAO();
                    
                    staff = staffDAO.isEmailExist(staff);
                    
                    if(staff != null) {
                        request.setAttribute("errorMsgs", "Email address is already registered with other account");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/staff/add.jsp");
                        view.forward(request, response);
                    }
                    else {
                        //hashing password
                        String hashPassword = AuthLogin.doHashing(password);

                        Staff new_staff = new Staff();
                        
                        new_staff.setFirstName(firstname);
                        new_staff.setLastName(lastname);
                        new_staff.setPhone(phone);
                        new_staff.setEmail(email);
                        new_staff.setPassword(hashPassword);
                        
                        staffDAO.storeUser(new_staff);

                        request.setAttribute("successMsgs", "You have signup successfully");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/staff/add.jsp");
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
