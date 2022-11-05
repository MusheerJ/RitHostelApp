package com.oysterkode.laundry.Student;

public class Student {

    static class Name {
        String firstName;
        String middleName;
        String lastName;
    }

    final public static String[] year = {
            StudentClass.FY,
            StudentClass.SY,
            StudentClass.TY,
            StudentClass.FinalY,
    };

    final public static String branches[] = {
            StudentBranch.CSE,
            StudentBranch.CSIT,
            StudentBranch.EE,
            StudentBranch.ENTC,
            StudentBranch.AE,
            StudentBranch.ME,
    };


    public static class Hostel {
        final public static String A_HOSTEL = "A";
        final public static String B_HOSTEL = "B";
        final public static String C_HOSTEL = "C";
        final public static String D_HOSTEL = "D";
        final public static String E_HOSTEL = "E";
        final public static String F_HOSTEL = "F";
        public static String[] hostels = {
                A_HOSTEL,
                B_HOSTEL,
                C_HOSTEL,
                D_HOSTEL,
                E_HOSTEL,
                F_HOSTEL

        };
    }


    private String userId;
    private String studentId;
    private String studentName;
    private String studentClass;
    private String studentBranch;
    private String email;
    private String password;
    private String classAndBranch;
    private String contact;
    private String roomNo;

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    private String DOB;
    private String hostel;
    private Name name;

    public Student() {
    }

    public Student(String studentId, String studentName, String studentClass, String studentBranch, String email, String password, String classAndBranch, String contact, String roomNo, String DOB, Name name) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.studentBranch = studentBranch;
        this.email = email;
        this.password = password;
        this.classAndBranch = classAndBranch;
        this.contact = contact;
        this.roomNo = roomNo;
        this.DOB = DOB;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getStudentBranch() {
        return studentBranch;
    }

    public void setStudentBranch(String studentBranch) {
        this.studentBranch = studentBranch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClassAndBranch() {
        return classAndBranch;
    }

    public void setClassAndBranch(String classAndBranch) {
        this.classAndBranch = classAndBranch;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}
