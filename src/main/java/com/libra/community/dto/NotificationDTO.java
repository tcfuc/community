package com.libra.community.dto;

import com.libra.community.model.Question;
import com.libra.community.model.User;
import lombok.Data;

/**
 * @author zhou
 * @date 2020/3/16
 * @time 21:38
 */
@Data
public class NotificationDTO {
    private Long id;
    private String notifier;
    private String receiver;
    private Long outerId;
    private String type;
    private Long gmtCreate;
    private Integer status;
    private Question question;
    private User user;
}
