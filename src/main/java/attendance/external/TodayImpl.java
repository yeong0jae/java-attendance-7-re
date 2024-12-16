package attendance.external;

import attendance.domain.Today;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;

public class TodayImpl implements Today {

    @Override
    public LocalDateTime getToday() {
        return DateTimes.now();
    }
}
