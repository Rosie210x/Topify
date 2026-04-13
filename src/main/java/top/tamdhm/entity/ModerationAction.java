package top.tamdhm.entity;

import jakarta.persistence.*;
import lombok.*;
import top.tamdhm.common.ModerationActionType;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "moderation_actions",
        indexes = {
                @Index(name = "idx_moderation_flag", columnList = "flag_id"),
                @Index(name = "idx_moderation_moderator", columnList = "moderator_user_id")
        })
@ToString(exclude = {"flag", "moderator"})
@EqualsAndHashCode(exclude = {"flag", "moderator"})
public class ModerationAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_id")
    private Integer actionId;

    /**
     * Optional link to the originating flag; nullable because some actions may be proactive.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flag_id")
    private Flag flag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_user_id", nullable = false)
    private User moderator;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false, length = 50)
    private ModerationActionType actionType;

    @Lob
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

}