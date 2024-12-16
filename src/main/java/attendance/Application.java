package attendance;

import attendance.controller.AttendanceController;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.file.CrewAttendanceInput;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        List<Crew> inputCrews = CrewAttendanceInput.readCrews();
        Crews crews = new Crews(inputCrews);

        AttendanceController attendanceController = new AttendanceController(
                inputView, outputView, crews
        );
        attendanceController.run();
    }
}
