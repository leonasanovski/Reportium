package apps.spring.reportium.entity.dto;

import apps.spring.reportium.entity.enumerations.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonReportSummaryDTO {
    private Integer personId;
    private String name;
    private String surname;
    private String address;
    private LocalDate birthDate;
    private Gender gender;
    private Boolean isAlive;
    private Integer age;
    private boolean hasCriminalReport;
    private boolean hasMedicalReport;
    private boolean hasAcademicReport;
    private boolean hasEmploymentReport;
}
