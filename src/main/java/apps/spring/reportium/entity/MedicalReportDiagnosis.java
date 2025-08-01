package apps.spring.reportium.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
/*
CREATE TABLE MedicalReport_Diagnosis (
    id SERIAL PRIMARY KEY,
    report_id INT NOT NULL REFERENCES MedicalReport(report_id) ON DELETE CASCADE,
    diagnosis_id INT NOT NULL REFERENCES Diagnosis(diagnosis_id) ON DELETE CASCADE,
    added_on DATE DEFAULT CURRENT_DATE,
    CONSTRAINT unique_report_diagnosis UNIQUE (report_id, diagnosis_id)
);
*/

@Data
@Entity
@Table(
        name = "medicalreport_diagnosis",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"report_id", "diagnosis_id"}, name = "unique_report_diagnosis")
        }
)
@NoArgsConstructor
public class MedicalReportDiagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id", nullable = false)
    private MedicalReport report;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diagnosis_id", nullable = false)
    private Diagnosis diagnosis;

    @Column(name = "added_on")
    private LocalDate addedOn = LocalDate.now();

}