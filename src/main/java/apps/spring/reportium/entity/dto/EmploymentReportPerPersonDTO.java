package apps.spring.reportium.entity.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmploymentReportPerPersonDTO {
    private Integer reportId;
    private String summary;
    private LocalDate startDate;
    private LocalDate endDate;
    private String jobRole;
    private Double incomePerMonth;

    public EmploymentReportPerPersonDTO(Integer reportId, String summary, java.sql.Date startDate,
                                        java.sql.Date endDate, String jobRole, Double incomePerMonth) {
        this.reportId = reportId;
        this.summary = summary;
        this.startDate = startDate != null ? startDate.toLocalDate() : null;
        this.endDate = endDate != null ? endDate.toLocalDate() : null;
        this.jobRole = jobRole;
        this.incomePerMonth = incomePerMonth;
    }

}
