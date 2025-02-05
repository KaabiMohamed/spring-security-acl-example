package com.med.security;

import com.med.security.config.PermissionConfiguration;
import com.med.security.domain.Report;
import com.med.security.init.DataInitializer;
import com.med.security.service.reports.CreateReportUseCase;
import com.med.security.service.reports.ReportsLookUpUseCase;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

@SpringBootTest
public class ReportsLookUpUseCaseTest {

    @Inject
    private ReportsLookUpUseCase reportsLookUpUseCase;

    @Inject
    private CreateReportUseCase createReportUseCase;

    @Test
    @DisplayName("Owner can find report by ID")
    @WithMockUser(value = DataInitializer.CHARLIE_BROWN)
    public void thatOwnerHaveAccess_findById() {
        Integer id = createReportUseCase.execute("title", "content").getId();
        Report report = reportsLookUpUseCase.findById(id);
        Assertions.assertEquals(DataInitializer.CHARLIE_BROWN, report.getOwner());
    }

    @Test
    @DisplayName("Super user can find report by ID")
    @WithMockUser(value = DataInitializer.BOB_JONES)
    public void thatSuperUserHaveAccess_findById() {
        // Create document as BOB
        Integer id = createReportUseCase.execute("title", "content").getId();
        // Authenticate a super user
        TestUtils.authenticateUser("TheSuperUser", "****", PermissionConfiguration.SUPER_USER);
        Report report = reportsLookUpUseCase.findById(id);
        // Make sure the super user can see it
        Assertions.assertNotNull(report);
        Assertions.assertEquals(DataInitializer.BOB_JONES, report.getOwner());
    }

    @Test
    @DisplayName("Only owner can see report; others are denied access")
    @WithMockUser(value = DataInitializer.BOB_JONES)
    public void makeSureOnlyOwnerCanSeeIt_findById() {
        // Create document as BOB
        Integer id = createReportUseCase.execute("title", "content").getId();
        // Authenticate another user (who should not have access)
        TestUtils.authenticateUser(DataInitializer.JOHN_DOE, "****", "READ", "ADMINISTRATION");

        Assertions.assertThrows(AuthorizationDeniedException.class, () -> {
            reportsLookUpUseCase.findById(id);
        });
    }

    @Test
    @DisplayName("Owner can retrieve all their reports")
    @WithMockUser(value = DataInitializer.ALICE_WALKER)
    public void thatOwnerHaveAccess_findAll() {
        createReportUseCase.execute("title", "content");
        createReportUseCase.execute("title", "content");
        createReportUseCase.execute("title", "content");
        int size = reportsLookUpUseCase.findAll().size();
        Assertions.assertEquals(3, size);
    }

    @Test
    @DisplayName("Users without proper access see only their own reports")
    @WithMockUser(value = DataInitializer.ALICE_WALKER)
    public void thatAnyOtherUserWillNotHaveAccess_findAll() {
        createReportUseCase.execute("title", "content");
        createReportUseCase.execute("title", "content");
        TestUtils.authenticateUser(DataInitializer.JANE_SMITH, "****", "READ", "ADMINISTRATION");
        createReportUseCase.execute("title", "content");
        List<Report> reports = reportsLookUpUseCase.findAll();
        Assertions.assertEquals(1, reports.size());
        Assertions.assertEquals(DataInitializer.JANE_SMITH, reports.stream().findFirst().get().getOwner());
    }
}
