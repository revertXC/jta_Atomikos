package com.revert.platform.common.aop;

import com.revert.platform.common.base.model.WebResult;
import com.revert.platform.common.constant.ResultInfo;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import java.lang.reflect.Method;

@Log4j2
@Aspect
@Component
public class AuthorityAspect {

    @Pointcut("@annotation(com.revert.platform.common.annotation.Authority)")
    public void pointcut(){};

    @ResponseBody
    @Around("pointcut()")
    public Object isAccessMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        /** 获取访问目标方法*/
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Object o[] = joinPoint.getArgs();
//        if(o != null && o.length > 0){
//            Class clazzs[] = methodSignature.getParameterTypes();
//            for(int i=0; i< clazzs.length ;i++){
//                switch (clazzs[i].getTypeName()){
//                    case "java.lang.Long":
//                        o[i] = new Long(999);
//                        break;
//                }
//            }
//        }
        Method targetMethod = methodSignature.getMethod();
        if(true){
            return joinPoint.proceed(o);
        }
        WebResult webResult = new WebResult();
        webResult.code(ResultInfo.CODE_AUTHORITY).message(ResultInfo.MSG_AUTHORITY);
        return webResult;
    }

}
