package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="TeleOpLeo", group="Linear Opmode")
public class TeleOpLeo extends LinearOpMode {

    // Declare OpMode members.
    //What's the time
    private ElapsedTime cooldown = new ElapsedTime();
    private ElapsedTime armCooldown = new ElapsedTime();
    private ElapsedTime parkCooldown = new ElapsedTime();
    private ElapsedTime spinnyBoyCooldown = new ElapsedTime();

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

    public Servo markServo = null;
    public double armPosition = .8;

    //Multipliers
    private boolean reversed = false;
    private boolean halfSpeed = false;
    private double multiplier = -1;

    //Toggles
    private boolean down;
    private boolean extended = false;
    private boolean allowVerticalDown;
    private boolean spinnyBoyDown = false;

    //Powers
    private double powerFRBL = 0;
    private double powerFLBR = 0;
    private double verticalPower = .8;
    
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
        
        markServo = hardwareMap.get(Servo.class, "markServo");

        rightMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);
        spinnyBoy1.setDirection(Servo.Direction.REVERSE);
        vertical2.setDirection(DcMotorSimple.Direction.REVERSE);

        vertical1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        vertical2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


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
        armCooldown.reset();

        gamepad1.setJoystickDeadzone(0.05f);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Reversed: ", reversed);
            telemetry.addData("Half Speed" , halfSpeed);
            telemetry.addData("Left Front: ", leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Back: ", leftMotorBack.getCurrentPosition());
            telemetry.addData("Right Front: ", rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Back: ", rightMotorBack.getCurrentPosition());
            telemetry.addData("Vertical1: ", vertical1.getCurrentPosition());
            telemetry.addData("Vertical2: ", vertical2.getCurrentPosition());
            telemetry.addData("Down: ", down);
            telemetry.update();

            armPosition = armServo.getPosition();

            if (gamepad1.dpad_left) {
                markServo.setPosition(0);
            }
            if (gamepad1.dpad_right){
                markServo.setPosition(1);
            }
            if((gamepad1.y || gamepad2.y) && cooldown.seconds() > .5) {
                reversed = !reversed;
                multiplier *= -1;
                cooldown.reset();
            }
            if((gamepad1.x || gamepad2.x) && cooldown.seconds() > .5) {
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
                powerFRBL = (gamepad1.left_stick_x + gamepad1.left_stick_y) / Math.sqrt(2);
                leftMotorBack.setPower(multiplier * powerFRBL);
                rightMotorFront.setPower(multiplier * powerFRBL);
                //FL BR pair
                powerFLBR = (gamepad1.left_stick_y - gamepad1.left_stick_x) / Math.sqrt(2);
                leftMotorFront.setPower(multiplier * powerFLBR);
                rightMotorBack.setPower(multiplier * powerFLBR);
                
            }
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
            //Spinner Up
            //#1 = left side
            //Bad OR statement
            if ((gamepad1.left_bumper && spinnyBoyCooldown.milliseconds() > 500) || gamepad2.dpad_left) {
                if (spinnyBoyDown) {
                    spinnyBoy1.setPosition(.7);
                    spinnyBoy2.setPosition(1);
                    spinnyBoyDown = false;
                }
                else{
                    spinnyBoy1.setPosition(.2);
                    spinnyBoy2.setPosition(.2);
                    spinnyBoyDown = true;
                }
                spinnyBoyCooldown.reset();
            }
            //Spinner Down
            if (gamepad2.dpad_right) {
                spinnyBoy1.setPosition(.2);
                spinnyBoy2.setPosition(.2);
            }
            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                vertical1.setPower(verticalPower);
                vertical2.setPower(verticalPower);
            }
            else if ((gamepad1.dpad_down || gamepad2.dpad_down) && (vertical1.getCurrentPosition() > 20 || allowVerticalDown)) {
                vertical1.setPower(-verticalPower);
                vertical2.setPower(-verticalPower);
            }
            else {
                vertical1.setPower(0);
                vertical2.setPower(0);
            }

            //extend-y arm for parking
            if ((gamepad1.left_trigger > .4 && gamepad1.right_trigger > .4) || (gamepad2.left_bumper) && parkCooldown.milliseconds() > 500){
                if (extended == true){
                    parkServo.setPosition(0);
                    extended = false;
                }
                else {
                    parkServo.setPosition(1);
                    extended = true;
                }
                parkCooldown.reset();
            }
            else if (gamepad1.right_stick_button && gamepad1.left_stick_button && parkCooldown.milliseconds() > 500) {
                parkServo.setPosition(0.5);
                parkCooldown.reset();
            }
            /*if (gamepad1.left_bumper || gamepad2.left_bumper) {
                armServo.setPosition(.4);
                down = true;
            }*/

            if ((gamepad1.right_bumper || gamepad2.right_bumper) && armCooldown.milliseconds() > 500) {
                if (down) {
                    armServo.setPosition(.6);
                    down = false;
                    armCooldown.reset();
                }
                else {
                    armServo.setPosition(.2);
                    armCooldown.reset();
                    down = true;
                }
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
            if (gamepad2.left_trigger > .4) {
                vertical1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                vertical2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                vertical1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                vertical2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }
            else if (gamepad2.right_trigger > .4) {
                allowVerticalDown = true;
            }
            else{
                allowVerticalDown = false;
            } 
        }
    //If opMode is turned off, instantly power off motors
    leftMotorFront.setPower(0);
    leftMotorBack.setPower(0);
    rightMotorFront.setPower(0);
    rightMotorBack.setPower(0);
    }
}