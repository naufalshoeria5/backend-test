package com.bni.test.backendfitness.base.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    public JavaMailSender mailSender(){
        return new JavaMailSenderImpl();
    }
}
