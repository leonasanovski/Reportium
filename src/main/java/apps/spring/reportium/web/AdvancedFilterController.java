package apps.spring.reportium.web;

import apps.spring.reportium.entity.DTOs.MedicalReportPerPersonDTO;
import apps.spring.reportium.entity.DTOs.ReportFilterDTO;
import apps.spring.reportium.entity.DTOs.view_fetching_dtos.CrimeReportViewFetchingDTO;
import apps.spring.reportium.entity.DTOs.view_fetching_dtos.MedicalReportViewFetchingDTO;
import apps.spring.reportium.entity.Report;
import apps.spring.reportium.entity.enumerations.ComparisonDTOEnum;
import apps.spring.reportium.entity.enumerations.DoctorSpecialization;
import apps.spring.reportium.entity.enumerations.InstitutionType;
import apps.spring.reportium.entity.enumerations.SeverityLevel;
import apps.spring.reportium.repository.ReportViewRepository;
import apps.spring.reportium.service.ReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class AdvancedFilterController {
    private final ReportService reportService;
    public AdvancedFilterController(ReportService reportService) {
        this.reportService = reportService;
    }
    @GetMapping("/advanced_filter")
    public String showAdvancedFilterForm(Model model) {
        model.addAttribute("filter", new ReportFilterDTO());
        model.addAttribute("severities", Arrays.asList(SeverityLevel.values()));
        model.addAttribute("specializations", Arrays.asList(DoctorSpecialization.values()));
        model.addAttribute("comparisons", Arrays.asList(ComparisonDTOEnum.values()));
        model.addAttribute("institutions", Arrays.asList(InstitutionType.values()));
        return "filter_panel";
    }

    @PostMapping("/advanced_filter")
    public String applyAdvancedFilter(@ModelAttribute ReportFilterDTO filter, Model model) {
        System.out.println("Advanced filter applied!");
        System.out.println(filter);
        return "redirect:/reports";
    }
}
