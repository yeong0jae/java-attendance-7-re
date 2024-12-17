package attendance.view;

import attendance.domain.Week;
import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class InputView {

    public String readCommand() {
        LocalDateTime now = DateTimes.now();
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

    public List<Integer> readAttendanceTime() {
        printInputMessage("등교 시간을 입력해 주세요");
        return Arrays.stream(Console.readLine().split(":"))
                .map(Integer::parseInt)
                .toList();
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
