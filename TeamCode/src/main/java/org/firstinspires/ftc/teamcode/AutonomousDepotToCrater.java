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

        location = tfodDetection(4);
        if (location == 2) {
            turnRight(25,allPower);
            runTo(31, allPower);
            sleep(500);
            turnLeft(95,allPower);
            runTo(20,allPower);
        }
        if (location == 1) {
            //Hit Marker
            turnLeft(18, allPower);
            runTo(18,allPower);
            sleep(250);
        }
        if (location == 0) {
            turnLeft(55, allPower);
            runTo(35,allPower);
            sleep(250);
            turnRight(150,allPower);
            runTo(22,allPower);
        }
        dropMarker();
        if (location == 2) {
            runTo(-14,allPower);
            turnRight(60,allPower);
            runTo(-18,allPower);
            turnLeft(66,allPower);
            runTo(7,allPower);
            turnLeft(40,allPower);
            runTo(20,allPower);
            turnLeft(20,allPower);
            runTo(50,.9);
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