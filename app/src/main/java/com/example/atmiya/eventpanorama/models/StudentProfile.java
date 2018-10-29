package com.example.atmiya.eventpanorama.models;

public class StudentProfile {
    String studentId;
    String name;
    String address;
    String collegeName;
    String contactNumber;
    String birthdate;
    boolean priviliged;

    public StudentProfile() {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public boolean getPriviliged() {
        return priviliged;
    }

    public void setPriviliged(boolean priviliged) {
        this.priviliged = priviliged;
    }
}
