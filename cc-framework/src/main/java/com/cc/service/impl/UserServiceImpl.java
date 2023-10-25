package com.cc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.enums.AppHttpCodeEnum;
import com.cc.exception.SystemException;
import com.cc.mapper.UserRoleMapper;
import com.cc.pojo.dto.UserDto;
import com.cc.pojo.entity.Role;
import com.cc.pojo.entity.User;
import com.cc.pojo.entity.UserRole;
import com.cc.pojo.vo.PageVo;
import com.cc.pojo.vo.RoleVo;
import com.cc.pojo.vo.UserInfoVo;
import com.cc.pojo.vo.UserVo;
import com.cc.result.ResponseResult;
import com.cc.service.RoleService;
import com.cc.service.UserRoleService;
import com.cc.service.UserService;
import com.cc.mapper.UserMapper;
import com.cc.utils.BeanCopyUtils;
import com.cc.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author 49751
* @description 针对表【sys_user(用户表)】的数据库操作Service实现
* @createDate 2023-10-17 09:34:46
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    /**
     * 个人信息查询
     * @return
     */
    @Override
    public ResponseResult userInfo() {
        //1. 获取userId，并且进行查询,封装Vo进行返回
        return new ResponseResult().ok(BeanCopyUtils.copyBean(getById(SecurityUtils.getUserId()), UserInfoVo.class));
    }
    /**
     * 用户信息更新
     * @param user
     * @return
     */
    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }
    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public PageVo pageList(Integer pageNum, Integer pageSize, String userName, String phonenumber, Integer status) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(userName)&&!userName.isEmpty(),User::getUserName,userName)
                .eq(Objects.nonNull(phonenumber)&&!phonenumber.isEmpty(),User::getPhonenumber,phonenumber)
                .eq(Objects.nonNull(status),User::getStatus,status);

        Page<User> page = new Page<>(pageNum,pageSize);
        userMapper.selectPage(page,wrapper);


        return new PageVo(page.getRecords(),page.getTotal());
    }

    @Override
    @Transactional
    public void add(UserDto userDto) {
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        userMapper.insert(user);

        List<UserRole> collect = userDto.getRoleIds().stream().map(
                roleId -> new UserRole(user.getId(), roleId)
        ).collect(Collectors.toList());

        userRoleService.saveBatch(collect);

    }

    @Override
    public UserVo getUserById(Long id) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        List<Long> roleIds = userRoleService.list(wrapper).stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<Role> roles = roleService.list(lambdaQueryWrapper);
        User user = userMapper.selectById(id);


        return new UserVo()
                .setUser(user)
                .setRoles(BeanCopyUtils.copyBeanList(roles, RoleVo.class))
                .setRoleIds(roleIds);
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper)>0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0;
    }


}




