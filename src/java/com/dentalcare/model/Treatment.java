package com.dentalcare.model;

import java.io.Serializable;
/**
 *
 * @author syahir
 */
public class Treatment implements Serializable {
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
