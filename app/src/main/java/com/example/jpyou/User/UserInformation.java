package com.example.jpyou.User;

import com.example.jpyou.PersonInformation;

import java.util.List;

public class UserInformation extends PersonInformation {
    private List<String> appointDay;

    public UserInformation(int id, String hoTen, String gioiTinh, String ngaySinh, String soDT, String email) {
        super(id, hoTen, gioiTinh, ngaySinh, soDT, email);
    }

    public UserInformation(List<String> appointDay) {
        this.appointDay = appointDay;
    }

    public List<String> getAppointDay() {
        return appointDay;
    }

    public void setAppointDay(List<String> appointDay) {
        this.appointDay = appointDay;
    }
}
