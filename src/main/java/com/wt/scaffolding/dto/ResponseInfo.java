package com.wt.scaffolding.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //注解在类上，自动生成空参构造方法
@AllArgsConstructor//注解在类上，自动生成全部参数构造方法
public class ResponseInfo {

	private String code;
	private String message;


}
