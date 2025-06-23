package apps.spring.reportium.service;

import apps.spring.reportium.entity.UserProfile;

public interface UserProfileService {
    UserProfile findByUserId(Integer userId);
    UserProfile save(UserProfile userProfile);
}
