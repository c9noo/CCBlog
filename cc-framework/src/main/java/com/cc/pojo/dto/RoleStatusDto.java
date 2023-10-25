package com.cc.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: CCBlog
 * @ClassName RoleStatusDto
 * @author: c9noo
 * @create: 2023-10-25 09:52
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleStatusDto {
    private Integer roleId;
    private Integer status;
}
