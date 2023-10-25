package com.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cc.constants.SystemConstants;
import com.cc.mapper.MenuMapper;
import com.cc.mapper.UserMapper;
import com.cc.pojo.entity.LoginUser;
import com.cc.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @program: CCBlog
 * @ClassName UserDetailsServiceImpl
 * @author: c9noo
 * @create: 2023-10-17 19:12
 * @Version 1.0
 * 自定义security的数据查询方式
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //1. 根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,s);
        User user = userMapper.selectOne(queryWrapper);
        //2. 判断是否查询成功
        if (Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //3. 返回用户信息
        if(user.getType().equals(SystemConstants.ADMAIN)){
            List<String> list = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,list);
        }
        return new LoginUser(user,null);
    }


}
