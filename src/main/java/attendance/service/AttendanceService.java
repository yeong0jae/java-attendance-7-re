package attendance.service;

import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.Week;
import attendance.util.ErrorMessage;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceService {

    private Crews crews;

    public AttendanceService(Crews crews) {
        this.crews = crews;
    }

    public Crew attendCrew(String name, LocalDateTime rawAttendanceTime) {
        Crew crew = new Crew(name, rawAttendanceTime);
        return crews.addCrew(crew);
    }

    public void isInCrew(String name) {
        crews.isIn(name);
    }

    public List<Crew> findCrews(String name) {
        return crews.findCrews(name);
    }

    public Crew changeAttendanceTime(Crew crew, List<Integer> updateTime) {
        return crew.updateAttendanceTime(updateTime);
    }

    public Crew findCrewByDate(List<Crew> crews, int dateForUpdate) {
        return crews.stream()
                .filter(crew -> crew.getAttendanceTime().getDayOfMonth() == dateForUpdate)
                .findFirst()
                .get();
    }

    public void findDangerCrews() {
        List<String> crewNames = crews.getCrews().stream()
                .map(Crew::getName)
                .distinct()
                .toList();
    }

    public LocalDateTime parseAttendanceTime(List<Integer> rawAttendanceTime) {
        validateTime(rawAttendanceTime);

        LocalDateTime now = DateTimes.now();
        LocalDateTime attendanceTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),
                rawAttendanceTime.get(0), rawAttendanceTime.get(1));

        validateNotOpen(attendanceTime);
        return attendanceTime;
    }

    private void validateTime(List<Integer> rawAttendanceTime) {
        if (rawAttendanceTime.get(0) < 0 || rawAttendanceTime.get(0) > 24) {
            throw new IllegalArgumentException(ErrorMessage.PREFIX + "잘못된 형식을 입력하였습니다.");
        }
        if (rawAttendanceTime.get(1) < 0 || rawAttendanceTime.get(1) > 59) {
            throw new IllegalArgumentException(ErrorMessage.PREFIX + "잘못된 형식을 입력하였습니다.");
        }
    }


    public void isHoliday() {
        LocalDateTime now = DateTimes.now();

        List<Integer> holidays = List.of(1, 7, 8, 14, 15, 21, 22, 25, 28, 29);
        if (holidays.contains(now.getDayOfMonth())) {
            throw new IllegalArgumentException(ErrorMessage.PREFIX +
                    now.getMonthValue() + "월 " + now.getDayOfMonth() + "일 "
                    + Week.from(now.getDayOfWeek().toString()) + "은 등교일이 아닙니다.");
        }
    }
}
