package apps.spring.reportium.entity;


import apps.spring.reportium.entity.converter.DoctorSpecConverter;
import apps.spring.reportium.entity.enumerations.DoctorSpecialization;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/*
CREATE TABLE Doctor (
    doctor_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    specialization doctor_specialization_options NOT NULL,
    years_of_experience INT CHECK (years_of_experience >= 0),
    is_active BOOLEAN DEFAULT TRUE
);
*/
@Data
@Entity
@Table(name = "Doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private int doctorId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    @Convert(converter = DoctorSpecConverter.class)
    private DoctorSpecialization specialization;

    @Column(name = "years_of_experience", nullable = false)
    private int yearsOfExperience;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
