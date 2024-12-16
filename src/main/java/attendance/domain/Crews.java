package attendance.domain;

import java.util.List;

public class Crews {
    private List<Crew> crews;

    public Crews(List<Crew> crews) {
        this.crews = crews;
    }

    public Crew addCrew(Crew crew) {
        crews.add(crew);
        return crew;
    }

    public List<Crew> getCrews() {
        return crews;
    }
}
