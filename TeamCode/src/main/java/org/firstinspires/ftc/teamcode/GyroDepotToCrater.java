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
            telemetry.addLine("right");
            telemetry.update();
            runTo(27, allPower);
            sleep(cooldown);
            turnLeft(55, allPower);
            sleep(cooldown);
            runTo(21, allPower);
        }
        else if (location == 1) {
            telemetry.addLine("middle");
            telemetry.update();

            runTo(5, allPower);
            sleep(cooldown);
            turnLeft(40, allPower);
            sleep(cooldown);
            runTo(32, allPower);
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
            turnRight(135, allPower);
            sleep(cooldown);
            runTo(25, allPower);
        }
        dropMarker();
        if (location == 2) {
            runTo(-2,allPower);
            turnLeft(50,allPower);
            runTo(30,allPower);
            turnLeft(65,allPower);
            runTo(60,allPower);
        }
        else if (location == 1) {
            runTo(-2,allPower);
            turnLeft(65,allPower);
            runTo(15,allPower);
            turnLeft(30,allPower);
            runTo(50,allPower);
        }
        else if (location == 0) {
            turnRight(10,allPower);
            runTo(-70,allPower);
        }
        box1.setPower(-1);
        box2.setPower(1);
        sleep(1500);
        box1.setPower(0);
        box2.setPower(0);
    }
}