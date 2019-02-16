package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Autonomous Crater Only", group="Autonomous Competition")
public class AutonomousCraterOnly extends GodfatherOfAllAutonomous {

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
            telemetry.addLine("right");
            telemetry.update();
            turnRight(25,allPower);
            runTo(21, allPower);
            turnLeft(30,allPower);
            runTo(7,allPower);
        }
        else if (location == 1) {
            telemetry.addLine("middle");
            telemetry.update();
            turnLeft(22, allPower);
            runTo(24,allPower);
        }
        else if (location == 0){
            telemetry.addLine("left");
            telemetry.update();
            turnLeft(65, allPower);
            runTo(33,allPower);
            turnRight(60, allPower);
            runTo(10, allPower);
        }
        box1.setPower(.8);
        box2.setPower(-.8);
        sleep(1000);
        box1.setPower(0);
        box2.setPower(0);

    }
}