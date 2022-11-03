package com.oysterkode.laundry.Complaint;

public class Complaint {

    public static class Category {
        final static String WI_FI = "WI-FI";
        final static String ELECTRICITY = "Electricity";
        final static String WATER = "Water";
    }

    private String studentId, date, time, hostel, status, category;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Complaint() {
    }

    public Complaint(String studentId, String date, String time, String hostel, String status, String category) {
        this.studentId = studentId;
        this.date = date;
        this.time = time;
        this.hostel = hostel;
        this.status = status;
        this.category = category;
    }
}
