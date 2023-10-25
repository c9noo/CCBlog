package com.cc.mapper;

import com.cc.pojo.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 49751
* @description 针对表【cc_comment(评论表)】的数据库操作Mapper
* @createDate 2023-10-18 16:24:26
* @Entity com.cc.pojo.entity.Comment
*/

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




