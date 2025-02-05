package com.med.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.core.Authentication;

/**
 * CONFIGURATION OF PERMISSIONS HIERARCHY
 */
@Slf4j
public class PermissionConfiguration {

    public static final String SUPER_USER = "SUPER_USER"; //USER WITH FULL ALL ACCESS

    // Full administrative access includes all permissions
    private static final Permission[] ADMINISTRATION_ACCESS_LIST = {
            BasePermission.ADMINISTRATION
    };

    // Write access includes write, read, and all higher permissions
    private static final Permission[] WRITE_ACCESS_LIST = {
            BasePermission.ADMINISTRATION,
            BasePermission.WRITE,
            BasePermission.READ,
    };

    // Create access includes create, read, and all higher permissions
    private static final Permission[] CREATE_ACCESS_LIST = {
            BasePermission.ADMINISTRATION,
            BasePermission.CREATE,
            BasePermission.READ,
    };

    // Delete access includes delete, read, and all higher permissions
    private static final Permission[] DELETE_ACCESS_LIST = {
            BasePermission.ADMINISTRATION,
            BasePermission.DELETE,
    };

    // Read access includes read and all higher permissions
    private static final Permission[] READ_ACCESS_LIST = {
            BasePermission.ADMINISTRATION,
            BasePermission.READ,
            BasePermission.CREATE,
            BasePermission.WRITE,
            BasePermission.DELETE
    };

    // Getter for the permission lists
    public static Permission[] getPermissionsFor(String permission) {
        if ("ADMINISTRATION".equalsIgnoreCase(permission)) {
            return ADMINISTRATION_ACCESS_LIST;
        } else if ("WRITE".equalsIgnoreCase(permission)) {
            return WRITE_ACCESS_LIST;
        } else if ("READ".equalsIgnoreCase(permission)) {
            return READ_ACCESS_LIST;
        } else if ("CREATE".equalsIgnoreCase(permission)) {
            return CREATE_ACCESS_LIST;
        } else if ("DELETE".equalsIgnoreCase(permission)) {
            return DELETE_ACCESS_LIST;
        }
        throw new IllegalStateException("Could not map permission" + permission + " is not allowed in the APP");
    }

    public static boolean isSuperUser(Authentication authentication) {
        return authentication.getAuthorities()
                .stream()
                .anyMatch(e -> e.getAuthority().equalsIgnoreCase(SUPER_USER));
    }
}
