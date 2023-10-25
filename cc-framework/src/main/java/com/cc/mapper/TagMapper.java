package com.cc.mapper;

import com.cc.pojo.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 49751
* @description 针对表【cc_tag(标签)】的数据库操作Mapper
* @createDate 2023-10-21 11:41:44
* @Entity com.cc.pojo.entity.Tag
*/
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}




