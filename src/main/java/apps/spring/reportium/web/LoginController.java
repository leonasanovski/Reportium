package apps.spring.reportium.web;

import apps.spring.reportium.entity.ReportiumUser;
import apps.spring.reportium.service.AuthenticationService;
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
    public LoginController(AuthenticationService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage() {
        return "login";
    }
    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        ReportiumUser user = null;
        try {
            user = authService.login(email, password);
            request.getSession().setAttribute("user", user);
            return "redirect:/home";
        } catch (RuntimeException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "login";
        }
    }
}

