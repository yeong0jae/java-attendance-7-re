package attendance.domain;

public class AttendCount {
    private int passCount;
    private int lateCount;
    private int absenceCount;

    public AttendCount() {
        this.passCount = 0;
        this.lateCount = 0;
        this.absenceCount = 0;
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

    public String getStatus() {
        int plusCount = lateCount / 3;
        if (plusCount + absenceCount > 5) {
            return "제적 대상자";
        }
        if (absenceCount >= 3) {
            return "면담 대상자";
        }
        if (absenceCount == 2) {
            return "경고 대상자";
        }
        return "";
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
}
