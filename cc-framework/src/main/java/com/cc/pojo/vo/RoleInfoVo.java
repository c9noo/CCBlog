package com.cc.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: CCBlog
 * @ClassName RoleInfoVo
 * @author: c9noo
 * @create: 2023-10-25 10:58
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleInfoVo {
    private Long id;
    private String remark;
    private String roleName;
    private Integer roleSort;
    private String roleKey;
    private String status;
}
