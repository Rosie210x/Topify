package top.tamdhm.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import top.tamdhm.dto.UserProfileResponse;
import top.tamdhm.entity.User;
import top.tamdhm.exception.ResourceNotFoundException;
import top.tamdhm.repository.UserRepository;
import top.tamdhm.security.CustomUserDetails;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfileResponse getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            throw new ResourceNotFoundException("Current user not found");
        }
        CustomUserDetails currentUser = (CustomUserDetails) authentication.getPrincipal();
        return mapToUserProfile(currentUser.getUser());
    }

    public List<UserProfileResponse> listAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToUserProfile)
                .collect(Collectors.toList());
    }

    public UserProfileResponse getUserById(Integer userId) {
        return userRepository.findById(userId)
                .map(this::mapToUserProfile)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
    }

    private UserProfileResponse mapToUserProfile(User user) {
        return UserProfileResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .displayName(user.getDisplayName())
                .role(user.getRole())
                .status(user.getStatus())
                .createdDate(user.getCreatedDate())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}
