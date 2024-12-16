package attendance.file;

import attendance.domain.Crew;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrewAttendanceInput {
    private static final String ATTENDANCES_FILE_PATH = "src/main/resources/attendances.csv";

    public static List<Crew> readCrews() {
        List<String> lines = readLines();

        List<Crew> crews = new ArrayList<>();
        for (String line : lines) {
            Crew crew = parseCrew(line);
            crews.add(crew);
        }
        return crews;
    }

    private static List<String> readLines() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(ATTENDANCES_FILE_PATH))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static Crew parseCrew(String line) {
        List<String> rawCrew = Arrays.stream(line.split(",")).toList();

        String name = rawCrew.get(0);
        String parseTime = rawCrew.get(1).replace(' ', 'T');
        LocalDateTime localDateTime = LocalDateTime.parse(parseTime);

        return new Crew(name, localDateTime);
    }
}
