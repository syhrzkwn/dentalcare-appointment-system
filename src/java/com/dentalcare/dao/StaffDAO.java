/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dentalcare.dao;

import com.dentalcare.model.Staff;
import com.dentalcare.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Syahir
 */
public class StaffDAO {
    private Connection con = null;
    private Statement statement = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;

    private int staff_id = 0;
    private String staff_firstname = "";
    private String staff_lastname = "";
    private String staff_phone = "";
    private String staff_email = "";
    private String staff_password = "";
    private String staff_status = "";
        
    public Staff authenticateUser(Staff staff){
        
        String email = staff.getEmail();
        String password = staff.getPassword();
            
        try {
            con = DBConnection.createConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM staffs");
            
            while (resultSet.next()) {
                staff_id = resultSet.getInt("staff_id");
                staff_firstname = resultSet.getString("staff_firstname");
                staff_lastname = resultSet.getString("staff_lastname");
                staff_phone = resultSet.getString("staff_phone");
                staff_email = resultSet.getString("staff_email");
                staff_password = resultSet.getString("staff_password");
                staff_status = resultSet.getString("staff_status");
                
                if(email.equals(staff_email) && password.equals(staff_password)) {
                    staff.setId(staff_id);
                    staff.setFirstName(staff_firstname);
                    staff.setLastName(staff_lastname);
                    staff.setPhone(staff_phone);
                    staff.setEmail(staff_email);
                    staff.setPassword(staff_password);
                    staff.setStatus(staff_status);
                    return staff;
                }
            }
            
            con.close();
            statement.close();
            
        } catch(SQLException ex) {
        }
        return null;
    }
    
    public Staff isEmailExist(Staff staff) {
        
        String email = staff.getEmail();
        
        try {
            con = DBConnection.createConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT staff_email FROM staffs");
            
            while (resultSet.next()) {
                staff_email = resultSet.getString("staff_email");
                
                if(email.equals(staff_email)) {
                    staff.setEmail(staff_email);
                    return staff;
                }
            }
            
            con.close();
            statement.close();
            
        } catch(SQLException ex) {
        }
        return null;
    }
    
    public void storeUser(Staff staff) {
        
        String firstname = staff.getFirstName();
        String lastname = staff.getLastName();
        String phone = staff.getPhone();
        String email = staff.getEmail();
        String password = staff.getPassword();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "INSERT INTO staffs(staff_firstname, staff_lastname, staff_phone, staff_email, staff_password) VALUES(?,?,?,?,?)"
            );
            
            pstmt.setString(1,firstname);
            pstmt.setString(2,lastname);
            pstmt.setString(3,phone);
            pstmt.setString(4,email);
            pstmt.setString(5,password);
            pstmt.executeUpdate();

            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public void updateEmail(Staff staff) {
        
        String email = staff.getEmail();
        int id = staff.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "UPDATE staffs SET staff_email=? WHERE staff_id=?"
            );
            
            pstmt.setString(1,email);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();

            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public void updatePassword(Staff staff) {
        
        String password = staff.getPassword();
        int id = staff.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "UPDATE staffs SET staff_password=? WHERE staff_id=?"
            );
            
            pstmt.setString(1,password);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();

            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public void updateProfile(Staff staff) {
        
        String firstname = staff.getFirstName();
        String lastname = staff.getLastName();
        String phone = staff.getPhone();
        String status = staff.getStatus();
        int id = staff.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "UPDATE staffs SET staff_firstname=?, staff_lastname=?, staff_phone=?, staff_status=? WHERE staff_id=?"
            );
            
            pstmt.setString(1,firstname);
            pstmt.setString(2,lastname);
            pstmt.setString(3,phone);
            pstmt.setString(4, status);
            pstmt.setInt(5,id);
            pstmt.executeUpdate();

            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
}
