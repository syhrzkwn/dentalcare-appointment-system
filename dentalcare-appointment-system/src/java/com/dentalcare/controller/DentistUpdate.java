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
public class DentistUpdate extends HttpServlet {

    private PreparedStatement pstmt1, pstmt2, pstmt3, pstmt4, pstmt5;
    
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

                    updateProfile(firstname, lastname, phone, status, id);
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

                    if(isEmailExists(email) != null) {
                        errorMsgs.add("Email address is already registered with other account");
                    }

                    if(!errorMsgs.isEmpty()) {
                        request.setAttribute("errorMsgs", errorMsgs);
                        RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/edit.jsp?dentist_id="+id);
                        view.forward(request, response);
                        return;
                    }

                    updateEmail(email, id);
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
                    updatePassword(hashPassword, id);
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
                    
                    if(isEmailExists(email_for_delete) == null) {
                        errorMsgs.add("Opps! Email doesn't match. Please re-type the patient email");
                    }

                    if(!errorMsgs.isEmpty()) {
                        request.setAttribute("errorMsgs", errorMsgs);
                        RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/edit.jsp?dentist_id="+id);
                        view.forward(request, response);
                        return;
                    }
                    
                    deleteAccount(email_for_delete, id);
                    request.setAttribute("successMsgs", "Patient account has been deleted");
                    RequestDispatcher view = request.getRequestDispatcher("/admin/dentist/index.jsp");
                    view.forward(request, response);
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
            
            //update dentist profile
            pstmt1 = conn.prepareStatement(
             "UPDATE dentists SET dentist_firstname=?, dentist_lastname=?, dentist_phone=?, dentist_status=? WHERE dentist_id=?"
            );
            
            //check email exists in db query
            pstmt2 = conn.prepareStatement(
             "SELECT dentist_email FROM dentists WHERE dentist_email=?"
            );
            
            //update dentist email
            pstmt3 = conn.prepareStatement(
             "UPDATE dentists SET dentist_email=? WHERE dentist_id=?"
            );
            
            //update dentist password
            pstmt4 = conn.prepareStatement(
             "UPDATE dentists SET dentist_password=? WHERE dentist_id=?"
            );
            
            //delete dentist account
            pstmt5 = conn.prepareStatement(
             "DELETE FROM dentists WHERE dentist_email=? AND dentist_id=?"
            );
            
        } catch (SQLException ex) {
        }
    }
    
    //method to update account profile
    private void updateProfile(String firstname, String lastname, String phone, String status, int id) throws SQLException {
        pstmt1.setString(1,firstname);
        pstmt1.setString(2,lastname);
        pstmt1.setString(3,phone);
        pstmt1.setString(4, status);
        pstmt1.setInt(5,id);
        pstmt1.executeUpdate();
    }
    
    //method for email exists checking
    protected String isEmailExists(String email) throws SQLException {
        pstmt2.setString(1,email);
        ResultSet rs = pstmt2.executeQuery(); //for execute select query
        
        if(rs.next()) {
            String data;
            data = rs.getString("dentist_email");
            return data;
        }
        else {
            return null;
        }
    }
    
    //method to update patient email
    private void updateEmail(String email, int id) throws SQLException {
        pstmt3.setString(1,email);
        pstmt3.setInt(2,id);
        pstmt3.executeUpdate();
    }
    
    //method to update patient password
    private void updatePassword(String password, int id) throws SQLException {
        pstmt4.setString(1,password);
        pstmt4.setInt(2,id);
        pstmt4.executeUpdate();
    }
    
    //method to update patient password
    private void deleteAccount(String email, int id) throws SQLException {
        pstmt5.setString(1,email);
        pstmt5.setInt(2,id);
        pstmt5.executeUpdate();
    }
}
