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

        int cooldown = 2500;

        detach();
        sleep(cooldown);
        if (location == 2) {
            telemetry.addLine("right");
            telemetry.update();
            //Going to right side to scan 2 blocks on right
            sleep(cooldown);
            runTo(7,allPower);
            sleep(cooldown);
            turnLeft(20, allPower);
            sleep(cooldown);
            runTo(21, allPower);
            sleep(cooldown);
            turnLeft(30,allPower);
            sleep(cooldown);
            runTo(7,allPower);
        }
        else if (location == 1) {
            telemetry.addLine("middle");
            telemetry.update();
            sleep(cooldown);
            turnLeft(30, allPower);
            sleep(cooldown);
            runTo(21, allPower);
            sleep(cooldown);
            runTo(7,allPower);
        }
        else if (location == 0){
            telemetry.addLine("left");
            telemetry.update();
            sleep(cooldown);
            turnLeft(30, allPower);
            sleep(cooldown);
            runTo(30, allPower);
        }
        box1.setPower(.8);
        box2.setPower(-.8);
        sleep(2000);
        box1.setPower(0);
        box2.setPower(0);

    }
}