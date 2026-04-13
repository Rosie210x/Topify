package top.tamdhm.common;

public enum WarStatus {
    DRAFT("Nháp"),
    ACTIVE("Đang hoạt động"),
    COMPLETED("Đã hoàn thành"),
    REMOVED("Đã xóa");

    private final String description;
    WarStatus(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
