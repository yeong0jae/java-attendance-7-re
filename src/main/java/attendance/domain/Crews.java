package attendance.domain;

import attendance.util.ErrorMessage;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class Crews {
    private List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = crews;
        addAbsentHistories();
        sortCrews();
    }

    private void sortCrews() {
        crews.sort(Comparator.comparing(Crew::getAttendanceTime));
    }

    private void addAbsentHistories() {
        List<String> uniqueCrewNames = findUniqueCrewNames();

        uniqueCrewNames.forEach(uniqueCrewName -> {
            List<Crew> crewHistories = findCrewsByName(uniqueCrewName);
            addAbsentHistory(crewHistories);
        });
    }

    private void addAbsentHistory(List<Crew> crewHistories) {
        List<Integer> weekdays = Calender.getWeekdaysToToday();

        weekdays.stream()
                .filter(weekday -> crewHistories.stream()
                        .noneMatch(crewHistory -> crewHistory.getAttendanceTime().getDayOfMonth() == weekday))
                .forEach(weekday -> {
                    Crew absentCrew = new Crew(
                            crewHistories.getFirst().getName(),
                            LocalDateTime.of(2024, 12, weekday, 0, 0),
                            AttendType.NONE
                    );
                    crews.add(absentCrew);
                });
    }

    private List<String> findUniqueCrewNames() {
        return crews.stream()
                .map(Crew::getName)
                .distinct()
                .toList();
    }

    public Crew addCrew(Crew crew) {
        crews.add(crew);
        crews.sort(Comparator.comparing(Crew::getAttendanceTime));
        return crew;
    }

    public List<Crew> getCrews() {
        return crews;
    }

    public void isIn(String name) {
        if (crews.stream()
                .noneMatch(crew -> crew.getName().equals(name))) {
            throw new IllegalArgumentException(ErrorMessage.PREFIX + "등록되지 않은 닉네임입니다.");
        }
    }

    public List<Crew> findCrewsByName(String name) {
        return crews.stream()
                .filter(crew -> crew.getName().equals(name))
                .toList();
    }

    public Crew findCrewByDate(String name, int dateForUpdate) {
        return crews.stream()
                .filter(crew -> crew.getName().equals(name))
                .filter(crew -> crew.getAttendanceTime().getDayOfMonth() == dateForUpdate)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.PREFIX + "해당 날짜에 출석한 기록이 없습니다."));
    }

    public boolean isNotIn(Crew crew) {
        return !crews.contains(crew);
    }
}
