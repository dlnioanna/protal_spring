package com.protal.myApp.jwt;

import com.google.common.net.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {
    public static final String tokenPrefix="Bearer";
    public static final String secretKey="secureProtalsecureProtalsecureProtal";
    public static final Integer tokenExpirationAfterDays=2;
    public JwtConfig() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public Integer getTokenExpirationAfterDays() {
        return tokenExpirationAfterDays;
    }

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
