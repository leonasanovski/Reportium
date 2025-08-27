package apps.spring.reportium.repository;

import apps.spring.reportium.entity.Institution;
import apps.spring.reportium.entity.dto.InstitutionTotalReportsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    @Query(value = """
                WITH top_3_institutions AS (
                  SELECT ar.institution_id, COUNT(*) AS total_reports
                  FROM report r
                  JOIN academicreport ar ON ar.report_id = r.report_id
                  WHERE r.created_at >= date_trunc('year', now()) - interval '1 year'
                  GROUP BY ar.institution_id
                  ORDER BY COUNT(*) DESC
                  LIMIT 3
                )
                SELECT i.name, a.total_reports
                FROM top_3_institutions a
                JOIN institution i ON i.institution_id = a.institution_id
                ORDER BY a.total_reports DESC;
            """, nativeQuery = true)
    List<InstitutionTotalReportsDTO> findTop3Institutions();
}
