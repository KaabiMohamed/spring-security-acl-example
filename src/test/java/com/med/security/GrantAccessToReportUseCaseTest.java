package com.med.security;

import com.med.security.domain.Report;
import com.med.security.init.DataInitializer;
import com.med.security.service.reports.CreateReportUseCase;
import com.med.security.service.reports.GrantAccessToReportUseCase;
import com.med.security.service.reports.ReportsLookUpUseCase;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.authorization.AuthorizationDeniedException;

@SpringBootTest
public class GrantAccessToReportUseCaseTest {

    @Inject
    GrantAccessToReportUseCase grantAccessToReportUseCase;

    @Inject
    CreateReportUseCase createReportUseCase;

    @Inject
    ReportsLookUpUseCase reportsLookUpUseCase;

    @Test
    @DisplayName("Owner can grant access to users and revoke access subsequently")
    public void testThatOwnerCanGrantAccessToAnyOtherUser() {
        // Authenticate as the owner (JOHN_DOE)
        TestUtils.authenticateUser(DataInitializer.JOHN_DOE, "****");

        // Create a report as JOHN_DOE
        Report report = createReportUseCase.execute("title", "content");

        // Grant read access to ALICE_WALKER and CHARLIE_BROWN
        grantAccessToReportUseCase.authorize(report, DataInitializer.ALICE_WALKER, BasePermission.READ);
        grantAccessToReportUseCase.authorize(report, DataInitializer.CHARLIE_BROWN, BasePermission.READ);

        // Authenticate as ALICE_WALKER, who should now have access
        TestUtils.authenticateUser(DataInitializer.ALICE_WALKER, null, "READ");
        Report fetchedReport = reportsLookUpUseCase.findById(report.getId());
        Assertions.assertNotNull(fetchedReport);
        Assertions.assertEquals(DataInitializer.JOHN_DOE, report.getOwner());

        // Switch back to the owner
        TestUtils.authenticateUser(DataInitializer.JOHN_DOE, "****");

        // Revoke access for ALICE_WALKER
        grantAccessToReportUseCase.revoke(fetchedReport, DataInitializer.ALICE_WALKER);

        // Re-authenticate as ALICE_WALKER; access should now be denied
        TestUtils.authenticateUser(DataInitializer.ALICE_WALKER, null, "READ");
        Assertions.assertThrows(AuthorizationDeniedException.class, () ->
                reportsLookUpUseCase.findById(report.getId())
        );
    }
}
