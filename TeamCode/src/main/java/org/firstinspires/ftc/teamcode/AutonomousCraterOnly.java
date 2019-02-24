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

        location = objectDetect();

        waitForStart();

        int cooldown = 1000;

        detach();
        sleep(cooldown);
        if (location == 2) {
            telemetry.addLine("right");
            telemetry.update();
            runTo(27, allPower);
            sleep(cooldown);
            turnLeft(40, allPower);
            sleep(cooldown);
            runTo(7, allPower);
        }
        else if (location == 1) {
            telemetry.addLine("middle");
            telemetry.update();

            runTo(5, allPower);
            sleep(cooldown);
            turnLeft(32, allPower);
            sleep(cooldown);
            runTo(23, allPower);
        }
        else if (location == 0){
            telemetry.addLine("left");
            telemetry.update();
            runTo(5, allPower);
            sleep(cooldown);
            turnLeft(82, allPower);
            sleep(cooldown);
            runTo(25, allPower);
            sleep(cooldown);
            turnRight(45, allPower);
            sleep(cooldown);
            runTo(7, allPower);
        }
        box1.setPower(-1);
        box2.setPower(1);
        sleep(cooldown);
        box1.setPower(0);
        box2.setPower(0);

    }
}