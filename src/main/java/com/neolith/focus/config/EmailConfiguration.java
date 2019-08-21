package com.neolith.focus.config;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
@PropertySource(value = {"classpath:email.properties"})
public class EmailConfiguration {

    @Autowired
    private Environment environment;

    @Value("${mail.protocol}")
    private String mailProtocol;

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private Integer port;

    @Value("${mail.support.username}")
    private String userName;

    @Value("${mail.support.password}")
    private String password;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(environment.getRequiredProperty("mail.smtp.host"));
        mailSender.setPort(Integer.parseInt(environment.getRequiredProperty("mail.smtp.port")));
        mailSender.setUsername(environment.getRequiredProperty("mail.support.username"));
        mailSender.setPassword(environment.getRequiredProperty("mail.support.password"));


        final Properties mailProperties = new Properties();
        mailProperties.put("mail.transport.protocol", "smtp");
        mailProperties.setProperty("mail.smtp.auth", "true");
        mailProperties.setProperty("mail.smtp.starttls.enable", "true");
        mailProperties.setProperty("mail.smtp.from", "");
        mailSender.setJavaMailProperties(mailProperties);

        return mailSender;
    }

    @Bean
    public VelocityEngine velocityEngine() throws VelocityException, IOException {
        VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
        factory.setVelocityProperties(getVelocityEngineProperties());
        return factory.createVelocityEngine();
    }

    private Properties getVelocityEngineProperties() {
        Properties properties = new Properties();
        properties.put("input.encoding", "UTF-8");
		properties.put("output.encoding", "UTF-8");
		properties.put("resource.loader", "class");
        properties.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return properties;
    }

}
