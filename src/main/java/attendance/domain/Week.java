package attendance.domain;

import java.time.LocalDateTime;

public enum Week {
    MONDAY("월요일", CampusTime.EDUCATE1),
    TUESDAY("화요일", CampusTime.EDUCATE2),
    WEDNESDAY("수요일", CampusTime.EDUCATE2),
    THURSDAY("목요일", CampusTime.EDUCATE2),
    FRIDAY("금요일", CampusTime.EDUCATE2),
    SATURDAY("토요일", CampusTime.EDUCATE2),
    SUNDAY("일요일", CampusTime.EDUCATE2);

    private final String dayOfWeek;
    private final CampusTime campusTime;

    Week(String dayOfWeek, CampusTime campusTime) {
        this.dayOfWeek = dayOfWeek;
        this.campusTime = campusTime;
    }

    public static String from(String dayOfWeek) {
        return valueOf(dayOfWeek).dayOfWeek;
    }

    public static boolean isMonday(LocalDateTime now) {
        return now.getDayOfWeek().toString().equals(valueOf("MONDAY").dayOfWeek);
    }
}
