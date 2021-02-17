package com.protal.myApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PasswordConfig {
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(10);
//    }


    //χωρίς κωδικοποίηση για να φαίνεται στη βάση τι αποθηκεύεται
    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder defaultEncoder = NoOpPasswordEncoder.getInstance();
        String defaultIdForEncode = "noop";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(defaultIdForEncode, defaultEncoder);
        DelegatingPasswordEncoder passworEncoder = new DelegatingPasswordEncoder(defaultIdForEncode, encoders);
        passworEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);

        return passworEncoder;
    }
}
