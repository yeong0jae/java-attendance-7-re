package attendance.domain;

public enum AttendType {
    PASS("출석"),
    LATE("지각"),
    ABSENCE("결석");

    private final String attendType;

    AttendType(String attendType) {
        this.attendType = attendType;
    }

    public String getAttendType() {
        return attendType;
    }
}
