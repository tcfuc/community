package com.libra.community.dto;

import com.libra.community.model.Tag;
import lombok.Data;

import java.util.List;

/**
 * @author zhou
 * @date 2020/3/22
 * @time 21:23
 */
@Data
public class TagDTO {
    private String tagTypeEN;
    private String tagTypeCN;
    private List<Tag> tag;
}
