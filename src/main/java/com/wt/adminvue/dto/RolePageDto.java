package com.wt.adminvue.dto;

import com.wt.adminvue.page.PageQueryDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName RolePageDto.java
 * @Description TODO
 * @createTime 2021年12月28日 08:51:00
 */
@Data
public class RolePageDto extends PageQueryDTO {
    @ApiModelProperty("角色名称")
    private String name;
}
