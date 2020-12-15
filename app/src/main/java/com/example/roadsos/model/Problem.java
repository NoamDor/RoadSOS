package com.example.roadsos.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Problem {
    @PrimaryKey
    @NonNull
    private String id;
    private int problemTypeId;
    private String carType;
    private String licensePlate;
    private String userName;
    private String phoneNumber;
    private String carImageUrl;

    public Problem(int problemTypeId,
                   String carType,
                   String licensePlate,
                   String userName,
                   String phoneNumber,
                   String carImageUrl) {
//        this.problemType = problemType;
        this.carType = carType;
        this.problemTypeId = problemTypeId;
        this.licensePlate = licensePlate;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.carImageUrl = carImageUrl;
        this.id = this.userName + "_" + new Date().toString();
    }

    public Problem() {

    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getProblemTypeId() {
        return this.problemTypeId;
    }

    public void setProblemTypeId(int problemTypeId) {
        this.problemTypeId = problemTypeId;
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
        problem.put("problemTypeId", problemTypeId);
        problem.put("carType", carType);
        problem.put("licensePlate", licensePlate);
        problem.put("userName", userName);
        problem.put("phoneNumber", phoneNumber);
        problem.put("carImageUrl", carImageUrl);

        return problem;
    }
}
