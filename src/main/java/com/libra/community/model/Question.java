package com.libra.community.model;

import lombok.Data;

/**
 * @author zhou
 * @date 2020/2/2
 * @time 15:53
 */
@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private String creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;

}
