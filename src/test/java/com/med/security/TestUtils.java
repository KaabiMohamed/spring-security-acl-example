package com.med.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class TestUtils {
    public static void authenticateUser(String username, String password, String... authorities) {
        Authentication authentication = new TestingAuthenticationToken(username, password, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Authenticated User {} {}", username, authentication.toString());

    }
}
