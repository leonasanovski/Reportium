package apps.spring.reportium.repository;

import apps.spring.reportium.entity.AcademicReport;
import apps.spring.reportium.entity.CriminalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicReportRepository extends JpaRepository<AcademicReport, Integer> {
    @Query("""
    SELECT ar
    FROM AcademicReport ar
    JOIN ar.report r
    JOIN r.person p
    WHERE p.personId = :personId
""")
    List<AcademicReport> findAllByPersonId(@Param("personId") Integer personId);

    @Query("""
    SELECT COUNT(ar) > 0
    FROM AcademicReport ar
    JOIN ar.report r
    WHERE r.person.personId = :personId
""")
    boolean existsByPersonId(@Param("personId") Integer personId);
}
