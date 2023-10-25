package com.cc.controller;

import com.cc.enums.AppHttpCodeEnum;
import com.cc.exception.SystemException;
import com.cc.pojo.entity.LoginUser;
import com.cc.pojo.entity.Menu;
import com.cc.pojo.entity.User;
import com.cc.pojo.vo.AdminUserInfoVo;
import com.cc.pojo.vo.RoutersVo;
import com.cc.pojo.vo.UserInfoVo;
import com.cc.result.ResponseResult;
import com.cc.service.LoginService;
import com.cc.service.MenuService;
import com.cc.service.RoleService;
import com.cc.utils.BeanCopyUtils;
import com.cc.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @program: CCBlog
 * @ClassName LoginController
 * @author: c9noo
 * @create: 2023-10-21 11:56
 * @Version 1.0
 **/
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LoginService SystemLoginServiceImpl;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){

        //TODO 为什么不是先根据用户id查询角色 然后在通过角色查询权限，而是直接通过用户id进行查询？
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());

        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装数据返回

        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }


    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return SystemLoginServiceImpl.logout();
    }
}
