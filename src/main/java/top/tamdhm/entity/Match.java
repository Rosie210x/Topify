package top.tamdhm.entity;

import jakarta.persistence.*;
import lombok.*;
import top.tamdhm.common.MatchStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matches",
        indexes = {
                @Index(name = "idx_match_round_war", columnList = "round_number, war_id"),
                @Index(name = "idx_match_status", columnList = "match_status")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"optionA", "optionB", "winner", "war"})
@EqualsAndHashCode(exclude = {"optionA", "optionB", "winner", "war"})
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Integer matchId;

    @Column(name = "round_number", nullable = false)
    private Integer roundNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_a", nullable = false)
    private Option optionA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_b", nullable = false)
    private Option optionB;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner_id")
    private Option winner;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_status", nullable = false, length = 50)
    private MatchStatus matchStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "war_id", nullable = false)
    private War war;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Vote> votes = new ArrayList<>();

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @PrePersist
    public void prePersist() {
        if (createdDate == null) createdDate = LocalDateTime.now();
        if (matchStatus == null) matchStatus = MatchStatus.OPENED;
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

}