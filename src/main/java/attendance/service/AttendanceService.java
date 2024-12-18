package attendance.service;

import attendance.domain.AttendCount;
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

    public List<Crew> findCrewHistories(String name) {
        return crews.findCrewsByName(name);
    }

    public Crew changeAttendanceTime(Crew crew, List<Integer> updateTime) {
        return crew.updateAttendanceTime(updateTime);
    }

    public Crew findCrewByDate(String name, int dateForUpdate) {
        return crews.findCrewByDate(name, dateForUpdate);
    }

    public LocalDateTime parseAttendanceTime(List<Integer> rawAttendanceTime) {
        validateTime(rawAttendanceTime);
        LocalDateTime now = DateTimes.now();
        return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),
                rawAttendanceTime.get(0), rawAttendanceTime.get(1));
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

    public AttendCount findCrewWarnings(List<Crew> crewHistories) {
        AttendCount attendCount = new AttendCount();

        crewHistories.forEach(crewHistory -> {
            attendCount.count(crewHistory.getAttendType());
            attendCount.setName(crewHistory.getName());
        });
        return attendCount;
    }

    public List<String> findUniqueCrewNames() {
        return crews.findUniqueCrewNames();
    }

    public AttendCount findWarningInfo(String name) {
        List<Crew> crewHistories = crews.findCrewsByName(name);
        return findCrewWarnings(crewHistories);
    }

    public List<AttendCount> findCrewsWarnings(List<String> uniqueCrewNames) {
        return uniqueCrewNames.stream()
                .map(this::findWarningInfo)
                .sorted(this::compareAttendCounts)
                .toList();
    }

    private int compareAttendCounts(AttendCount count1, AttendCount count2) {
        int statusComparison = compareByStatus(count1, count2);
        if (statusComparison != 0) {
            return statusComparison;
        }

        int absenceComparison = compareByAbsence(count1, count2);
        if (absenceComparison != 0) {
            return absenceComparison;
        }

        int totalAbsenceComparison = compareByTotalAbsence(count1, count2);
        if (totalAbsenceComparison != 0) {
            return totalAbsenceComparison;
        }

        return compareByName(count1, count2);
    }

    private int compareByStatus(AttendCount count1, AttendCount count2) {
        return Integer.compare(count2.getStatus().getPriority(), count1.getStatus().getPriority());
    }

    private int compareByAbsence(AttendCount count1, AttendCount count2) {
        return Integer.compare(count2.getAbsenceCount(), count1.getAbsenceCount());
    }

    private int compareByTotalAbsence(AttendCount count1, AttendCount count2) {
        int totalAbsence1 = count1.getAbsenceCount() + count1.getLateCount();
        int totalAbsence2 = count2.getAbsenceCount() + count2.getLateCount();
        return Integer.compare(totalAbsence2, totalAbsence1);
    }

    private int compareByName(AttendCount count1, AttendCount count2) {
        return count1.getName().compareTo(count2.getName());
    }
    
}
