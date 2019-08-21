package com.neolith.focus.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointcutDefinition {

    @Pointcut("within(com.neolith.focus.controller..*)")
    public void webLayer() {
    }

    @Pointcut("within(com.neolith.focus.service..*)")
    public void serviceLayer() {
    }

    @Pointcut("within(com.neolith.focus.dao..*)")
    public void dataAccessLayer() {
    }

}
