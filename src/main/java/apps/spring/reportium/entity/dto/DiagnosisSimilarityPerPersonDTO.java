package apps.spring.reportium.entity.dto;

import lombok.Data;

@Data
public class DiagnosisSimilarityPerPersonDTO {
    private Long personId;
    private String fullName;
    private Long matchingDiagnosesCount;
    private String matchingLabels;

    public DiagnosisSimilarityPerPersonDTO() {
    }

    public DiagnosisSimilarityPerPersonDTO(Long personId, String fullName,
                                           Long matchingDiagnosesCount, String matchingLabels) {
        this.personId = personId;
        this.fullName = fullName;
        this.matchingDiagnosesCount = matchingDiagnosesCount;
        this.matchingLabels = matchingLabels;
    }
}
