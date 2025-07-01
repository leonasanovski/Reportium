package apps.spring.reportium.web;
import apps.spring.reportium.entity.Report;
import apps.spring.reportium.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportsController {
    private final ReportService reportService;

    public ReportsController(ReportService reportService) {
        this.reportService = reportService;
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

}
