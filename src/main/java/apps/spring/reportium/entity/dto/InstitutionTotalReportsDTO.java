package apps.spring.reportium.entity.dto;

import lombok.Data;

@Data
public class InstitutionTotalReportsDTO {
    private String institutionName;
    private Long numberOfReports;

    public InstitutionTotalReportsDTO(){};
    public InstitutionTotalReportsDTO(String institutionName, Long numberOfReports) {
        this.institutionName = institutionName;
        this.numberOfReports = numberOfReports;
    }
}
