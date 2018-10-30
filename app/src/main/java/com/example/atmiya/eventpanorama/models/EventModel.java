package com.example.atmiya.eventpanorama.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class EventModel implements Parcelable {
    public String eventName;
    public String college;
    public String location;
    public Date eventDate;
    public String eventDateString;
    public int eventEntries;
    public String eventDescription;
    public String eventBriefDescription;
    public String imagePrimaryUrl;
    private String imageSecondaryUrl;

    public String createdUserId;


    public EventModel(String eventName, String college, String location, Date eventDate, String eventDateString, int eventEntries, String eventDescription, String eventBriefDescription, String imagePrimaryUrl, String imageSecondaryUrl, String createdUserId) {
        this.eventName = eventName;
        this.college = college;
        this.location = location;
        this.eventDate = eventDate;
        this.eventDateString = eventDateString;
        this.eventEntries = eventEntries;
        this.eventDescription = eventDescription;
        this.eventBriefDescription = eventBriefDescription;
        this.imagePrimaryUrl = imagePrimaryUrl;
        this.imageSecondaryUrl = imageSecondaryUrl;
        this.createdUserId = createdUserId;
    }

    public EventModel() {
    }

    protected EventModel(Parcel in) {
        eventName = in.readString();
        college = in.readString();
        location = in.readString();
        eventDate = new Date(in.readLong());
        eventDateString = in.readString();
        eventEntries = in.readInt();
        eventDescription = in.readString();
        eventBriefDescription = in.readString();
        imagePrimaryUrl = in.readString();
        imageSecondaryUrl = in.readString();
        createdUserId = in.readString();
    }

    public static final Creator<EventModel> CREATOR = new Creator<EventModel>() {
        @Override
        public EventModel createFromParcel(Parcel in) {
            return new EventModel(in);
        }

        @Override
        public EventModel[] newArray(int size) {
            return new EventModel[size];
        }
    };

    public void setValues(String eventName, String college, String location, Date eventDate, String eventDateString, int eventEntries, String eventDescription, String eventBriefDescription) {
        this.eventName = eventName;
        this.college = college;
        this.location = location;
        this.eventDate = eventDate;
        this.eventDateString = eventDateString;
        this.eventEntries = eventEntries;
        this.eventDescription = eventDescription;
        this.eventBriefDescription = eventBriefDescription;
    }

    public String getImagePrimaryUrl() {
        return imagePrimaryUrl;
    }

    public void setImagePrimaryUrl(String imagePrimaryUrl) {
        this.imagePrimaryUrl = imagePrimaryUrl;
    }

    public String getImageSecondaryUrl() {
        return imageSecondaryUrl;
    }

    public void setImageSecondaryUrl(String imageSecondaryUrl) {
        this.imageSecondaryUrl = imageSecondaryUrl;
    }
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDateString() {
        return eventDateString;
    }

    public void setEventDateString(String eventDateString) {
        this.eventDateString = eventDateString;
    }

    public int getEventEntries() {
        return eventEntries;
    }

    public void setEventEntries(int eventEntries) {
        this.eventEntries = eventEntries;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventBriefDescription() {
        return eventBriefDescription;
    }

    public void setEventBriefDescription(String eventBriefDescription) {
        this.eventBriefDescription = eventBriefDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventName);
        dest.writeString(college);
        dest.writeString(location);
        dest.writeLong(eventDate.getTime());
        dest.writeString(eventDateString);
        dest.writeInt(eventEntries);
        dest.writeString(eventDescription);
        dest.writeString(eventBriefDescription);
        dest.writeString(imagePrimaryUrl);
        dest.writeString(imageSecondaryUrl);
        dest.writeString(createdUserId);
    }
    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }
}
