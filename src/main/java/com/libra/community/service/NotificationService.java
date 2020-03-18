package com.libra.community.service;

import com.libra.community.dto.NotificationDTO;
import com.libra.community.dto.PaginationDTO;
import com.libra.community.dto.QuestionDTO;
import com.libra.community.enums.NotificationEnum;
import com.libra.community.mapper.NotificationMapper;
import com.libra.community.mapper.QuestionMapper;
import com.libra.community.mapper.UserMapper;
import com.libra.community.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhou
 * @date 2020/3/16
 * @time 21:46
 */
@Service
public class NotificationService {
    @Resource
    private NotificationMapper notificationMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuestionMapper questionMapper;

    public PaginationDTO listByReceiver(Integer currentPage, Integer size, String receiver) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(receiver);
        PaginationDTO paginationDTO = page(notificationExample, currentPage, size);
        return paginationDTO;
    }

    public PaginationDTO page(NotificationExample notificationExample, Integer currentPage, Integer size) {
//        计算问题页数
        Integer totalCount = Math.toIntExact(notificationMapper.countByExample(notificationExample));
        Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
//        对当前页进行范围判断
        currentPage = currentPage < 1 ? 1 : currentPage;
        currentPage = currentPage > totalPage ? totalPage : currentPage;
//        查询回复
        Integer offset = size * (currentPage - 1);
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, size));
//        添加页面元素
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setTotalPage(totalPage);
        paginationDTO.setCurrentPage(currentPage);
        paginationDTO.setPagination(paginationDTO.getTotalPage(), paginationDTO.getCurrentPage());
//        查询回复所属用户和问题信息
        List<NotificationDTO> notificationDTOList = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setType(NotificationEnum.getNameByType(notification.getType()));
            notificationDTO.setQuestion(questionMapper.selectByPrimaryKey(notification.getOuterid()));
            UserExample userExample = new UserExample();
            userExample.createCriteria()
                    .andAccountIdEqualTo(notification.getNotifier());
            List<User> users = userMapper.selectByExample(userExample);
            notificationDTO.setUser(users.get(0));
            notificationDTOList.add(notificationDTO);
        }
        paginationDTO.setNotifications(notificationDTOList);

        return paginationDTO;
    }
}
