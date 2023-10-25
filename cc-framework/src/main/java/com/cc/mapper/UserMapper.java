package com.cc.mapper;

import com.cc.pojo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 49751
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2023-10-17 09:34:46
* @Entity com.cc.pojo.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




