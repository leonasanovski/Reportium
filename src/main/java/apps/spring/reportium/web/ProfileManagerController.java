package apps.spring.reportium.web;

import apps.spring.reportium.entity.ReportiumUser;
import apps.spring.reportium.entity.UserProfile;
import apps.spring.reportium.entity.enumerations.LogType;
import apps.spring.reportium.repository.UserProfileRepository;
import apps.spring.reportium.service.AuthenticationService;
import apps.spring.reportium.service.ReportiumUserService;
import apps.spring.reportium.service.RoleService;
import apps.spring.reportium.service.UserLogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/profiles")
public class ProfileManagerController {
    private final ReportiumUserService userService;
    private final RoleService roleService;
    private final AuthenticationService authenticationService;
    private final UserLogService userLogService;
    private final UserProfileRepository userProfileRepository;

    public ProfileManagerController(ReportiumUserService userService, RoleService roleService, AuthenticationService authenticationService, UserLogService userLogService, UserProfileRepository userProfileRepository) {
        this.userService = userService;
        this.roleService = roleService;
        this.authenticationService = authenticationService;
        this.userLogService = userLogService;
        this.userProfileRepository = userProfileRepository;
    }

    @GetMapping
    public String userManager(Model model) {
        List<ReportiumUser> all_users = userService.findAll();
        model.addAttribute("reportium_users", all_users);
        model.addAttribute("roles", roleService.findAll());
        ReportiumUser currentUser = authenticationService.getCurrentUser();
        model.addAttribute("logged_user", currentUser.getUserId());
        return "user_manager";
    }

    @PostMapping("/update-role")
    public String updateRole(@RequestParam("userId") Integer userId,
                             @RequestParam("roleId") Integer roleId) {
        userService.updateRole(userId, roleId);
        userLogService.createLog(userProfileRepository.findByReportiumUserUserId(userId).getProfileId(),
                LogType.CHANGE_ROLE);
        return "redirect:/profiles";
    }
}
