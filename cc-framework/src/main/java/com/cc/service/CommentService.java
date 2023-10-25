package com.cc.service;

import com.cc.pojo.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.result.ResponseResult;

/**
* @author 49751
* @description 针对表【cc_comment(评论表)】的数据库操作Service
* @createDate 2023-10-18 16:24:26
*/
public interface CommentService extends IService<Comment> {

    /**
     * 文章对应的评论查询
     *
     * @param commentType
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    /**
     * 新增评论
     * @param comment
     * @return
     */
    ResponseResult addComment(Comment comment);
}
