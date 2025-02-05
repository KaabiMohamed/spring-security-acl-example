package com.med.security.service.reports;

import com.med.security.access.AclPermissionService;
import com.med.security.domain.Report;
import com.med.security.domain.ReportRepository;
import com.med.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("isAuthenticated()")
public class CreateReportUseCase {
    private final ReportRepository reportRepository;
    private final AclPermissionService aclPermissionService;

    public Report execute(String title, String content) {
        Report report = reportRepository.save(Report.builder()
                .owner(UserService.getAuthenticatedUserName())
                .title(title)
                .content(content).build());
        aclPermissionService.grantAccessToObject(report,
                UserService.getAuthenticatedUserName(),
                BasePermission.ADMINISTRATION);
        return report;
    }
}
