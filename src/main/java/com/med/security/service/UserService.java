package com.med.security.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public static String getAuthenticatedUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
