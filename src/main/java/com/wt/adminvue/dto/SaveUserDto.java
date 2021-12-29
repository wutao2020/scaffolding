package com.wt.adminvue.dto;

import lombok.Data;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName SaveUserDto.java
 * @Description TODO
 * @createTime 2021年12月29日 10:36:00
 */
@Data
public class SaveUserDto {
    private Long id;

    private String username;

    private String password;

    private String avatar;

    private String email;

    private String city;


    private Integer statu;
}
