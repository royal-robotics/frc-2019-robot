package frc.libs.autonomous;

public abstract class AutoStep
{
    protected final AutoLogger logger;
    private boolean _hasStarted;
    private boolean _hasCompleted;

    public AutoStep(AutoLogger logger)
    {
        this.logger = logger;
        _hasStarted = false;
        _hasCompleted = false;
    }

    public final void start()
    {
        // Don't do anything if the step has already been started
        if (_hasStarted)
            return;

        logger.LogStartStep(this);
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
        logger.LogCompleteStep(this);
        _hasCompleted = true;
        stop();
    }

    protected abstract void initialize();

    public abstract void stop();
}