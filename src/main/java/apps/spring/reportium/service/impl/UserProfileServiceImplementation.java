package apps.spring.reportium.service.impl;

import apps.spring.reportium.entity.ReportiumUser;
import apps.spring.reportium.entity.UserProfile;
import apps.spring.reportium.repository.ReportiumUserRepository;
import apps.spring.reportium.repository.UserProfileRepository;
import apps.spring.reportium.service.UserProfileService;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImplementation implements UserProfileService {
    private final UserProfileRepository profileRepository;
    private final ReportiumUserRepository reportiumUserRepository;

    public UserProfileServiceImplementation(UserProfileRepository profileRepository, ReportiumUserRepository reportiumUserRepository) {
        this.profileRepository = profileRepository;
        this.reportiumUserRepository = reportiumUserRepository;
    }


    @Override
    public UserProfile findByUserId(Integer userId) {
        ReportiumUser ru = reportiumUserRepository
                .findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with id=%d not found", userId)));

        return profileRepository
                .findByReportiumUser(ru)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User profile for user %s not found!",ru.getEmail())));
    }

    @Override
    public UserProfile save(UserProfile userProfile) {
        profileRepository.save(userProfile);
        return userProfile;
    }
}
