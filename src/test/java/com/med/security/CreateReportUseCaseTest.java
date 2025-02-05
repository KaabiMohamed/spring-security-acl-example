package com.med.security;

import com.med.security.domain.Report;
import com.med.security.init.DataInitializer;
import com.med.security.service.reports.CreateReportUseCase;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
public class CreateReportUseCaseTest {

    @Inject
    private CreateReportUseCase createReportUseCase;

    @Test
    @DisplayName("Should create report with correct owner when authenticated")
    @WithMockUser(DataInitializer.JOHN_DOE)
    public void shouldCreateReportWithCorrectOwner() {
        String reportTitle = "Hello World";
        String reportContent = "This is a serious article";

        Report report = createReportUseCase.execute(reportTitle, reportContent);

        Assertions.assertEquals(DataInitializer.JOHN_DOE, report.getOwner());
        Assertions.assertEquals(reportTitle, report.getTitle());
        Assertions.assertEquals(reportContent, report.getContent());
    }

    @Test
    @DisplayName("Should throw exception for anonymous user creating report")
    @WithAnonymousUser
    public void shouldThrowExceptionForAnonymousUserCreatingReport() {
        String reportTitle = "Hello World";
        String reportContent = "This is a serious article";

        Assertions.assertThrows(AuthorizationDeniedException.class, () -> {
            createReportUseCase.execute(reportTitle, reportContent);
        });
    }
}
