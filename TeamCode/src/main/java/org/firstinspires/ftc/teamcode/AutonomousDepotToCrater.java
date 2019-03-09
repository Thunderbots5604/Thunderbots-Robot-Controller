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
            runTo(17.6,allPower);
        }
        if (location == 1) {
            //Hit Marker
            turnLeft(18, allPower);
            runTo(20,allPower);
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
            turnLeft(90,allPower);
            runTo(16,allPower);
            turnLeft(30,allPower);
            runTo(50,allPower);
        }
        if (location == 1) {
            //Go Back
            runTo(-2,allPower);
            turnLeft(90,allPower);
            runTo(5,allPower);
            //Adjust
            turnLeft(30,allPower);
            runTo(8,allPower);
            turnLeft(30,allPower);
            runTo(50,allPower);
        }
        if (location == 0) {
            runTo(-5, allPower);
            turnRight(10, allPower);
            runTo(-80, .8);
        }
        box1.setPower(-1);
        box2.setPower(1);
        sleep(2000);
        box1.setPower(1);
        box2.setPower(-1);
        sleep(2000);
        box1.setPower(0);
        box2.setPower(0);
    }
}