package com.cc.service;

import com.cc.pojo.dto.UserDto;
import com.cc.pojo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.pojo.vo.PageVo;
import com.cc.pojo.vo.UserVo;
import com.cc.result.ResponseResult;

/**
* @author 49751
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2023-10-17 09:34:46
*/
public interface UserService extends IService<User> {

    /**
     * 个人信息查询
     * @return
     */
    ResponseResult userInfo();


    /**
     * 用户信息更新
     * @param user
     * @return
     */
    ResponseResult updateUserInfo(User user);

    /**
     * 用户注册
     * @param user
     * @return
     */
    ResponseResult register(User user);

    PageVo pageList(Integer pageNum, Integer pageSize, String userName, String phonenumber, Integer status);

    void add(UserDto userDto);

    UserVo getUserById(Long id);
}
