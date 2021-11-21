package com.wt.scaffolding.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-17
 */
@Data
@TableName("sys_logs")
@ApiModel(value = "Logs对象", description = "")
public class Logs implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("userId")
    private Integer userId;

    @ApiModelProperty("模块名")
    @TableField("module")
    private String module;

    @ApiModelProperty("成功失败")
    @TableField("flag")
    private Integer flag;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @TableField("createTime")
    private LocalDateTime createTime;


}
