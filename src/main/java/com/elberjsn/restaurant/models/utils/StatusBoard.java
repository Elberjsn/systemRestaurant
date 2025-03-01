package com.elberjsn.restaurant.models.utils;

public enum StatusBoard {
    FREE(01,"FREE"),
    RESERVED(02,"RESERVED"),
    WAITING(03,"WAITING"),
    OCCUPIED(04,"OCCUPIED"),
    DISABLED(05,"DISABLED");

    private int code;
    private String description;

    private StatusBoard(int code,String description){
        this.code = code;
        this.description=description;
    }

    private StatusBoard(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static String fromValue(int value) {
        for (StatusBoard status : StatusBoard.values()) {
            if (status.getCode() == value) {
                return status.getDescription();
            }
        }
        throw new IllegalArgumentException("Código inválido: " + value);
    }
}
