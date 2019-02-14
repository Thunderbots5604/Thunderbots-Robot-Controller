package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name="Autonomous Crater", group="Autonomous Competition")
public class AutonomousCrater extends GodfatherOfAllAutonomous {

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
            //Hit Marker
            turnRight(25,allPower);
            runTo(21, allPower);
            sleep(100);
            //Go Back
            runTo(-15,allPower);
            //Adjust
            turnLeft(46,allPower);
            runTo(2.5,allPower);
            turnLeft(66.6,allPower);
            runTo(52,allPower);
            // Go to Depot
            turnLeft(36.0,allPower);
            runTo(32,allPower);
        }
        if (location == 1) {
            //Hit Marker
            turnLeft(18, allPower);
            runTo(18,allPower);
            sleep(250);
            //Go Back
            turnLeft(5,allPower);
            runTo(-16,allPower);
            //Adjust
            turnRight(8,allPower);
            runTo(2.5,allPower);
            //Go to Depot
            turnLeft(66.6,allPower);
            runTo(50.5,allPower);
            turnLeft(42.0,allPower);
            runTo(32,allPower);
        }
        if (location == 0){
            //Hit Marker
            turnLeft(56, allPower);
            runTo(31,allPower);
            sleep(250);
            //Make sure it gets marker off of robot
            runTo(-15,allPower);
            sleep(250);
            turnLeft(40,allPower);
            runTo(29,allPower);
            //Go to Depot
            turnLeft(33.33,allPower);
            runTo(15,allPower);
            turnLeft(16.6,allPower);
            runTo(25,allPower);
        }
        //Drop marker
        sleep(100);
        dropMarker();
        //Run back to crater to park
        runTo(-66.6,1);
        runTo(-10,allPower * .8);
    }
}