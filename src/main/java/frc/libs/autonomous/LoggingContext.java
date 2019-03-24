package frc.libs.autonomous;

import org.apache.logging.log4j.*;
import frc.libs.utils.ForceTriggerPolicy;

public class LoggingContext {
    //private static Logger logger = LogManager.getLogger("AutoRoutine");

    public final Marker marker;
    private final AutoStep autoStep;

    public LoggingContext(Marker markerParent, AutoStep autoStep) {
        String contextName = autoStep.getClass().getSimpleName();
        Marker marker = getUniqueMarker(contextName);
        if (marker != null)
            marker.setParents(markerParent);

        this.marker = marker;
        this.autoStep = autoStep;
    }

    public static void init() {
        ForceTriggerPolicy.forceRollover();
        MarkerManager.clear();
        ThreadContext.clearAll();
    }

    // To allow eager loading of this class
    public static void poke() { }

    public void log(String message) {
        // Push autoStep state onto the log
        // logger.log(Level.INFO, marker, message);
    }

    private static Marker getUniqueMarker(String markerName) {
        String markerNameFirst = markerName + "_1";
        if (MarkerManager.exists(markerNameFirst))
        {
            for (int i = 2; true; i++)
            {
                String markerNameNumbered = markerName + "_" + i;
                if (!MarkerManager.exists(markerNameNumbered))
                    return MarkerManager.getMarker(markerNameNumbered);
            }
        }
        else
        {
            return MarkerManager.getMarker(markerNameFirst);
        }
    }
}