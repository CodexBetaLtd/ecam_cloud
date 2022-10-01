package com.codex.ecam.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class FocusLogger {

	@Around(value = "com.codex.ecam.aop.PointcutDefinition.serviceLayer()")
    public Object serviceLayerLog(ProceedingJoinPoint joinPoint) throws Throwable {
        return log(joinPoint);
    }

    private Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        Logger log = LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());

        StringBuffer message = new StringBuffer();

        message.append("---------- Log ---------");
        message.append(", Method : " + joinPoint.getSignature().getName());
        message.append(", Args : " + Arrays.toString(joinPoint.getArgs()));
        try {
            Object result = joinPoint.proceed();
            message.append(", Result : " + result);
            log.info(message.toString());
            return result;
        } catch (Throwable e) {
            message.append(", Exception : ");
            message.append(e.getMessage());
            log.error(message.toString());
            throw e;
        }
	}

}
