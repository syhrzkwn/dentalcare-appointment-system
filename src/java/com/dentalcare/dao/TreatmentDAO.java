package com.dentalcare.dao;

import com.dentalcare.model.Treatment;
import com.dentalcare.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Syahir
 */
public class TreatmentDAO {
    private Connection con = null;
    private PreparedStatement pstmt = null;

    private int treat_id = 0;
    private String treat_title = "";
    private String treat_desc = "";
    
    public void storeTreatment(Treatment treatment) {
        
        treat_title = treatment.getTitle();
        treat_desc = treatment.getDesc();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "INSERT INTO treatments(treat_title, treat_desc) VALUES(?,?)"
            );
            
            pstmt.setString(1,treat_title);
            pstmt.setString(2,treat_desc);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public void updateTreatment(Treatment treatment) {
        
        treat_id = treatment.getId();
        treat_title = treatment.getTitle();
        treat_desc = treatment.getDesc();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "UPDATE treatments SET treat_title=?, treat_desc=? WHERE treat_id=?"
            );
            
            pstmt.setString(1,treat_title);
            pstmt.setString(2,treat_desc);
            pstmt.setInt(3,treat_id);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public void deleteTreatment(Treatment treatment) {
        
        treat_id = treatment.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "DELETE FROM treatments WHERE treat_id=?"
            );
            
            pstmt.setInt(1,treat_id);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
}
