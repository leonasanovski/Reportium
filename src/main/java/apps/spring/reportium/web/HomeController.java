package apps.spring.reportium.web;

import apps.spring.reportium.entity.*;
import apps.spring.reportium.entity.dto.*;
import apps.spring.reportium.entity.exceptions.PersonNotFoundException;
import apps.spring.reportium.repository.InstitutionRepository;
import apps.spring.reportium.repository.ReportRepository;
import apps.spring.reportium.service.PersonService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final PersonService personService;
    private final ReportRepository reportRepository;
    private final InstitutionRepository institutionRepository;

    public HomeController(PersonService personService, ReportRepository reportRepository, InstitutionRepository institutionRepository) {
        this.personService = personService;
        this.reportRepository = reportRepository;
        this.institutionRepository = institutionRepository;
    }

    @GetMapping
    public String showHomePage(Model model) {
        List<PersonReportSummaryDTO> report_summary = personService.personSummaryReportData();
        model.addAttribute("report_summary_list", report_summary);
        return "home";
    }

    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        personService.deletePerson(email, id);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String viewPersonReports(@PathVariable("id") Long personId, Model model) {
        Person person = personService.findById(Math.toIntExact(personId));
        if (person == null) {
            throw new PersonNotFoundException("Person with id " + personId + " not found.");
        }
        List<CrimeReportPerPersonDTO> person_cr = reportRepository.getCriminalReportsByPersonId(personId);
        List<MedicalReportPerPersonDTO> person_mr = reportRepository.getMedicalReportsByPersonId(personId);
        List<AcademicReportPerPersonDTO> person_ar = reportRepository.getAcademicReportsByPersonId(personId);
        List<EmploymentReportPerPersonDTO> person_er = reportRepository.getEmploymentReportsByPersonId(personId);
        ReportStatisticsPerPersonDTO statistics_per_person = reportRepository.getStatisticsForPerson(personId);
        List<DiagnosisSimilarityPerPersonDTO> diagnosis_similarity = reportRepository.getSimilarDiagnosesForPerson(personId);
        List<InstitutionTotalReportsDTO> top3_institutions = institutionRepository.findTop3Institutions();
        model.addAttribute("medical_reports", person_mr);
        model.addAttribute("criminal_reports", person_cr);
        model.addAttribute("academic_reports", person_ar);
        model.addAttribute("employment_reports", person_er);
        model.addAttribute("statistics", statistics_per_person);
        model.addAttribute("diagnosis_similarities", diagnosis_similarity);
        model.addAttribute("person", person);
        model.addAttribute("institutions",top3_institutions);
        return "person_reports";
    }
}
