package com.wt.adminvue.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName PassDto.java
 * @Description TODO
 * @createTime 2021年12月29日 11:09:00
 */
@Data
public class PassDto {

    @ApiModelProperty( "新密码")
    private String password;

    @ApiModelProperty( "旧密码")
    private String currentPass;
}
