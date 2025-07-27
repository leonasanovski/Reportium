package apps.spring.reportium.service;

import apps.spring.reportium.entity.DTOs.*;
import apps.spring.reportium.entity.EmploymentReport;
import apps.spring.reportium.entity.Report;
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
//    Page<Report> findPage(Integer reportId, Integer pageNum, Integer pageSize);
    Page<Report> findPaginatedReports(int page, int size, String sortField, String sortDir);
    List<Report> getReportsByAdvancedFilter(ReportFilterDTO filter);

    void saveNewEmploymentReport(Long personId, LocalDate startDate, LocalDate endDate,String jobRole, BigDecimal income, String summary);
}
