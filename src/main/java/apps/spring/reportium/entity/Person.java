package apps.spring.reportium.entity;
import apps.spring.reportium.entity.converter.GenderConverter;
import apps.spring.reportium.entity.enumerations.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/*Table Person from PostgresSQL
CREATE TYPE gender_enum AS ENUM ('Male','Female');
--entity for Person with all the attributes
CREATE TABLE Person(
	person_id SERIAL PRIMARY KEY,
	embg varchar(16) UNIQUE NOT NULL,
	name varchar(50) NOT NULL,
	surname varchar(50) NOT NULL,
	gender gender_enum NOT NULL,
	date_of_birth date NOT NULL,
	is_alive BOOLEAN DEFAULT TRUE,
    date_of_death DATE,
	address varchar(80) NOT NULL,
	contact_phone varchar(20) NOT NULL
);
*/

@Data
@Entity
@Table(name = "Person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private int personId;

    @Column(nullable = false, unique = true)
    private String embg;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Convert(converter = GenderConverter.class)
    @Column(nullable = false)
    private Gender gender;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "is_alive", nullable = false)
    private boolean isAlive = true;

    @Column(name = "date_of_death")
    private LocalDate dateOfDeath;

    @Column(nullable = false)
    private String address;

    @Column(name = "contact_phone", nullable = false)
    private String contactPhone;

}
