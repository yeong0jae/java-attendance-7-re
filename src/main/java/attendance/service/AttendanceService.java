package attendance.service;

import attendance.domain.Crew;
import attendance.domain.Crews;
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
}
