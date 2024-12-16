package attendance.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Crew {
    private final String name;
    private LocalDateTime attendanceTime;
    private AttendType attendType;

    public Crew(String name, LocalDateTime attendanceTime) {
        this.name = name;
        this.attendanceTime = attendanceTime;
        this.attendType = judgeAttendType();
    }

    public AttendType getAttendType() {
        return attendType;
    }

    private AttendType judgeAttendType() {
        boolean isMonday = Week.isMonday(attendanceTime);
        if (isMonday) {
            LocalDateTime shouldTime = LocalDateTime.of(
                    attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDayOfMonth(),
                    13, 0);
            return getLate(shouldTime);
        }
        LocalDateTime shouldTime = LocalDateTime.of(
                attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDayOfMonth(),
                10, 0);
        return getLate(shouldTime);
    }

    private AttendType getLate(LocalDateTime shouldTime) {
        if (shouldTime.isBefore(attendanceTime)) {
            if (Duration.between(shouldTime, attendanceTime).getSeconds() > 1800) {
                return AttendType.ABSENCE;
            }
            if (Duration.between(shouldTime, attendanceTime).getSeconds() > 300) {
                return AttendType.LATE;
            }
            return AttendType.PASS;
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
        return new Crew(name, attendanceTime);
    }
}
