package apps.spring.reportium.entity.dto;

import apps.spring.reportium.entity.converter.DoctorSpecConverter;
import apps.spring.reportium.entity.converter.GenderConverter;
import apps.spring.reportium.entity.converter.InstitutionTypeConverter;
import apps.spring.reportium.entity.enumerations.*;
import jakarta.persistence.Convert;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;


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
    private Double punishment_fine;//if a punishment type is fine (euros)
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
        return sb.toString();
    }
}