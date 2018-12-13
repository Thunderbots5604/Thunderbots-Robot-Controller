package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;



//Left motors are now right physically
//Right motors are now left physically


@TeleOp(name="TeleOp", group="Linear Opmode")
public class TeleOpTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;
    private DcMotor crater = null;
    private DcMotor horizontal = null;
    private DcMotor crane1 = null;
    private DcMotor crane2 = null;

    private CRServo wheel = null;
    private Servo elevator = null;

    private boolean reversed = false;
    private int multiplier = 1;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");
        horizontal = hardwareMap.get(DcMotor.class, "horizontal");
        crater = hardwareMap.get(DcMotor.class, "crater");
        wheel = hardwareMap.get(CRServo.class, "wheel");
        crane1 = hardwareMap.get(DcMotor.class, "crane_a");
        crane2 = hardwareMap.get(DcMotor.class, "crane_b");
        elevator = hardwareMap.get(Servo.class, "elevator");

        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();/*
        elevator.setPosition(.15);*/

        while (opModeIsActive()) {

            telemetry.addData("Left Power: ", leftMotorFront.getPower());
            telemetry.addData("Right Power: ", rightMotorFront.getPower());
            telemetry.addData("Reversed: ", reversed);
            telemetry.update();

            if(gamepad1.b && runtime.seconds() > 1) {
                if(reversed == true) {
                    multiplier = 1;
                    reversed = false;
                    runtime.reset();
                }
                else if(reversed == false) {
                    reversed = true;
                    multiplier = -1;
                    runtime.reset();
                }
            }
            if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(multiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(multiplier * gamepad1.left_stick_y);
                rightMotorFront.setPower(multiplier * gamepad1.left_stick_y);
                rightMotorBack.setPower(multiplier * gamepad1.left_stick_y);
            }
            else if (gamepad1.right_stick_x != 0 && gamepad1.left_stick_y == 0) {
                leftMotorFront.setPower(gamepad1.right_stick_x);
                leftMotorBack.setPower(gamepad1.right_stick_x);
                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
            }
            else if (reversed == false && gamepad1.left_stick_y < 0 && gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                leftMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if (reversed == true && gamepad1.left_stick_y < 0 && gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2);
                rightMotorBack.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2);
            }
            else if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2);
                rightMotorBack.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2);
            }
            else if (reversed == false && gamepad1.left_stick_y < 0 && gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
            }
            else if (reversed == true && gamepad1.left_stick_y < 0 && gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                leftMotorBack.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2);
                leftMotorBack.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }



            if (gamepad1.dpad_down || gamepad2.dpad_down) {
                crane1.setPower(-1);
                crane2.setPower(1);
            }
            else if (gamepad1.dpad_up || gamepad2.dpad_up) {
                crane1.setPower(1);
                crane2.setPower(-1);
            }
            else {
                crane1.setPower(0);
                crane2.setPower(0);
            }



            if (gamepad1.left_bumper || gamepad2.left_bumper) {
                horizontal.setPower(-.5);
            }
            else if (gamepad1.right_bumper || gamepad2.right_bumper) {
                horizontal.setPower(.5);
            }
            else {
                horizontal.setPower(0);
            }



            if (gamepad1.left_trigger > 0 || gamepad2.left_trigger > 0) {
                wheel.setPower(1);
            }
            else if (gamepad1.right_trigger > 0 || gamepad2.right_trigger > 0) {
                wheel.setPower(-1);
            }
            else {
                wheel.setPower(0);
            }



            if (gamepad1.x || gamepad2.x) {
                elevator.setPosition(.25);
            }
            else if (gamepad1.y || gamepad2.y) {
                elevator.setPosition(0.85);
            }



            if (gamepad1.dpad_left || gamepad2.dpad_left) {
                crater.setPower(-.5);
            }
            else if (gamepad1.dpad_right || gamepad2.dpad_right) {
                crater.setPower(.5);
            }
            else {
                crater.setPower(0);
            }
        }
    }
}
