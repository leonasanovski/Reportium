package apps.spring.reportium.specifications;

import apps.spring.reportium.entity.*;
import apps.spring.reportium.entity.DTOs.ReportFilterDTO;
import apps.spring.reportium.entity.enumerations.PunishmentType;
import apps.spring.reportium.entity.enumerations.SelectedFilterSection;
import apps.spring.reportium.entity.exceptions.AgeFilterOnNotAlivePeopleException;
import apps.spring.reportium.repository.PersonRepository;
import apps.spring.reportium.repository.ReportRepository;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Expression;

import static apps.spring.reportium.entity.enumerations.ComparisonDTOEnum.*;

public class ReportFilterSpecificationBuilder {
    public static Specification<Report> build(ReportFilterDTO filter) {
        return (root, query, cb) -> {
            //global things
            query.distinct(true);
            LocalDate now = LocalDate.now();
            List<Predicate> predicates = new ArrayList<>();
            if (filter.getFilter_selected().equals(SelectedFilterSection.PERSON)) {
                //proof of concept 1 -> Person Join Filters
                /* Person */
                Join<Report, Person> report_on_person_join = root.join("person", JoinType.LEFT);
                // predicate that checks if there exists a person with the string provided in the name
                if (filter.getPerson_name_string() != null && !filter.getPerson_name_string().isBlank()) {
                    predicates.add(cb.like(cb.lower(report_on_person_join.get("name")),
                            "%" + filter.getPerson_name_string().toLowerCase() + "%"));
                }
                // predicate that checks if there exists a person with the string provided in the surname
                if (filter.getPerson_surname_string() != null && !filter.getPerson_surname_string().isBlank()) {
                    predicates.add(cb.like(cb.lower(report_on_person_join.get("surname")), "%" + filter.getPerson_surname_string().toLowerCase() + "%"));
                }
                // Age filters work for people who are alive only, otherwise no
                if (filter.getIs_alive()) {
                    if (filter.getCorrect_age() != null && filter.getCorrect_age() > 0) {
                        Expression<Integer> years_age = cb.function("date_part", Integer.class, cb.literal("year"),
                                cb.function("age", String.class, cb.currentDate(), report_on_person_join.get("dateOfBirth")));
                        predicates.add(cb.equal(years_age, filter.getCorrect_age()));
                    } else if (filter.getCorrect_age() == null || filter.getCorrect_age() == 0) {
                        if (filter.getAge_start() > 0) {
                            LocalDate start_age = now.minusYears(filter.getAge_start());//
                            predicates.add(cb.lessThanOrEqualTo(report_on_person_join.get("dateOfBirth"), start_age));
                        }
                        if (filter.getAge_end() < 120) {
                            LocalDate end_age = now.minusYears(filter.getAge_end() + 1).plusDays(1);
                            predicates.add(cb.greaterThanOrEqualTo(report_on_person_join.get("dateOfBirth"), end_age));
                        }
                    }
                    predicates.add(cb.isNull(report_on_person_join.get("dateOfDeath")));
                } else {
                    predicates.add(cb.isNotNull(report_on_person_join.get("dateOfDeath")));
                }
                // Gender
                if (filter.getGender() != null) {
                    predicates.add(cb.equal(report_on_person_join.get("gender"), filter.getGender()));
                }
                // Address
                if (filter.getAddress_string() != null && !filter.getAddress_string().isBlank()) {
                    predicates.add(cb.like(
                            cb.lower(report_on_person_join.get("address")),
                            "%" + filter.getAddress_string().toLowerCase() + "%"
                    ));
                }
            }
            if (filter.getFilter_selected().equals(SelectedFilterSection.EMPLOYMENT)) {
                //proof of concept 2 -> Employment Report Join Filters
                /* Employment Report */
                Join<Report, EmploymentReport> employment_report_join = root.join("employmentReport", JoinType.LEFT);
                //predicate for income check (more,less,equal)
                if (filter.getIncome_comparison() != null && filter.getIncome_amount() > 0) {
                    switch (filter.getIncome_comparison()) {
                        case more ->
                                predicates.add(cb.greaterThan(employment_report_join.get("incomePerMonth"), filter.getIncome_amount()));
                        case equal ->
                                predicates.add(cb.equal(employment_report_join.get("incomePerMonth"), filter.getIncome_amount()));
                        case less ->
                                predicates.add(cb.lessThan(employment_report_join.get("incomePerMonth"), filter.getIncome_amount()));
                    }
                }
                //predicate for years_experience check (more,less,equal)
                if (filter.getYears_experience_comparison() != null && filter.getYears_experience() > 0) {
                    //this function is in the database, and I execute it
                    Expression<Integer> totalYearsExpr = cb.function(
                            "years_total", Integer.class,
                            employment_report_join.get("startDate"), employment_report_join.get("endDate")
                    );
                    switch (filter.getYears_experience_comparison()) {
                        case more -> predicates.add(cb.greaterThan(totalYearsExpr, filter.getYears_experience()));
                        case equal -> predicates.add(cb.equal(totalYearsExpr, filter.getYears_experience()));
                        case less -> predicates.add(cb.lessThan(totalYearsExpr, filter.getYears_experience()));
                    }
                }
            }
            if (filter.getFilter_selected().equals(SelectedFilterSection.ACADEMIC)) {
                //proof of concept 3 -> Academic Report Join Filters
                /* Academic Report */
                Join<Report, AcademicReport> academic_report_join = root.join("academicReport", JoinType.LEFT);
                //predicate for field of study
                if (filter.getAcademic_field() != null && !filter.getAcademic_field().isBlank()) {
                    predicates.add(cb.like(cb.lower(academic_report_join.get("academicField")), "%" + filter.getAcademic_field().toLowerCase() + "%"));
                }
                //predicate for institution field
                Join<AcademicReport, Institution> academic_report_institution_join = academic_report_join.join("institution", JoinType.LEFT);
                if (filter.getInstitution_type() != null) {
                    predicates.add(cb.equal(academic_report_institution_join.get("type"), filter.getInstitution_type()));
                }
            }
            if (filter.getFilter_selected().equals(SelectedFilterSection.MEDICAL)) {
                //proof of concept 4 -> Medical Report Join Filters
                /* Medical Report */
                Join<Report, MedicalReport> medical_report_join = root.join("medicalReport", JoinType.LEFT);
                Join<MedicalReport, Doctor> medical_report_doctor_join = medical_report_join.join("doctor", JoinType.LEFT);
                //predicate for has next control
                if (filter.getHas_next_control()) {
                    predicates.add(cb.isNotNull(medical_report_join.get("nextControlDate")));
                } else {
                    predicates.add(cb.isNull(medical_report_join.get("nextControlDate")));
                }
                //predicate for doctor name
                if (filter.getDoctor_name_string() != null && !filter.getDoctor_name_string().isBlank()) {
                    predicates.add(cb.like(cb.lower(medical_report_doctor_join.get("name")), "%" + filter.getDoctor_name_string().toLowerCase() + "%"));
                }
                //predicate for doctor surname
                if (filter.getDoctor_surname_string() != null && !filter.getDoctor_surname_string().isBlank()) {
                    predicates.add(cb.like(cb.lower(medical_report_doctor_join.get("surname")), "%" + filter.getDoctor_surname_string().toLowerCase() + "%"));
                }
                //predicate for specialization
                if (filter.getSpecialization() != null) {
                    predicates.add(cb.equal(medical_report_doctor_join.get("specialization"), filter.getSpecialization()));
                }
                Join<MedicalReport, MedicalReportDiagnosis> mrd_join = medical_report_join.join("medicalReportDiagnoses", JoinType.INNER);
                Join<MedicalReportDiagnosis, Diagnosis> diagnosis_join = mrd_join.join("diagnosis", JoinType.INNER);
                //predicate for if the illness is chronic
                if (filter.getIs_chronic() != null) {
                    predicates.add(cb.equal(diagnosis_join.get("isChronic"), filter.getIs_chronic()));
                }
            }
            if (filter.getFilter_selected().equals(SelectedFilterSection.CRIMINAL)) {
                //proof of concept 5 -> Criminal Report Join Filters
                /* Criminal Report */
                Join<Report, CriminalReport> criminal_report_join = root.join("criminalReport", JoinType.LEFT);
                Join<CriminalReport, CrimeType> crime_type_join = criminal_report_join.join("crimeType", JoinType.LEFT);
                //predicate for severity level
                if (filter.getCrime_severity_level() != null) {
                    predicates.add(cb.equal(crime_type_join.get("severityLevel"), filter.getCrime_severity_level()));
                }
                //predicate for resolved
                if (filter.getIs_resolved() != null) {
                    predicates.add(cb.equal(criminal_report_join.get("resolved"), filter.getIs_resolved()));
                }
                Join<CriminalReport, Punishment> punishment_join = criminal_report_join.join("punishment", JoinType.INNER);
                //predicate for punishment as fine
                if (PunishmentType.FINE.equals(filter.getPunishment_type()) && filter.getPunishment_fine() != null && filter.getPunishment_fine() > 0) {
                    predicates.add(cb.equal(punishment_join.get("fineToPay"), filter.getPunishment_fine()));
                }
                //predicate for punishment as prison
                if (PunishmentType.PRISON.equals(filter.getPunishment_type()) && filter.getPunishment_years() != null && filter.getPunishment_years() > 0) {
                    Expression<LocalDate> created_at = root.get("createdAt");
                    Expression<Integer> years_in_prison = cb.function("years_total", Integer.class,
                            created_at, punishment_join.get("releaseDate"));
                    predicates.add(cb.equal(years_in_prison, filter.getPunishment_years()));
                }
                //predicate for criminal type
                if (filter.getCrime_type_label() != null && !filter.getCrime_type_label().isBlank()) {
                    predicates.add(cb.like(cb.lower(crime_type_join.get("label")), "%" + filter.getCrime_type_label().toLowerCase() + "%"));
                }
            }


            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
