package apps.spring.reportium.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/*
--creating the medical report table
CREATE TABLE MedicalReport (
    report_id INT PRIMARY KEY,
    next_control_date DATE NOT NULL,
    CONSTRAINT fk_report_id FOREIGN KEY (report_id) REFERENCES Report(report_id) ON DELETE CASCADE ON UPDATE CASCADE
);
*/

@Data
@Entity
@Table(name = "medicalreport")
public class MedicalReport {

    @Id
    @Column(name = "report_id")
    private int reportId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "report_id", foreignKey = @ForeignKey(name = "fk_report_id"))
    private Report report;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id", foreignKey = @ForeignKey(name = "fk_doctor_id"))
    private Doctor doctor;

    @Column(name = "next_control_date")
    private LocalDate nextControlDate;
}
