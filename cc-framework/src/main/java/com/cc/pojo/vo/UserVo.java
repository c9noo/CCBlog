package com.cc.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cc.pojo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @program: CCBlog
 * @ClassName UserVo
 * @author: c9noo
 * @create: 2023-10-25 08:58
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserVo {
    private List<Long> roleIds;
    private List<RoleVo> roles;
    private User user;
}
