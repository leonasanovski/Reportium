package apps.spring.reportium.entity;

import apps.spring.reportium.entity.converter.ReportTypeConverter;
import apps.spring.reportium.entity.enumerations.ReportType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/*
CREATE TABLE Report (
    report_id SERIAL PRIMARY KEY,
    report_type VARCHAR(50) NOT NULL,
    summary VARCHAR(255),
    created_at DATE NOT NULL,
    person_id INT,
	CONSTRAINT fk_person_id FOREIGN KEY (person_id) REFERENCES Person(person_id) ON DELETE SET NULL ON UPDATE CASCADE,
	CONSTRAINT check_report_type CHECK (report_type IN ('Medical', 'Criminal', 'Academic', 'Employment'))
);
 */
@Data
@Entity
@Table(name = "Report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private int reportId;

    @Convert(converter = ReportTypeConverter.class)
    @Column(name = "report_type",nullable = false)
    private ReportType reportType;

    private String summary;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", foreignKey = @ForeignKey(name = "fk_person_id"))
    private Person person;
}
