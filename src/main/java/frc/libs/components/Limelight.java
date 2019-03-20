package frc.libs.components;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {
    private NetworkTable _table;

    public Limelight() {
        _table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public boolean hasTarget() {
        return _table.getEntry("tv").getNumber(0.0).equals(1.0);
    }

    public double xTarget() {
        return _table.getEntry("tx").getNumber(0.0).doubleValue();
    }

    public double yTarget() {
        return _table.getEntry("ty").getNumber(0.0).doubleValue();
    }

    public void setPipeline(int pipeline) {
        _table.getEntry("pipeline").setNumber(pipeline);
    }
}