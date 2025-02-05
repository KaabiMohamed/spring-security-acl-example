package com.med.security.access;

import org.springframework.security.acls.model.Permission;

public interface AclPermissionService {

    void grantAccessToObject(Object object, String user, Permission permission);

    void revokeAccessToObject(Object object, String user, boolean deleteChildren);
}
