package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Autonomous Depot-Crater", group="Autonomous Competition")
public class AutonomousDepotToCrater extends GodfatherOfAllAutonomous {

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
        if (location == 2) {
            runTo(-14,allPower);
            turnRight(60,allPower);
            runTo(-18,allPower);
            turnLeft(10,allPower);
            turnLeft(60,allPower);
            runTo(7,allPower);
            turnLeft(30,allPower);
            runTo(10,allPower);
            turnLeft(20,allPower);
            runTo(60,.9);
        }
        if (location == 1) {
            runTo(-28,allPower);
            turnRight(8,allPower);
            turnLeft(60,allPower);
            runTo(7,allPower);
            turnLeft(30,allPower);
            runTo(10,allPower);
            turnLeft(20,allPower);
            runTo(60,.9);
        }
        if (location == 0) {
            runTo(-5, allPower);
            turnRight(15, allPower);
            runTo(-85, .8);
        }
    }
}