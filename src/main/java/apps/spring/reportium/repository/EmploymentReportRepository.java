package apps.spring.reportium.repository;

import apps.spring.reportium.entity.EmploymentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmploymentReportRepository extends JpaRepository<EmploymentReport,Integer> {
    @Query("""
    SELECT er
    FROM EmploymentReport er
    JOIN er.report r
    JOIN r.person p
    WHERE p.personId = :personId
""")
    List<EmploymentReport> findAllByPersonId(@Param("personId") Integer personId);

    @Query("""
    SELECT COUNT(er) > 0
    FROM EmploymentReport er
    JOIN er.report r
    WHERE r.person.personId = :personId
""")
    boolean existsByPersonId(@Param("personId") Integer personId);
}
