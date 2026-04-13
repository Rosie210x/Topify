package top.tamdhm.entity;

import jakarta.persistence.*;
import lombok.*;
import top.tamdhm.common.Role;
import top.tamdhm.common.UserStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name="email", unique = true, nullable = false, length = 100)
    private String email;

    //if using OAuth, password_hash will be null
    @Column(name="password_hash", nullable = false)
    private String passwordHash;


    @Column(name="display_name", nullable = false, length = 100)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name="user_status", nullable = false)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private Role role;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @PrePersist
    public void prePersist() {
        if (createdDate == null) {
        createdDate = LocalDateTime.now();
        }
        if (status == null) {
            status = UserStatus.ACTIVE;
        }
        if (role == null) {
            role = Role.USER;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<War> war;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Vote> vote;

    @OneToOne
    @JoinColumn(name = "leaderboard_entry_id")
    private LeaderboardEntry leaderboardEntry;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EventLog> log;
}
