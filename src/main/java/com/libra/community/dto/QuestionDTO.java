package com.libra.community.dto;

import com.libra.community.model.User;
import lombok.Data;

/**
 * @author zhou
 * @date 2020/2/5
 * @time 13:22
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private User user;
}
