package com.libra.community.exception;

/**
 * @author zhou
 * @date 2020/2/20
 * @time 14:56
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("你找的问题不在了,要不要换一个试试？");

    private String message;

    CustomizeErrorCode(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
