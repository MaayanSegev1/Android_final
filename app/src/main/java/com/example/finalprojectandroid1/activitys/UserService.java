package com.example.finalprojectandroid1.activitys;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class UserService extends Service {

    private String email;

    public UserService(String email) {
        this.email = email;
    }

    public UserService() {
        //empty constructor
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "email='" + email + '\'' +
                '}';
    }
}
