package apps.spring.reportium.security;

import apps.spring.reportium.entity.ReportiumUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
/*
Converts your own ReportiumUser class into a format that Spring Security can use to log in,
authorize, and enforce access restrictions like @PreAuthorize("hasRole('ADMIN')").
It actually allows authorization and authentication with custom way.
*/
public class CustomUserDetails implements UserDetails {

    private final ReportiumUser user;

    public CustomUserDetails(ReportiumUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = user.getProfile().getRole().getRoleName().name();
        return List.of(new SimpleGrantedAuthority("ROLE_" + roleName));
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return user.isActive(); }

    public ReportiumUser getUser() {
        return user;
    }
}
