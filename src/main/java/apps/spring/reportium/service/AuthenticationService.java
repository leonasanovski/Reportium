package apps.spring.reportium.service;

import apps.spring.reportium.entity.ReportiumUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*

CREATE TABLE ReportiumUser (
    user_id SERIAL PRIMARY KEY,
    name varchar(30) NOT NULL,
    surname varchar(30) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash TEXT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
 */
public interface AuthenticationService {
    void register_user(String name, String surname, String email, String password, String password_confirmation);
    ReportiumUser login(String email, String password);
    ReportiumUser getCurrentUser();
}
