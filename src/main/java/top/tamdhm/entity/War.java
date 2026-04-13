package top.tamdhm.entity;

import jakarta.persistence.*;
import lombok.*;
import top.tamdhm.common.WarPhase;
import top.tamdhm.common.WarStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "wars",
        indexes = {
        @Index(name = "idx_war_status", columnList = "war_status"),
        @Index(name = "idx_war_end_date", columnList = "end_date")})
@ToString(exclude = {"options", "creator", "tournament"})
@EqualsAndHashCode(exclude = {"options", "creator", "tournament"})
public class War {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "war_id")
    private Integer warId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "war_status", nullable = false, length = 50)
    private WarStatus warStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "war_phase", nullable = false, length = 50)
    private WarPhase phase;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @ElementCollection
    @CollectionTable(name = "war_tags", joinColumns = @JoinColumn(name = "war_id"))
    @Column(name = "tag", length = 100)
    private List<String> tags = new ArrayList<>();

    @OneToMany(mappedBy = "war", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Option> options = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = true)
    private Tournament tournament;

    @OneToMany(mappedBy = "war", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Flag> flags = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (createdDate == null) {
            createdDate = LocalDateTime.now();
        }
        if (warStatus == null) {
            warStatus = WarStatus.DRAFT;
        }
        if (phase == null) {
            phase = WarPhase.EARLY;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

}