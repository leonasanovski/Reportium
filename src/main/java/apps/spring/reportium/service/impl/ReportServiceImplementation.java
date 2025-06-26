package apps.spring.reportium.service.impl;

import apps.spring.reportium.entity.DTOs.*;
import apps.spring.reportium.entity.Report;
import apps.spring.reportium.repository.ReportRepository;
import apps.spring.reportium.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImplementation implements ReportService {
    private final ReportRepository reportRepository;

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
        int safePage = Math.max(0, page - 1);

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(safePage, size, sort);
        return reportRepository.findAll(pageable);
    }

    @Override
    public List<Report> getReportsByTypeViews(List<String> reportTypes) {
        return List.of();
    }

    @Override
    public List<Report> getReportsByAdvancedFilter(ReportFilterDTO filter) {
        return List.of();
    }
}
