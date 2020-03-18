package com.libra.community.enums;

/**
 * @author zhou
 * @date 2020/3/16
 * @time 20:27
 */
public enum NotificationStatusEnum {
    UNREAD(0),
    READ(1),
    ;

    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status){
        this.status = status;
    }
}
