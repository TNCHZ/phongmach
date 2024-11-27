package com.example.jpyou.User;

import com.example.jpyou.PersonInformation;

import java.util.List;

public class UserInformation extends PersonInformation {
    private List<String> appointDay;
    private String txtDescribe;
    private Integer imgAvatar;
    private Boolean blConfirmed;


    public UserInformation(String hoTen) {
        super(hoTen);
    }

    public UserInformation(int id, String hoTen, String gioiTinh, String ngaySinh, String soDT, String email) {
        super(id, hoTen, gioiTinh, ngaySinh, soDT, email);
    }

    public UserInformation(String hoTen, String txtDescribe, Integer imgAvatar) {
        super(hoTen);
        this.txtDescribe = txtDescribe;
        this.imgAvatar = imgAvatar;
    }

    public Integer getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(Integer imgAvatar) {
        this.imgAvatar = imgAvatar;
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

    public List<String> getAppointDay() {
        return appointDay;
    }

    public void setAppointDay(List<String> appointDay) {
        this.appointDay = appointDay;
    }
}
