package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="TeleOpAlpha2", group="Linear Opmode")
public class TeleOpAlpha2 extends LinearOpMode {

    // Declare OpMode members.
    //What's the time
    private ElapsedTime cooldown = new ElapsedTime();
    private ElapsedTime armCooldown = new ElapsedTime();

    //Motors and Servos
    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;

    public DcMotor vertical1 = null;
    public DcMotor vertical2 = null;

    public Servo spinnyBoy1 = null;
    public Servo spinnyBoy2 = null;

    public Servo armServo = null;

    public Servo parkServo = null;

    public double armPosition = .8;

    //Multipliers
    private boolean reversed = false;
    private boolean halfSpeed = false;
    private double multiplier = -1;

    //Powers
    private double powerFRBL;
    private double powerFLBR;

    //Toggles
    private boolean down;
    private boolean pickUpSequence;
    private boolean extended = false;

    @Override
    public void runOpMode() {

        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");

        vertical1 = hardwareMap.get(DcMotor.class, "vertical1");
        vertical2 = hardwareMap.get(DcMotor.class, "vertical2");

        spinnyBoy1 = hardwareMap.get(Servo.class, "spin1");
        spinnyBoy2 = hardwareMap.get(Servo.class, "spin2");

        armServo = hardwareMap.get(Servo.class, "armServo");

        parkServo = hardwareMap.get(Servo.class, "parkServo");

        rightMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);
        spinnyBoy1.setDirection(Servo.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        vertical1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertical2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertical1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        vertical2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if (armServo.getPosition() < .6) {
            down = true;
        }
        else {
            down = false;
        }

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Reversed: ", reversed);
            telemetry.addData("Half Speed" , halfSpeed);
            telemetry.addData("Left Front: ", leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Back: ", leftMotorBack.getCurrentPosition());
            telemetry.addData("Right Front: ", rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Back: ", rightMotorBack.getCurrentPosition());
            telemetry.addData("Vertical1 ", vertical1.getCurrentPosition());
            telemetry.addData("Vertical2 ", vertical2.getCurrentPosition());
            telemetry.update();

            armPosition = armServo.getPosition();

            if((/*gamepad1.y || */gamepad2.y) && cooldown.seconds() > .5) {
                reversed = !reversed;
                multiplier *= -1;
                cooldown.reset();
            }
            if((/*gamepad1.x || */gamepad2.x) && cooldown.seconds() > .5) {
                halfSpeed = !halfSpeed;
                if (halfSpeed) {
                    multiplier *= .25;
                }
                else {
                    multiplier *= 4;
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
            else if ((gamepad1.left_stick_y == 0 && gamepad1.right_stick_x != 0) || (gamepad2.left_stick_y == 0 && gamepad2.right_stick_x != 0)) {
                leftMotorFront.setPower(Math.abs(multiplier) * gamepad1.right_stick_x * .7);
                leftMotorBack.setPower(Math.abs(multiplier) * gamepad1.right_stick_x * .7);
                rightMotorFront.setPower(-Math.abs(multiplier) * gamepad1.right_stick_x * .7);
                rightMotorBack.setPower(-Math.abs(multiplier) * gamepad1.right_stick_x * .7);
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
            //#1 = left side
            if (gamepad1.dpad_left || gamepad2.dpad_left) {
                spinnyBoy1.setPosition(.7);
                spinnyBoy2.setPosition(.7);

            }
            //Spinner Up
            if (gamepad1.dpad_right || gamepad2.dpad_right) {
                spinnyBoy1.setPosition(.29);
                spinnyBoy2.setPosition(.29);
            }
            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                vertical1.setPower(-.5);
                vertical2.setPower(.5);
            }
            else if (gamepad1.dpad_down || gamepad2.dpad_down) {
                vertical1.setPower(.5);
                vertical2.setPower(-.5);
            } else if (pickUpSequence == true && vertical1.getCurrentPosition() > -100 && vertical2.getCurrentPosition() < 100){
                vertical1.setPower(-.5);
                vertical2.setPower(.5);
            }
            else {
                vertical1.setPower(0);
                vertical2.setPower(0);
            }
            //extend-y arm for parking
            if (gamepad1.left_bumper || gamepad2.left_bumper){
                if (extended == true){
                    parkServo.setPosition(.4);
                    extended = false;
                }
                else {
                    parkServo.setPosition(.5);
                    extended = true;
                }
            }
            /*if (gamepad1.left_bumper || gamepad2.left_bumper) {
                armServo.setPosition(.4);
                down = true;
            }*/
            if ((gamepad1.right_bumper || gamepad2.right_bumper) && armCooldown.milliseconds() > 500) {
                if (down) {
                    armServo.setPosition(1);
                    down = false;
                }
                else {
                    if (vertical1.getCurrentPosition() >= -110 && vertical2.getCurrentPosition() <= 110){
                        while (vertical1.getCurrentPosition() <= -10 && vertical2.getCurrentPosition() >= 10){
                            vertical1.setPower(.5);
                            vertical2.setPower(-.5);
                        }
                    }
                    armServo.setPosition(.2);
                    down = true;
                }
                armCooldown.reset();
            }
            if (gamepad1.b || gamepad2.b) {
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