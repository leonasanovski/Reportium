package apps.spring.reportium.entity;

import apps.spring.reportium.entity.converter.PunishmentTypeConverter;
import apps.spring.reportium.entity.converter.PunishmentValueUnitConverter;
import apps.spring.reportium.entity.enumerations.PunishmentType;
import apps.spring.reportium.entity.enumerations.ValueUnit;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/*
--punishment table
CREATE TABLE Punishment (
    punishment_id SERIAL PRIMARY KEY,
    report_id INT UNIQUE NOT NULL,
    value_unit VARCHAR(20) NOT NULL,
    punishment_type VARCHAR(50) NOT NULL,
    fine_to_pay DECIMAL(10,2),
    release_date DATE,
    CONSTRAINT check_punishment_type CHECK (punishment_type IN ('fine', 'prison')),
    CONSTRAINT check_value_unit CHECK (value_unit IN ('euros', 'years')),
    CONSTRAINT fk_report_id FOREIGN KEY (report_id) REFERENCES CriminalReport(report_id) ON DELETE CASCADE
);
 */
@Data
@Entity
@Table(name = "Punishment")
public class Punishment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "punishment_id")
    private int punishmentId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "report_id", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "fk_report_id"))
    private CriminalReport report;

    @Convert(converter = PunishmentValueUnitConverter.class)
    @Column(name = "value_unit", nullable = false)
    private ValueUnit valueUnit;

    @Convert(converter = PunishmentTypeConverter.class)
    @Column(name = "punishment_type", nullable = false)
    private PunishmentType punishmentType;

    @Column(name = "fine_to_pay")
    private double fineToPay;

    @Column(name = "release_date")
    private LocalDate releaseDate;
}
