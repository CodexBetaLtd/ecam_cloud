package com.neolith.focus.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.neolith.focus.service", "com.neolith.focus.support", "com.neolith.focus.security" })
public class TestSpringConfiguration {

}
