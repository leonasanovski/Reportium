package apps.spring.reportium.web;

import apps.spring.reportium.entity.Diagnosis;
import apps.spring.reportium.entity.enumerations.SeverityLevel;
import apps.spring.reportium.repository.DiagnosisRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/diagnosis")
public class DiagnosisController {
    private final DiagnosisRepository diagnosisRepository;

    public DiagnosisController(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    @GetMapping("/create")
    public String createDiagnosis(@RequestParam Long personId,
                                  Model model) {
        model.addAttribute("personId", personId);
        model.addAttribute("severityLevels", SeverityLevel.values());
        model.addAttribute("diagnosis", diagnosisRepository.findAll());
        return "create_diagnosis";
    }

    @PostMapping
    public String addDiagnosis(@RequestParam Long personId,
                               @RequestParam String description,
                               @RequestParam String therapy,
                               @RequestParam Boolean isChronic,
                               @RequestParam SeverityLevel severityLevel) {
        Diagnosis diagnosis = new Diagnosis(description, therapy, isChronic, severityLevel);
        diagnosisRepository.save(diagnosis);
        return "redirect:/reports/add/medical?personId=" + personId;
    }
}
