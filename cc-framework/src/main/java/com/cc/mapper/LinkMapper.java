package com.cc.mapper;

import com.cc.pojo.entity.Link;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 49751
* @description 针对表【cc_link(友链)】的数据库操作Mapper
* @createDate 2023-10-17 09:21:08
* @Entity com.cc.pojo.entity.Link
*/
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}




