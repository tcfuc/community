package com.libra.community.service;

import com.libra.community.dto.CommentDTO;
import com.libra.community.enums.CommentTypeEnum;
import com.libra.community.enums.NotificationEnum;
import com.libra.community.enums.NotificationStatusEnum;
import com.libra.community.exception.CustomizeErrorCode;
import com.libra.community.exception.CustomizeException;
import com.libra.community.mapper.*;
import com.libra.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhou
 * @date 2020/2/26
 * @time 15:38
 */
@Service
public class CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private CommentExtMapper commentExtMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private QuestionExtMapper questionExtMapper;

    @Resource
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment) {
//判空parentID
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
//判空type
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_TYPE_PARAM_WRONG);
        }
// 判断回复类型
        if (comment.getType().equals(CommentTypeEnum.COMMENT.getType())) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            incCommentCount(comment.getParentId());
            createNotify(comment, dbComment.getCommentator(), NotificationEnum.REPLY_COMMENT);
        } else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);
            createNotify(comment, question.getCreator(), NotificationEnum.REPLY_QUESTION);
        }
        commentMapper.insert(comment);
    }

    //通过id获取评论
    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        List<CommentDTO> commentDTOS = new ArrayList<>();

        if (comments != null && comments.size() != 0) {
            //拉姆达表达式 获取去重的用户AccountId
            List<String> accountIds = comments.stream().map(Comment::getCommentator).distinct().collect(Collectors.toList());
            //获取评论人并转为Map
            UserExample userExample = new UserExample();
            userExample.createCriteria()
                    .andAccountIdIn(accountIds);
            List<User> users = userMapper.selectByExample(userExample);
            Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getAccountId, user -> user));
            //转换comment为commentDTO
            commentDTOS = comments.stream().map(comment -> {
                CommentDTO commentDTO = new CommentDTO();
                BeanUtils.copyProperties(comment, commentDTO);
                commentDTO.setUser(userMap.get(comment.getCommentator()));
                return commentDTO;
            }).collect(Collectors.toList());
        }
        return commentDTOS;
    }

    //评论数加一
    public void incCommentCount(Long id) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setCommentCount(1);;
        commentExtMapper.incCommentCount(comment);
    }

    public void createNotify(Comment comment, String receiver, NotificationEnum type){
        Notification notification = new Notification();
        notification.setNotifier(comment.getCommentator());
        notification.setReceiver(receiver);
        notification.setOuterid(comment.getParentId());
        notification.setType(type.getType());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notificationMapper.insert(notification);
    }
}
