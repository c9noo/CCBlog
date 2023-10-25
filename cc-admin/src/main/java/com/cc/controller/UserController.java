package com.cc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cc.pojo.dto.UserDto;
import com.cc.pojo.entity.ArticleTag;
import com.cc.pojo.entity.User;
import com.cc.pojo.entity.UserRole;
import com.cc.pojo.vo.UserVo;
import com.cc.result.ResponseResult;
import com.cc.service.UserRoleService;
import com.cc.service.UserService;
import com.cc.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: CCBlog
 * @ClassName UserController
 * @author: c9noo
 * @create: 2023-10-24 21:31
 * @Version 1.0
 **/
@RestController("systemUser/")
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String userName,String phonenumber,Integer status){
        return ResponseResult.okResult(userService.pageList(pageNum,pageSize,userName,phonenumber,status));
    }

    @PostMapping
    public ResponseResult add(@RequestBody UserDto userDto){
        userDto.setId(null);
        userService.add(userDto);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id){
        userService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getUserById(@PathVariable Long id){
        return ResponseResult.okResult(userService.getUserById(id));
    }

    @PutMapping
    public ResponseResult updateUser(@RequestBody UserDto userDto){
        List<Long> roleIds = userDto.getRoleIds();
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        userService.save(user);
        userRoleService.remove(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userDto.getId()));
        List<UserRole> collect = roleIds.stream().map(
                roleId -> new UserRole(userDto.getId(), roleId)
        ).collect(Collectors.toList());
        userRoleService.saveBatch(collect);
        return ResponseResult.okResult();
    }



}
