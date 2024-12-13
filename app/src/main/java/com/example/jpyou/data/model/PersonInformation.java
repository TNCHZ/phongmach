package com.example.jpyou.data.model;
import androidx.annotation.NonNull;

import java.io.Serializable;

public class PersonInformation implements Serializable {
    private String id;
    private String hoTen;
    private String gioiTinh;
    private String ngaySinh;
    private String soDT;
    private String email;
    private byte[] imgAvatar;

    public PersonInformation(String id, String hoTen) {
        this.id = id;
        this.hoTen = hoTen;
    }



    public PersonInformation() {
    }

    public PersonInformation(String hoTen) {
        this.hoTen = hoTen;
    }


    public PersonInformation(String id, String hoTen, String gioiTinh, String ngaySinh, String soDT, String email) {
        this.id = id;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.soDT = soDT;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public byte[] getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(byte[] imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return this.hoTen;
    }
}
