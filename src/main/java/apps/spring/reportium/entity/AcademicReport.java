package apps.spring.reportium.entity;

import jakarta.persistence.*;
import lombok.Data;
/*
--academic reports creating entity
CREATE TABLE AcademicReport (
    report_id INT PRIMARY KEY,
    institution_id INT NOT NULL,
    academic_field VARCHAR(100),
    description_of_report TEXT,
    CONSTRAINT fk_report_id FOREIGN KEY (report_id) REFERENCES Report(report_id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_institution_id FOREIGN KEY (institution_id) REFERENCES Institution(institution_id) ON DELETE CASCADE ON UPDATE CASCADE
);
*/

@Data
@Entity
@Table(name = "academicreport")
public class AcademicReport {
    @Id
    @Column(name = "report_id")
    private int reportId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "report_id", foreignKey = @ForeignKey(name = "fk_report_id"))
    private Report report;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "institution_id", nullable = false, foreignKey = @ForeignKey(name = "fk_institution_id"))
    private Institution institution;

    @Column(name = "academic_field", nullable = false)
    private String academicField;

    @Column(name = "description_of_report", columnDefinition = "TEXT")
    private String descriptionOfReport;
}
