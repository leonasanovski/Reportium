package apps.spring.reportium.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
/*
--criminalReport table
CREATE TABLE CriminalReport (
    report_id INT PRIMARY KEY REFERENCES Report(report_id) ON DELETE CASCADE,
    location VARCHAR(100),
    created_at DATE DEFAULT CURRENT_DATE,
    resolved BOOLEAN DEFAULT FALSE,
    crime_type_id INT REFERENCES CrimeType(crime_type_id) ON DELETE SET NULL,
    descriptive_punishment TEXT
);
*/


@Data
@Entity
@Table(name = "criminalreport")
public class CriminalReport {
    @Id
    @Column(name = "report_id")
    private int reportId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "report_id")
    private Report report;

    private String location;

    private boolean resolved = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "crime_type_id", foreignKey = @ForeignKey(name = "fk_crime_type_id"))
    private CrimeType crimeType;

    @Column(name = "descriptive_punishment", columnDefinition = "TEXT")
    private String descriptivePunishment;
}
