package com.med.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Slf4j
public class CustomPermissionEvaluator extends AclPermissionEvaluator {

    public CustomPermissionEvaluator(AclService aclService) {
        super(aclService);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object domainObject, Object permission) {
        if (PermissionConfiguration.isSuperUser(authentication))
            return true;
        if (permission instanceof String role) {
            return super.hasPermission(authentication, domainObject, PermissionConfiguration.getPermissionsFor(role));
        } else return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (PermissionConfiguration.isSuperUser(authentication))
            return true;
        if (permission instanceof String role) {
            return super.hasPermission(authentication, targetId, targetType, PermissionConfiguration.getPermissionsFor(role));
        } else
            return false;
    }

}
