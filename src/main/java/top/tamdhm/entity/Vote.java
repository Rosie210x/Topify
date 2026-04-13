package top.tamdhm.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes",
        indexes = {
                @Index(name = "idx_votes_match", columnList = "match_id"),
                @Index(name = "idx_votes_voter", columnList = "voter_id"),
                @Index(name = "idx_votes_session", columnList = "session_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"match", "voter", "option"})
@EqualsAndHashCode(exclude = {"match", "voter", "option"})
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Integer voteId;

    @Column(name = "vote_time", nullable = false)
    private LocalDateTime voteTime;

    @Column(name = "session_id")
    private Integer sessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private Option option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter_id", nullable = false)
    private User voter;

    @PrePersist
    public void prePersist() {
        if (voteTime == null) {
            voteTime = LocalDateTime.now();
        }
    }
}