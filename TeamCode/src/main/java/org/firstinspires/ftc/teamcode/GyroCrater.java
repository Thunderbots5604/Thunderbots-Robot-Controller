package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@Autonomous(name="Gyro Crater Only", group="Gyro")
public class GyroCrater extends GodfatherOfAllAutonomous {

    @Override
    public void runOpMode() {

        int cooldown = 1000;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        initialization();

        location = objectDetect();

        waitForStart();

        detach();
        turnOut();
        //detach finished

        //Auto Crater only
        sleep(cooldown);
        if (location == 2) {
            telemetry.addLine("right");
            telemetry.update();
            runTo(27, allPower);
            sleep(cooldown);
            turnLeft(40, allPower);
            sleep(cooldown);
            runTo(3, allPower);
        }
        else if (location == 1) {
            telemetry.addLine("middle");
            telemetry.update();

            runTo(5, allPower);
            sleep(cooldown);
            turnLeft(40, allPower);
            sleep(cooldown);
            runTo(23, allPower);
        }
        else if (location == 0){
            telemetry.addLine("left");
            telemetry.update();
            runTo(5, allPower);
            sleep(cooldown);
            turnLeft(75, allPower);
            sleep(cooldown);
            runTo(25, allPower);
            sleep(cooldown);
            turnRight(45, allPower);
            sleep(cooldown);
            runTo(7, allPower);
        }
        box1.setPower(-1);
        box2.setPower(1);
        sleep(2000);
        box1.setPower(0);
        box2.setPower(0);
    }
}