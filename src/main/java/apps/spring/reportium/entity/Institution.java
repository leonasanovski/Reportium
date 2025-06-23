package apps.spring.reportium.entity;

import apps.spring.reportium.entity.converter.InstitutionTypeConverter;
import apps.spring.reportium.entity.enumerations.InstitutionType;
import jakarta.persistence.*;
import lombok.Data;
/*
--enumeration - type of institution
CREATE TYPE institution_type AS ENUM ('Primary School', 'High School', 'University', 'Academy');
--institution entity table
CREATE TABLE Institution (
    institution_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(100),
    city VARCHAR(50),
    type institution_type NOT NULL,
    year_established INT CHECK (year_established >= 1800 AND year_established <= EXTRACT(YEAR FROM CURRENT_DATE)),
	CONSTRAINT unique_institution_name_city UNIQUE (name, city)
);
*/
@Data
@Entity
@Table(
        name = "Institution",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "city"})
)
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "institution_id")
    private int institutionId;

    @Column(nullable = false)
    private String name;

    private String address;

    private String city;

    @Convert(converter = InstitutionTypeConverter.class)
    @Column(nullable = false)
    private InstitutionType type;

    @Column(name = "year_established", nullable = false)
    private int yearEstablished;
}
