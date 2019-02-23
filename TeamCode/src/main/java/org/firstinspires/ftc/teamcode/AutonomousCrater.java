package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous(name="Autonomous Crater and Depot", group="Autonomous Competition")
public class AutonomousCrater extends GodfatherOfAllAutonomous {

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
            //Hit Marker
            turnRight(27,allPower);
            runTo(21, allPower);
            sleep(100);
            //Go Back
            runTo(-15,allPower);
            //Adjust
            turnLeft(46,allPower);
            runTo(2.5,allPower);
            turnLeft(69,allPower);
            runTo(42.0,allPower);
            // Go to Depot
            turnLeft(50,allPower);
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
            turnLeft(69,allPower);
            runTo(50,allPower);
            turnLeft(45.0,allPower);
            runTo(30.5,allPower);
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
            runTo(25,allPower);
            //Go to Depot
            turnLeft(33,allPower);
            runTo(13,allPower);
            turnLeft(27,allPower);
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