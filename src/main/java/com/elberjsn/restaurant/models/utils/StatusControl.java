package com.elberjsn.restaurant.models.utils;

public enum StatusControl {

    PAY(01,"PAY"),
    OPEN(02,"OPEN"),
    CLOSED(03,"CLOSED"),
    CANCELED(04,"CANCELED"),
    DISABLED(05,"DISABLED");

    private int code;
    private String description;

    private StatusControl(int code,String description){
        this.code = code;
        this.description=description;
    }

    private StatusControl(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static String fromValue(int value) {
        for (StatusControl status : StatusControl.values()) {
            if (status.getCode() == value) {
                return status.getDescription();
            }
        }
        throw new IllegalArgumentException("Código inválido: " + value);
    }
}
