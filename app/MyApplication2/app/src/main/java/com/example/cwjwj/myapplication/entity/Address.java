package com.example.cwjwj.myapplication.entity;

public class Address {
    private int id;
    private String email;
    private String password;
    private String display_name;
    private int is_using;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public int getIs_using() {
        return is_using;
    }

    public void setIs_using(int is_using) {
        this.is_using = is_using;
    }
}
