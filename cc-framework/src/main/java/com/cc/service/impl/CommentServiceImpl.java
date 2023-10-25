package com.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.constants.SystemConstants;
import com.cc.enums.AppHttpCodeEnum;
import com.cc.exception.SystemException;
import com.cc.pojo.entity.Comment;
import com.cc.pojo.vo.CommentVo;
import com.cc.pojo.vo.PageVo;
import com.cc.result.ResponseResult;
import com.cc.service.CommentService;
import com.cc.mapper.CommentMapper;
import com.cc.service.UserService;
import com.cc.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 49751
* @description 针对表【cc_comment(评论表)】的数据库操作Service实现
* @createDate 2023-10-18 16:24:26
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //1. 查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getRootId,-1);

        //2. 评论类型
        queryWrapper.eq(Comment::getType,commentType);

        //3. 分页查询
        Page<Comment> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        commentVoList.forEach(commentVo -> {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        });
        return ResponseResult.okResult(new PageVo(commentVoList,page.getTotal()));
    }

    /**
     * 新增评论
     * @param comment
     * @return
     */
    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }


    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        return list.stream()
                .map(comment -> {
                    CommentVo commentVo = BeanCopyUtils.copyBean(comment, CommentVo.class);
                    String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
                    commentVo.setUsername(nickName);
                    if (commentVo.getToCommentUserId() != -1) {
                        String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                        commentVo.setToCommentUserName(toCommentUserName);
                    }
                    return commentVo;
                })
                .collect(Collectors.toList());
    }

}




