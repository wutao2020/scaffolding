package com.wt.adminvue.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 * 
 * @author 吴涛
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented
public @interface LogAnnotation {
	String module() default "";// 操作模块
	String operType() default "";  // 操作类型
	String operDesc() default "";  // 操作说明
}
