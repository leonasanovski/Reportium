package apps.spring.reportium.entity.dto.view_fetching_dtos;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmploymentReportViewFetchingDTO {
    private Integer reportId;
    private String summary;
    private LocalDate reportCreatedAt;
    private String embgOfPerson;
    private String personFullname;
    private String gender;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private String addressOfLiving;
    private String contactPhone;
    private String typeOfReport;
    private LocalDate startedWorkingDate;
    private LocalDate endedWorkingDate;
    private String jobRole;
    private Double incomePerMonthInEuros;

    public EmploymentReportViewFetchingDTO(Integer reportId, String summary, java.sql.Date reportCreatedAt, String embgOfPerson, String personFullname,
                                           String gender, java.sql.Date dateOfBirth, java.sql.Date dateOfDeath, String addressOfLiving, String contactPhone,
                                           String typeOfReport, java.sql.Date startedWorkingDate, java.sql.Date endedWorkingDate, String jobRole,
                                           Double incomePerMonthInEuros) {
        this.reportId = reportId;
        this.summary = summary;
        this.reportCreatedAt = reportCreatedAt != null ? reportCreatedAt.toLocalDate() : null;
        this.embgOfPerson = embgOfPerson;
        this.personFullname = personFullname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth != null ? dateOfBirth.toLocalDate() : null;
        this.dateOfDeath = dateOfDeath != null ? dateOfDeath.toLocalDate() : null;
        this.addressOfLiving = addressOfLiving;
        this.contactPhone = contactPhone;
        this.typeOfReport = typeOfReport;
        this.startedWorkingDate = startedWorkingDate != null ? startedWorkingDate.toLocalDate() : null;
        this.endedWorkingDate = endedWorkingDate != null ? endedWorkingDate.toLocalDate() : null;
        this.jobRole = jobRole;
        this.incomePerMonthInEuros = incomePerMonthInEuros;
    }
}
