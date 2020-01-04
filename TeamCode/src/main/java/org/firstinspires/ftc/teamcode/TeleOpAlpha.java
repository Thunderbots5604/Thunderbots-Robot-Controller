package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="TeleOpAlpha", group="Linear Opmode")
public class TeleOpAlpha extends LinearOpMode {

    // Declare OpMode members.
    //What's the time
    private ElapsedTime cooldown = new ElapsedTime();
    private ElapsedTime armCooldown = new ElapsedTime();
    private ElapsedTime armCooldownUp = new ElapsedTime();
    private ElapsedTime armCooldownDown = new ElapsedTime();

    //Motors and Servos
    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;
    public Servo spinnyBoy1 = null;
    public Servo spinnyBoy2 = null;

    //Multipliers
    private boolean reversed = false;
    private boolean halfSpeed = false;
    private double multiplier = -1;

    //Motor powers
    private double powerFRBL = 0;
    private double powerFLBR = 0;


    @Override
    public void runOpMode() {

        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");

        spinnyBoy1 = hardwareMap.get(Servo.class, "spin1");
        spinnyBoy2 = hardwareMap.get(Servo.class, "spin2");

        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Reversed: ", reversed);
            telemetry.addData("Half Speed" , halfSpeed);
            telemetry.addData("Left Front: ", leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Back: ", leftMotorBack.getCurrentPosition());
            telemetry.addData("Right Front: ", rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Back: ", rightMotorBack.getCurrentPosition());
            telemetry.update();

            if((gamepad1.y) && cooldown.seconds() > .5) {
                reversed = !reversed;
                multiplier *= -1;
                cooldown.reset();
            }
            if((gamepad1.x) && cooldown.seconds() > .5) {
                halfSpeed = !halfSpeed;
                if (halfSpeed) {
                    multiplier *= .5;
                }
                else {
                    multiplier *= 2;
                }
                cooldown.reset();
            }

            //Movement, in one if statement hopefully
            if (gamepad1.left_stick_y != 0 || gamepad1.left_stick_x != 0) {
                //FR BL pair
                powerFRBL = (gamepad1.left_stick_x + gamepad1.left_stick_y) / 2;
                leftMotorBack.setPower(multiplier * powerFRBL);
                rightMotorFront.setPower(multiplier * powerFRBL);
                //FL BR pair
                powerFLBR = (gamepad1.left_stick_y - gamepad1.left_stick_x) / 2;
                leftMotorFront.setPower(multiplier * powerFLBR);
                rightMotorBack.setPower(multiplier * powerFLBR);
            }
            //Point Turn
            //If right stick is moving on the x axis
            else if (gamepad1.left_stick_y == 0 && gamepad1.right_stick_x != 0) {
                leftMotorFront.setPower(Math.abs(multiplier) * gamepad1.right_stick_x);
                leftMotorBack.setPower(Math.abs(multiplier) * gamepad1.right_stick_x);
                rightMotorFront.setPower(-Math.abs(multiplier) * gamepad1.right_stick_x);
                rightMotorBack.setPower(-Math.abs(multiplier) * gamepad1.right_stick_x);
                telemetry.addData("Right stick", gamepad1.right_stick_x);
            }
            //Don't move if no joysticks moved
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            //Spinner down
            if (gamepad1.dpad_left || gamepad2.dpad_left) {
                spinnyBoy1.setPosition(.45);
                spinnyBoy2.setPosition(.45);
            }
            //Spinner Up
            if (gamepad1.dpad_right || gamepad2.dpad_right) {
                spinnyBoy1.setPosition(.9);
                spinnyBoy2.setPosition(0);
            }
            /*if (gamepad1.dpad_up) {
                //verticalslide1.setPower(.5);
                //verticalslide2.setPower(-.5);
            }
            else if (gamepad1.dpad_down) {
                verticalslide1.setPower(-.5);
                verticalslide2.setPower(.5);
            }
            else {
                verticalslide1.setPower(0);
                verticalslide2.setPower(0);
            }
            if (gamepad1.right_bumper && !gamepad1.left_bumper) {
                horizontalSlide.setPosition(0);
            }
            if (gamepad1.left_bumper && !gamepad1.right_bumper) {
                horizontalSlide.setPosition(1);
            }
            if (gamepad1.left_bumper && gamepad1.right_bumper) {
                armServo.setPosition(.5);
            }
            if (gamepad1.left_bumper && gamepad1.right_bumper) {
                if (armPosition > .7) {
                    armServo.setPosition(.5);
                }
                else {
                    armServo.setPosition(.9);
                }
            }*/
            if (gamepad2.b) {
                leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                leftMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                leftMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightMotorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightMotorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
        }
        //If opMode is turned off, instantly power off motors
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
}