package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
//Completely untested
@Autonomous(name="Gyro Depot-Crater", group="Gyro")
public class GyroDepotToCrater extends GyroDepot {

    @Override
    public void runOpMode() {

        int cooldown = 500;

        depotDrop();

        sleep(cooldown);

        if (location == 2) {
            telemetry.addLine("right");
            telemetry.update();
            dropMarker();
            runTo(10, allPower);
            sleep(cooldown);
            turnRight(135, allPower);
            sleep(cooldown);
            runTo(-80, allPower);

        }
        else if (location == 1) {
            telemetry.addLine("middle");
            telemetry.update();
            runTo(10, allPower);
            dropMarker();
            sleep(cooldown);
            turnRight(75, allPower);
            sleep(cooldown);
            runTo(-80, allPower);

        }
        else if (location == 0){
            telemetry.addLine("left");
            telemetry.update();

            turnRight(15, allPower);
            dropMarker();
            sleep(cooldown);
            runTo(-80, allPower);
        }
    }
}