package frc.libs.autonomous;

import org.apache.logging.log4j.*;

public abstract class AutoStep
{
    protected final LoggingContext logger;
    private boolean _hasStarted;
    private boolean _hasCompleted;

    public AutoStep(Marker markerParent)
    {
        this.logger = new LoggingContext(markerParent, this);
        _hasStarted = false;
        _hasCompleted = false;
    }

    public final void start()
    {
        // Don't do anything if the step has already been started
        if (_hasStarted)
            return;

        logger.log("Beginning");
        _hasStarted = true;
        initialize();
    }

    public final boolean hasStarted()
    {
        return _hasStarted;
    }

    public final boolean hasCompleted()
    {
        return _hasCompleted;
    }

    public final void complete()
    {
        logger.log("Completing");
        _hasCompleted = true;
        stop();
    }

    protected abstract void initialize();

    public abstract void stop();
}