package top.tamdhm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "event_logs",
        indexes = {
                @Index(name = "idx_event_user", columnList = "user_id"),
                @Index(name = "idx_event_type", columnList = "event_type"),
                @Index(name = "idx_event_created", columnList = "created_at")
        })
@ToString(exclude = {"payload"})
@EqualsAndHashCode(exclude = {"payload"})
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "event_type", nullable = false, length = 200)
    private String eventType;

    /**
     * Payload stored as text/JSON. For Postgres you can change columnDefinition to "jsonb" if desired.
     */
    @Lob
    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

}