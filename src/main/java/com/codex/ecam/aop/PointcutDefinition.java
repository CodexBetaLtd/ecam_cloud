package com.codex.ecam.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointcutDefinition {

    @Pointcut("within(com.codex.ecam.controller..*)")
    public void webLayer() {
    }

    @Pointcut("within(com.codex.ecam.service..*)")
    public void serviceLayer() {
    }

    @Pointcut("within(com.codex.ecam.dao..*)")
    public void dataAccessLayer() {
    }

}
