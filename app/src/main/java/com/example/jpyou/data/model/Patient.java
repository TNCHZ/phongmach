package com.example.jpyou.data.model;

public class Patient extends PersonInformation {
    private String appointDay;
    private String txtDescribe;
    private String nameAppoint;

    public Patient(String id, String hoTen, String txtDescribe, String appointDay) {
        super(id, hoTen);
        this.appointDay = appointDay;
        this.txtDescribe = txtDescribe;
    }

    
    public Patient(int id, String appointDay, String nameAppoint) {
        super(String.valueOf(id));
        this.appointDay = appointDay;
        this.nameAppoint = nameAppoint;
    }

    public Patient(String id, String hoTen, String gioiTinh, String ngaySinh, String soDT, String email) {
        super(id, hoTen, gioiTinh, ngaySinh, soDT, email);
    }

    public Patient(String appointDay, String nameAppoint) {
        this.appointDay = appointDay;
        this.nameAppoint = nameAppoint;
    }

    public Patient(String id, String hoTen, String txtDescribe) {
        super(id, hoTen);
        this.txtDescribe = txtDescribe;
    }


    public String getTxtDescribe() {
        return txtDescribe;
    }

    public void setTxtDescribe(String txtDescribe) {
        this.txtDescribe = txtDescribe;
    }

    public String getAppointDay() {
        return appointDay;
    }

    public void setAppointDay(String appointDay) {
        this.appointDay = appointDay;
    }

    public String getNameAppoint() {
        return nameAppoint;
    }

    public void setNameAppoint(String nameAppoint) {
        this.nameAppoint = nameAppoint;
    }
}
