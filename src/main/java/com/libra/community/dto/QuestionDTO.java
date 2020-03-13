package com.libra.community.dto;

import com.libra.community.model.User;
import lombok.Data;

import java.util.List;

/**
 * @author zhou
 * @date 2020/2/5
 * @time 13:22
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private List<String> tags;
    private Long gmtCreate;
    private Long gmtModified;
    private String creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private User user;
}
