package com.libra.community.dto;

import com.libra.community.model.User;
import lombok.Data;

/**
 * @author zhou
 * @date 2020/2/20
 * @time 17:46
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private String content;
    private Integer commentCount;
    private Long gmtModified;
    private Long gmtCreate;
    private Long likeCount;
    private String commentator;
    private User user;
}
