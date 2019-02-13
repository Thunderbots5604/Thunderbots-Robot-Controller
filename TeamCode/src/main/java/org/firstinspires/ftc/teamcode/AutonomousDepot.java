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
            turnRight(25,allPower);
            runTo(31, allPower);
            sleep(500);
            turnLeft(60,allPower);
            runTo(20,allPower);
        }
        if (location == 1) {
            turnLeft(12, allPower);
            runTo(21,allPower);
            sleep(500);
        }
        if (location == 0) {
            turnLeft(50, allPower);
            runTo(32,allPower);
            sleep(250);
            turnRight(110,allPower);
            runTo(20,allPower);
        }
        dropMarker();
    }
}