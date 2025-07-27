package apps.spring.reportium.entity.DTOs;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Date;
//fixme
@Data
public class ReportStatisticsPerPersonDTO {
    // General
    private Long totalReportsFound;
    private Date firstReportOfPerson;
    private Date latestReportOfPerson;
    // Academic
    private Long academicTotal;
    private String mostCommonField;
    private String educationPath;
    // Employment
    private Long jobCount;
    private Long totalWorkingInDays;
    private Long totalWorkingInMonths;
    private Long totalWorkingInYears;
    private Long longestJobDays;
    private Double maxIncomeFromJob;
    // Medical
    private Long diagnosisTotal;
    private Double chronicRatio;
    private String mostFrequentDiagnosis;
    // Criminal
    private Long criminalCaseTotal;
    private Double resolutionRatio;
    public ReportStatisticsPerPersonDTO(Long totalReportsFound,
                                        Date firstReportOfPerson,
                                        Date latestReportOfPerson,
                                        Long academicTotal,
                                        String mostCommonField,
                                        String educationPath,
                                        Long jobCount,
                                        Long totalWorkingInDays,
                                        Long totalWorkingInMonths,
                                        Long totalWorkingInYears,
                                        Long longestJobDays,
                                        Double maxIncomeFromJob,
                                        Long diagnosisTotal,
                                        Double chronicRatio,
                                        String mostFrequentDiagnosis,
                                        Long criminalCaseTotal,
                                        Double resolutionRatio) {
        this.totalReportsFound = totalReportsFound;
        this.firstReportOfPerson = firstReportOfPerson;
        this.latestReportOfPerson = latestReportOfPerson;
        this.academicTotal = academicTotal;
        this.mostCommonField = mostCommonField;
        this.educationPath = educationPath;
        this.jobCount = jobCount;
        this.totalWorkingInDays = totalWorkingInDays;
        this.totalWorkingInMonths = totalWorkingInMonths;
        this.totalWorkingInYears = totalWorkingInYears;
        this.longestJobDays = longestJobDays;
        this.maxIncomeFromJob = maxIncomeFromJob;
        this.diagnosisTotal = diagnosisTotal;
        this.chronicRatio = chronicRatio;
        this.mostFrequentDiagnosis = mostFrequentDiagnosis;
        this.criminalCaseTotal = criminalCaseTotal;
        this.resolutionRatio = resolutionRatio;
    }
}
