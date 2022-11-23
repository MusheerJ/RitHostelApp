package com.oysterkode.laundry.Complaint;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Complaint implements Serializable {

    public static class Category {
        final public static String WI_FI = "WI-FI";
        final public static String ELECTRICITY = "Electricity";
        final public  static String WATER = "Water";
        final public static String OTHER = "Other";
    }

    public static class Status {
        final public static String PENDING = "Pending";
        final public static String IN_PROGRESS = "In-Progress";
        final public static String RESOLVED = "Resolved";
    }

    private String complaintId;
    private String studentId;
    private String date;
    private String time;
    private String hostel;
    private String hostelRoomNumber;
    private String status;
    private String category;
    private String desc;


    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHostelRoomNumber() {
        return hostelRoomNumber;
    }

    public void setHostelRoomNumber(String hostelRoomNumber) {
        this.hostelRoomNumber = hostelRoomNumber;
    }


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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String[] getTimeAndDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String dateAndTime[] = dtf.format(now).split(" ");
        return dateAndTime;
    }

    public static String generateComplaintId() {
        return "" + System.currentTimeMillis();
    }
}
