package com.wt.scaffolding.advice;

import cn.hutool.core.util.StrUtil;
import com.wt.scaffolding.annotation.LogAnnotation;
import com.wt.scaffolding.model.Logs;
import com.wt.scaffolding.service.ILogsService;
import com.wt.scaffolding.utils.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 统一日志处理
 *
 * @author 吴涛
 * 2021年11月17日
 */
@Aspect
@Component
public class LogAdvice {

	@Autowired
	private ILogsService logService;

	@Around(value = "@annotation(com.wt.scaffolding.annotation.LogAnnotation)")
	public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
		Logs sysLogs = new Logs();
		// 设置当前登录用户
        sysLogs.setUserId(UserUtil.getLoginUser().getId());
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

		String module = null;
		LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
		module = logAnnotation.module();
		if (StrUtil.isEmpty(module)) {
			ApiOperation apiOperation = methodSignature.getMethod().getDeclaredAnnotation(ApiOperation.class);
			if (apiOperation != null) {
				module = apiOperation.value();
			}
		}

		if (StrUtil.isEmpty(module)) {
			throw new RuntimeException("没有指定日志module");
		}
		sysLogs.setModule(module);

		try {
			Object object = joinPoint.proceed();
			sysLogs.setFlag(1);

			return object;
		} catch (Exception e) {
			sysLogs.setFlag(1);
			sysLogs.setRemark(e.getMessage());
			throw e;
        } finally {
            if (sysLogs.getUserId() != null&&sysLogs.getUserId().intValue()>0) {
                logService.save(sysLogs);
            }
        }

	}
}
