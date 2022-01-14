package com.wt.adminvue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 日志
 * </p>
 *
 * @author wutao
 * @since 2021-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_logs")
@ApiModel(value="Logs对象", description="日志")
public class Logs implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "模块名")
    private String module;

    @ApiModelProperty(value = "请求参数")
    private String operRequParam;

    @ApiModelProperty(value = "请求方法名")
    private String operMethod;

    @ApiModelProperty(value = "异常名称")
    private String excName;

    @ApiModelProperty(value = "异常信息")
    private String excMessage;

    @ApiModelProperty(value = "用户id")
    private Long operUserId;

    @ApiModelProperty(value = "操作员名称")
    private String operUserName;

    @ApiModelProperty(value = "操作URI")
    private String operUri;

    @ApiModelProperty(value = "操作员IP")
    private String operIp;

    @ApiModelProperty(value = " 操作版本号")
    private String operVer;

    @ApiModelProperty(value = "发生异常时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "返回结果")
    private String operResParam;

    @ApiModelProperty(value = "操作类型")
    private String operType;

    @ApiModelProperty(value = "操作描述")
    private String operDesc;

    @ApiModelProperty(value = "是否异常(1是，0否)")
    private Integer isAbnormal;

    @ApiModelProperty(value = "日志来源(1管理端，0app)")
    private Integer isManage;


}
