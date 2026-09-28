/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dentalcare.dao;

import com.dentalcare.model.Dentist;
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
public class DentistDAO {
    private Connection con = null;
    private Statement statement = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;

    private int dentist_id = 0;
    private String dentist_firstname = "";
    private String dentist_lastname = "";
    private String dentist_phone = "";
    private String dentist_email = "";
    private String dentist_password = "";
    private String dentist_status = "";
    
    public Dentist authenticateUser(Dentist dentist){
        
        String email = dentist.getEmail();
        String password = dentist.getPassword();
            
        try {
            con = DBConnection.createConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM dentists");
            
            while (resultSet.next()) {
                dentist_id = resultSet.getInt("dentist_id");
                dentist_firstname = resultSet.getString("dentist_firstname");
                dentist_lastname = resultSet.getString("dentist_lastname");
                dentist_phone = resultSet.getString("dentist_phone");
                dentist_email = resultSet.getString("dentist_email");
                dentist_password = resultSet.getString("dentist_password");
                dentist_status = resultSet.getString("dentist_status");
                
                if(email.equals(dentist_email) && password.equals(dentist_password)) {
                    dentist.setId(dentist_id);
                    dentist.setFirstName(dentist_firstname);
                    dentist.setLastName(dentist_lastname);
                    dentist.setPhone(dentist_phone);
                    dentist.setEmail(dentist_email);
                    dentist.setPassword(dentist_password);
                    dentist.setStatus(dentist_status);
                    return dentist;
                }
            }
            
            con.close();
            statement.close();
            
        } catch(SQLException ex) {
        }
        return null;
    }
    
    public Dentist isEmailExist(Dentist dentist) {
        
        String email = dentist.getEmail();
        
        try {
            con = DBConnection.createConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT dentist_email FROM dentists");
            
            while (resultSet.next()) {
                dentist_email = resultSet.getString("dentist_email");
                
                if(email.equals(dentist_email)) {
                    dentist.setEmail(dentist_email);
                    return dentist;
                }
            }
            
            con.close();
            statement.close();
            
        } catch(SQLException ex) {
        }
        return null;
    }
    
    public void storeUser(Dentist dentist) {
        
        String firstname = dentist.getFirstName();
        String lastname = dentist.getLastName();
        String phone = dentist.getPhone();
        String email = dentist.getEmail();
        String password = dentist.getPassword();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "INSERT INTO dentists(dentist_firstname, dentist_lastname, dentist_phone, dentist_email, dentist_password) VALUES(?,?,?,?,?)"
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
    
    public void updateProfile(Dentist dentist) {
    
        String firstname = dentist.getFirstName();
        String lastname = dentist.getLastName();
        String phone = dentist.getPhone();
        String status = dentist.getStatus();
        int id = dentist.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
             "UPDATE dentists SET dentist_firstname=?, dentist_lastname=?, dentist_phone=?, dentist_status=? WHERE dentist_id=?"
            );
            
            pstmt.setString(1,firstname);
            pstmt.setString(2,lastname);
            pstmt.setString(3,phone);
            pstmt.setString(4,status);
            pstmt.setInt(5,id);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public void updateEmail(Dentist dentist) {
        
        String email = dentist.getEmail();
        int id = dentist.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
             "UPDATE dentists SET dentist_email=? WHERE dentist_id=?"
            );
            
            pstmt.setString(1,email);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public void updatePassword(Dentist dentist) {
    
        String password = dentist.getPassword();
        int id = dentist.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
             "UPDATE dentists SET dentist_password=? WHERE dentist_id=?"
            );
            
            pstmt.setString(1,password);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
        
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public Dentist checkEmailForDelete(Dentist dentist) {
        
        String email = dentist.getEmail();
        int id = dentist.getId();
        
        try {
            con = DBConnection.createConnection();
            pstmt = con.prepareStatement(
                "SELECT dentist_email FROM dentists WHERE dentist_email=? AND dentist_id=?"
            );
            
            pstmt.setString(1,email);
            pstmt.setInt(2,id);
            resultSet = pstmt.executeQuery();
            
            while(resultSet.next()) {
                dentist_email = resultSet.getString("dentist_email");
                dentist.setEmail(dentist_email);
                return dentist;
            }
            
            con.close();
            pstmt.close();
                
        } catch(SQLException ex) {
        }
        return null;
    }
    
    public void deleteUser(Dentist dentist) {
        
        String email = dentist.getEmail();
        int id = dentist.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "DELETE FROM dentists WHERE dentist_email=? AND dentist_id=?"
            );
            
            pstmt.setString(1,email);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();

            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
}
