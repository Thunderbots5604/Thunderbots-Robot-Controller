package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@Disabled
@Autonomous(name="AutonomousCrater", group="Autonomous Encoder")
public class AutonomousCrater extends LinearOpMode {

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
        //Stuff to display for Telemetry

        //crane.setPower(1);
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
            crane.setPower(-.75);
        }
        crane.setPower(0);

        sleep(2500);

        runtime.reset();
        while(runtime.milliseconds() < 150) {
            telemetry.addLine("Move forward");
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
            telemetry.addLine("Turning Right");
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
            telemetry.addLine("Move Forward");
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
            telemetry.addLine("Turning Back");
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

        sleep(1000);
        runtime.reset();
        while(runtime.milliseconds() < 2000) {
            telemetry.addLine("Going for crater");
            telemetry.update();
            leftMotorFront.setPower(-.4);
            leftMotorBack.setPower(-.4);
            rightMotorFront.setPower(-.4);
            rightMotorBack.setPower(-.4);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
}
