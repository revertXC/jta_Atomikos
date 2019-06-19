package com.revert.platform.common.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * controller 方法 日志记录
 */
@Aspect
/** 声明此类是 切面*/
@Component
public class WebLogAspect {
    /**
     * 日志对象
     */
    protected Logger log = LoggerFactory.getLogger(getClass());
    //ThreadLocal保证不受其他线程影响，用于记录接口响应时间
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();

    @Pointcut("execution(public * com.revert..*.controller..*.*(..))")
    public void pointcut(){}

    @Before(value = "pointcut()")
    public void doBefore(JoinPoint joinPoint){
        MethodSignature methodSignature = ((MethodSignature)joinPoint.getSignature());
        String doMethod = methodSignature.getDeclaringType().toString()+"."+methodSignature.getMethod().getName();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        threadLocal.set(System.currentTimeMillis());
        if(requestAttributes == null) return;
        HttpServletRequest request = requestAttributes.getRequest();
        Enumeration<String> paramNamesEnu = request.getParameterNames();
        List<String> paramValues = new ArrayList<>();
        List<String> paramNames = new ArrayList<>();
        while(paramNamesEnu.hasMoreElements()){
            String key = paramNamesEnu.nextElement();
            paramValues.add(request.getParameter(key));
            paramNames.add(key);
        }
        log.info("方法:{},参数名称:{},参数值:{}",doMethod,paramNames,paramValues);
    }

    @AfterReturning(value = "pointcut()",returning = "res")
    public void doAfter(JoinPoint joinPoint, Object res){
        MethodSignature methodSignature = ((MethodSignature)joinPoint.getSignature());
        String doMethod = methodSignature.getDeclaringType().toString()+"."+methodSignature.getMethod().getName();
        /**记录一下接口响应时间*/
        Long execTime = System.currentTimeMillis() - threadLocal.get();
        log.info("方法:{},执行时间(毫秒):{},返回结果:{}",doMethod,execTime, JSONObject.toJSONString(res));
    }

}
