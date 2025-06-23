package apps.spring.reportium.entity;

import apps.spring.reportium.entity.enumerations.SeverityLevel;
import jakarta.persistence.*;
import lombok.Data;

/*
--creating criminal type table
CREATE TABLE CrimeType (
    crime_type_id SERIAL PRIMARY KEY,
    label VARCHAR(100) NOT NULL,
	severity_level VARCHAR(10) NOT NULL,
    constraint check_severity CHECK (severity_level IN ('LOW', 'MEDIUM', 'HIGH'))
);
*/
@Data
@Entity
@Table(name = "crimetype")
public class CrimeType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crime_type_id")
    private int crimeTypeId;

    @Column(nullable = false, unique = true)
    private String label;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeverityLevel severityLevel;
}
