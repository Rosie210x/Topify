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
@Table(name = "leaderboard_entries",
        indexes = {
                @Index(name = "idx_leaderboard_user", columnList = "user_id"),
                @Index(name = "idx_leaderboard_score", columnList = "hot_take_score")
        })
@ToString(exclude = {"user"})
@EqualsAndHashCode(exclude = {"user"})
public class LeaderboardEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Integer entryId;

    @OneToOne(mappedBy ="leaderboardEntry",fetch = FetchType.LAZY)
    private User user;

    @Column(name = "hot_take_score", nullable = false)
    private Double hotTakeScore;

    @Column(name = "last_updated_at", nullable = false)
    private LocalDateTime lastUpdatedAt;

    @Version
    @Column(name = "version")
    private Long version;

    @PrePersist
    public void prePersist() {
        if (lastUpdatedAt == null) lastUpdatedAt = LocalDateTime.now();
        if (hotTakeScore == null) hotTakeScore = 0.0;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdatedAt = LocalDateTime.now();
    }
}