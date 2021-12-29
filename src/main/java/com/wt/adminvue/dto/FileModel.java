package com.wt.adminvue.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 吴涛
 * @version 1.0.0
 * @ClassName FileModel.java
 * @Description TODO
 * @createTime 2021年12月29日 14:06:00
 */
@Data
public class FileModel {
    @ApiModelProperty("文件路径")
    private String filePath;
}
