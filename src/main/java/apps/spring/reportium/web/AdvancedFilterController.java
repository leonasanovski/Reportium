package apps.spring.reportium.web;

import apps.spring.reportium.entity.DTOs.MedicalReportPerPersonDTO;
import apps.spring.reportium.entity.DTOs.ReportFilterDTO;
import apps.spring.reportium.entity.DTOs.view_fetching_dtos.CrimeReportViewFetchingDTO;
import apps.spring.reportium.entity.DTOs.view_fetching_dtos.MedicalReportViewFetchingDTO;
import apps.spring.reportium.entity.FilterSession;
import apps.spring.reportium.entity.Report;
import apps.spring.reportium.entity.enumerations.*;
import apps.spring.reportium.repository.ReportViewRepository;
import apps.spring.reportium.service.FilterSessionService;
import apps.spring.reportium.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class AdvancedFilterController {
    private final ReportService reportService;
    private final FilterSessionService filterSessionService;
    public AdvancedFilterController(ReportService reportService, FilterSessionService filterSessionService) {
        this.reportService = reportService;
        this.filterSessionService = filterSessionService;
    }
    @GetMapping("/advanced_filter")
    public String showAdvancedFilterForm(Model model) {
        model.addAttribute("filter", new ReportFilterDTO());
        model.addAttribute("severities", Arrays.asList(SeverityLevel.values()));
        model.addAttribute("specializations", Arrays.asList(DoctorSpecialization.values()));
        model.addAttribute("comparisons", Arrays.asList(ComparisonDTOEnum.values()));
        model.addAttribute("institutions", Arrays.asList(InstitutionType.values()));
        model.addAttribute("filter_types", SelectedFilterSection.values());
        return "filter_panel";
    }
    @PostMapping("/advanced_filter")
    public String applyAdvancedFilter(@ModelAttribute ReportFilterDTO filter, Model model) {
        System.out.println("Advanced filter applied!");
        System.out.println(filter);
        return "redirect:/reports";
    }
    @PostMapping("/advanced_filter_pt2")
    public String handleFilter(@ModelAttribute("filter") ReportFilterDTO filter, Model model) {
        System.out.println("Selected filter: " + filter.getFilter_selected());
        List<Report> filteredReports = reportService.getReportsByAdvancedFilter(filter);

        System.out.println("Results: " + filteredReports.size());
        for (Report r : filteredReports) {
            System.out.println("Report ID: " + r.getReportId() + ", Summary: " + r.getSummary());
        }

        model.addAttribute("results", filteredReports);
        model.addAttribute("filter", filter);
        filterSessionService.save(filter);
        return "filtered_results";
    }



}
