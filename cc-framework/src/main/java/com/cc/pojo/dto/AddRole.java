package com.cc.pojo.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cc.pojo.entity.RoleMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @program: CCBlog
 * @ClassName AddRole
 * @author: c9noo
 * @create: 2023-10-25 10:36
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddRole {
    private String roleName;
    private String roleKey;
    private Integer roleSort;
    private String status;
    private String remark;
    private List<Long> menuIds;
}
