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
            turnRight(120,allPower);
            runTo(20,allPower);
        }
        dropMarker();
        if (location == 2) {
            runTo(-14,allPower);
            turnRight(60,allPower);
            runTo(-18,allPower);
            turnLeft(10,allPower);
        }
        if (location == 1) {
            runTo(-28,allPower);
            turnRight(8,allPower);
        }
        if (location == 0) {
            runTo(-5, allPower);
            turnRight(15, allPower);
            runTo(-85, .8);
        }
        if(location != 0){
            turnLeft(60,allPower);
            runTo(7,allPower);
            turnLeft(30,allPower);
            runTo(10,allPower);
            turnLeft(20,allPower);
            runTo(75,.8);
        }
    }
}