package frc.libs.autonomous;

import java.util.*;

public class AutoLogger {

    private final HashSet<AutoStep> autoSteps = new HashSet<>();

    // TODO: Pass in an logger config that configures:
    //      - Where the logs go.
    //      - How the logs are formatted.
    public AutoLogger() {

    }

    public void LogStartStep(AutoStep autoStep) {
        LogInformation("Starting: " + autoStep.getClass().getSimpleName());
    }

    public void LogCompleteStep(AutoStep autoStep) {
        LogInformation("Completing: " + autoStep.getClass().getSimpleName());
    }

    public void LogInformation(String message)
    {
        System.out.println(message);
    }
}