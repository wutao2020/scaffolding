package com.wt.adminvue.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName NavModel.java
 * @Description TODO
 * @createTime 2021年12月27日 16:24:00
 */
@Data
public class NavModel {
    @ApiModelProperty("权限")
    List<String> authoritys;
    @ApiModelProperty("菜单")
    List<SysMenuModel> nav;
}
