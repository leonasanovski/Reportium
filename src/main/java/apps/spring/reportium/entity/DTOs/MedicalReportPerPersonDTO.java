package apps.spring.reportium.entity.DTOs;

import apps.spring.reportium.entity.enumerations.DoctorSpecialization;
import apps.spring.reportium.entity.enumerations.SeverityLevel;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicalReportPerPersonDTO {
    private Integer reportId;
    private String summary;
    private LocalDate nextControlDate;
    private String shortDescription;
    private String therapy;
    private SeverityLevel severity;
    private Boolean isChronic;
    private String doctorName;
    private String doctorSurname;
    private DoctorSpecialization specialization;

    public MedicalReportPerPersonDTO(
            Integer reportId,
            String summary,
            java.sql.Date nextControlDate,
            String shortDescription,
            String therapy,
            String severity,
            Boolean isChronic,
            String doctorName,
            String doctorSurname,
            String specialization
    ) {
        this.reportId = reportId;
        this.summary = summary;
        this.nextControlDate = nextControlDate != null ? nextControlDate.toLocalDate() : null;
        this.shortDescription = shortDescription;
        this.therapy = therapy;
        this.severity = SeverityLevel.valueOf(severity.toUpperCase().replace(" ", "_"));
        this.isChronic = isChronic;
        this.doctorName = doctorName;
        this.doctorSurname = doctorSurname;
        this.specialization = DoctorSpecialization.valueOf(specialization.toUpperCase().replace(" ", "_"));
    }
}
