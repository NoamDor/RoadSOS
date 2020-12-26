package com.example.roadsos.enums;

public enum StatusCode {
    NEW(0),
    OCCUPIED(1),
    DONE(2);

    private final int value;

    private StatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
