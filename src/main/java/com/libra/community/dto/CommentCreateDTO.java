package com.libra.community.dto;

import com.libra.community.model.User;
import lombok.Data;

/**
 * @author zhou
 * @date 2020/2/20
 * @time 17:46
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private Integer type;
    private String content;
}
