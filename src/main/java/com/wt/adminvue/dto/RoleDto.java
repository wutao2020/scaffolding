package com.wt.adminvue.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName RoleDto.java
 * @Description TODO
 * @createTime 2021年12月28日 09:27:00
 */
@Data
public class RoleDto {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String code;

    @ApiModelProperty(value = "备注")
    private String remark;

    private Integer statu;
}
