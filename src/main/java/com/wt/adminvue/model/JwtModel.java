package com.wt.adminvue.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JwtModel {
    @ApiModelProperty("token")
    public String jwt;
}
