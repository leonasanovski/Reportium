package apps.spring.reportium.entity.dto;

import apps.spring.reportium.entity.enumerations.SeverityLevel;
import lombok.Data;

@Data
public class CrimeReportPerPersonDTO {
    private Integer reportId;
    private String label;
    private SeverityLevel severityLevel;
    private String location;
    private Boolean resolved;
    private String descriptivePunishment;

    public CrimeReportPerPersonDTO(Integer reportId, String label, String severityLevel,
                             String location, Boolean resolved, String descriptivePunishment) {
        this.reportId = reportId;
        this.label = label;
        this.severityLevel = SeverityLevel.valueOf(severityLevel);
        this.location = location;
        this.resolved = resolved;
        this.descriptivePunishment = descriptivePunishment;
    }
}
