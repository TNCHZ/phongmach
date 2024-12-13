package com.example.jpyou.data.model;

public class CalendarEdit {
    private String day;
    private Doctor doctor;

    public CalendarEdit(String day, Doctor doctor) {
        this.day = day;
        this.doctor = doctor;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
