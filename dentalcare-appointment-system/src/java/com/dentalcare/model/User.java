/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dentalcare.model;

import java.io.Serializable;
/**
 *
 * @author syahir
 */
public class User implements Serializable {
    private int id;
    private String firstname, lastname, phone, email, status;
    
    //constructor
    public User() {
        this.id = 0;
        this.firstname = "";
        this.lastname = "";
        this.phone = "";
        this.email = "";
        this.status = "";
    }
    
    //mutator
    public void setId(int id) {
        this.id = id;
    }
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }
    public void setLastName(String lastname) {
        this.lastname = lastname;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    //accessor
    public int getId() {
        return id;
    }
    public String getFirstName() {
        return firstname;
    }
    public String getLastName() {
        return lastname;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String getStatus() {
        return status;
    }
}
