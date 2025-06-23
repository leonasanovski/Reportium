package apps.spring.reportium.repository;

import apps.spring.reportium.entity.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalReportRepository extends JpaRepository<MedicalReport,Integer> {
    @Query("""
    SELECT mr
    FROM MedicalReport mr
    JOIN mr.report r
    JOIN r.person p
    WHERE p.personId = :personId
""")
    public List<MedicalReport> findAllByPersonId(@Param("personId") Integer personId);

    @Query("""
    SELECT COUNT(mr) > 0
    FROM MedicalReport mr
    JOIN mr.report r
    WHERE r.person.personId = :personId
""")
    public boolean existsByPersonId(@Param("personId") Integer personId);
}
