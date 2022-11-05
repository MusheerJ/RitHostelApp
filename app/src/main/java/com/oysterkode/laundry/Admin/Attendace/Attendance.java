package com.oysterkode.laundry.Admin.Attendace;

import java.io.Serializable;

public class Attendance implements Serializable {

    public static class Status {
        public final static String ABSENT = "Absent";
        public final static String PRESENT = "Present";
        public final static String LEAVE = "Leave";
    }

    private String studentPRN;
    private String studentName;
    private String status;
    private String date;
    private String room;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    private String hostel;

    public String getStudentPRN() {
        return studentPRN;
    }

    public void setStudentPRN(String studentPRN) {
        this.studentPRN = studentPRN;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Attendance() {
    }
}
