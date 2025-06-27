package apps.spring.reportium.entity.DTOs;

import apps.spring.reportium.entity.converter.DoctorSpecConverter;
import apps.spring.reportium.entity.converter.GenderConverter;
import apps.spring.reportium.entity.converter.InstitutionTypeConverter;
import apps.spring.reportium.entity.enumerations.*;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;


@Data
public class ReportFilterDTO {
    /*PERSON FIELDS*/
    private String person_name_surname_string;
    private Integer correct_age;//option 1
    private Integer age_start;//option 2
    private Integer age_end;//option 2
    @Convert(converter = GenderConverter.class)
    private Gender gender;
    private String address_string;
    private Boolean is_alive;
    /*ACADEMIC REPORT FIELDS*/
    private String academic_field;
    @Convert(converter = InstitutionTypeConverter.class)
    private InstitutionType institution_type;
    /*EMPLOYMENT REPORT FIELDS*/
    private Integer income_amount;
    @Enumerated(EnumType.STRING)
    private ComparisonDTOEnum income_comparison;
    private Integer years_experience;
    @Enumerated(EnumType.STRING)
    private ComparisonDTOEnum years_experience_comparison;
    /*CRIME REPORT FIELDS*/
    private String crime_type_label;
    @Enumerated(EnumType.STRING)
    private SeverityLevel crime_severity_level;
    private Boolean is_resolved;
    @Enumerated(EnumType.STRING)
    private PunishmentType punishment_type;//FINE, PRISON
    private Integer punishment_fine;//if a punishment type is fine (euros)
    private Integer punishment_years;//if a punishment type is prison (years)
    /*MEDICAL REPORT FIELDS*/
    private String doctor_name_string;//doc
    private String doctor_surname_string;//doc
    private Boolean is_active;//doc
    @Convert(converter = DoctorSpecConverter.class)
    private DoctorSpecialization specialization;//doc
    private Boolean is_chronic;
    private Boolean has_next_control;

    public boolean hasAnyAdvancedFilterSet() {
        return person_name_surname_string != null ||
                correct_age != null ||
                age_start != null ||
                gender != null ||
                academic_field != null ||
                income_amount != null ||
                years_experience != null ||
                crime_type_label != null ||
                doctor_name_string != null ||
                doctor_surname_string != null ||
                is_chronic != null ||
                has_next_control != null;
    }


    @Override
    public String toString() {
        return "ReportFilterDTO{" +
                "person_name_surname_string='" + person_name_surname_string + '\'' +
                ", correct_age=" + correct_age +
                ", age_start=" + age_start +
                ", age_end=" + age_end +
                ", gender=" + gender +
                ", address_string='" + address_string + '\'' +
                ", is_alive=" + is_alive +
                ", academic_field='" + academic_field + '\'' +
                ", institution_type=" + institution_type +
                ", income_amount=" + income_amount +
                ", income_comparison=" + income_comparison +
                ", years_experience=" + years_experience +
                ", years_experience_comparison=" + years_experience_comparison +
                ", crime_type_label='" + crime_type_label + '\'' +
                ", crime_severity_level=" + crime_severity_level +
                ", is_resolved=" + is_resolved +
                ", punishment_type=" + punishment_type +
                ", punishment_fine=" + punishment_fine +
                ", punishment_years=" + punishment_years +
                ", doctor_name_string='" + doctor_name_string + '\'' +
                ", doctor_surname_string='" + doctor_surname_string + '\'' +
                ", is_active=" + is_active +
                ", specialization=" + specialization +
                ", is_chronic=" + is_chronic +
                ", has_next_control=" + has_next_control +
                '}';
    }

}
