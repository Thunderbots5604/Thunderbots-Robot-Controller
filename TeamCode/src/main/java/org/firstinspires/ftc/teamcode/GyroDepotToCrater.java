package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
//Completely untested
@Autonomous(name="Gyro Depot-Crater", group="Gyro")
public class GyroDepotToCrater extends GodfatherOfAllAutonomous {

    @Override
    public void runOpMode() {

        int cooldown = 500;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        initialization();

        location = objectDetect();

        waitForStart();

        detach();
        double heading = 0;
        double turn;
        while (runtime.milliseconds() < 5000 && heading == 0) {
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            heading = formatAngle(angles.angleUnit, angles.firstAngle);
        }
        turn = ((Math.abs(33 + heading))*1.1);
        telemetry.addData("Runtime", runtime.milliseconds());
        telemetry.addData("Heading: ", heading);
        telemetry.addData("Turning: ", turn);
        telemetry.update();
        sleep(cooldown);
        if (heading > -33.0) {
            turnRight(turn, allPower);
        }
        else if (heading < -33.0) {
            turnLeft(turn, allPower);
        }
        //detach finished

        //Auto Crater only
        sleep(cooldown);
        if (location == 2) {
            turnRight(10,allPower);
            runTo(36, allPower);
            sleep(500);
            turnLeft(90,allPower);
            runTo(30,allPower);
        }
        if (location == 1) {
            //Hit Marker
            runTo(5,allPower);
            turnLeft(27, allPower);
            runTo(33.33,allPower);
            sleep(250);
        }
        if (location == 0) {
            runTo(5,allPower);
            turnLeft(75, allPower);
            runTo(36,allPower);
            sleep(250);
            turnRight(125,allPower);
            runTo(22,allPower);
        }
        dropMarker();
        if (location == 2) {
            turnLeft(60,allPower);
            runTo(16.6,allPower);
            turnLeft(40,allPower);
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
            turnRight(16.6, allPower);
            runTo(-50, 1);
            runTo(-24,allPower);
        }
        box1.setPower(-1);
        box2.setPower(1);
        sleep(2000);
        box1.setPower(0);
        box2.setPower(0);
    }
}