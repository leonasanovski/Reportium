package apps.spring.reportium.entity;

import apps.spring.reportium.entity.enumerations.SeverityLevel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
create table Diagnosis(
    diagnosis_id serial primary key,
    short_description text not null,
    therapy text,
    is_chronic boolean default false,
    severity VARCHAR(10),
    constraint check_severity CHECK (severity in ('LOW','MEDIUM','HIGH'))
);
*/
@Data
@Entity
@Table(name = "Diagnosis")
@NoArgsConstructor
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_id")
    private Integer diagnosisId;

    @Column(nullable = false, name = "short_description", columnDefinition = "TEXT")
    private String shortDescription;

    @Column(columnDefinition = "TEXT")
    private String therapy;

    @Column(name = "is_chronic")
    private boolean isChronic = false;

    @Column(name = "severity", nullable = false)
    @Enumerated(EnumType.STRING)
    private SeverityLevel severityLevel;

    public Diagnosis(String shortDescription, String therapy, boolean isChronic, SeverityLevel severityLevel) {
        this.shortDescription = shortDescription;
        this.therapy = therapy;
        this.isChronic = isChronic;
        this.severityLevel = severityLevel;
    }
}
