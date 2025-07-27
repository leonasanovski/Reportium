package apps.spring.reportium.web;
import apps.spring.reportium.entity.Person;
import apps.spring.reportium.entity.Report;
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
    public ReportsController(ReportService reportService, PersonService personService) {
        this.reportService = reportService;
        this.personService = personService;
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
}
