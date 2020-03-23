package com.libra.community.enums;

/**
 * @author zhou
 * @date 2020/3/6
 * @time 16:17
 */
public enum TagTypeEnum {
    FRONT_END(1,"前端","FrontEnd"),
    BACK_END(2,"后端","BackEnd"),
    MOBILE(3,"移动端","Mobile"),
    DATABASE(4,"数据库","Database"),
    OPERATIONS(5,"运维","Operations"),
    AI(6,"人工智能","AI"),
    TOOL(7,"工具","Tool"),
    OTHER(8,"其他","Other"),
    ;

    private Integer type;
    private String cnName;
    private String enName;

    TagTypeEnum(Integer type, String cnName, String enName) {
        this.type = type;
        this.cnName = cnName;
        this.enName = enName;
    }

    public Integer getType() {
        return type;
    }

    public String getCnName() {
        return cnName;
    }

    public String getEnName() {
        return enName;
    }

    public static boolean isExist(Integer type) {
        for (TagTypeEnum tagTypeEnum : TagTypeEnum.values()) {
            if (tagTypeEnum.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public static String getCnName(Integer type) {
        for (TagTypeEnum tagTypeEnum : TagTypeEnum.values()) {
            if (tagTypeEnum.getType() == type) {
                return tagTypeEnum.getCnName();
            }
        }
        return null;
    }

    public static String getEnName(Integer type) {
        for (TagTypeEnum tagTypeEnum : TagTypeEnum.values()) {
            if (tagTypeEnum.getType() == type) {
                return tagTypeEnum.getEnName();
            }
        }
        return null;
    }
}
