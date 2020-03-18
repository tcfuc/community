package com.libra.community.enums;

/**
 * @author zhou
 * @date 2020/3/16
 * @time 20:22
 */
public enum NotificationEnum {
    REPLY_QUESTION(1, "回复了问题"),
    REPLY_COMMENT(2, "回复了评论"),
    ;

    private int type;
    private String name;

    public int getType(){
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationEnum(int status, String name){
        this.type = status;
        this.name = name;
    }

    public static String getNameByType(int type){
        for (NotificationEnum value : NotificationEnum.values()) {
            if ( value.getType() == type){
                return value.getName();
            }
        }
        return null;
    }
}
