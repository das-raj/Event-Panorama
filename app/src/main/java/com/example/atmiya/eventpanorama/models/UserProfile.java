package com.example.atmiya.eventpanorama.models;

import java.io.Serializable;
import java.util.Date;

public class UserProfile implements Serializable {
    String name;
    String userId;
    String address;
    String collegeName;
    String contactNumber;
    String email;
    String dateString;
    Date dateOfBirth;
    boolean privileged;

    public UserProfile() {
    }

    public UserProfile(String name, String userId, String address, String collegeName, String contactNumber, String email, String dateString, Date dateOfBirth, boolean privileged) {
        this.name = name;
        this.userId = userId;
        this.address = address;
        this.collegeName = collegeName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.dateString = dateString;
        this.dateOfBirth = dateOfBirth;
        this.privileged = privileged;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean isPrivileged() {
        return privileged;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPrivileged(boolean privileged) {
        this.privileged = privileged;
    }
}
