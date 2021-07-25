package com.example.finalprojectandroid1.activitys;

import android.provider.ContactsContract;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;


public class LocationUser {
    private String userEmail;
    private GeoPoint geo_point;
    private @ServerTimestamp Date timestemp;

    public LocationUser(String userEmail, GeoPoint geo_point, Date timestemp) {
        this.userEmail = userEmail;
        this.geo_point = geo_point;
        this.timestemp = timestemp;
    }

    public LocationUser() {
        // empty constructor
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public GeoPoint getGeo_point() {
        return geo_point;
    }

    public void setGeo_point(GeoPoint geo_point) {
        this.geo_point = geo_point;
    }

    public Date getTimestemp() {
        return timestemp;
    }

    public void setTimestemp(Date timestemp) {
        this.timestemp = timestemp;
    }

    @Override
    public String toString() {
        return "LocationUser{" +
                "userEmail='" + userEmail + '\'' +
                ", geo_point=" + geo_point +
                ", timestemp=" + timestemp +
                '}';
    }

}
