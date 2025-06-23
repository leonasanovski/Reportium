package apps.spring.reportium.repository;

import apps.spring.reportium.entity.ReportiumUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportiumUserRepository extends JpaRepository<ReportiumUser, Integer> {
    Optional<ReportiumUser> findByEmail(String email);
}
