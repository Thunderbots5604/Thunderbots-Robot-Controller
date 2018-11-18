package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@Disabled
@Autonomous(name="Ambitious", group="Autonomous")
public class Ambitious extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;
    private DcMotor crane = null;
    private DistanceSensor distance = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");
        crane = hardwareMap.get(DcMotor.class, "crane");
        distance = hardwareMap.get(DistanceSensor.class, "distance");
        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();


        runtime.reset();
        while (Double.isNaN(distance.getDistance(DistanceUnit.MM))) {
            telemetry.addLine("Phase: Lowering Part 1");
            telemetry.addData("Distance", distance.getDistance(DistanceUnit.MM));
            telemetry.update();
            crane.setPower(1);
        }
        sleep(900);
        crane.setPower(0);
        runtime.reset();
        while (distance.getDistance(DistanceUnit.MM) > 70) {
            telemetry.addLine("Phase: Lowering Part 2");
            telemetry.addData("Distance", distance.getDistance(DistanceUnit.MM));
            telemetry.update();
            crane.setPower(.75);
        }
        crane.setPower(0);
        sleep(100);
        runtime.reset();
        while(runtime.milliseconds() < 50) {
            telemetry.addLine("Bring up the crane a little bit.");
            telemetry.addData("Distance", distance.getDistance(DistanceUnit.MM));
            telemetry.update();
            crane.setPower(-.75);
        }
        crane.setPower(0);

        sleep(2500);

        runtime.reset();
        while(runtime.milliseconds() < 150) {
            telemetry.addLine("Move forward a little");
            telemetry.update();
            leftMotorFront.setPower(-.25);
            leftMotorBack.setPower(-.25);
            rightMotorFront.setPower(-.25);
            rightMotorBack.setPower(-.25);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);
        runtime.reset();
        while(runtime.milliseconds() < 750) {
            telemetry.addLine("Turns right out of the hook");
            telemetry.update();
            leftMotorFront.setPower(.35);
            leftMotorBack.setPower(.35);
            rightMotorFront.setPower(-.35);
            rightMotorBack.setPower(-.35);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);
        runtime.reset();
        while(runtime.milliseconds() < 500) {
            telemetry.addLine("Move Forward a little");
            telemetry.update();
            leftMotorFront.setPower(-.25);
            leftMotorBack.setPower(-.25);
            rightMotorFront.setPower(-.25);
            rightMotorBack.setPower(-.25);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);
        runtime.reset();
        while(runtime.milliseconds() < 750) {
            telemetry.addLine("Turning Left to readjust and center");
            telemetry.update();
            leftMotorFront.setPower(-.35);
            leftMotorBack.setPower(-.35);
            rightMotorFront.setPower(.35);
            rightMotorBack.setPower(.35);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(500);
        runtime.reset();
        while(runtime.milliseconds() < 500) {
            telemetry.addLine("Going for team depot. Goes fast to get rid of team marker.");
            telemetry.update();
            leftMotorFront.setPower(-1);
            leftMotorBack.setPower(-1);
            rightMotorFront.setPower(-1);
            rightMotorBack.setPower(-1);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        runtime.reset();
        while(runtime.milliseconds() < 100) {
            telemetry.addLine("Backing up to let the team marker go");
            telemetry.update();
            leftMotorFront.setPower(.4);
            leftMotorBack.setPower(.4);
            rightMotorFront.setPower(.4);
            rightMotorBack.setPower(.4);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(500);

        runtime.reset();
        while(runtime.milliseconds() < 1500) {
            telemetry.addLine("Going for team depot and pushing the team marker in");
            telemetry.update();
            leftMotorFront.setPower(-.25);
            leftMotorBack.setPower(-.25);
            rightMotorFront.setPower(-.25);
            rightMotorBack.setPower(-.25);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);
        runtime.reset();
        while(runtime.milliseconds() < 500) {
            telemetry.addLine("Backing up");
            telemetry.update();
            leftMotorFront.setPower(.5);
            leftMotorBack.setPower(.5);
            rightMotorFront.setPower(.5);
            rightMotorBack.setPower(.5);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);
        runtime.reset();
        while(runtime.milliseconds() < 2500) {
            telemetry.addLine("Turn right to crater");
            telemetry.update();
            leftMotorFront.setPower(.35);
            leftMotorBack.setPower(.35);
            rightMotorFront.setPower(-.35);
            rightMotorBack.setPower(-.35);
        }

        sleep(1000);
        runtime.reset();
        while(runtime.milliseconds() < 3000) {
            telemetry.addLine("Going for crater");
            telemetry.update();
            leftMotorFront.setPower(-.5);
            leftMotorBack.setPower(-.5);
            rightMotorFront.setPower(-.5);
            rightMotorBack.setPower(-.5);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
}
