package attendance.view;

import attendance.domain.Crew;
import attendance.domain.Week;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OutputView {

    public void printAttendResult(Crew attendedCrew) {
        LocalDateTime attendTime = attendedCrew.getAttendanceTime();

        print(attendTime.getMonthValue() + "월 " + attendTime.getDayOfMonth() + "일 "
                + Week.from(attendTime.getDayOfWeek().toString()) + " "
                + String.format("%02d", attendTime.getHour()) + ":" + String.format("%02d", attendTime.getMinute())
                + " (" + attendedCrew.getAttendType().getAttendType() + ")");
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

    public void printUpdateCrew(Crew preCrew, Crew crew) {
        LocalDateTime attendTime = preCrew.getAttendanceTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm");
        String minute = attendTime.format(formatter);

        System.out.print(attendTime.getMonthValue() + "월 " + attendTime.getDayOfMonth() + "일 "
                + Week.from(attendTime.getDayOfWeek().toString()) + " "
                + attendTime.getHour() + ":" + minute
                + " (" + preCrew.getAttendType().getAttendType() + ") ");

        LocalDateTime newTime = crew.getAttendanceTime();
        String newMinute = newTime.format(formatter);
        System.out.println(
                "-> " + newTime.getHour() + ":" + newMinute + " (" + crew.getAttendType().getAttendType() + ")");
    }
}
