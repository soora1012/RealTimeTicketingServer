package com.ticketing.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private Cookie cookie = new Cookie();

    @Getter
    @Setter
    public static class Cookie {

        private boolean secure;

        private String sameSite;
    }
}
