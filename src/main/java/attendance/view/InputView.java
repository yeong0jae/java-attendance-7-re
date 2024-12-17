package attendance.view;

import attendance.domain.Today;
import attendance.domain.Week;
import attendance.external.TodayImpl;
import attendance.util.ErrorMessage;
import camp.nextstep.edu.missionutils.Console;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class InputView {

    private final Today today = new TodayImpl();

    public String readCommand(LocalDateTime now) {
        printInputMessage(
                "오늘은 " + now.getMonthValue() + "월 " + now.getDayOfMonth() + "일 "
                        + Week.from(now.getDayOfWeek().toString()) + "입니다. "
                        + "기능을 선택해주세요.");
        printInputMessage("1. 출석 확인");
        printInputMessage("2. 출석 수정");
        printInputMessage("3. 크루별 출석 기록 확인");
        printInputMessage("4. 제적 위험자 확인");
        printInputMessage("Q. 종료");
        return Console.readLine();
    }

    private void printInputMessage(String inputMessage) {
        System.out.println(inputMessage);
    }

    public String readNickname() {
        printInputMessage("닉네임을 입력해 주세요.");
        return Console.readLine();
    }

    public LocalDateTime readAttendanceTime() {
        printInputMessage("등교 시간을 입력해 주세요");
        List<Integer> rawTime = Arrays.stream(Console.readLine().split(":"))
                .map(Integer::parseInt)
                .toList();
        validateTime(rawTime);
        LocalDateTime now = today.getToday();
        LocalDateTime attendanceTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),
                rawTime.get(0), rawTime.get(1));
        validateNotHoliday(attendanceTime);
        validateNotOpen(attendanceTime);
        return attendanceTime;
    }

    private void validateNotOpen(LocalDateTime attendanceTime) {
        if (attendanceTime.getHour() >= 8) {
            throw new IllegalArgumentException(ErrorMessage.PREFIX + "캠퍼스 운영 시간에만 출석이 가능합니다.");
        }
    }

    private void validateNotHoliday(LocalDateTime attendanceTime) {
        List<Integer> holidays = List.of(1, 7, 8, 14, 15, 21, 22, 25, 28, 29);
        if (holidays.contains(attendanceTime.getDayOfMonth())) {
            throw new IllegalArgumentException(ErrorMessage.PREFIX +
                    attendanceTime.getMonthValue() + "월 " + attendanceTime.getDayOfMonth() + "일 "
                    + Week.from(attendanceTime.getDayOfWeek().toString()) + "은 등교일이 아닙니다.");
        }
    }

    private void validateTime(List<Integer> rawTime) {
        if (rawTime.get(0) < 0 && rawTime.get(0) > 24) {
            throw new IllegalArgumentException(ErrorMessage.PREFIX + "잘못된 형식을 입력하였습니다.");
        }
    }

    public String readNameForUpdate() {
        printInputMessage("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return Console.readLine();
    }

    public int readDateForUpdate() {
        printInputMessage("수정하려는 날짜(일)를 입력해 주세요.");
        return Integer.parseInt(Console.readLine());
    }

    public List<Integer> readChangeDate() {
        printInputMessage("언제로 변경하겠습니까?");
        List<Integer> rawTime = Arrays.stream(Console.readLine().split(":"))
                .map(Integer::parseInt)
                .toList();
        return rawTime;
    }

}