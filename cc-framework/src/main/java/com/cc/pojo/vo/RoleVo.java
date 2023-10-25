package com.cc.pojo.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: CCBlog
 * @ClassName RoleVo
 * @author: c9noo
 * @create: 2023-10-24 21:51
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleVo {
    private Long createBy;
    private Date createTime;
    private String delFlag;
    private Long id;
    private String remark;
    private String roleName;
    private Integer roleSort;
    private String roleKey;
    private String status;
    private Long updateBy;
}
