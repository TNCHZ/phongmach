package com.example.jpyou.data.model;

public class Role {
    private PersonInformation ps;
    private String role;

    public Role(PersonInformation ps, String role) {
        this.ps = ps;
        this.role = role;
    }

    public PersonInformation getPs() {
        return ps;
    }

    public void setPs(PersonInformation ps) {
        this.ps = ps;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
