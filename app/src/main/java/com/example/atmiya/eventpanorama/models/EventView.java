package com.example.atmiya.eventpanorama.models;

import android.net.Uri;
import android.widget.ImageView;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.database.Exclude;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

public class EventView implements Serializable {
    String college;
    String eventBriefDescription;
    String eventDescription;
    String eventName;
    String imagePrimaryUrl;
    String imageSecondaryUrl;
    String eventDateString;
    String location;
    String documentId;

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getImagePrimaryUrl() {
        return imagePrimaryUrl;
    }

    public String getImageSecondaryUrl() {
        return imageSecondaryUrl;
    }

    public void setImagePrimaryUrl(String imagePrimaryUrl) {
        this.imagePrimaryUrl = imagePrimaryUrl;
    }

    public void setImageSecondaryUrl(String imageSecondaryUrl) {
        this.imageSecondaryUrl = imageSecondaryUrl;
    }

    public EventView(){}

    public String getEventName() {
        return eventName;
    }

    public String getCollege() {
        return college;
    }

    public String getEventBriefDescription() {
        return eventBriefDescription;
    }

    public String getEventDescription() {
        return eventDescription;
    }


    public String getEventDateString() {
        return eventDateString;
    }

    public String getLocation() {
        return location;
    }
}
