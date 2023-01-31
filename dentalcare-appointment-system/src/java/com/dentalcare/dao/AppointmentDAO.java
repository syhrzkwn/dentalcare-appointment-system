/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dentalcare.dao;

import com.dentalcare.model.Appointment;
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
public class AppointmentDAO {
    private Connection con = null;
    private Statement statement = null;
    private PreparedStatement pstmt = null;
    private ResultSet resultSet = null;

    private int aptmt_id = 0;
    private String aptmt_date = "";
    private String aptmt_time = "";
    private String aptmt_status = "";
    private String aptmt_remark = "";
    private int patient_id = 0;
    private int treat_id = 0;
    private int dentist_id = 0;
    
    public int checkDatetime(Appointment appointment) {
        
        aptmt_date = appointment.getDate();
        aptmt_time = appointment.getTime();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
            "SELECT COUNT(*) AS count FROM appointments WHERE aptmt_date=? AND aptmt_time=? AND aptmt_status != 'Cancelled'"
            );
            
            pstmt.setString(1,aptmt_date);
            pstmt.setString(2,aptmt_time);
            ResultSet rs = pstmt.executeQuery();
            
            int data = 0;
            if(rs.next()) {
                data = rs.getInt("count");
            }
            
            con.close();
            pstmt.close();
            
            return data;
        
        } catch(SQLException ex) {
        }
        return 0;
    }
    
    public String checkFirstAppointment(Appointment appointment) {
        
        patient_id = appointment.getPatientId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
                "SELECT first_time_booked FROM patients WHERE patient_id=?"
            );
            
            pstmt.setInt(1,patient_id);
            ResultSet rs = pstmt.executeQuery();
            
            String data = null;
            if(rs.next()) {
                data = rs.getString(1);
            }
            
            con.close();
            pstmt.close();
            
            return data;
            
        } catch(SQLException ex) {
        }
        return null;
    }
    
    public void updateFirstAppointment(Appointment appointment) {
        
        patient_id = appointment.getPatientId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
                "UPDATE patients SET first_time_booked='Y' WHERE patient_id=?"
            );
            
            pstmt.setInt(1,patient_id);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public void storeAppointment(Appointment appointment) {
        
        aptmt_date = appointment.getDate();
        aptmt_time = appointment.getTime();
        aptmt_remark = appointment.getRemark();
        patient_id = appointment.getPatientId();
        treat_id = appointment.getTreatId();
        
        try {
        
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
             "INSERT INTO appointments(aptmt_date, aptmt_time, aptmt_remark, patient_id, treat_id) VALUES(?,?,?,?,?)"
            );
            
            pstmt.setString(1,aptmt_date);
            pstmt.setString(2,aptmt_time);
            pstmt.setString(3,aptmt_remark);
            pstmt.setInt(4,patient_id);
            pstmt.setInt(5,treat_id);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
        
        } catch(SQLException ex) {
        }
    }
    
    public void updateAppointment(Appointment appointment) {
        
        aptmt_date = appointment.getDate();
        aptmt_time = appointment.getTime();
        aptmt_status = appointment.getStatus();
        aptmt_remark = appointment.getRemark();
        aptmt_id = appointment.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
                "UPDATE appointments SET aptmt_date=?, aptmt_time=?, aptmt_status=?, aptmt_remark=? WHERE aptmt_id=?"
            );
            
            pstmt.setString(1,aptmt_date);
            pstmt.setString(2,aptmt_time);
            pstmt.setString(3,aptmt_status);
            pstmt.setString(4,aptmt_remark);
            pstmt.setInt(5,aptmt_id);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public String isDentistAssigned(Appointment appointment) {
        
        aptmt_date = appointment.getDate();
        aptmt_time = appointment.getTime();
        dentist_id = appointment.getDentistId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
             "SELECT * FROM appointments WHERE aptmt_date=? AND aptmt_time=? AND dentist_id=? AND aptmt_status != 'Cancelled'"
            );
            
            pstmt.setString(1,aptmt_date);
            pstmt.setString(2,aptmt_time);
            pstmt.setInt(3,dentist_id);
            ResultSet rs = pstmt.executeQuery();
            
            String data = null;
            if(rs.next()) {
                data = rs.getString(1);
            }
            
            con.close();
            pstmt.close();
            
            return data;

        } catch(SQLException ex) {
        }
        return null;
    }
    
    public void assignDentist(Appointment appointment) {
        
        aptmt_id = appointment.getId();
        dentist_id = appointment.getDentistId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
             "UPDATE appointments SET dentist_id=? WHERE aptmt_id=?"
            );
            
            pstmt.setInt(1,dentist_id);
            pstmt.setInt(2,aptmt_id);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
    
    public void deleteAppointment(Appointment appointment) {
        
        aptmt_id = appointment.getId();
        
        try {
            con = DBConnection.createConnection();
            
            pstmt = con.prepareStatement(
             "DELETE FROM appointments WHERE aptmt_id=?"
            );
            
            pstmt.setInt(1, aptmt_id);
            pstmt.executeUpdate();
            
            con.close();
            pstmt.close();
            
        } catch(SQLException ex) {
        }
    }
}
