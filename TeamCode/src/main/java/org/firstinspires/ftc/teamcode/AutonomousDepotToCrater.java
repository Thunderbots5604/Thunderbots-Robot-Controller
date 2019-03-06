package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Disabled
@Autonomous(name="Autonomous Depot-Crater", group="Autonomous Competition")
public class AutonomousDepotToCrater extends GodfatherOfAllAutonomous {

    private int location = -1;

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        initialization();

        location = objectDetect();

        waitForStart();

        detach();
        if (location == 2) {
            turnRight(45,allPower);
            runTo(31, allPower);
            sleep(500);
            turnLeft(80,allPower);
            runTo(16.6,allPower);
        }
        if (location == 1) {
            //Hit Marker
            turnLeft(18, allPower);
            runTo(18,allPower);
            sleep(250);
        }
        if (location == 0) {
            turnLeft(65, allPower);
            runTo(36,allPower);
            sleep(250);
            turnRight(180,allPower);
            runTo(22,allPower);
        }
        dropMarker();
        if (location == 2) {
            runTo(-13,allPower);
            turnRight(60,allPower);
            runTo(-22,allPower);
            turnLeft(66,allPower);
            runTo(4,allPower);
            turnLeft(60,allPower);
            runTo(50,allPower);
            turnLeft(66.6,allPower);
            runTo(30,.9);
        }
        if (location == 1) {
            //Go Back
            turnLeft(5,allPower);
            runTo(-16,allPower);
            //Adjust
            turnRight(8,allPower);
            runTo(2.5,allPower);
            //Go to Crater
            turnLeft(66.6,allPower);
            runTo(50.5,allPower);
            turnLeft(42.0,allPower);
            runTo(32,allPower);
        }
        if (location == 0) {
            runTo(-5, allPower);
            turnRight(20, allPower);
            runTo(-80, .8);
        }
    }
}