package frc.libs.motionprofile;

import java.io.*;
import java.time.Duration;
import java.util.*;

import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters.UriConverter;

public class CsvFileMotionProfile implements IMotionProfile {

    List<Segment> segments = new ArrayList<>();

    public CsvFileMotionProfile(String filename) throws NumberFormatException, IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader br = new BufferedReader(fileReader);

        // Skip the csv headers line
        br.readLine();

        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");

            Duration time = durationFromSeconds(Double.parseDouble(values[0]));
            double x = Double.parseDouble(values[1]);
            double y = Double.parseDouble(values[2]);
            double position = Double.parseDouble(values[3]);
            double velocity = Double.parseDouble(values[4]);
            double acceleration = Double.parseDouble(values[5]);
            double jerk = Double.parseDouble(values[6]);
            double heading = Double.parseDouble(values[7]);

            Segment segment = new Segment(time, x, y, position, velocity, acceleration, jerk, heading);
            segments.add(segment);
        }

        br.close();
    }

    @Override
    public Duration duration() {

        Duration firstTime = segments.get(0).time;
        double secondsPerSegment = secondsFromDuration(firstTime);

        double durationSeconds = secondsPerSegment * segments.size();
        return durationFromSeconds(durationSeconds);
    }

    @Override
    public Segment getSegment(Duration index) {
        double durationSeconds = secondsFromDuration(duration());
        double indexSeconds = secondsFromDuration(index);

        if (indexSeconds > durationSeconds)
            return null;

        double indexPercent = indexSeconds / durationSeconds;

        int discreteIndex = (int)Math.round(indexPercent * segments.size());
        return segments.get(discreteIndex);
    }

    private static Duration durationFromSeconds(double seconds) {
        final long secondsOnly = (long)seconds;
        final long nanos = (long)((seconds - secondsOnly) * 1_000_000_000);
        return Duration.ofSeconds(secondsOnly, nanos);
    }

    private static double secondsFromDuration(Duration duration) {
        double seconds = duration.getSeconds();
        seconds += duration.getNano() / 1_000_000_000.0;
        return seconds;
    }
}