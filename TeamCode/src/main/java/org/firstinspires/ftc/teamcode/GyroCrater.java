package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@Autonomous(name="Gyro Crater Only", group="Autonomous Competition")
public class GyroCrater extends GodfatherOfAllAutonomous {

    @Override
    public void runOpMode() {

        int cooldown = 1000;
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        initialization();

        location = objectDetect();

        waitForStart();

        while (distance.getDistance(DistanceUnit.MM) > 100) {
            telemetry.addLine("Phase: Lowering Part 1");
            telemetry.addData("Distance", distance.getDistance(DistanceUnit.MM));
            telemetry.update();
            crane1.setPower(-1);
            crane2.setPower(1);
        }
        runtime.reset();
        while (distance.getDistance(DistanceUnit.MM) > 47) {
            telemetry.addLine("Phase: Lowering Part 2");
            telemetry.addData("Distance", distance.getDistance(DistanceUnit.MM));
            telemetry.update();
            crane1.setPower(-(allPower + .18));
            crane2.setPower(allPower + .18);
        }
        runtime.reset();
        while(runtime.milliseconds() < 200) {
            crane1.setPower(-(allPower + .18));
            crane2.setPower(allPower + .18);
        }
        runtime.reset();
        while(runtime.milliseconds() < 50) {
            crane1.setPower(allPower);
            crane2.setPower(-(allPower));
        }
        crane1.setPower(0);
        crane2.setPower(0);

        sleep(100);
        //Getting out of hook
        //sleep(250);
        runTo(1.5,allPower + .1);
        turnRight(20,allPower);
        sleep(500);
        runTo(1,allPower + .1);
        turnRight(45,allPower);
        sleep(500);
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
        sleep(2500);
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
            turnLeft(40, allPower);
            sleep(cooldown);
            runTo(3, allPower);
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
        sleep(2000);
        box1.setPower(0);
        box2.setPower(0);
    }
}