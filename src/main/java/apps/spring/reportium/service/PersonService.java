package apps.spring.reportium.service;

import apps.spring.reportium.entity.*;
import apps.spring.reportium.entity.DTOs.PersonReportSummaryDTO;

import java.util.List;

public interface PersonService {
    List<PersonReportSummaryDTO> personSummaryReportData();
    Person findById(Integer personId);
    List<CriminalReport> getPersonsCr(Integer personId);
    List<AcademicReport> getPersonsAr(Integer personId);
    List<EmploymentReport> getPersonsEr(Integer personId);
    List<MedicalReport> getPersonsMr(Integer personId);
}
