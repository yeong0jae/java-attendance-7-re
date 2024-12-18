package attendance.view;

import attendance.domain.Crew;
import attendance.domain.Week;
import java.time.LocalDateTime;
import java.util.List;

public class OutputView {

    public void printAttendResult(Crew attendedCrew) {
        LocalDateTime attendTime = attendedCrew.getAttendanceTime();

        print(attendTime.getMonthValue() + "월 " + attendTime.getDayOfMonth() + "일 "
                + Week.from(attendTime.getDayOfWeek().toString()) + " "
                + String.format("%02d", attendTime.getHour()) + ":" + String.format("%02d", attendTime.getMinute())
                + " (" + attendedCrew.getAttendType().getAttendType() + ")");
    }

    public void printAttendResultNoLineSeparate(Crew attendedCrew) {
        LocalDateTime attendTime = attendedCrew.getAttendanceTime();

        System.out.print(attendTime.getMonthValue() + "월 " + attendTime.getDayOfMonth() + "일 "
                + Week.from(attendTime.getDayOfWeek().toString()) + " "
                + String.format("%02d", attendTime.getHour()) + ":" + String.format("%02d", attendTime.getMinute())
                + " (" + attendedCrew.getAttendType().getAttendType() + ") ");
    }

    public void printHistories(List<Crew> histories) {
        histories.forEach(this::printAttendResult);
    }

    private void print(String output) {
        System.out.println(output);
    }

    private void lineSeparate() {
        System.out.println();
    }

    public void printUpdateTime(Crew updatedCrew) {
        LocalDateTime updatedTime = updatedCrew.getAttendanceTime();

        System.out.println(
                "-> " + String.format("%02d", updatedTime.getHour()) + ":" + String.format("%02d",
                        updatedTime.getMinute())
                        + " (" + updatedCrew.getAttendType().getAttendType() + ")");
    }
}
