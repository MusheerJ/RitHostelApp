package com.oysterkode.laundry.Leave;

import java.io.Serializable;

public class Leave implements Serializable {
    public static class Status {
        public static final String PENDING = "Pending";
        public static final String APPROVED = "Approved";
        public static final String REJECTED = "Rejected";
    }

    private String leaveId;
    private String studentName;
    private String studentId;
    private String from;
    private String to;
    private String duration;
    private String destination;
    private String parentContact;
    private String reason;
    private String status;

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    private String hostel;
    private String room;

    public String getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(String leaveId) {
        this.leaveId = leaveId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getParentContact() {
        return parentContact;
    }

    public void setParentContact(String parentContact) {
        this.parentContact = parentContact;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Leave() {
    }

    public Leave(String studentId, String from, String to, String duration, String destination, String parentContact, String reason) {
        this.studentId = studentId;
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.destination = destination;
        this.parentContact = parentContact;
        this.reason = reason;
    }


    public static String generateLeaveId() {
//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference quizRef = rootRef.child("Leave");
//        String key = quizRef.push().getKey();
//
//        return key;
        return "" + System.currentTimeMillis();
    }
}
