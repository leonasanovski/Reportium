package apps.spring.reportium.service.impl;

import apps.spring.reportium.entity.dto.*;
import apps.spring.reportium.entity.Report;
import apps.spring.reportium.entity.enumerations.PunishmentType;
import apps.spring.reportium.repository.ReportRepository;
import apps.spring.reportium.service.ReportService;
import apps.spring.reportium.specifications.ReportFilterSpecificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service

public class ReportServiceImplementation implements ReportService {
    private final ReportRepository reportRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ReportServiceImplementation(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public List<AcademicReportPerPersonDTO> getAcademicReports(Long personId) {
        return reportRepository.getAcademicReportsByPersonId(personId);
    }

    @Override
    public List<MedicalReportPerPersonDTO> getMedicalReports(Long personId) {
        return reportRepository.getMedicalReportsByPersonId(personId);
    }

    @Override
    public List<EmploymentReportPerPersonDTO> getEmploymentReports(Long personId) {
        return reportRepository.getEmploymentReportsByPersonId(personId);
    }

    @Override
    public List<CrimeReportPerPersonDTO> getCriminalReports(Long personId) {
        return reportRepository.getCriminalReportsByPersonId(personId);
    }

    @Override
    public Page<Report> findPaginatedReports(int page, int size, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending().and(Sort.by("reportId").ascending())
                : Sort.by(sortField).descending().and(Sort.by("reportId").descending());

        Pageable pageable = PageRequest.of(page, size, sort);
        return reportRepository.findAll(pageable);

    }

    @Override
    public List<Report> getReportsByAdvancedFilter(ReportFilterDTO filter) {
        Specification<Report> spec = ReportFilterSpecificationBuilder.build(filter);
        return reportRepository.findAll(spec); // from JpaSpecificationExecutor
    }

    @Override
    public void saveNewEmploymentReport(Long personId,
                                        LocalDate startDate,
                                        LocalDate endDate,
                                        String jobRole,
                                        BigDecimal income,
                                        String summary) {
        jdbcTemplate.update(
                "CALL insert_employment_report(?::integer, ?::date, ?::date, ?::text, ?::numeric, ?::text)",
                personId,
                startDate,
                endDate,
                jobRole,
                income,
                summary
        );


    }

    @Override
    public void saveNewAcademicReport(Long personId,
                                      Long institution_id,
                                      String academicField,
                                      String descriptionOfReport) {
        jdbcTemplate.update(
                "CALL insert_academic_report(?::INT, ?::INT, ?::TEXT, ?::TEXT)",
                personId,
                institution_id, // assuming Institution has getInstitutionId()
                academicField,
                descriptionOfReport
        );
    }

    @Override
    public void saveNewCriminalReport(Long personId, String caseSummary, String location, Boolean isResolved, Long crimeTypeId, PunishmentType punishmentType, Double fineToPay, LocalDate releaseDate) {
        jdbcTemplate.update(
                "CALL insert_criminal_report(?::INT, ?::TEXT, ?::TEXT, ?::BOOLEAN, ?::INT, ?::TEXT, ?::NUMERIC, ?::DATE)",
                personId,
                caseSummary,
                location,
                isResolved,
                crimeTypeId,
                punishmentType.name(),
                fineToPay,
                releaseDate
        );
    }

    @Override
    public void saveNewMedicalReport(Long personId, String summary, Long doctorId, LocalDate nextControlDate, List<Long> diagnosisIds) {
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            Array diagnosisArray = conn.createArrayOf("INTEGER",
                    diagnosisIds != null ? diagnosisIds.toArray(new Long[0]) : new Long[0]);

            jdbcTemplate.update(
                    "CALL insert_medical_report(?::INT, ?::TEXT, ?::INT, ?::DATE, ?::INT[])",
                    personId,
                    summary,
                    doctorId,
                    nextControlDate,
                    diagnosisArray
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error executing insert_medical_report", e);
        }
    }


}
