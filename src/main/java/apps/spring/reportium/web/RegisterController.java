package apps.spring.reportium.web;

import apps.spring.reportium.service.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
    private final AuthenticationService authenticationService;

    public RegisterController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String surname,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam("password_confirmation") String confirm,
                               Model model) {
        try {
            authenticationService.register_user(name, surname, email, password, confirm);
            return "redirect:/login";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("hasError", true);
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
    }

}
