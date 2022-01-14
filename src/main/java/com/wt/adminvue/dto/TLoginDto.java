package com.wt.adminvue.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName TLoginDto.java
 * @Description TODO
 * @createTime 2022年01月14日 13:58:00
 */
@Data
public class TLoginDto {
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户昵称")
    private String petName;
}
