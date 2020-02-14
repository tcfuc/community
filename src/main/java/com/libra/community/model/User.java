package com.libra.community.model;

import lombok.Data;

/**
 * @author zhou
 * @date 2020/1/22
 * @time 21:11
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}
