package com.med.security.service.reports;

import com.med.security.access.AclPermissionService;
import com.med.security.domain.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Service;

/**
 * User story : as a document administrator i want to grant access of a document to another user
 */
@Service
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasPermission(#report, 'ADMINISTRATION')")
public class GrantAccessToReportUseCase {

    private final AclPermissionService aclPermissionService;

    public void authorize(Report report, String username, Permission permission) {
        aclPermissionService.grantAccessToObject(report, username, permission);
    }

    public void revoke(Report report, String username) {
        aclPermissionService.revokeAccessToObject(report, username, true);
    }

}
