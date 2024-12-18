package attendance.domain;

public class AttendCount {
    private int passCount;
    private int lateCount;
    private int absenceCount;
    private String name;
    private CrewStatus status;

    public AttendCount() {
        this.passCount = 0;
        this.lateCount = 0;
        this.absenceCount = 0;
        this.status = findStatus();
    }

    public void count(AttendType attendType) {
        if (attendType == AttendType.PASS) {
            passCount++;
        }
        if (attendType == AttendType.LATE) {
            lateCount++;
        }
        if (attendType == AttendType.ABSENCE || attendType == AttendType.NONE) {
            absenceCount++;
        }
    }

    public CrewStatus findStatus() {
        int plusCount = lateCount / 3;
        if (plusCount + absenceCount > 5) {
            return CrewStatus.REMOVED;
        }
        if (absenceCount >= 3) {
            return CrewStatus.INTERVIEW;
        }
        if (absenceCount == 2) {
            return CrewStatus.WARNING;
        }
        return CrewStatus.NONE;
    }

    public int getPassCount() {
        return passCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public int getAbsenceCount() {
        return absenceCount;
    }

    public String getName() {
        return name;
    }

    public CrewStatus getStatus() {
        return status;
    }

    public boolean isWarning() {
        return !findStatus().isNone();
    }

    public void setName(String name) {
        this.name = name;
    }
}
