package attendance.domain;

public enum CrewStatus {
    REMOVED("제적", 3),
    INTERVIEW("면담", 2),
    WARNING("경고", 1),
    NONE("", 0);

    private final String status;
    private final int priority;

    CrewStatus(String status, int priority) {
        this.status = status;
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isNone() {
        return this == NONE;
    }
}
