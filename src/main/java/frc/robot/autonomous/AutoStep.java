package frc.robot.autonomous;

public abstract class AutoStep
{
    private boolean _hasStarted;
    private boolean _hasCompleted;

    public AutoStep()
    {
        _hasStarted = false;
        _hasCompleted = false;
    }

    public final void start()
    {
        // Don't do anything if the step has already been started
        if (_hasStarted)
            return;

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

    public final void Complete()
    {
        _hasCompleted = true;
        stop();
    }

    protected abstract void initialize();

    protected abstract void stop();
}