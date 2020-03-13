package com.libra.community.exception;

/**
 * @author zhou
 * @date 2020/2/20
 * @time 14:56
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"你找的问题不在了，要不要换一个试试？"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复！"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试。"),
    SYSTEM_ERROR(2004,"服务器炸了！"),
    COMMENT_TYPE_PARAM_WRONG(2005,"评论类型错误或不存在！"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在，要不要换一个试试？"),
    COMMENT_IS_EMPTY(2007,"评论不能为空，请重新输入！"),
    ;

    private Integer code;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
