package com.dentalcare.controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import com.dentalcare.model.Patient;
import com.dentalcare.model.Dentist;
import com.dentalcare.model.Staff;
import com.dentalcare.dao.PatientDAO;
import com.dentalcare.dao.DentistDAO;
import com.dentalcare.dao.StaffDAO;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
public class AuthLogin extends HttpServlet {

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
        String password = request.getParameter("password");
        String user_type = request.getParameter("user_type");
        
        try {
            if(email.isEmpty()) {
                errorMsgs.add("Email is required");
            }
            
            if(password.isEmpty()) {
                errorMsgs.add("Password is required");
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("$B9f86")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
                view.forward(request, response);
                return;
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("8Dv46$")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/login.jsp"); //change the path later
                view.forward(request, response);
                return;
            }
            
            if(!errorMsgs.isEmpty() && user_type.equals("08y*6M")) {
                request.setAttribute("errorMsgs", errorMsgs);
                RequestDispatcher view = request.getRequestDispatcher("/admin/login.jsp?secret_key=dn3@ZDt8UJ8l");
                view.forward(request, response);
                return;
            }

            //hashing password
            String hashPassword = doHashing(password);
            
            switch (user_type) {
                case "$B9f86":
                {
                    Patient patient = new Patient();
        
                    patient.setEmail(email);
                    patient.setPassword(hashPassword);
                    
                    PatientDAO patientDAO = new PatientDAO();
                    
                    patient = patientDAO.authenticateUser(patient);
        
                    if(patient == null) {
                        request.setAttribute("errorMsgs", "Email or Password is invalid");
                        RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
                        view.forward(request, response);
                    }
                    else {
                        HttpSession session = request.getSession();
                        session.setAttribute("patient", patient);
                        request.setAttribute("successMsgs", "You have login successfully");

                        RequestDispatcher view = request.getRequestDispatcher("/patient/home.jsp");
                        view.forward(request,response);
                    }
                    
                    break;
                }
                case "8Dv46$":
                {
                    Dentist dentist = new Dentist();
        
                    dentist.setEmail(email);
                    dentist.setPassword(hashPassword);
                    
                    DentistDAO dentistDAO = new DentistDAO();
                    
                    dentist = dentistDAO.authenticateUser(dentist);
                    
                    if(dentist == null) {
                        request.setAttribute("errorMsgs", "Email or Password is invalid");
                        RequestDispatcher view = request.getRequestDispatcher("/login.jsp"); //change the path later
                        view.forward(request, response);
                    }
                    else {
                        HttpSession session = request.getSession();
                        session.setAttribute("dentist", dentist);
                        request.setAttribute("successMsgs", "You have login successfully");

                        RequestDispatcher view = request.getRequestDispatcher("/login.jsp"); //change the path later
                        view.forward(request,response);
                    }
                    
                    break;
                }
                case "08y*6M":
                {
                    Staff staff = new Staff();
        
                    staff.setEmail(email);
                    staff.setPassword(hashPassword);
                    
                    StaffDAO staffDAO = new StaffDAO();
                    
                    staff = staffDAO.authenticateUser(staff);
                    
                    if(staff == null) {
                        request.setAttribute("errorMsgs", "Email or Password is invalid");
                        RequestDispatcher view = request.getRequestDispatcher("/admin/login.jsp?secret_key=dn3@ZDt8UJ8l");
                        view.forward(request, response);
                    }
                    else {
                        HttpSession session = request.getSession();
                        session.setAttribute("staff", staff);
                        request.setAttribute("successMsgs", "You have login successfully");

                        RequestDispatcher view = request.getRequestDispatcher("/admin/dashboard.jsp");
                        view.forward(request,response);
                    }
                    
                    break;
                }
                default:
                {
                    request.setAttribute("errorMsgs", "You don't have an access!");
                    RequestDispatcher view = request.getRequestDispatcher("/login.jsp");
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

    //method for hashing password
    protected static String doHashing(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            
            messageDigest.update(password.getBytes());
            
            byte[] resultByteArray = messageDigest.digest();
            
            StringBuilder sb = new StringBuilder();
            
            for(byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }
            
            return sb.toString();
            
        } catch(NoSuchAlgorithmException ex) {
        }
        return "";
    }
}
