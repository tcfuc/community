package com.libra.community.dto;

import lombok.Data;

/**
 * @author zhou
 * @date 2019/12/7
 * @time 14:10
 */
@Data
public class GitHubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}
