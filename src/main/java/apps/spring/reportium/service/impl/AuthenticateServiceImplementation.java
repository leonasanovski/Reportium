package apps.spring.reportium.service.impl;

import apps.spring.reportium.entity.UserProfileLog;
import apps.spring.reportium.entity.enumerations.LogType;
import apps.spring.reportium.repository.UserProfileLogRepository;
import apps.spring.reportium.entity.ReportiumUser;
import apps.spring.reportium.entity.UserProfile;
import apps.spring.reportium.entity.exceptions.NoExistingCredentialsException;
import apps.spring.reportium.entity.exceptions.NotAuthenticatedUserException;
import apps.spring.reportium.entity.exceptions.UserAlreadyExistsException;
import apps.spring.reportium.repository.ReportiumUserRepository;
import apps.spring.reportium.repository.UserProfileRepository;
import apps.spring.reportium.service.AuthenticationService;
import apps.spring.reportium.service.UserLogService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticateServiceImplementation implements AuthenticationService {
    private final ReportiumUserRepository reportiumUserRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileLogRepository profileLogRepository;
    private final UserLogService userProfileLogService;

    public AuthenticateServiceImplementation(ReportiumUserRepository reportiumUserRepository,
                                             UserProfileRepository userProfileRepository,
                                             PasswordEncoder passwordEncoder,
                                             UserProfileLogRepository profileLogRepository,
                                             UserLogService userProfileLogService) {
        this.reportiumUserRepository = reportiumUserRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileLogRepository = profileLogRepository;
        this.userProfileLogService = userProfileLogService;
    }

    @Override
    @Transactional
    public void register_user(String name, String surname, String email, String password, String password_confirmation) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty() || password_confirmation == null || password_confirmation.isEmpty()) {
            throw new NoExistingCredentialsException("Credentials cannot be empty.");
        }
        if (!password.equals(password_confirmation)) {
            throw new IllegalArgumentException("Passwords do not match. Check the mistake and try again.");
        }
        if (reportiumUserRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException(String.format("User with this email '%s' already exists.", email));
        }
        //creating the user
        ReportiumUser new_application_user = new ReportiumUser();
        new_application_user.setActive(true);
        new_application_user.setName(name);
        new_application_user.setSurname(surname);
        new_application_user.setEmail(email);
        new_application_user.setPasswordHash(passwordEncoder.encode(password));
        new_application_user.setCreatedAt(LocalDateTime.now());

        ReportiumUser savedUser = reportiumUserRepository.save(new_application_user);
        UserProfile user_profile = userProfileRepository.findByReportiumUser(savedUser).get();

        userProfileLogService.createLog(user_profile.getProfileId(), LogType.REGISTRATION);
    }

    @Override
    public ReportiumUser login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new NoExistingCredentialsException("Credentials cannot be empty.");
        }
        ReportiumUser user = reportiumUserRepository.findByEmail(email)
                .orElseThrow(() -> new NoExistingCredentialsException("Invalid email."));
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new NoExistingCredentialsException("Invalid password.");
        }
        return user;
    }

    @Override
    public ReportiumUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new NotAuthenticatedUserException("No user is currently authenticated.");
        }
        String email = authentication.getName();
        return reportiumUserRepository.findByEmail(email)
                .orElseThrow(() -> new NoExistingCredentialsException("Authenticated user not found in the database."));
    }
}
