package attendance.domain;

import attendance.util.ErrorMessage;
import java.util.Comparator;
import java.util.List;

public class Crews {
    private List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = crews;
        crews.sort(Comparator.comparing(Crew::getAttendanceTime));
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

    public List<Crew> findCrews(String name) {
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
}
