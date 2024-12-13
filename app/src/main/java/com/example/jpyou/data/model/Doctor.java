package com.example.jpyou.data.model;

public class Doctor extends PersonInformation{
    private String kinhNghiem;

    public Doctor(String hoTen, String kinhNghiem) {
        super(hoTen);
        this.kinhNghiem = kinhNghiem;
    }

    public Doctor(String id, String hoTen, String gioiTinh, String ngaySinh, String soDT, String email) {
        super(id, hoTen, gioiTinh, ngaySinh, soDT, email);
    }

    public String getKinhNghiem() {
        return kinhNghiem;
    }

    public void setKinhNghiem(String kinhNghiem) {
        this.kinhNghiem = kinhNghiem;
    }
}
