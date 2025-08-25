package apps.spring.reportium.entity.dto.view_fetching_dtos;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MedicalReportViewFetchingDTO {
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
    private LocalDate diagnosisCreationDate;
    private String diagnosisDescription;
    private Boolean isChronic;
    private String severity;
    private String therapyForDiagnosis;
    private LocalDate nextControlDate;
    private String doctorFullname;
    private String doctorSpecialization;
    private Boolean isDoctorStillActive;
    private Integer yearsOfExperience;

    public MedicalReportViewFetchingDTO(Integer reportId, String summary, java.sql.Date reportCreatedAt, String embgOfPerson, String personFullname,
                                        String gender, java.sql.Date dateOfBirth, java.sql.Date dateOfDeath, String addressOfLiving, String contactPhone,
                                        String typeOfReport, java.sql.Date diagnosisCreationDate, String diagnosisDescription, Boolean isChronic,
                                        String severity, String therapyForDiagnosis,
                                        java.sql.Date nextControlDate, String doctorFullname, String doctorSpecialization,
                                        Boolean isDoctorStillActive, Integer yearsOfExperience) {
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
        this.diagnosisCreationDate = diagnosisCreationDate != null ? diagnosisCreationDate.toLocalDate() : null;
        this.diagnosisDescription = diagnosisDescription;
        this.isChronic = isChronic;
        this.severity = severity;
        this.therapyForDiagnosis = therapyForDiagnosis;
        this.nextControlDate = nextControlDate != null ? nextControlDate.toLocalDate() : null;
        this.doctorFullname = doctorFullname;
        this.doctorSpecialization = doctorSpecialization;
        this.isDoctorStillActive = isDoctorStillActive;
        this.yearsOfExperience = yearsOfExperience;
    }
}
