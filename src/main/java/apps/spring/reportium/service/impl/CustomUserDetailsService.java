package apps.spring.reportium.service.impl;

import apps.spring.reportium.entity.ReportiumUser;
import apps.spring.reportium.repository.ReportiumUserRepository;
import apps.spring.reportium.security.CustomUserDetails;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ReportiumUserRepository userRepository;

    public CustomUserDetailsService(ReportiumUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ReportiumUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(user);
    }

}
