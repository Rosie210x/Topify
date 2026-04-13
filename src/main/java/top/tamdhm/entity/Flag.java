package top.tamdhm.entity;

import jakarta.persistence.*;
import lombok.*;
import top.tamdhm.common.FlagReason;
import top.tamdhm.common.FlagStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "flags",
        indexes = {
                @Index(name = "idx_flags_war", columnList = "war_id"),
                @Index(name = "idx_flags_reporter", columnList = "reporter_user_id"),
                @Index(name = "idx_flags_status", columnList = "status")
        })
@ToString(exclude = {"war", "reporter"})
@EqualsAndHashCode(exclude = {"war", "reporter"})
public class Flag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flag_id")
    private Integer flagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "war_id", nullable = false)
    private War war;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_user_id", nullable = false)
    private User reporter;

    @OneToMany(mappedBy = "flag", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ModerationAction> actions = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "reason", length = 50)
    private FlagReason reason;

    @Column(name = "reason_text", length = 2000)
    private String reasonText;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private FlagStatus status;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (status == null) status = FlagStatus.OPEN;
    }

    @PreUpdate
    public void preUpdate() {
        // keep for future use (audit) — no-op here
    }

}