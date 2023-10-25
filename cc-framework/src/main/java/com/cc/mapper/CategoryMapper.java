package com.cc.mapper;

import com.cc.pojo.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 49751
* @description 针对表【cc_category(分类表)】的数据库操作Mapper
* @createDate 2023-10-16 16:12:30
* @Entity com.cc.pojo.entity.Category
*/

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




