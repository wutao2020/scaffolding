package com.wt.adminvue.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName PermDto.java
 * @Description TODO
 * @createTime 2021年12月29日 09:05:00
 */
@Data
public class PermDto {
    @ApiModelProperty("角色id")
    private Long roleId;
    @ApiModelProperty("菜单id")
    private List<Long> menuIds;
}
