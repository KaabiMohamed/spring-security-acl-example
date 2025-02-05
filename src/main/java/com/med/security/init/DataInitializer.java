package com.med.security.init;

import com.med.security.domain.User;
import com.med.security.domain.UserRepository;
import com.med.security.service.reports.CreateReportUseCase;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    public static final String JOHN_DOE = "john_doe";
    public static final String JANE_SMITH = "jane_smith";
    public static final String ALICE_WALKER = "alice_walker";
    public static final String BOB_JONES = "bob_jones";
    public static final String CHARLIE_BROWN = "charlie_brown";

    private final UserRepository userRepository;
    private final CreateReportUseCase createReportUseCase;

    @PostConstruct
    public void initData() {
        userRepository.save(User.builder()
                .username(JOHN_DOE)
                .name("John Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build());

        userRepository.save(User.builder()
                .username(JANE_SMITH)
                .name("Jane Smith")
                .email("jane.smith@example.com")
                .password("securepass")
                .build());

        userRepository.save(User.builder()
                .username(ALICE_WALKER)
                .name("Alice Walker")
                .email("alice.walker@example.com")
                .password("alice1234")
                .build());

        userRepository.save(User.builder()
                .username(BOB_JONES)
                .name("Bob Jones")
                .email("bob.jones@example.com")
                .password("bobsecure")
                .build());

        userRepository.save(User.builder()
                .username(CHARLIE_BROWN)
                .name("Charlie Brown")
                .email("charlie.brown@example.com")
                .password("charlie2025")
                .build());
        //createReportUseCase.execute("hllo", "me");
    }
}
