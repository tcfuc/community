package com.libra.community.provider;

import com.libra.community.enums.CommentTypeEnum;
import com.libra.community.enums.TagTypeEnum;
import com.libra.community.mapper.CommentMapper;
import com.libra.community.mapper.QuestionMapper;
import com.libra.community.mapper.TagMapper;
import com.libra.community.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhou
 * @date 2020/3/5
 * @time 10:00
 */
@Component
public class CorrectDataBaseProvider {
    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private TagMapper tagMapper;

    public void correct() {
//        correctCommentCount();
//        test();
    }

    public void correctCommentCount() {
//        获取questionId
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andIdIsNotNull();
        List<Question> questions = questionMapper.selectByExample(questionExample);
        List<Long> questionIds = questions.stream()
                .map(question -> question.getId())
                .collect(Collectors.toList());
//        查询评论数 修改数据库数据
        for (Long questionId : questionIds) {
            CommentExample commentExample = new CommentExample();
            commentExample.createCriteria()
                    .andParentIdEqualTo(questionId)
                    .andTypeEqualTo(CommentTypeEnum.QUESTION.getType());
            Long commentCount = commentMapper.countByExample(commentExample);

            Question question = new Question();
            question.setId(questionId);
            question.setCommentCount(commentCount.intValue());
            questionMapper.updateByPrimaryKeySelective(question);
        }

//        获取commentId
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andIdIsNotNull();
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        List<Long> commentIds = comments.stream()
                .map(comment -> comment.getId())
                .collect(Collectors.toList());
//        查询评论回复数
        for (Long commentId : commentIds) {
            CommentExample replyExample = new CommentExample();
            replyExample.createCriteria()
                    .andParentIdEqualTo(commentId)
                    .andTypeEqualTo(CommentTypeEnum.COMMENT.getType());
            ;
            Long commentCount = commentMapper.countByExample(replyExample);

            Comment comment = new Comment();
            comment.setId(commentId);
            comment.setCommentCount(commentCount.intValue());
            commentMapper.updateByPrimaryKeySelective(comment);
        }
    }

    public void test() {
        String[] tags1 = StringUtils.split("Javascript 前端 vue.js css html html5 node.js react.js jquery css3 es6 typescript G chrome npm bootstrap sass lass chrome-devtools firefox angular  coffeescript safari postcss postman fiddler  yarn webkit firebug edge", " ");
        String[] tags2 = StringUtils.split("php  java node.js  python c++ c golang spring django springboot 后端 flask c# swoole  ruby asp.net ruby-on-rails scala rust lavarel 爬虫", " ");
        String[] tags3 = StringUtils.split("java android  ios objective-c 小程序 swift react-native xcode android-studio webapp   flutter  kotlin", " ");
        String[] tags4 = StringUtils.split("mysql redis mongodb sql 数据库 json elasticsearch nosql memcached postgresql sqlite mariadb", " ");
        String[] tags5 = StringUtils.split("linux nginx docker apache centos ubuntu 服务器 负载均衡 运维 ssh vagrant 容器  jenkins devops debian fabric", " ");
        String[] tags6 = StringUtils.split("算法 机器学习 人工智能 深度学习 数据挖掘 tensorflow 神经网络 图像识别 人脸识别 自然语言处理 机器人 pytorch 自动驾驶", " ");
        String[] tags7 = StringUtils.split("git github macos visual-studio-code windows vim sublime-text intellij-idea eclipse phpstorm webstorm 编辑器 svn visual-studio pycharm emacs", " ");
        String[] tags8 = StringUtils.split("程序员 http segmentfault https 安全 websocket restful xss 区块链 graphql rpc 比特币 以太坊 udp 智能合约", " ");

        for (String s : tags1) {
            tagInsert(s, TagTypeEnum.FRONT_END);
        }
        for (String s : tags2) {
            tagInsert(s, TagTypeEnum.BACK_END);
        }
        for (String s : tags3) {
            tagInsert(s, TagTypeEnum.MOBILE);
        }
        for (String s : tags4) {
            tagInsert(s, TagTypeEnum.DATABASE);
        }
        for (String s : tags5) {
            tagInsert(s, TagTypeEnum.OPERATIONS);
        }
        for (String s : tags6) {
            tagInsert(s, TagTypeEnum.AI);
        }
        for (String s : tags7) {
            tagInsert(s, TagTypeEnum.TOOL);
        }
        for (String s : tags8) {
            tagInsert(s, TagTypeEnum.OTHER);
        }
    }

    public void tagInsert(String s, TagTypeEnum type){
        Tag tag = new Tag();
        tag.setAccountId("43395850");
        tag.setTag(s);
        tag.setTagType(type.getType());
        tag.setGmtCreate(System.currentTimeMillis());
        tag.setGmtModified(tag.getGmtCreate());
        tagMapper.insert(tag);
    }
}
