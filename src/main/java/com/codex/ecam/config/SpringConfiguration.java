package com.codex.ecam.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import(value = { DatabaseConfiguration.class, EmailConfiguration.class, AWSConfiguration.class })
@ComponentScan(basePackages = { "com.codex.ecam.service", "com.codex.ecam.util", "com.codex.ecam.dao",
		"com.codex.ecam.aop", "com.codex.ecam.security", "com.codex.ecam.validation", "com.codex.ecam.support","com.codex.ecam.cache" })
@EnableAspectJAutoProxy
@EnableScheduling
public class SpringConfiguration {

}
