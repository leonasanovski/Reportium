package apps.spring.reportium.repository;

import apps.spring.reportium.entity.FilterSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterSessionRepository extends JpaRepository<FilterSession,Long> {
}
