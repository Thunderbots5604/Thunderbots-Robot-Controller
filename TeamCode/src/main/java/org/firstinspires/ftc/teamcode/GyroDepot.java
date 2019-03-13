package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@Autonomous(name="Gyro Depot Only", group="Gyro")
public class GyroDepot extends GodfatherOfAllAutonomous {

    @Override
    public void runOpMode() {
        depotDrop();
        dropMarker();
    }

    public void depotDrop() {
        int cooldown = 500;
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
            turnLeft(55, allPower);
            sleep(cooldown);
            runTo(19, allPower);
        }
        else if (location == 1) {
            telemetry.addLine("middle");
            telemetry.update();

            runTo(5, allPower);
            sleep(cooldown);
            turnLeft(40, allPower);
            sleep(cooldown);
            runTo(30, allPower);
        }
        else if (location == 0){
            telemetry.addLine("left");
            telemetry.update();
            runTo(5, allPower);
            sleep(cooldown);
            turnLeft(82, allPower);
            sleep(cooldown);
            runTo(38, allPower);
            sleep(cooldown);
            turnRight(143, allPower);
            sleep(cooldown);
            runTo(22, allPower);
        }
    }
}