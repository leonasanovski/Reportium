package apps.spring.reportium.service.impl;

import apps.spring.reportium.entity.UserProfile;
import apps.spring.reportium.entity.UserProfileLog;
import apps.spring.reportium.entity.enumerations.LogType;
import apps.spring.reportium.entity.exceptions.NoExistingCredentialsException;
import apps.spring.reportium.repository.UserProfileLogRepository;
import apps.spring.reportium.repository.UserProfileRepository;
import apps.spring.reportium.service.UserLogService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserLogServiceImplementation implements UserLogService {
    private final UserProfileRepository profileRepository;
    private final UserProfileLogRepository userProfileLogRepository;

    public UserLogServiceImplementation(UserProfileRepository profileRepository,
                                        UserProfileLogRepository userProfileLogRepository) {
        this.profileRepository = profileRepository;
        this.userProfileLogRepository = userProfileLogRepository;
    }

    @Override
    @Transactional
    public void createLog(Integer userId, LogType type) {
        if (type == null) {
            throw new IllegalArgumentException("Log type must not be null");
        }

        UserProfile profile = profileRepository
                .findById(userId).orElseThrow(() -> new NoExistingCredentialsException("User does not exist"));

        String fullName = buildFullName(profile);

        String description = switch (type) {
            case LOGIN -> "User %s logged in successfully.".formatted(fullName);
            case REGISTRATION -> "User %s registered successfully.".formatted(fullName);
            case LOGOUT -> "User %s logged out successfully.".formatted(fullName);
            case CHANGE_ROLE -> "The role of user %s was changed successfully.".formatted(fullName);
            case CHANGE_PASSWORD -> "User %s changed the password successfully.".formatted(fullName);
        };

        UserProfileLog log = new UserProfileLog();
        log.setUserProfile(profile);
        log.setChangeDescription(description);
        log.setChangedAt(LocalDateTime.now());
        userProfileLogRepository.save(log);
    }

    private String buildFullName(UserProfile profile) {
        var u = profile.getReportiumUser();
        String name = u.getName() == null ? "" : u.getName().trim();
        String surname = u.getSurname() == null ? "" : u.getSurname().trim();
        return (name + " " + surname).trim();
    }
}
