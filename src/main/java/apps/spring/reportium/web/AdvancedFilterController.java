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
    public String showInitialFilterSelection(Model model) {
        ReportFilterDTO defaultFilter = new ReportFilterDTO();
        defaultFilter.setFilter_selected(SelectedFilterSection.PERSON);
        model.addAttribute("filter", defaultFilter);
        model.addAttribute("filter_types", SelectedFilterSection.values());
        return "redirect:/advanced_filter_pt2?filter_selected=PERSON";//default to be selected as person
    }

    @PostMapping("/advanced_filter")
    public String applyAdvancedFilter(@ModelAttribute ReportFilterDTO filter, Model model) {
        System.out.println("Advanced filter applied!");
        System.out.println(filter);
        return "redirect:/reports";
    }
    @GetMapping("/advanced_filter_pt2")
    public String showAdvancedFilterPage(@RequestParam(name = "filter_selected", required = false) String filterSelected, Model model) {
        ReportFilterDTO filterDTO = new ReportFilterDTO();
        SelectedFilterSection filter_type = (filterSelected != null)
                ? SelectedFilterSection.valueOf(filterSelected)
                : SelectedFilterSection.PERSON;
        filterDTO.setFilter_selected(filter_type);
        model.addAttribute("choice", filter_type);
        model.addAttribute("filter", filterDTO);
        model.addAttribute("filter_types", SelectedFilterSection.values());
        model.addAttribute("severities", Arrays.asList(SeverityLevel.values()));
        model.addAttribute("specializations", Arrays.asList(DoctorSpecialization.values()));
        model.addAttribute("comparisons", Arrays.asList(ComparisonDTOEnum.values()));
        model.addAttribute("institutions", Arrays.asList(InstitutionType.values()));
        addSharedEnums(model);

        return "filter_panel";
    }

    @PostMapping("/advanced_filter_pt2")
    public String handleFilter(@ModelAttribute("filter") ReportFilterDTO filter, Model model) {
        SelectedFilterSection section = filter.getFilter_selected();
        if (section == null) filter.setFilter_selected(SelectedFilterSection.PERSON);
        List<Report> filteredReports = reportService.getReportsByAdvancedFilter(filter);
        model.addAttribute("results", filteredReports);
        model.addAttribute("filter", filter);
        model.addAttribute("choice", section);
        addSharedEnums(model);
        filterSessionService.save(filter);
        return "filtered_results";
    }

    private void addSharedEnums(Model model) {
        model.addAttribute("severities", Arrays.asList(SeverityLevel.values()));
        model.addAttribute("specializations", Arrays.asList(DoctorSpecialization.values()));
        model.addAttribute("comparisons", Arrays.asList(ComparisonDTOEnum.values()));
        model.addAttribute("institutions", Arrays.asList(InstitutionType.values()));
    }
}
