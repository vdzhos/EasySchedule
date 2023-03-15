package com.easyschedule.configurations;

import com.easyschedule.utils.Utils;
import com.easyschedule.utils.UtilsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Utils getUtils(){
        return new UtilsImpl();
    }

}
