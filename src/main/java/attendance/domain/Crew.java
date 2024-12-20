package attendance.domain;

import attendance.util.ErrorMessage;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Crew {
    private static final Integer OPEN_TIME = 8;
    private static final Integer OPEN_MINUTE = 0;
    private static final Integer CLOSE_TIME = 23;
    private static final Integer CLOSE_MINUTE = 0;

    private final String name;
    private LocalDateTime attendanceTime;
    private AttendType attendType;

    public Crew(String name, LocalDateTime attendanceTime) {
        this.name = name;
        validateNotOpen(attendanceTime);
        this.attendanceTime = attendanceTime;
        this.attendType = judgeAttendType();
    }

    public Crew(String name, LocalDateTime attendanceTime, AttendType attendType) {
        this.name = name;
        this.attendanceTime = attendanceTime;
        this.attendType = attendType;
    }

    public AttendType getAttendType() {
        return attendType;
    }

    private void validateNotOpen(LocalDateTime attendanceTime) {
        LocalDateTime openTime = LocalDateTime.of(
                attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDayOfMonth(),
                OPEN_TIME, OPEN_MINUTE);
        LocalDateTime closeTime = LocalDateTime.of(
                attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDayOfMonth(),
                CLOSE_TIME, CLOSE_MINUTE);
        if (attendanceTime.isBefore(openTime) || attendanceTime.isAfter(closeTime)) {
            throw new IllegalArgumentException(ErrorMessage.PREFIX + "캠퍼스 운영 시간에만 출석이 가능합니다.");
        }
    }

    private AttendType judgeAttendType() {
        boolean isMonday = Week.isMonday(attendanceTime);
        if (isMonday) {
            LocalDateTime passTime = LocalDateTime.of(
                    attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDayOfMonth(),
                    13, 0);
            return getLate(passTime);
        }
        LocalDateTime passTime = LocalDateTime.of(
                attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDayOfMonth(),
                10, 0);
        return getLate(passTime);
    }

    private AttendType getLate(LocalDateTime passTime) {
        if (attendanceTime.isAfter(passTime)) {
            if (Duration.between(passTime, attendanceTime).getSeconds() > 1800) {
                return AttendType.ABSENCE;
            }
            if (Duration.between(passTime, attendanceTime).getSeconds() > 300) {
                return AttendType.LATE;
            }
        }
        return AttendType.PASS;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public Crew updateAttendanceTime(List<Integer> updateTime) {
        attendanceTime = LocalDateTime.of(
                attendanceTime.getYear(), attendanceTime.getMonth(),
                attendanceTime.getDayOfMonth(), updateTime.get(0), updateTime.get(1)
        );
        this.attendType = judgeAttendType();
        return this;
    }
}
