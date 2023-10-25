package com.cc.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: CCBlog
 * @ClassName RoleDto
 * @author: c9noo
 * @create: 2023-10-25 14:16
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private Long id;

    private String roleName;

    private String roleKey;

    private Integer roleSort;

    private String status;
    private String remark;
    private List<Long> menuIds;
}
