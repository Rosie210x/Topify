package top.tamdhm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.tamdhm.common.Role;
import top.tamdhm.common.UserStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private Integer userId;
    private String username;
    private String email;
    private String displayName;
    private Role role;
    private UserStatus status;
    private LocalDateTime createdDate;
    private String avatarUrl;
}
