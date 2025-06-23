package apps.spring.reportium.repository;

import apps.spring.reportium.entity.MedicalReportDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalReportDiagnosisRepository extends JpaRepository<MedicalReportDiagnosis,Long> {
}
