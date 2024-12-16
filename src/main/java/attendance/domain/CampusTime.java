package attendance.domain;

public enum CampusTime {
    EDUCATE1(13, 18),
    EDUCATE2(10, 18),

    OPEN(8, 23);

    private final int startTime;
    private final int endTime;

    CampusTime(int startTime, int endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
