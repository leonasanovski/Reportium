package apps.spring.reportium.repository;

import apps.spring.reportium.entity.CrimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrimeTypeRepository extends JpaRepository<CrimeType,Long> {
}
