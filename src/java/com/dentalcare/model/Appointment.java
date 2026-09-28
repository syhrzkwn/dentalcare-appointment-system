/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dentalcare.model;

import java.io.Serializable;
/**
 *
 * @author Syahir
 */
public class Appointment implements Serializable {
    int id, patient_id, treat_id, dentist_id;
    String date, time, status, remark;
    
    //constructor
    public Appointment() {
        this.id = 0;
        this.patient_id = 0;
        this.treat_id = 0;
        this.dentist_id = 0;
        this.date = "";
        this.time = "";
        this.status = "";
        this.remark = "";
    }
    
    //mutator
    public void setId(int id) {
        this.id = id;
    }
    public void setPatientId(int patient_id) {
        this.patient_id = patient_id;
    }
    public void setTreatId(int treat_id) {
        this.treat_id = treat_id;
    }
    public void setDentistId(int dentist_id) {
        this.dentist_id = dentist_id;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    //accessor
    public int getId() {
        return id;
    }
    public int getPatientId() {
        return patient_id;
    }
    public int getTreatId() {
        return treat_id;
    }
    public int getDentistId() {
        return dentist_id;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public String getStatus() {
        return status;
    }
    public String getRemark() {
        return remark;
    }
}
