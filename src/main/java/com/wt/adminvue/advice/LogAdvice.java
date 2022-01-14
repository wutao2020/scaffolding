package com.wt.adminvue.advice;

import com.alibaba.fastjson.JSON;
import com.wt.adminvue.annotation.LogAnnotation;
import com.wt.adminvue.entity.Logs;
import com.wt.adminvue.service.ILogsService;
import com.wt.adminvue.util.IPUtils;
import com.wt.adminvue.util.UserUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
	/**
	 * @description
	 * @author 吴涛
	 * @date 2021-12-10 17:25
	设置操作日志切入点 记录操作日志 在注解的位置切入代码

	 */
	@Pointcut("@annotation(com.wt.adminvue.annotation.LogAnnotation)")
	public void operLogPoinCut(){

	}
	/**
	 * @description
	 * @author 吴涛
	 * @date 2021-12-10 17:28
	设置操作异常切入点记录异常日志 扫描所有controller包下操作

	 */
    @Pointcut("execution(* com.wt.adminvue.controller..*.*(..))")
	public void operExceptionLogPoinCut(){

	}
	/**
	      * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
	      *
	      * @param joinPoint 切入点
	      * @param keys      返回结果
	      */
	@AfterReturning(value="operLogPoinCut()",returning="keys")
	public void saveOperLog(JoinPoint joinPoint, Object keys) {
		// 获取RequestAttributes
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		// 从获取RequestAttributes中获取HttpServletRequest的信息
		HttpServletRequest request =(HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
		Logs operlog = new Logs();
		try{
		    // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature =(MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
			String[] parameterNames = signature.getParameterNames();
			Object[] parameterValues = joinPoint.getArgs();
			//  请求的参数
			Map<String,Object> paramMap = new HashMap<>();
			for (int i=0;i<parameterValues.length;i++) {
				String s = JSON.toJSONString(parameterValues[i]);
				paramMap.put(parameterNames[i], s);
			}
            // 获取操作
            LogAnnotation opLog = method.getAnnotation(LogAnnotation.class);
            if (opLog != null){
                String operModul = opLog.module();
                String operType = opLog.operType();
                String operDesc = opLog.operDesc();
                operlog.setModule(operModul); // 操作模块
                operlog.setOperType(operType); // 操作类型
                operlog.setOperDesc(operDesc); // 操作描述
            }
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." +methodName;
            operlog.setOperMethod(methodName); // 请求方法

            // 将参数所在的数组转换成json
            String params = JSON.toJSONString(paramMap);
            operlog.setOperRequParam(params);// 请求参数
            operlog.setOperResParam(JSON.toJSONString(keys));// 返回结果
			operlog.setIsManage(UserUtil.getLoginUser().getIsAdmin());
			operlog.setOperUserId(UserUtil.getLoginUser().getUserId()); // 请求用户ID
            operlog.setIsAbnormal(0);
            operlog.setOperUserName(UserUtil.getLoginUser().getUsername());// 请求用户名称
            operlog.setOperIp(IPUtils.getIpAddr(request));// 请求IP
            operlog.setOperUri(request.getRequestURI());// 请求URI
            operlog.setCreateTime(LocalDateTime.now()); // 创建时间
            operlog.setOperVer("1.0.0");// 操作版本
            logService.save(operlog);
            }catch (Exception e){
		    e.printStackTrace();
		}
	}
	/**
		      * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
		      *
		      * @param joinPoint 切入点
		      * @param e         异常信息
		      */
	@AfterThrowing(pointcut = "operExceptionLogPoinCut()",throwing ="e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
	    // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        Logs excepLog = new Logs();
        try{
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method =signature.getMethod();
			String[] parameterNames = signature.getParameterNames();
			Object[] parameterValues = joinPoint.getArgs();
			//  请求的参数
			Map<String,Object> paramMap = new HashMap<>();
			for (int i=0;i<parameterValues.length;i++) {
				String s = JSON.toJSONString(parameterValues[i]);
				paramMap.put(parameterNames[i], s);
			}
			// 获取操作
			LogAnnotation opLog = method.getAnnotation(LogAnnotation.class);
			if (opLog != null){
				String operModul = opLog.module();
				String operType = opLog.operType();
				String operDesc = opLog.operDesc();
				excepLog.setModule(operModul); // 操作模块
				excepLog.setOperType(operType); // 操作类型
				excepLog.setOperDesc(operDesc); // 操作描述
			}
            // 获取请求的类名
            String className =joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName =className+"." + methodName;
            // 将参数所在的数组转换成json
            String params =JSON.toJSONString(paramMap);
			excepLog.setOperRequParam(params); // 请求参数
            excepLog.setOperMethod(methodName);// 请求方法名
            excepLog.setExcName(e.getClass().getName());// 异常名称
            excepLog.setExcMessage(stackTraceToString(e.getClass().getName(),e.getMessage(),e.getStackTrace()));// 异常信息
			excepLog.setIsManage(UserUtil.getLoginUser().getIsAdmin());
			excepLog.setOperUserId(UserUtil.getLoginUser().getUserId()); // 请求用户ID
			excepLog.setIsAbnormal(1);
            excepLog.setOperUserName(UserUtil.getLoginUser().getUsername());// 请求用户名称
            excepLog.setOperUri(request.getRequestURI());// 操作URI
            excepLog.setOperIp(IPUtils.getIpAddr(request));// 操作员IP
            excepLog.setOperVer("1.0.0");// 操作版本号
            excepLog.setCreateTime(LocalDateTime.now());// 发生异常时间
			logService.save(excepLog);
		}catch(Exception e2){
			e2.printStackTrace();
		}
	}
	/**
	      * 转换异常信息为字符串
	      *
	      * @param exceptionName    异常名称
	      * @param exceptionMessage 异常信息
	      * @param elements         堆栈信息
	      */
	public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
		StringBuffer strbuff = new StringBuffer();
		for (StackTraceElement stet:elements) {
			strbuff.append(stet + "\n");
		}
		String message = exceptionName + ":" + exceptionMessage + "\n\t" + strbuff.toString();
		return message;
	}

}
