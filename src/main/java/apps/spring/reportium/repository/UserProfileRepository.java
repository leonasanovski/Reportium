package apps.spring.reportium.repository;

import apps.spring.reportium.entity.ReportiumUser;
import apps.spring.reportium.entity.UserProfile;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    Optional<UserProfile> findByReportiumUser(ReportiumUser reportiumUser);

    UserProfile findByReportiumUserUserId(int reportiumUserUserId);
}
