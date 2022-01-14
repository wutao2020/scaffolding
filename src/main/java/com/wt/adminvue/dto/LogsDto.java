package com.wt.adminvue.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wt.adminvue.page.PageQueryDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author 吴涛
 * @since 2021-11-17
 */
@Data
public class LogsDto extends PageQueryDTO {

    @TableField("id")
    private Integer id;

    @ApiModelProperty("操作员名称")
    private String operUserName;

    @ApiModelProperty("发生异常时间")
    private String createTime;

    @ApiModelProperty(" 操作类型")
    private String operType;

    @ApiModelProperty("是否异常(1是，0否)")
    private Integer isAbnormal;

    @ApiModelProperty("日志来源(1管理端，2app)")
    private Integer isManage;
}
