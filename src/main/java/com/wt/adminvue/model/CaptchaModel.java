package com.wt.adminvue.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CaptchaModel {
    @ApiModelProperty("验证码")
    private String captchaImg;
    @ApiModelProperty("随机码")
    private String randomCode;
}
