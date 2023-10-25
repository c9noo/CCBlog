package com.cc.service;

import com.cc.pojo.dto.TagListDto;
import com.cc.pojo.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.pojo.vo.PageVo;
import com.cc.pojo.vo.TagVo;
import com.cc.result.ResponseResult;

import java.util.List;

/**
* @author 49751
* @description 针对表【cc_tag(标签)】的数据库操作Service
* @createDate 2023-10-21 11:41:44
*/
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    void addTag(TagListDto tagListDto);

    List<TagVo> listAllTag();
}
