package apps.spring.reportium.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/*
--creating the employment report table
CREATE TABLE EmploymentReport (
    report_id INT PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE,
    job_role VARCHAR(100),
    income_per_month DECIMAL(10,2),
    CONSTRAINT fk_report_id FOREIGN KEY (report_id) REFERENCES Report(report_id) ON DELETE CASCADE,
    CONSTRAINT check_employment_dates CHECK (end_date IS NULL OR end_date > start_date),
    CONSTRAINT check_income_positive CHECK (income_per_month >= 0)
);
*/
@Data
@Entity
@Table(name = "employmentreport")
public class EmploymentReport {
    @Id
    @Column(name = "report_id")
    private int reportId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "report_id")
    private Report report;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "job_role")
    private String jobRole;

    @Column(name = "income_per_month", nullable = false)
    private double incomePerMonth;
}