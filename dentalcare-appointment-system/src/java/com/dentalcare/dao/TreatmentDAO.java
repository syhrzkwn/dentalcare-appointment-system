/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dentalcare.dao;

import com.dentalcare.model.Treatment;
import com.dentalcare.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Syahir
 */
public class TreatmentDAO {
    private Connection con = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;

    private int treat_id = 0;
    private String treat_title = "";
    private String treat_desc = "";
    
    public void storeTreatment(Treatment treatment) {
        
        String title = treatment.getTitle();
        String desc = treatment.getDesc();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "INSERT INTO treatments(treat_title, treat_desc) VALUES(?,?)"
            );
            
            pstmt.setString(1,title);
            pstmt.setString(2,desc);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public void updateTreatment(Treatment treatment) {
        
        int id = treatment.getId();
        String title = treatment.getTitle();
        String desc = treatment.getDesc();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "UPDATE treatments SET treat_title=?, treat_desc=? WHERE treat_id=?"
            );
            
            pstmt.setString(1,title);
            pstmt.setString(2,desc);
            pstmt.setInt(3,id);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public void deleteTreatment(Treatment treatment) {
        
        int id = treatment.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "DELETE FROM treatments WHERE treat_id=?"
            );
            
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
}
