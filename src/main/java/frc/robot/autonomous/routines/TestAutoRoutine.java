package frc.robot.autonomous.routines;

import java.util.*;
import frc.libs.autonomous.*;
import frc.libs.autonomous.buildingblocks.*;
import frc.robot.Robot;
import frc.robot.autonomous.steps.*;

public class TestAutoRoutine extends AutoRoutine {
    private final Robot robot;

    public TestAutoRoutine(Robot robot) {
        super();

        this.robot = robot;
    }

    @Override
    protected List<AutoStep> createRoutine() {
        ArrayList<AutoStep> list_of_steps = new ArrayList<AutoStep>();

        return list_of_steps;
    }
}