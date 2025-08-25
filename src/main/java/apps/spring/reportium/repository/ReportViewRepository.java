package apps.spring.reportium.repository;

import apps.spring.reportium.entity.dto.view_fetching_dtos.*;
import apps.spring.reportium.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportViewRepository extends JpaRepository<Report, Long> {
    @Query(value = "SELECT * FROM academic_report_view", nativeQuery = true)
    List<AcademicReportViewFetchingDTO> getAcademicReportViews();

    @Query(value = "SELECT * FROM employment_report_view", nativeQuery = true)
    List<EmploymentReportViewFetchingDTO> getEmploymentReportViews();

    @Query(value = "SELECT * FROM medical_report_view", nativeQuery = true)
    List<MedicalReportViewFetchingDTO> getMedicalReportViews();

    @Query(value = "SELECT * FROM criminal_report_view", nativeQuery = true)
    List<CrimeReportViewFetchingDTO> getCrimeReportViews();
}
