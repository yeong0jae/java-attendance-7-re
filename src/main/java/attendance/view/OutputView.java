package attendance.view;

import attendance.domain.AttendCount;
import attendance.domain.AttendType;
import attendance.domain.Crew;
import attendance.domain.Week;
import java.time.LocalDateTime;
import java.util.List;

public class OutputView {

    public void printAttendResult(Crew attendedCrew) {
        LocalDateTime attendTime = attendedCrew.getAttendanceTime();

        if (!AttendType.isNone(attendedCrew.getAttendType())) {
            print(attendTime.getMonthValue() + "월 "
                    + String.format("%02d", attendTime.getDayOfMonth()) + "일 "
                    + Week.from(attendTime.getDayOfWeek().toString()) + " "
                    + String.format("%02d", attendTime.getHour()) + ":"
                    + String.format("%02d", attendTime.getMinute())
                    + " (" + attendedCrew.getAttendType().getAttendType() + ") ");
        } else {
            print(attendTime.getMonthValue() + "월 "
                    + attendTime.getDayOfMonth() + "일 "
                    + Week.from(attendTime.getDayOfWeek().toString()) + " "
                    + "--" + ":"
                    + "--"
                    + " (" + attendedCrew.getAttendType().getAttendType() + ") ");
        }
    }

    public void printAttendResultNoLineSeparate(Crew attendedCrew) {
        LocalDateTime attendTime = attendedCrew.getAttendanceTime();

        System.out.print(attendTime.getMonthValue() + "월 "
                + String.format("%02d", attendTime.getDayOfMonth()) + "일 "
                + Week.from(attendTime.getDayOfWeek().toString()) + " "
                + String.format("%02d", attendTime.getHour()) + ":"
                + String.format("%02d", attendTime.getMinute())
                + " (" + attendedCrew.getAttendType().getAttendType() + ") ");
    }

    public void printHistories(List<Crew> crewHistories) {
        crewHistories.forEach(this::printAttendResult);
    }

    private void print(String output) {
        System.out.println(output);
    }

    public void printUpdateTime(Crew updatedCrew) {
        LocalDateTime updatedTime = updatedCrew.getAttendanceTime();

        System.out.println(
                "-> " + String.format("%02d", updatedTime.getHour()) + ":" + String.format("%02d",
                        updatedTime.getMinute())
                        + " (" + updatedCrew.getAttendType().getAttendType() + ")" + " 수정 완료!");
    }

    public void printAttendCount(AttendCount attendCount) {
        System.out.println();
        System.out.println("출석: " + attendCount.getPassCount() + "회");
        System.out.println("지각: " + attendCount.getLateCount() + "회");
        System.out.println("결석: " + attendCount.getAbsenceCount() + "회");
        System.out.println();
        String status = attendCount.getStatus();
        if (!status.isEmpty()) {
            System.out.println(status);
        }
        System.out.println();
    }
}
