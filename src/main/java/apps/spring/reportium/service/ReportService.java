package apps.spring.reportium.service;

import apps.spring.reportium.entity.DTOs.*;
import apps.spring.reportium.entity.EmploymentReport;
import apps.spring.reportium.entity.Institution;
import apps.spring.reportium.entity.Report;
import apps.spring.reportium.entity.enumerations.PunishmentType;
import apps.spring.reportium.entity.enumerations.ValueUnit;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    List<Report> findAll();

    List<AcademicReportPerPersonDTO> getAcademicReports(Long personId);

    List<MedicalReportPerPersonDTO> getMedicalReports(Long personId);

    List<EmploymentReportPerPersonDTO> getEmploymentReports(Long personId);

    List<CrimeReportPerPersonDTO> getCriminalReports(Long personId);

    Page<Report> findPaginatedReports(int page, int size, String sortField, String sortDir);

    List<Report> getReportsByAdvancedFilter(ReportFilterDTO filter);

    void saveNewEmploymentReport(Long personId, LocalDate startDate, LocalDate endDate, String jobRole, BigDecimal income, String summary);

    void saveNewAcademicReport(Long personId, Long institution_id, String academicField, String descriptionOfReport);

    void saveNewCriminalReport(Long personId, String caseSummary, String location, Boolean isResolved, Long crimeTypeId, PunishmentType punishmentType, Double fineToPay, LocalDate releaseDate);

    void saveNewMedicalReport(Long personId, String summary, Long doctorId, LocalDate nextControlDate, List<Long> diagnosisIds);
}
