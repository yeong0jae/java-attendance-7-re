package attendance.controller;

import static attendance.controller.RetryController.retryUntilValid;

import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.service.AttendanceService;
import attendance.util.ErrorMessage;
import attendance.view.InputView;
import attendance.view.OutputView;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceController {
    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceService attendanceService;

    public AttendanceController(InputView inputView, OutputView outputView, Crews crews) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceService = new AttendanceService(crews);
    }

    public void run() {
        String command = inputView.readCommand(DateTimes.now());
        execute(command);
    }

    private void execute(String command) {
        switch (command) {
            case "1" -> attendCrew();
            case "2" -> updateCrew();
            case "3" -> findHistory();
            case "4" -> findDanger();
            case "Q" -> {
            }
        }
        throw new IllegalArgumentException(ErrorMessage.PREFIX
                + "잘못된 형식을 입력하였습니다.");
    }

    private void attendCrew() {
        String nickName = retryUntilValid(() -> {
            String name = inputView.readNickname();
            attendanceService.isInCrew(name);
            return name;
        });
        LocalDateTime attendanceTime = retryUntilValid(inputView::readAttendanceTime);
        
        Crew crew = new Crew(nickName, attendanceTime);
        Crew attendedCrew = attendanceService.attendCrew(crew);
        outputView.printAttendResult(attendedCrew);
    }

    private void updateCrew() {
        List<Crew> crews = retryUntilValid(() -> {
            String name = inputView.readNameForUpdate();
            return attendanceService.findCrews(name);
        });

        int dateForUpdate = inputView.readDateForUpdate();
        Crew crew = attendanceService.findCrewByDate(crews, dateForUpdate);
        Crew preCrew = new Crew(crew.getName(), crew.getAttendanceTime());

        List<Integer> rawDate = inputView.readChangeDate();
        attendanceService.changeAttendanceTime(crew, rawDate);

        outputView.printUpdateCrew(preCrew, crew);
    }

    private void findHistory() {
        String name = inputView.readNickname();
        List<Crew> histories = attendanceService.findHistoryOf(name);
        outputView.printHistories(histories);
    }

    private void findDanger() {
        attendanceService.findDangerCrews();
    }

}
