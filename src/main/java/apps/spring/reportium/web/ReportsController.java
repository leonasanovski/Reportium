package apps.spring.reportium.web;
import apps.spring.reportium.entity.CrimeType;
import apps.spring.reportium.entity.Institution;
import apps.spring.reportium.entity.Person;
import apps.spring.reportium.entity.Report;
import apps.spring.reportium.entity.enumerations.PunishmentType;
import apps.spring.reportium.entity.enumerations.ValueUnit;
import apps.spring.reportium.repository.CrimeTypeRepository;
import apps.spring.reportium.repository.InstitutionRepository;
import apps.spring.reportium.service.PersonService;
import apps.spring.reportium.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportsController {
    private final ReportService reportService;
    private final PersonService personService;
    private final InstitutionRepository institutionRepository;
    private final CrimeTypeRepository crimeTypeRepository;
    public ReportsController(ReportService reportService, PersonService personService, InstitutionRepository institutionRepository, CrimeTypeRepository crimeTypeRepository) {
        this.reportService = reportService;
        this.personService = personService;
        this.institutionRepository = institutionRepository;
        this.crimeTypeRepository = crimeTypeRepository;
    }
    @GetMapping
    public String listReports(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "35") int size,
                              @RequestParam(defaultValue = "reportId") String sortField,
                              @RequestParam(defaultValue = "asc") String sortDir) {
        Page<Report> reportPage = reportService.findPaginatedReports(page, size, sortField, sortDir);
        model.addAttribute("reports", reportPage.getContent());
        model.addAttribute("reportsPage", reportPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reportPage.getTotalPages());
        model.addAttribute("totalItems", reportPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "reports";
    }

    @GetMapping("/add/employment")
    public String createEmploymentReport(@RequestParam Long personId, Model model) {
        Person person = personService.findById(personId.intValue());
        model.addAttribute("person", person);
        System.out.println(personId);
        return "new_employment_report";
    }
    @PostMapping("/add/employment")
    public String submitEmploymentData(@RequestParam Long personId,
                                       @RequestParam LocalDate startDate,
                                       @RequestParam (required = false) LocalDate endDate,
                                       @RequestParam String jobRole,
                                       @RequestParam BigDecimal income,
                                       @RequestParam String summary) {
        System.out.printf(
                "EMPLOYMENT FORM DATA%nPerson ID: %d%nStart Date: %s%nEnd Date: %s%nJob Role: %s%nIncome: %s%nSummary: %s%n%n",
                personId,
                startDate,
                endDate != null ? endDate.toString() : "null",
                jobRole,
                income.toString(),
                summary
        );
        reportService.saveNewEmploymentReport(personId, startDate, endDate, jobRole, income, summary);
        return "redirect:/" + personId;
    }


    @GetMapping("/add/academic")
    public String createAcademicReport(@RequestParam Long personId, Model model) {
        Person person = personService.findById(personId.intValue());
        model.addAttribute("person", person);
        model.addAttribute("institutions", institutionRepository.findAll());
        System.out.println(personId);
        return "new_academic_report";
    }
    @PostMapping("/add/academic")
    public String submitAcademicData(@RequestParam Long personId,
                                     @RequestParam Long institutionId,
                                     @RequestParam String academicField,
                                     @RequestParam String descriptionOfReport) {
        reportService.saveNewAcademicReport(personId, institutionId, academicField, descriptionOfReport);
        return "redirect:/" + personId;
    }


    @GetMapping("/add/criminal")
    public String createCriminalReport(@RequestParam Long personId, Model model) {
        Person person = personService.findById(personId.intValue());
        model.addAttribute("person", person);
        model.addAttribute("punishmentTypes", PunishmentType.values());
        model.addAttribute("crimeTypes", crimeTypeRepository.findAll());
        return "new_criminal_report";
    }

    @PostMapping("/add/criminal")
    public String submitCriminalData(@RequestParam Long personId,
                                     @RequestParam String caseSummary,
                                     @RequestParam String location,
                                     @RequestParam Boolean isResolved,
                                     @RequestParam Long crimeTypeId,
                                     @RequestParam PunishmentType punishmentType,
                                     @RequestParam (required = false) Double fineToPay,
                                     @RequestParam (required = false) LocalDate releaseDate) {
        reportService.saveNewCriminalReport(personId, caseSummary, location, isResolved, crimeTypeId, punishmentType, fineToPay, releaseDate);
        return "redirect:/" + personId;
    }
   //TODO("Same as Employment, but for Medical, Academic and Criminal Report to be added.")
}
