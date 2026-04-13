package top.tamdhm.common;

public enum MatchStatus {
    OPENED("Đã mở"),
    CLOSED("Đã đóng");


    private final String description;
    MatchStatus(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
