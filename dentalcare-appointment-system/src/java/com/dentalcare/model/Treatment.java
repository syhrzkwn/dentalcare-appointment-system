/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dentalcare.model;

/**
 *
 * @author syahir
 */
public class Treatment {
    private int id;
    private String title;
    private String desc;
    
    //constructor
    public Treatment() {
        this.id = 0;
        this.title = "";
        this.desc = "";
    }
    
    //mutator
    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    //accessor
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDesc() {
        return desc;
    }
}
