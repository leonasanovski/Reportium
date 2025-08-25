package apps.spring.reportium.web;

import apps.spring.reportium.entity.ReportiumUser;
import apps.spring.reportium.entity.enumerations.LogType;
import apps.spring.reportium.repository.UserProfileRepository;
import apps.spring.reportium.service.AuthenticationService;
import apps.spring.reportium.service.UserLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final AuthenticationService authService;
    private final UserProfileRepository userProfileRepository;
    private final UserLogService userLogService;
    public LoginController(AuthenticationService authService, UserProfileRepository userProfileRepository, UserLogService userLogService) {
        this.authService = authService;
        this.userProfileRepository = userProfileRepository;
        this.userLogService = userLogService;
    }

    @GetMapping
    public String getLoginPage() {
        return "login";
    }
    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println("email: " + email);
        System.out.println("password: " + password);
        ReportiumUser user = null;
        try {
            user = authService.login(email, password);
            userProfileRepository.findByReportiumUser(user).ifPresent(userProfile -> {
                System.out.println("userProfile = " + userProfile);
                userLogService.createLog(userProfile.getProfileId(), LogType.LOGIN);
            });
            request.getSession().setAttribute("user", user);
            return "redirect:/home";
        } catch (RuntimeException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "login";
        }
    }
}

