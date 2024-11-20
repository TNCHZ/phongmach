package com.example.jpyou.Nurse;

public class SchedulePatient {
    private String txtName;
    private String txtDescribe;
    private Integer imgAvatar;
    private Boolean blConfirmed;

    public SchedulePatient(String txtName, String txtDescribe, Integer imgAvatar) {
        this.txtName = txtName;
        this.txtDescribe = txtDescribe;
        this.imgAvatar = imgAvatar;
        this.blConfirmed = false;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getTxtDescribe() {
        return txtDescribe;
    }

    public void setTxtDescribe(String txtDescribe) {
        this.txtDescribe = txtDescribe;
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

}
