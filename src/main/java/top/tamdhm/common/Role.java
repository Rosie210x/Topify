package top.tamdhm.common;

import java.util.List;

public enum Role {
    ADMIN("Quản trị viên"),
    MODERATOR("Kiểm soát viên"),
    USER("Người dùng");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static List<Role> getRoles() {
        return List.of(
                ADMIN, MODERATOR, USER
        );
    }
}
