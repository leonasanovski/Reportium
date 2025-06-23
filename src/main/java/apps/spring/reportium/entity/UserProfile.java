package apps.spring.reportium.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "userprofile")
@Data
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private int profileId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private ReportiumUser reportiumUser;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(nullable = false)
    private String username;

    @Column(name = "profile_created_at", updatable = false)
    private LocalDateTime profileCreatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserProfileLog> logs;
}
