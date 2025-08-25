package apps.spring.reportium.repository;

import apps.spring.reportium.entity.dto.*;
import apps.spring.reportium.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>, JpaSpecificationExecutor<Report> {

    @Query(value = """
            SELECT r.report_id, academic_field, description_of_report, i.name, i.type, i.address, i.year_established
            FROM report r
            JOIN academicreport ar ON ar.report_id = r.report_id
            JOIN institution i ON i.institution_id = ar.institution_id
            WHERE r.person_id = :personId
            """, nativeQuery = true)
    List<AcademicReportPerPersonDTO> getAcademicReportsByPersonId(@Param("personId") Long personId);

    @Query(value = """
            SELECT r.report_id, summary, er.start_date, er.end_date, job_role, income_per_month
            FROM report r
            JOIN employmentreport er ON er.report_id = r.report_id
            WHERE r.person_id = :personId
            """, nativeQuery = true)
    List<EmploymentReportPerPersonDTO> getEmploymentReportsByPersonId(@Param("personId") Long personId);

    @Query(value = """
            SELECT r.report_id, summary, next_control_date, d.short_description, d.therapy, d.severity, d.is_chronic,
                   doc.name, doc.surname, doc.specialization
            FROM report r
            JOIN medicalreport mr ON mr.report_id = r.report_id
            JOIN medicalreport_diagnosis mrd ON mr.report_id = mrd.report_id
            JOIN diagnosis d ON mrd.diagnosis_id = d.diagnosis_id
            JOIN doctor doc ON doc.doctor_id = mr.doctor_id
            WHERE r.person_id = :personId
            """, nativeQuery = true)
    List<MedicalReportPerPersonDTO> getMedicalReportsByPersonId(@Param("personId") Long personId);

    @Query(value = """
            SELECT r.report_id, label, severity_level, location, resolved, descriptive_punishment
            FROM report r
            JOIN criminalreport cr ON cr.report_id = r.report_id
            JOIN crimetype ct ON cr.crime_type_id = ct.crime_type_id
            WHERE r.person_id = :personId
            """, nativeQuery = true)
    List<CrimeReportPerPersonDTO> getCriminalReportsByPersonId(@Param("personId") Long personId);

    @Query(value = """
            WITH
            selected_person_reports AS (
                SELECT * FROM report WHERE person_id = :person_id
            ),
            
            academic AS (
                SELECT r.created_at, ar.academic_field, ar.report_id
                FROM report r
                         JOIN academicreport ar ON r.report_id = ar.report_id
                         JOIN institution i ON i.institution_id = ar.institution_id
                WHERE r.person_id = :person_id
            ),
            
            employment AS (
                SELECT e.start_date, COALESCE(e.end_date, CURRENT_DATE) AS end_date, e.income_per_month
                FROM employmentreport e
                         JOIN report r ON r.report_id = e.report_id
                WHERE r.person_id = :person_id
            ),
            
            medical AS (
                SELECT d.short_description, d.is_chronic
                FROM report r
                         JOIN medicalreport mr ON r.report_id = mr.report_id
                         JOIN medicalreport_diagnosis mrd ON mrd.report_id = mr.report_id
                         JOIN diagnosis d ON d.diagnosis_id = mrd.diagnosis_id
                WHERE r.person_id = :person_id
            ),
            
            criminal AS (
                SELECT cr.descriptive_punishment, cr.resolved as is_resolved
                FROM report r
                         JOIN criminalreport cr ON r.report_id = cr.report_id
                         JOIN crimetype ct ON ct.crime_type_id = cr.crime_type_id
                WHERE r.person_id = :person_id
            ),
            
            ordered_academic_reports_by_date AS (
                SELECT
                    ar.academic_field,
                    r.created_at,
                    ROW_NUMBER() OVER (PARTITION BY ar.academic_field ORDER BY r.created_at) AS row_num
                FROM report r
                         JOIN academicreport ar ON ar.report_id = r.report_id
                WHERE r.person_id = :person_id
            ),
            
            filtered_academic_pathway AS (
                SELECT academic_field, MIN(created_at) AS started_on
                FROM ordered_academic_reports_by_date
                GROUP BY academic_field
            ),
            
            employment_report_stats AS (
                SELECT
                    CAST(SUM(ABS(COALESCE(e.end_date, CURRENT_DATE) - e.start_date)) AS BIGINT) AS total_working_in_days,
                    CAST(CEIL(SUM(ABS(COALESCE(e.end_date, CURRENT_DATE) - e.start_date)) / 30.0) AS BIGINT) AS total_working_in_months,
                    CAST(CEIL(SUM(ABS(COALESCE(e.end_date, CURRENT_DATE) - e.start_date)) / 365.0) AS BIGINT) AS total_working_in_years,
                    CAST(MAX(COALESCE(e.end_date, CURRENT_DATE) - e.start_date) AS BIGINT) AS longest_job_days
                FROM employmentreport e
                         JOIN report r ON r.report_id = e.report_id
                WHERE r.person_id = :person_id
            )
            SELECT
            -- General
            CAST((SELECT COUNT(*) FROM selected_person_reports) AS BIGINT) AS total_reports_found,
            CAST((SELECT created_at::date FROM selected_person_reports ORDER BY created_at ASC LIMIT 1) AS DATE) AS first_report_of_person,
            CAST((SELECT created_at::date FROM selected_person_reports ORDER BY created_at DESC LIMIT 1) AS DATE) AS latest_report_of_person,
            -- Academic
            CAST((SELECT COUNT(*) FROM academic) AS BIGINT) AS academic_total,
            CAST((SELECT academic_field
                  FROM academic
                  GROUP BY academic_field
                  ORDER BY COUNT(*) DESC
                LIMIT 1) AS TEXT) AS most_common_field,
            CAST((SELECT STRING_AGG(academic_field, ' → ' ORDER BY started_on)
                  FROM filtered_academic_pathway) AS TEXT) AS education_path,
            -- Employment
            CAST((SELECT COUNT(*) FROM employment) AS BIGINT) AS job_count,
            (SELECT total_working_in_days FROM employment_report_stats),
            (SELECT total_working_in_months FROM employment_report_stats),
            (SELECT total_working_in_years FROM employment_report_stats),
            (SELECT longest_job_days FROM employment_report_stats),
            CAST((SELECT MAX(income_per_month) FROM employment) AS DOUBLE PRECISION) AS max_income_from_job,
            -- Medical
            CAST((SELECT COUNT(*) FROM medical) AS BIGINT) AS diagnosis_total,
            CAST((SELECT ROUND(SUM(CASE WHEN is_chronic THEN 1 ELSE 0 END)::decimal / COUNT(*), 2)
                  FROM medical) AS DOUBLE PRECISION) AS chronic_ratio,
            CAST((SELECT short_description
                  FROM medical
                  GROUP BY short_description
                  ORDER BY COUNT(*) DESC
                LIMIT 1) AS TEXT) AS most_frequent_diagnosis,
            -- Criminal
            CAST((SELECT COUNT(*) FROM criminal) AS BIGINT) AS criminal_case_total,
            CAST((SELECT ROUND(SUM(CASE WHEN is_resolved THEN 1 ELSE 0 END)::decimal / COUNT(*), 2)
                  FROM criminal) AS DOUBLE PRECISION) AS resolution_ratio
            """, nativeQuery = true)
    ReportStatisticsPerPersonDTO getStatisticsForPerson(@Param("person_id") Long person_id);

    @Query(value = """
            with selected_person_diagnosis as(
                select distinct d.diagnosis_id as diagnosis_id, d.short_description as label
                from person p
                         join report r on r.person_id = p.person_id
                         join medicalreport_diagnosis mrd on mrd.report_id = r.report_id
                         join diagnosis d on mrd.diagnosis_id = d.diagnosis_id
                where p.person_id = :person_id
            )
            select cast(p2.person_id as bigint),
                 p2.name || ' ' || p2.surname as full_name,
                 cast(count(distinct spd.diagnosis_id) as bigint) as matching_diagnoses_count,
                 string_agg(distinct spd.label, ', ') as matching_labels
            from selected_person_diagnosis spd
                     join medicalreport_diagnosis mrd2 on mrd2.diagnosis_id = spd.diagnosis_id
                     join report r2 on r2.report_id = mrd2.report_id
                     join person p2 on p2.person_id = r2.person_id
            where p2.person_id != :person_id
            group by p2.person_id, p2.name, p2.surname
            having count(distinct spd.diagnosis_id) >=1
            order by matching_diagnoses_count desc;
            """, nativeQuery = true)
    List<DiagnosisSimilarityPerPersonDTO> getSimilarDiagnosesForPerson(@Param("person_id") Long person_id);

    Page<Report> findAll(Pageable pageable);

    Page<Report> findAllByReportId(Integer reportId, Pageable pageable);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """ 
            UPDATE Report r
            SET r.person.personId = :stub_person_id
            WHERE r.person.personId = :target_to_delete_person_id
            """)
    int reassignReportsToStub(@Param("target_to_delete_person_id") Long targetId,
                              @Param("stub_person_id") Long stubId);

}

