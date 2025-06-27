package apps.spring.reportium.entity.DTOs.view_fetching_dtos;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AcademicReportViewFetchingDTO {
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
    private String academicField;
    private String academicReportDescription;
    private String institutionName;
    private String institutionAddress;
    private Integer institutionYearOfEstablishing;
    private String cityWhereEducating;
    private String typeOfEducation;

    public AcademicReportViewFetchingDTO(Integer reportId, String summary,
                                         java.sql.Date reportCreatedAt, String embgOfPerson, String personFullname,
                                         String gender, java.sql.Date dateOfBirth, java.sql.Date dateOfDeath, String addressOfLiving,
                                         String contactPhone, String typeOfReport, String academicField, String academicReportDescription,
                                         String institutionName, String institutionAddress, Integer institutionYearOfEstablishing,
                                         String cityWhereEducating, String typeOfEducation) {
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
        this.academicField = academicField;
        this.academicReportDescription = academicReportDescription;
        this.institutionName = institutionName;
        this.institutionAddress = institutionAddress;
        this.institutionYearOfEstablishing = institutionYearOfEstablishing;
        this.cityWhereEducating = cityWhereEducating;
        this.typeOfEducation = typeOfEducation;
    }

}
