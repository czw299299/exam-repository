package com.cwq.codespringbootstarter;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoServiceConfig {
    @Bean
    public UtilService utilService(){
        return new UtilService();
    }
    @Bean
    public CodeUtil codeUtil(){
        return new CodeUtil();
    }
    @Bean
    public CodeImage codeImage() {return new CodeImage();}
}
