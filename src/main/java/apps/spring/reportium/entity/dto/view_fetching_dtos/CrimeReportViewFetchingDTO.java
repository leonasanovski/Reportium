package apps.spring.reportium.entity.dto.view_fetching_dtos;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CrimeReportViewFetchingDTO {
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
    private String typeOfCriminal;
    private String whereCriminalIsReported;
    private Boolean isResolved;
    private String descriptivePunishment;
    private String severityLevel;
    private String punishment;

    public CrimeReportViewFetchingDTO(Integer reportId, String summary, java.sql.Date reportCreatedAt, String embgOfPerson,
                                      String personFullname, String gender, java.sql.Date dateOfBirth, java.sql.Date dateOfDeath,
                                      String addressOfLiving, String contactPhone, String typeOfReport, String typeOfCriminal,
                                      String whereCriminalIsReported, Boolean isResolved, String descriptivePunishment,
                                      String severityLevel, String punishment) {
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
        this.typeOfCriminal = typeOfCriminal;
        this.whereCriminalIsReported = whereCriminalIsReported;
        this.isResolved = isResolved;
        this.descriptivePunishment = descriptivePunishment;
        this.severityLevel = severityLevel;
        this.punishment = punishment;
    }
}
