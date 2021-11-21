package com.wt.scaffolding.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName TokenModel.java
 * @Description TODO
 * @createTime 2021年11月18日 14:55:00
 */
@Data
@TableName("t_token")
@ApiModel(value = "Token对象", description = "")
public class TokenModel {
    @ApiModelProperty("token")
    @TableId("id")
    private String id;

    @ApiModelProperty("LoginUser的json串")
    @TableField("val")
    private String val;

    @TableField("expireTime")
    private LocalDateTime expireTime;

    @TableField("createTime")
    private LocalDateTime createTime;

    @TableField("updateTime")
    private LocalDateTime updateTime;
}
