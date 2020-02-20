package com.libra.community.exception;

/**
 * @author zhou
 * @date 2020/2/20
 * @time 14:35
 */
public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode){
        this.message = errorCode.getMessage();
    }

    public CustomizeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
