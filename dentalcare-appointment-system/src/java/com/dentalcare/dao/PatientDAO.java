/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dentalcare.dao;

import com.dentalcare.model.Patient;
import com.dentalcare.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author Syahir
 */
public class PatientDAO {
    
    private Connection con = null;
    private Statement statement = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;

    private int patient_id = 0;
    private String patient_firstname = "";
    private String patient_lastname = "";
    private String patient_phone = "";
    private String patient_email = "";
    private String patient_password = "";
    private String patient_status = "";
        
    public Patient authenticateUser(Patient patient) {
        
        String email = patient.getEmail();
        String password = patient.getPassword();
            
        try {
            con = DBConnection.createConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM patients WHERE patient_status='Active'");
            
            while (resultSet.next()) {
                patient_id = resultSet.getInt("patient_id");
                patient_firstname = resultSet.getString("patient_firstname");
                patient_lastname = resultSet.getString("patient_lastname");
                patient_phone = resultSet.getString("patient_phone");
                patient_email = resultSet.getString("patient_email");
                patient_password = resultSet.getString("patient_password");
                patient_status = resultSet.getString("patient_status");
                
                if(email.equals(patient_email) && password.equals(patient_password)) {
                    patient.setId(patient_id);
                    patient.setFirstName(patient_firstname);
                    patient.setLastName(patient_lastname);
                    patient.setPhone(patient_phone);
                    patient.setEmail(patient_email);
                    patient.setPassword(patient_password);
                    patient.setStatus(patient_status);
                    return patient;
                }
            }
            
            con.close();
            statement.close();
            
        } catch(SQLException ex) {
        }
        return null;
    }
    
    public Patient isEmailExist(Patient patient) {
        
        String email = patient.getEmail();
        
        try {
            con = DBConnection.createConnection();
            statement = con.createStatement();
            resultSet = statement.executeQuery("SELECT patient_email FROM patients");
            
            while (resultSet.next()) {
                patient_email = resultSet.getString("patient_email");
                
                if(email.equals(patient_email)) {
                    patient.setEmail(patient_email);
                    return patient;
                }
            }
            
            con.close();
            statement.close();
            
        } catch(SQLException ex) {
        }
        return null;
    }
    
    public Patient storeUser(Patient patient) {
        
        String firstname = patient.getFirstName();
        String lastname = patient.getLastName();
        String phone = patient.getPhone();
        String email = patient.getEmail();
        String password = patient.getPassword();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "INSERT INTO patients(patient_firstname, patient_lastname, patient_phone, patient_email, patient_password) VALUES(?,?,?,?,?)"
            );
            
            pstmt.setString(1,firstname);
            pstmt.setString(2,lastname);
            pstmt.setString(3,phone);
            pstmt.setString(4,email);
            pstmt.setString(5,password);
            pstmt.executeUpdate();

            con.close();
            pstmt.close();
            
            return authenticateUser(patient);
            
        } catch(SQLException ex) {
        }
        return null;
    }
    
    public Patient checkEmailForDelete(Patient patient) {
        
        String email = patient.getEmail();
        int id = patient.getId();
        
        try {
            con = DBConnection.createConnection();
            pstmt = con.prepareStatement(
                "SELECT patient_email FROM patients WHERE patient_email=? AND patient_id=?"
            );
            
            pstmt.setString(1,email);
            pstmt.setInt(2,id);
            resultSet = pstmt.executeQuery();
            
            while(resultSet.next()) {
                patient_email = resultSet.getString("patient_email");
                patient.setEmail(patient_email);
                return patient;
            }
            
            con.close();
            pstmt.close();
                
        } catch(SQLException ex) {
        }
        return null;
    }
    
    public String deleteUser(Patient patient) {
        
        String email = patient.getEmail();
        int id = patient.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "DELETE FROM patients WHERE patient_email=? AND patient_id=?"
            );
            
            pstmt.setString(1,email);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();

            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
            return ex.getMessage();
        }
        return null;
    }
    
    public void updateEmail(Patient patient) {
        
        String email = patient.getEmail();
        int id = patient.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
             "UPDATE patients SET patient_email=? WHERE patient_id=?"
            );
            
            pstmt.setString(1,email);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public void updatePassword(Patient patient) {
    
        String password = patient.getPassword();
        int id = patient.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
             "UPDATE patients SET patient_password=? WHERE patient_id=?"
            );
            
            pstmt.setString(1,password);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
        
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public void updateProfile(Patient patient) {
        
        String firstname = patient.getFirstName();
        String lastname = patient.getLastName();
        String phone = patient.getPhone();
        int id = patient.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
             "UPDATE patients SET patient_firstname=?, patient_lastname=?, patient_phone=? WHERE patient_id=?"
            );
            
            pstmt.setString(1,firstname);
            pstmt.setString(2,lastname);
            pstmt.setString(3,phone);
            pstmt.setInt(4,id);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    //have additional update for patient status
    public void updateProfileInAdmin(Patient patient) {
        
        String firstname = patient.getFirstName();
        String lastname = patient.getLastName();
        String phone = patient.getPhone();
        String status = patient.getStatus();
        int id = patient.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
             "UPDATE patients SET patient_firstname=?, patient_lastname=?, patient_phone=?, patient_status=? WHERE patient_id=?"
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
}