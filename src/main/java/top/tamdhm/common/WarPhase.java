package top.tamdhm.common;

public enum WarPhase {
    EARLY("Đang mở tự do"),
    FINAL("Phiên bản cuối"),
    LOCKED_FINAL("Đã đóng thay đổi");

    private final String description;
    WarPhase(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
