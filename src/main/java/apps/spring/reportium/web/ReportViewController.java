package apps.spring.reportium.web;

import apps.spring.reportium.entity.DTOs.view_fetching_dtos.AcademicReportViewFetchingDTO;
import apps.spring.reportium.entity.DTOs.view_fetching_dtos.CrimeReportViewFetchingDTO;
import apps.spring.reportium.entity.DTOs.view_fetching_dtos.EmploymentReportViewFetchingDTO;
import apps.spring.reportium.entity.DTOs.view_fetching_dtos.MedicalReportViewFetchingDTO;
import apps.spring.reportium.repository.ReportViewRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Controller
public class ReportViewController {
    private final ReportViewRepository reportViewRepository;
    private final ObjectMapper objectMapper;

    public ReportViewController(ReportViewRepository reportViewRepository, ObjectMapper objectMapper) {
        this.reportViewRepository = reportViewRepository;
        this.objectMapper = objectMapper;
    }

    /*
     * Ova ustvari mapira sekoj mozen red shto go ima od string, vo objekti od toj string
     * primer
     * Sekoj dto ima svoi atributi i tie se vo redovi staveni vo edna lista od objekti
     * 500, ass, 2002 ... site mozni atributi
     * Epa, ova prai ustvari sekoja vakva vrednost ja mapira vo objekt odnosno, prvata deka e povrzana so reportID - > getReportId.value i taka za site
     * Zatoa vrakja lista od mapi, kaj sto sekoj string e mapiran vo objekt od poveke atributi, a toj
     * */
    public List<Map<String, Object>> convertDtosToMapsOfObjects(List<?> dto_objects) {
        return dto_objects
                .stream()
                .map(dto -> objectMapper.convertValue(dto, new TypeReference<Map<String, Object>>() {}))
                .collect(Collectors.toList());
    }

    public Function<String, String> capitalize_first_letter = str -> str.substring(0, 1).toUpperCase() + str.substring(1);

    @GetMapping("/view_reports")
    public String showSelectedReportView(@RequestParam("reportView") String reportView, Model model) {
        switch (reportView.toUpperCase()) {
            case "EMPLOYMENT" -> {
                List<EmploymentReportViewFetchingDTO> reports = reportViewRepository.getEmploymentReportViews();
                List<Map<String, Object>> data = convertDtosToMapsOfObjects(reports);
                List<String> columns = data.isEmpty() ? List.of() : new ArrayList<>(data.getFirst()
                        .keySet()
                        .stream()
                        .map(capitalize_first_letter)
                        .collect(Collectors.toList()));
                model.addAttribute("reportType", "Employment");
                model.addAttribute("columns", columns);
                model.addAttribute("data", data);
            }
            case "CRIMINAL" -> {
                List<CrimeReportViewFetchingDTO> reports = reportViewRepository.getCrimeReportViews();
                List<Map<String, Object>> data = convertDtosToMapsOfObjects(reports);
                List<String> columns = data.isEmpty() ? List.of() : new ArrayList<>(data.getFirst()
                        .keySet()
                        .stream()
                        .map(capitalize_first_letter)
                        .collect(Collectors.toList()));
                model.addAttribute("reportType", "Criminal");
                model.addAttribute("columns", columns);
                model.addAttribute("data", data);
            }
            case "ACADEMIC" -> {
                List<AcademicReportViewFetchingDTO> reports = reportViewRepository.getAcademicReportViews();
                List<Map<String, Object>> data = convertDtosToMapsOfObjects(reports);
                List<String> columns = data.isEmpty() ? List.of() : new ArrayList<>(data.getFirst()
                        .keySet()
                        .stream()
                        .map(capitalize_first_letter)
                        .collect(Collectors.toList()));
                model.addAttribute("reportType", "Academic");
                model.addAttribute("columns", columns);
                model.addAttribute("data", data);
            }
            case "MEDICAL" -> {
                List<MedicalReportViewFetchingDTO> reports = reportViewRepository.getMedicalReportViews();
                List<Map<String, Object>> data = convertDtosToMapsOfObjects(reports);
                List<String> columns = data.isEmpty() ? List.of() : new ArrayList<>(data.getFirst()
                        .keySet()
                        .stream()
                        .map(capitalize_first_letter)
                        .collect(Collectors.toList()));
                model.addAttribute("reportType", "Medical");
                model.addAttribute("columns", columns);
                model.addAttribute("data", data);
            }
            default -> {
                return "redirect:/reports";
            }
        }
        return "different_report_views";
    }

    private String formatCsvValue(Object value) {
        if (value == null) return "";
        String s = value.toString();
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            s = s.replace("\"", "\"\"");
            return "\"" + s + "\"";
        }
        return s;
    }

    @GetMapping("/download_as_csv")
    public void downloadReportAsCsv(@RequestParam("reportView") String reportView, HttpServletResponse response) throws Exception {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=" + reportView.toLowerCase() + "_report.csv");
        List<?> reports;
        switch (reportView.toUpperCase()) {
            case "EMPLOYMENT" -> reports = reportViewRepository.getEmploymentReportViews();
            case "CRIMINAL" -> reports = reportViewRepository.getCrimeReportViews();
            case "ACADEMIC" -> reports = reportViewRepository.getAcademicReportViews();
            case "MEDICAL" -> reports = reportViewRepository.getMedicalReportViews();
            default -> {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid report view type.");
                return;
            }
        }
        List<Map<String, Object>> data = convertDtosToMapsOfObjects(reports);
        if (data.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NO_CONTENT, "No data to export.");
            return;
        }
        PrintWriter writer = response.getWriter();
        List<String> headers = new ArrayList<>(data.getFirst().keySet());
        writer.println(String.join(",", headers));
        for (Map<String, Object> row : data) {
            List<String> cells = headers.stream()
                    .map(h -> formatCsvValue(row.get(h)))
                    .collect(Collectors.toList());
            writer.println(String.join(",", cells));
        }
        writer.flush();
        writer.close();
    }

    public List<?> getReports(String reportView) {
        List<?> reports;
        switch (reportView.toUpperCase()) {
            case "EMPLOYMENT" -> reports = reportViewRepository.getEmploymentReportViews();
            case "CRIMINAL" -> reports = reportViewRepository.getCrimeReportViews();
            case "ACADEMIC" -> reports = reportViewRepository.getAcademicReportViews();
            case "MEDICAL" -> reports = reportViewRepository.getMedicalReportViews();
            default -> reports = List.of();
        }
        return reports;
    }

    @GetMapping("/download_as_pdf")
    public void downloadReportAsPdf(@RequestParam("reportView") String reportView, HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + reportView.toLowerCase() + "_report.pdf");
        List<?> reports = getReports(reportView);
        List<Map<String, Object>> data = convertDtosToMapsOfObjects(reports);
        Document document = new Document(PageSize.A4.rotate()); // to be landscape
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.add(new Paragraph(reportView.toUpperCase() + " Report"));
        document.add(new Paragraph(" "));

        List<String> headers = new ArrayList<>(data.getFirst().keySet());
        PdfPTable table = new PdfPTable(headers.size());
        table.setWidthPercentage(100);

        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            table.addCell(cell);
        }

        for (Map<String, Object> row : data) {
            for (String header : headers) {
                Object value = row.get(header);
                table.addCell(value != null ? value.toString() : "");
            }
        }

        document.add(table);
        document.close();
    }
}
