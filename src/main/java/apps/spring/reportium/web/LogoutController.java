package apps.spring.reportium.web;

import apps.spring.reportium.entity.enumerations.LogType;
import apps.spring.reportium.repository.ReportiumUserRepository;
import apps.spring.reportium.repository.UserProfileRepository;
import apps.spring.reportium.service.UserLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    private final ReportiumUserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserLogService userLogService;

    public LogoutController(ReportiumUserRepository userRepository, UserProfileRepository userProfileRepository, UserLogService userLogService) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.userLogService = userLogService;
    }

    @GetMapping
    public String logout(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getPrincipal())) {
            System.out.println(auth.getName());
            userRepository.findByEmail(auth.getName())
                    .flatMap(userProfileRepository::findByReportiumUser)
                    .ifPresent(profile -> {
                        Integer userId = profile.getReportiumUser().getUserId();
                        userLogService.createLog(userId, LogType.LOGOUT);
                    });
        }

        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }
}

