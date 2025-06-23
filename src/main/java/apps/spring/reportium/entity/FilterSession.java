package apps.spring.reportium.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/*
CREATE TABLE FilterSession (
    session_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES "User"(user_id) ON DELETE CASCADE,
    filter_description TEXT NOT NULL,
    searched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
*/
@Data
@Entity
@Table(name = "filtersession")
public class FilterSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private int sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private ReportiumUser reportiumUser;

    @Column(name = "filter_description", nullable = false)
    private String filterDescription;

    @Column(name = "searched_at")
    private LocalDateTime searchedAt = LocalDateTime.now();
}
