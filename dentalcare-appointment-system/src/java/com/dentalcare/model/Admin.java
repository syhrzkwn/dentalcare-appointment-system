/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dentalcare.model;

/**
 *
 * @author syahir
 */
public class Admin extends User {
    private String role;
    
    //default constructor
    public Admin() {
        super();
        this.role = "";
    }
       
    //normal constructor
    public Admin(String firstName, String lastName, String phone, String email, String password, String role) {
        super(firstName, lastName, phone, email, password);
        this.role = role;
    }
    
    //mutator
    public void setRole(String role) {
        this.role = role;
    }
    
    //accessor
    public String getRole() {
        return this.role;
    }
}
