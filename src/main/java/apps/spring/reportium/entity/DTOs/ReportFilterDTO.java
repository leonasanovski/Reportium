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

import java.math.BigDecimal;


@Data
public class ReportFilterDTO {
    /*WHICH FILTER*/
    @Enumerated(EnumType.STRING)
    private SelectedFilterSection filter_selected;
    /*PERSON FIELDS*/
    private Integer person_id;
    private String person_name_string;
    private String person_surname_string;
    private Integer correct_age;//option 1
    private Integer age_start = 0;//option 2
    private Integer age_end = 120;//option 2
    @Convert(converter = GenderConverter.class)
    private Gender gender;
    private String address_string;
    private Boolean is_alive = true;
    /*ACADEMIC REPORT FIELDS*/
    private String academic_field;
    @Convert(converter = InstitutionTypeConverter.class)
    private InstitutionType institution_type;
    /*EMPLOYMENT REPORT FIELDS*/
    private Double income_amount;
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
    @Convert(converter = DoctorSpecConverter.class)
    private DoctorSpecialization specialization;//doc
    private Boolean is_chronic;
    private Boolean has_next_control;

    public boolean hasAnyAdvancedFilterSet() {
        return person_name_string != null ||
                person_surname_string != null ||
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
        StringBuilder sb = new StringBuilder();
        sb.append("Report Filter: ").append(filter_selected.toString()).append("\n");
        //TODO the advanced filter description
//        switch (filter_selected) {
//            case PERSON:
//                sb.append("Person: ").append(person_name_string).append(" ").append(person_surname_string).append("\n");
//                if (correct_age != null && correct_age > 0) {
//                    sb.append("Correct Age: ").append(correct_age);
//
//                } else if (age_start != null && age_start > 0 && age_end != null && age_end < 120) {
//                    sb.append("Age Range [").append(age_start).append(",").append(age_end).append("] ").append("\n");
//                }
//                sb.append("Gender: ").append(gender.toString()).append("\n");
//                sb.append("Address: ").append(address_string).append("\n");
//                if (getIs_alive().equals(Boolean.TRUE)) {
//                    sb.append("alive").append("\n");
//                } else {
//                    sb.append("dead").append("\n");
//                }
//                break;
//            case ACADEMIC:
//                sb.append("Academic Field: ").append(academic_field).append("\n");
//                sb.append("Institution Type: ").append(institution_type.toString()).append("\n");
//                break;
//            case EMPLOYMENT:
//                if (income_comparison != null && income_amount != null && income_amount > 0)
//                    sb.append("Income ").append(income_comparison).append(" ").append(income_amount).append(" EUR\n");
//                if (years_experience_comparison != null && years_experience != null && years_experience > 0)
//                    sb.append("Experience ").append(years_experience_comparison).append(" ")
//                            .append(years_experience).append(" years\n");
//                break;
//
//            case CRIMINAL:
//                if (crime_type_label != null && !crime_type_label.isBlank())
//                    sb.append("Crime Type: ").append(crime_type_label).append("\n");
//                if (crime_severity_level != null)
//                    sb.append("Severity Level: ").append(crime_severity_level).append("\n");
//                if (is_resolved != null)
//                    sb.append("Resolved: ").append(is_resolved ? "Yes" : "No").append("\n");
//
//                if (punishment_type != null) {
//                    sb.append("Punishment: ").append(punishment_type).append(" ");
//                    if (punishment_type == PunishmentType.FINE && punishment_fine != null)
//                        sb.append(punishment_fine).append(" EUR\n");
//                    else if (punishment_type == PunishmentType.PRISON && punishment_years != null)
//                        sb.append(punishment_years).append(" years\n");
//                }
//                break;
//
//            case MEDICAL:
//                if (doctor_name_string != null && !doctor_name_string.isBlank())
//                    sb.append("Doctor Name: ").append(doctor_name_string).append("\n");
//                if (doctor_surname_string != null && !doctor_surname_string.isBlank())
//                    sb.append("Doctor Surname: ").append(doctor_surname_string).append("\n");
//                if (specialization != null)
//                    sb.append("Specialization: ").append(specialization).append("\n");
//                if (is_chronic != null)
//                    sb.append("Chronic Illness: ").append(is_chronic ? "Yes" : "No").append("\n");
//                if (has_next_control != null)
//                    sb.append("Next Control Scheduled: ").append(has_next_control ? "Yes" : "No").append("\n");
//                break;
//        }
        return sb.toString();
    }
}
