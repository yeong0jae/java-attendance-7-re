package attendance.domain;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public enum Calender {
    WEEKENDS(List.of(1, 7, 8, 14, 15, 21, 22, 25, 28, 29)),
    WEEKDAYS(List.of(2, 3, 4, 5, 6, 9, 10, 11, 12, 13, 16, 17, 18, 19, 20, 23, 24, 26, 27, 30, 31));

    private final List<Integer> days;

    Calender(List<Integer> days) {
        this.days = days;
    }

    public static boolean isWeekday(LocalDateTime attendanceTime) {
        return WEEKDAYS.days.contains(attendanceTime.getDayOfMonth());
    }

    public List<Integer> getDays() {
        return days;
    }

    public static List<Integer> getWeekdaysToToday() {
        List<Integer> weekdays = new ArrayList<>();
        LocalDateTime today = LocalDateTime.of(
                DateTimes.now().getYear(), DateTimes.now().getMonth(), DateTimes.now().getDayOfMonth() - 1,
                0, 0);

        for (int day = 1; day <= today.getDayOfMonth(); day++) {
            if (WEEKDAYS.days.contains(day)) {
                weekdays.add(day);
            }
        }
        return weekdays;
    }
}
