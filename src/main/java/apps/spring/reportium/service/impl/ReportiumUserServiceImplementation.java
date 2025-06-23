package apps.spring.reportium.service.impl;

import apps.spring.reportium.entity.ReportiumUser;
import apps.spring.reportium.entity.UserProfile;
import apps.spring.reportium.repository.ReportiumUserRepository;
import apps.spring.reportium.repository.RoleRepository;
import apps.spring.reportium.service.ReportiumUserService;
import apps.spring.reportium.service.RoleService;
import apps.spring.reportium.service.UserProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportiumUserServiceImplementation implements ReportiumUserService {
    private final ReportiumUserRepository userRepository;
    private final UserProfileService userProfileService;
    private final RoleService roleService;

    public ReportiumUserServiceImplementation(ReportiumUserRepository userRepository, UserProfileService userProfileService, RoleService roleService) {
        this.userRepository = userRepository;
        this.userProfileService = userProfileService;
        this.roleService = roleService;
    }

    @Override
    public List<ReportiumUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void updateRole(Integer userId, Integer roleId) {
        UserProfile serviceUser = userProfileService.findByUserId(userId);
        serviceUser.setRole(roleService.findById(roleId));
        userProfileService.save(serviceUser);
    }

    @Override
    public ReportiumUser findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(String.format("User with email=%s not found", email)));
    }
}
