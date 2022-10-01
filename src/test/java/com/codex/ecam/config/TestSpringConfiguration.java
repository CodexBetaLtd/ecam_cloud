package com.codex.ecam.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.codex.ecam.service", "com.codex.ecam.support", "com.codex.ecam.security" })
public class TestSpringConfiguration {

}
