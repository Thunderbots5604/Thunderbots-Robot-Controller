package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous(name="Autonomous Depot", group="Autonomous Competition")
public class AutonomousDepot extends GodfatherOfAllAutonomous {

    private int location = -1;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        initialization();

        waitForStart();

        detach();

        location = tfodDetection(6);
        if (location == 2) {
            turnRight(10, allPower);
            runTo(25, allPower);
            turnLeft(60,allPower);
            runTo(20,allPower);
        }
        if (location == 1) {
            turnLeft(8, allPower);
            runTo(35, allPower);
        }
        if (location == 0) {
            turnLeft(56, allPower);
            runTo(41, allPower);
            turnRight(110,allPower);
            runTo(20,allPower);
        }
        dropMarker();
    }
}