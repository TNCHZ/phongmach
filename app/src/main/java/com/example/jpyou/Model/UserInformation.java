package com.example.jpyou.Model;

public class UserInformation extends PersonInformation {
    private String appointDay;
    private String txtDescribe;
    private String  nameAppoint;
    private Boolean blConfirmed;




    public UserInformation(String appointDay, String nameAppoint) {
        this.appointDay = appointDay;
        this.nameAppoint = nameAppoint;
    }

    public UserInformation(String id, String hoTen, String gioiTinh, String ngaySinh, String soDT, String email) {
        super(id, hoTen, gioiTinh, ngaySinh, soDT, email);
    }

    public UserInformation(String id, String hoTen, String txtDescribe) {
        super(id, hoTen);
        this.txtDescribe = txtDescribe;
    }

    public Boolean getBlConfirmed() {
        return blConfirmed;
    }

    public void setBlConfirmed(Boolean blConfirmed) {
        this.blConfirmed = blConfirmed;
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
