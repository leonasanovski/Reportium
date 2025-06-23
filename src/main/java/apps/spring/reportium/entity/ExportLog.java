package apps.spring.reportium.entity;
import apps.spring.reportium.entity.enumerations.ExportFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
/*
CREATE TABLE ExportLog (
    export_id SERIAL PRIMARY KEY,
    session_id INT NOT NULL REFERENCES FilterSession(session_id) ON DELETE CASCADE,
    file_name VARCHAR(100) NOT NULL,
    filter_summary TEXT,
    export_format VARCHAR(20) default 'CSV' CHECK (export_format IN ('CSV', 'PDF')),
    export_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
*/




@Data
@Entity
@Table(name = "exportlog")
public class ExportLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "export_id")
    private int exportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private FilterSession filterSession;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "filter_summary", columnDefinition = "TEXT")
    private String filterSummary;

    @Column(name = "export_format", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExportFormat exportFormat;

    @Column(name = "export_date", nullable = false)
    private LocalDateTime exportDate = LocalDateTime.now();
}
