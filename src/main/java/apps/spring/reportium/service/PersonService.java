package apps.spring.reportium.service;

import apps.spring.reportium.entity.*;
import apps.spring.reportium.entity.dto.PersonReportSummaryDTO;

import java.util.List;

public interface PersonService {
    void deletePerson(String userEmail, Long personId);
    List<PersonReportSummaryDTO> personSummaryReportData();
    Person findById(Integer personId);
    List<CriminalReport> getPersonsCr(Integer personId);
    List<AcademicReport> getPersonsAr(Integer personId);
    List<EmploymentReport> getPersonsEr(Integer personId);
    List<MedicalReport> getPersonsMr(Integer personId);
}
