package attendance.service;

import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.util.ErrorMessage;
import java.util.List;

public class AttendanceService {

    private Crews crews;

    public AttendanceService(Crews crews) {
        this.crews = crews;
    }

    public Crew attendCrew(Crew crew) {
        return crews.addCrew(crew);
    }

    public void isInCrew(String name) {
        if (crews.getCrews().stream()
                .noneMatch(crew -> crew.getName().equals(name))) {
            throw new IllegalArgumentException(ErrorMessage.PREFIX + "등록되지 않은 닉네임입니다.");
        }
    }

    public List<Crew> findHistoryOf(String name) {
        return crews.getCrews().stream()
                .filter(crew -> crew.getName().equals(name))
                .toList();
    }

    public List<Crew> findCrews(String name) {
        return crews.getCrews().stream()
                .filter(crew -> crew.getName().equals(name))
                .toList();
    }

    public Crew findCrewByDate(List<Crew> crews, int dateForUpdate) {
        return crews.stream()
                .filter(crew -> crew.getAttendanceTime().getDayOfMonth() == dateForUpdate)
                .findFirst()
                .get();
    }

    public Crew changeAttendanceTime(Crew crew, List<Integer> updateTime) {
        return crew.updateAttendanceTime(updateTime);
    }

    public void findDangerCrews() {
        List<String> crewNames = crews.getCrews().stream()
                .map(Crew::getName)
                .distinct()
                .toList();
    }
}
