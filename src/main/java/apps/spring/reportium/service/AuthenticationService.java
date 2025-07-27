package apps.spring.reportium.service;

import apps.spring.reportium.entity.ReportiumUser;

public interface AuthenticationService {
    void register_user(String name, String surname, String email, String password, String password_confirmation);
    ReportiumUser login(String email, String password);
    ReportiumUser getCurrentUser();
}
