package attendance.domain;

import java.time.LocalDateTime;

public enum Week {
    MONDAY("월요일"),
    TUESDAY("화요일"),
    WEDNESDAY("수요일"),
    THURSDAY("목요일"),
    FRIDAY("금요일"),
    SATURDAY("토요일"),
    SUNDAY("일요일");

    private final String dayOfWeek;

    Week(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public static String from(String dayOfWeek) {
        return valueOf(dayOfWeek).dayOfWeek;
    }

    public static boolean isMonday(LocalDateTime attendanceTime) {
        return attendanceTime.getDayOfWeek().toString().equals(MONDAY.name());
    }
}
