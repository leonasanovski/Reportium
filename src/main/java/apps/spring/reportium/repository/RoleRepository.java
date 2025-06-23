package apps.spring.reportium.repository;

import apps.spring.reportium.entity.Role;
import apps.spring.reportium.entity.enumerations.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
}
