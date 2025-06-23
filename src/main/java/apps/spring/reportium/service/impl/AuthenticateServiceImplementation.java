package apps.spring.reportium.service.impl;
import apps.spring.reportium.entity.UserProfileLog;
import apps.spring.reportium.repository.UserProfileLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import apps.spring.reportium.entity.ReportiumUser;
import apps.spring.reportium.entity.Role;
import apps.spring.reportium.entity.UserProfile;
import apps.spring.reportium.entity.exceptions.NoExistingCredentialsException;
import apps.spring.reportium.entity.exceptions.NotAuthenticatedUserException;
import apps.spring.reportium.entity.exceptions.UserAlreadyExistsException;
import apps.spring.reportium.repository.ReportiumUserRepository;
import apps.spring.reportium.repository.RoleRepository;
import apps.spring.reportium.repository.UserProfileRepository;
import apps.spring.reportium.service.AuthenticationService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticateServiceImplementation implements AuthenticationService {
    private final ReportiumUserRepository reportiumUserRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserProfileLogRepository profileLogRepository;

    public AuthenticateServiceImplementation(ReportiumUserRepository reportiumUserRepository, UserProfileRepository userProfileRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserProfileLogRepository profileLogRepository) {
        this.reportiumUserRepository = reportiumUserRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.profileLogRepository = profileLogRepository;
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
        if(reportiumUserRepository.findByEmail(email).isPresent()) {
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
        //I have a trigger that creates the user profile
        UserProfile user_profile = userProfileRepository.findByReportiumUser(savedUser).get();
        UserProfileLog userProfileLog = new UserProfileLog();
        userProfileLog.setUserProfile(user_profile);
        userProfileLog.setChangedAt(LocalDateTime.now());
        String description = String.format("New user <%s %s> with mail '%s' has been registered.", savedUser.getName(), savedUser.getSurname() , savedUser.getEmail());
        userProfileLog.setChangeDescription(description);
        profileLogRepository.save(userProfileLog);
    }

    public ReportiumUser login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new NoExistingCredentialsException("Credentials cannot be empty.");
        }
        ReportiumUser user = reportiumUserRepository.findByEmail(email)
                .orElseThrow(() -> new NoExistingCredentialsException("Invalid email."));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new NoExistingCredentialsException("Invalid password.");
        }
        UserProfileLog userProfileLog = new UserProfileLog();
        UserProfile up = userProfileRepository.findByReportiumUser(user).get();
        userProfileLog.setUserProfile(up);
        userProfileLog.setChangedAt(LocalDateTime.now());
        String description = String.format("User <%s %s> with mail '%s' has logged in.", user.getName(), user.getSurname() , user.getEmail());
        userProfileLog.setChangeDescription(description);
        profileLogRepository.save(userProfileLog);
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
