package apps.spring.reportium.entity;

import apps.spring.reportium.entity.enumerations.RoleName;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    private String description;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<UserProfile> userProfiles;
}
