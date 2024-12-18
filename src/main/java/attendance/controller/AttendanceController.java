package attendance.controller;

import attendance.domain.AttendCount;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.service.AttendanceService;
import attendance.view.InputView;
import attendance.view.OutputView;
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
        String command;
        do {
            command = inputView.readCommand();
            execute(command);
        } while (!command.equals("Q"));
    }

    private void execute(String command) {
        switch (command) {
            case "1" -> attendCrew();
            case "2" -> updateCrew();
            case "3" -> findHistory();
            case "4" -> findDanger();
        }
    }

    private void attendCrew() {
        attendanceService.isHoliday();

        String name = inputView.readNickname();
        attendanceService.isInCrew(name);

        List<Integer> rawAttendanceTime = inputView.readAttendanceTime();
        LocalDateTime attendanceTime = attendanceService.parseAttendanceTime(rawAttendanceTime);

        Crew attendedCrew = attendanceService.attendCrew(name, attendanceTime);

        outputView.printAttendResult(attendedCrew);
    }

    private void updateCrew() {
        String name = inputView.readNameForUpdate();
        attendanceService.isInCrew(name);

        int dateForUpdate = inputView.readDateForUpdate();
        List<Integer> rawChangeDate = inputView.readChangeDate();

        Crew crew = attendanceService.findCrewByDate(name, dateForUpdate);
        outputView.printAttendResultNoLineSeparate(crew);

        Crew updatedCrew = attendanceService.changeAttendanceTime(crew, rawChangeDate);
        outputView.printUpdateTime(updatedCrew);
    }

    private void findHistory() {
        String name = inputView.readNickname();
        List<Crew> crewHistories = attendanceService.findCrewHistories(name);

        outputView.printHistories(crewHistories);

        AttendCount attendCount = attendanceService.findCrewWarnings(crewHistories);
        outputView.printAttendCount(attendCount);
    }

    private void findDanger() {
        attendanceService.findDangerCrews();
    }

}
