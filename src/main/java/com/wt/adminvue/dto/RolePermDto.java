package com.wt.adminvue.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName RolePermDto.java
 * @Description TODO
 * @createTime 2021年12月29日 10:52:00
 */
@Data
public class RolePermDto {
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("角色")
    private List<Long> roleIds;
}
