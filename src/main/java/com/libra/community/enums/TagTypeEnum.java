package com.libra.community.enums;

/**
 * @author zhou
 * @date 2020/3/6
 * @time 16:17
 */
public enum TagTypeEnum {
    FRONT_END(1),
    BACK_END(2),
    MOBILE(3),
    DATABASE(4),
    OPERATIONS(5),
    AI(6),
    TOOL(7),
    OTHER(8),
    ;

    private Integer type;

    TagTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type) {
                return true;
            }
        }
        return false;
    }
}
