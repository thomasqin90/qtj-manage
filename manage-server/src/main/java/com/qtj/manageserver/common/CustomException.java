package com.qtj.manageserver.common;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private int code;

    public CustomException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(String message) {
        this(500, message);
    }
}
