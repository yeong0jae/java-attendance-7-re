package attendance.controller;

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
        List<Crew> crews = attendanceService.findCrews(name);

        int dateForUpdate = inputView.readDateForUpdate();
        Crew crew = attendanceService.findCrewByDate(crews, dateForUpdate);
        Crew preCrew = new Crew(crew.getName(), crew.getAttendanceTime());

        List<Integer> rawDate = inputView.readChangeDate();
        attendanceService.changeAttendanceTime(crew, rawDate);

        outputView.printUpdateCrew(preCrew, crew);
    }

    private void findHistory() {
        String name = inputView.readNickname();
        List<Crew> histories = attendanceService.findCrews(name);
        outputView.printHistories(histories);
    }

    private void findDanger() {
        attendanceService.findDangerCrews();
    }

}
