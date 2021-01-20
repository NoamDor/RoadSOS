package com.example.roadsos.model;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.roadsos.model.ProblemStatus;
import com.example.roadsos.enums.StatusCode;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Problem implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String uid;
    @TypeConverters(Converters.class)
    private ProblemStatus status;
    @TypeConverters(Converters.class)
    private ProblemType problemType;
    @TypeConverters(Converters.class)
    private MyLocation location;
    private String carType;
    private String licensePlate;
    private String userName;
    private String phoneNumber;
    private String carImageUrl;

    public Problem(String uid,
                   MyLocation location,
                   ProblemType problemType,
                   String carType,
                   String licensePlate,
                   String userName,
                   String phoneNumber,
                   String carImageUrl) {
        this.uid = uid;
        this.location = location;
        this.status = new ProblemStatus(StatusCode.NEW, "חדש");
        this.carType = carType;
        this.problemType = problemType;
        this.licensePlate = licensePlate;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.carImageUrl = carImageUrl;
        this.id = this.userName + "_" + new Date().toString();
    }

    public Problem() {

    }

    public String getUid() { return this.uid; }

    public void setUid(String uid) { this.uid = uid; }

    public MyLocation getLocation() { return this.location; }

    public void setLocation(MyLocation location) { this.location = location; }

    public ProblemStatus getStatus() { return this.status; }

    public void setStatus(ProblemStatus status) { this.status = status; }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProblemType getProblemType() {
        return this.problemType;
    }

    public void setProblemType(ProblemType problemType) {
        this.problemType = problemType;
    }

    public String getCarType() {
        return this.carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getLicensePlate() {
        return this.licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCarImageUrl() {
        return this.carImageUrl;
    }

    public void setCarImageUrl(String carImageUrl) {
        this.carImageUrl = carImageUrl;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> problem = new HashMap<>();
        problem.put("id", id);
        problem.put("uid", uid);
        problem.put("location", location);
        problem.put("status", status);
        problem.put("problemType", problemType);
        problem.put("carType", carType);
        problem.put("licensePlate", licensePlate);
        problem.put("userName", userName);
        problem.put("phoneNumber", phoneNumber);
        problem.put("carImageUrl", carImageUrl);

        return problem;
    }
}
