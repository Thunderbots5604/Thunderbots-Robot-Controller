package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous(name="AutonomousDetachOnly", group="Autonomous Competition")
public class AutoDetach extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;
    private DcMotor crane = null;
    private DistanceSensor distance = null;
    private final double INCHES_PER_TICK = .0215524172;
    private final double DEGREES_PER_TICK = .1525087903;

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

        sleep(1000);

        if(Double.isNaN(distance.getDistance(DistanceUnit.MM)) != true) {
            runTo(.5, .25);
            turnRight(35, .35);
            runTo(10, .25);
            turnLeft(40, .35);
            runTo(-6, .25);
        }
        /*runtime.reset();
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
        while(runtime.milliseconds() < 1500) {
            telemetry.addLine("Turning");
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
        while(runtime.milliseconds() < 1500) {
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
        rightMotorBack.setPower(0);*/
    }
    private void runTo(double inches, double power) {
        //Just do 40 inches

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int negative = -1;
        if (inches < 0) {
            negative = 1;
        }
        inches *= -1;
        leftMotorFront.setTargetPosition((int)(inches / INCHES_PER_TICK));
        leftMotorBack.setTargetPosition((int)(inches / INCHES_PER_TICK));
        rightMotorFront.setTargetPosition((int)(inches / INCHES_PER_TICK));
        rightMotorBack.setTargetPosition((int)(inches / INCHES_PER_TICK));
        leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (leftMotorFront.isBusy() && rightMotorFront.isBusy()) {
            telemetry.addData("Target Position", leftMotorFront.getTargetPosition());
            telemetry.addData("Left Motor Position", leftMotorFront.getCurrentPosition());
            telemetry.addData("Right Motor Position", rightMotorFront.getCurrentPosition());
            telemetry.update();
            leftMotorFront.setPower(negative * power);
            leftMotorBack.setPower(negative * power);
            rightMotorFront.setPower(negative * power);
            rightMotorBack.setPower(negative * power);
        }
        runtime.reset();
        while(runtime.milliseconds() < 100) {
            leftMotorFront.setPower(-1 * negative * .1);
            leftMotorBack.setPower(-1 * negative * .1);
            rightMotorFront.setPower(-1 * negative * .1);
            rightMotorBack.setPower(-1 * negative * .1);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
    private void turnRight(double degrees, double power) {
        degrees *= .95;

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotorFront.setTargetPosition((int)((degrees) / DEGREES_PER_TICK));
        leftMotorBack.setTargetPosition((int)((degrees) / DEGREES_PER_TICK));
        rightMotorFront.setTargetPosition((int)((-degrees) / DEGREES_PER_TICK));
        rightMotorBack.setTargetPosition((int)((-degrees) / DEGREES_PER_TICK));
        leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (leftMotorFront.isBusy() && rightMotorFront.isBusy()) {
            telemetry.addData("Target Turn", degrees);
            telemetry.addData("Left Motor Position", leftMotorFront.getCurrentPosition());
            telemetry.addData("Right Motor Position", rightMotorFront.getCurrentPosition());
            telemetry.update();
            leftMotorFront.setPower(power);
            leftMotorBack.setPower(power);
            rightMotorFront.setPower(-power);
            rightMotorBack.setPower(-power);
        }
        runtime.reset();
        while(runtime.milliseconds() < 100) {
            leftMotorFront.setPower(-.1);
            leftMotorBack.setPower(-.1);
            rightMotorFront.setPower(.1);
            rightMotorBack.setPower(.1);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    private void turnLeft(double degrees, double power) {
        degrees *= .95;

        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotorFront.setTargetPosition((int)((-degrees) / DEGREES_PER_TICK));
        leftMotorBack.setTargetPosition((int)((-degrees) / DEGREES_PER_TICK));
        rightMotorFront.setTargetPosition((int)((degrees) / DEGREES_PER_TICK));
        rightMotorBack.setTargetPosition((int)((degrees) / DEGREES_PER_TICK));
        leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (leftMotorFront.isBusy() && rightMotorFront.isBusy()) {
            telemetry.addData("Target Turn", degrees);
            telemetry.addData("Left Motor Position", leftMotorFront.getCurrentPosition());
            telemetry.addData("Right Motor Position", rightMotorFront.getCurrentPosition());
            telemetry.update();
            leftMotorFront.setPower(-power);
            leftMotorBack.setPower(-power);
            rightMotorFront.setPower(power);
            rightMotorBack.setPower(power);
        }
        runtime.reset();
        while(runtime.milliseconds() < 100) {
            leftMotorFront.setPower(.1);
            leftMotorBack.setPower(.1);
            rightMotorFront.setPower(-.1);
            rightMotorBack.setPower(-.1);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}

