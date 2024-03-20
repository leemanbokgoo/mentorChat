package com.example.metoChat.config;

import com.example.metoChat.aop.TimeTraceAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public TimeTraceAop timeTraceAop(){
        return new TimeTraceAop();
    }


}
