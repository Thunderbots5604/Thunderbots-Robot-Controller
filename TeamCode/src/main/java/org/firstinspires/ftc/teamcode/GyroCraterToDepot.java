package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@Autonomous(name="Gyro Crater-Depot", group="Gyro")
public class GyroCraterToDepot extends GodfatherOfAllAutonomous {

    @Override
    public void runOpMode() {

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
            runTo(21, allPower);
            sleep(cooldown);
            runTo(-18, allPower);
            sleep(cooldown);
            turnLeft(90, allPower);
            sleep(cooldown);
            runTo(39, allPower);
            sleep(cooldown);
            turnLeft(70, allPower);
            sleep(cooldown);
            runTo(50, allPower);
            dropMarker();
            runTo(-80, allPower);
        }
        else if (location == 1) {
            telemetry.addLine("middle");
            telemetry.update();

            runTo(5, allPower);
            sleep(cooldown);
            turnLeft(40, allPower);
            sleep(cooldown);
            runTo(15, allPower);
            sleep(cooldown);
            runTo(-15, allPower);

            sleep(cooldown);
            turnLeft(60, allPower);
            sleep(cooldown);
            runTo(40, allPower);
            sleep(cooldown);
            turnLeft(70, allPower);
            sleep(cooldown);
            runTo(50, allPower);
            dropMarker();
            runTo(-80, allPower);
        }
        else if (location == 0){
            telemetry.addLine("left");
            telemetry.update();
            runTo(5, allPower);
            sleep(cooldown);
            turnLeft(77, allPower);
            sleep(cooldown);
            runTo(20, allPower);
            sleep(cooldown);
            turnLeft(45, allPower);
            sleep(cooldown);
            runTo(15, allPower);
            turnLeft(38, allPower);
            sleep(cooldown);
            runTo(50, allPower);
            sleep(cooldown);
            dropMarker();
            runTo(-80, allPower);
        }
    }
}