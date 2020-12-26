package com.example.roadsos.model;

import com.example.roadsos.enums.StatusCode;

public class ProblemStatus {
    public int code;
    public String desc;

    public ProblemStatus(StatusCode code, String desc) {
        this.code = code.getValue();
        this.desc = desc;
    }

    public ProblemStatus() {

    }
}
