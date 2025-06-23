package apps.spring.reportium.repository;

import apps.spring.reportium.entity.CriminalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CriminalReportRepository extends JpaRepository<CriminalReport, Integer> {
    @Query("""
    SELECT cr
    FROM CriminalReport cr
    JOIN cr.report r
    JOIN r.person p
    WHERE p.personId = :personId
""")
    List<CriminalReport> findAllByPersonId(@Param("personId") Integer personId);

    @Query("""
    SELECT COUNT(cr) > 0
    FROM CriminalReport cr
    JOIN cr.report r
    WHERE r.person.personId = :personId
""")
    boolean existsByPersonId(@Param("personId") Integer personId);
}
