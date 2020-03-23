package com.libra.community.service;

import com.libra.community.dto.TagDTO;
import com.libra.community.enums.TagTypeEnum;
import com.libra.community.mapper.TagMapper;
import com.libra.community.model.Tag;
import com.libra.community.model.TagExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhou
 * @date 2020/3/22
 * @time 20:27
 */
@Service
public class TagService {
    @Resource
    TagMapper tagMapper;

    public List<TagDTO> getTags(){
        TagExample tagExample = new TagExample();
        List<Tag> tags = tagMapper.selectByExample(tagExample);
        List<Integer> tagTypes = tags.stream().map(Tag::getTagType).distinct().collect(Collectors.toList());
        List<TagDTO> tagList = new ArrayList<>();
        for (Integer tagType : tagTypes) {
            List<Tag> tagFilter = tags.stream().filter(tag1 -> tag1.getTagType().equals(tagType)).collect(Collectors.toList());
            TagDTO tagDTO = new TagDTO();
            tagDTO.setTagTypeCN(TagTypeEnum.getCnName(tagType));
            tagDTO.setTagTypeEN(TagTypeEnum.getEnName(tagType));
            tagDTO.setTag(tagFilter);
            tagList.add(tagDTO);
        }
        return tagList;
    }
}
