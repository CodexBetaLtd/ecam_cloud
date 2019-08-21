package com.neolith.focus.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import(value = { DatabaseConfiguration.class, EmailConfiguration.class })
@ComponentScan(basePackages = {"com.neolith.focus.service", "com.neolith.focus.util", "com.neolith.focus.dao",
		"com.neolith.focus.aop", "com.neolith.focus.security", "com.neolith.focus.validation", "com.neolith.focus.support"})
@EnableAspectJAutoProxy
@EnableScheduling
public class SpringConfiguration {

}
