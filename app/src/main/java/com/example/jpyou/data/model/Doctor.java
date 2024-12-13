package com.example.jpyou.data.model;

import java.util.List;

public class Doctor extends PersonInformation{
    private String kinhNghiem;
    private List<String> ngayLam;

    public Doctor(String id, String hoTen, String kinhNghiem) {
        super(id, hoTen);
        this.kinhNghiem = kinhNghiem;
    }

    public Doctor(String id, String hoTen, String kinhNghiem, List<String> ngayLam) {
        super(id, hoTen);
        this.kinhNghiem = kinhNghiem;
        this.ngayLam = ngayLam;
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

    public List<String> getNgayLam() {
        return ngayLam;
    }

    public void setNgayLam(List<String> ngayLam) {
        this.ngayLam = ngayLam;
    }
}
