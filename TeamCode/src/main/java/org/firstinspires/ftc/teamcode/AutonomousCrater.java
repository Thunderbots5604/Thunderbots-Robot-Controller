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
            runTo(23, allPower);
            sleep(500);
            //Go Back
            runTo(-19,allPower);
            sleep(300);
            //Adjust
            turnLeft(48,allPower);
            runTo(5,allPower);
            turnLeft(53,allPower);
            runTo(48,allPower);
            // Go to Depot
            turnLeft(42.0,allPower);
            runTo(42.0,allPower);
        }
        if (location == 1) {
            //Hit Marker
            turnLeft(12, allPower);
            runTo(21,allPower);
            sleep(500);
            //Go Back
            turnLeft(5,allPower);
            runTo(-20,allPower);
            //Adjust
            turnRight(8,allPower);
            runTo(3,allPower);
            //Go to Depot
            turnLeft(66.6,allPower);
            runTo(47,allPower);
            turnLeft(50,allPower);
            runTo(32,allPower);
        }
        if (location == 0){
            //Hit Marker
            turnLeft(50, allPower);
            runTo(32,allPower);
            sleep(250);
            //Make sure it gets marker off of robot
            runTo(-15,allPower);
            sleep(250);
            turnLeft(42.0,allPower);
            runTo(25,allPower);
            //Go to Depot
            turnLeft(33.33,allPower);
            runTo(10,allPower);
            turnLeft(15,allPower);
            runTo(33.33,allPower);
        }

        telemetry.addData("Location: ", location);
        telemetry.update();
        //Drop marker
        sleep(250);
        dropMarker();
        //Run back to crater to park
        sleep(200);
        turnLeft(5,allPower);
        runTo(-45,1);
        sleep(200);
        runTo(-10,.3);
    }
}