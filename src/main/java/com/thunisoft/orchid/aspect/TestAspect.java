package com.thunisoft.orchid.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.thunisoft.orchid.annotation.MyAnnotation;

/**
 * @author wangjiafeng
 * @version 1.0
 * @description
 * @date 2020-03-31 下午 16:26
 */
@Aspect
@Component
public class TestAspect {
    //Pointcut 是指那些方法需要被执行"AOP",是由"Pointcut Expression"来描述的
    @Pointcut("@annotation(com.thunisoft.orchid.annotation.MyAnnotation)")
    public void addAdvice(){}

    //@Around 环绕切点,在进入切点前,跟切点后执行
    @Around("addAdvice()")
    public Object Interceptor(ProceedingJoinPoint joinPoint){
        System.out.println("环绕切点：开始");
        Object retmsg=null;
        try {
            retmsg = joinPoint.proceed();
            System.err.println("++++++++"+retmsg);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("环绕切点：结束-" + retmsg);
        Object result = null;
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            String deviceId = (String) args[0];
            if (!"03".equals(deviceId)) {
                return "no anthorization";
            }
        }
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    //@Before 在切点前执行方法,内容为指定的切点
    @Before("addAdvice()")
    public void before(JoinPoint joinPoint){
        MethodSignature sign =  (MethodSignature)joinPoint.getSignature();
        Method method = sign.getMethod();
        MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
        System.out.println("before切点之前：" + annotation.value());
    }

    //@After 在切点后,return前执行,
    @After("addAdvice()")
    public void after() {
        System.out.println("after切点之后");
    }
}
