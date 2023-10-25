package com.cc.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: CCBlog
 * @ClassName UserDto
 * @author: c9noo
 * @create: 2023-10-25 08:26
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String userName;
    private String nickName;
    private String password;
    private String status;
    private String email;
    private String phonenumber;
    private String sex;
    List<Long> roleIds;
}
