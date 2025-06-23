package apps.spring.reportium.repository;

import apps.spring.reportium.entity.DTOs.AcademicReportPerPersonDTO;
import apps.spring.reportium.entity.DTOs.CrimeReportPerPersonDTO;
import apps.spring.reportium.entity.DTOs.EmploymentReportPerPersonDTO;
import apps.spring.reportium.entity.DTOs.MedicalReportPerPersonDTO;
import apps.spring.reportium.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

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

    Page<Report> findAll(Pageable pageable);

    Page<Report> findAllByReportId(Integer reportId, Pageable pageable);

}

