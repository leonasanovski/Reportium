package apps.spring.reportium.entity.DTOs;

import apps.spring.reportium.entity.enumerations.InstitutionType;
import lombok.Data;

@Data
public class AcademicReportPerPersonDTO {
    private Integer reportId;
    private String academicField;
    private String descriptionOfReport;
    private String institutionName;
    private InstitutionType institutionType;
    private String institutionAddress;
    private Integer yearEstablished;

    public AcademicReportPerPersonDTO(Integer reportId, String academicField, String descriptionOfReport,
                             String institutionName, String institutionType, String institutionAddress, Integer yearEstablished) {
        this.reportId = reportId;
        this.academicField = academicField;
        this.descriptionOfReport = descriptionOfReport;
        this.institutionName = institutionName;
        this.institutionType = InstitutionType.valueOf(institutionType.toUpperCase().replace(" ", "_"));
        this.institutionAddress = institutionAddress;
        this.yearEstablished = yearEstablished;
    }
}
