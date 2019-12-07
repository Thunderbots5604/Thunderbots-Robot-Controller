package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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
    private ElapsedTime reset = new ElapsedTime();

    //Motors and Servos
    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;
    private Servo clawServo = null;
    private Servo armServo = null;

    //Multipliers
    private boolean reversed = false;
    private boolean halfSpeed = false;
    private double multiplier = -1;

    //If arm or claw is moved
    private boolean positionSetDown = true;
    private boolean positionSetUp = true;
    //If crane is still moving
    private boolean clawMoving = false;
    //Position of claw and arm
    private double clawPosition = 0;
    private double armPosition = 0;
    private double targetPositionArm = 0.1;
    private double targetPositionClaw = 0.1;
    private int countLeftButton = 0;

    @Override
    public void runOpMode() {

        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");
        clawServo = hardwareMap.get(Servo.class, "claw_servo");
        armServo = hardwareMap.get(Servo.class, "arm_servo");

        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        cooldown.reset();
        armCooldown.reset();
        clawServo.setPosition(0.1);
        sleep(1000);
        armServo.setPosition(.1);

        while (opModeIsActive()) {
            telemetry.addData("Reversed: ", reversed);
            telemetry.addData("Half Speed" , halfSpeed);
            telemetry.update();
            clawPosition = clawServo.getPosition();
            armPosition = armServo.getPosition();

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

            //Moving Forward
            if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(multiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(multiplier * gamepad1.left_stick_y);
                rightMotorFront.setPower(multiplier * gamepad1.left_stick_y);
                rightMotorBack.setPower(multiplier * gamepad1.left_stick_y);
            }

            //Point Turn
            //If right stick is moving on the x axis
            else if (gamepad1.left_stick_y == 0 && gamepad1.right_stick_x != 0) {
                leftMotorFront.setPower(Math.abs(multiplier) * gamepad1.right_stick_x);
                leftMotorBack.setPower(Math.abs(multiplier) * gamepad1.right_stick_x);
                rightMotorFront.setPower(-Math.abs(multiplier) * gamepad1.right_stick_x);
                rightMotorBack.setPower(-Math.abs(multiplier) * gamepad1.right_stick_x);
                telemetry.addData("Rgiht stick", gamepad1.right_stick_x);
            }

            //Swerve Turn
            //Left Turn
            else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x < 0 && !reversed) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(multiplier * (gamepad1.left_stick_y));
                rightMotorBack.setPower(multiplier * (gamepad1.left_stick_y));
            }
            //Right Turn
            else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x > 0 && !reversed) {
                leftMotorFront.setPower(multiplier * (gamepad1.left_stick_y));
                leftMotorBack.setPower(multiplier * (gamepad1.left_stick_y));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            //Reverse Left Turn
            else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x < 0 && reversed) {
                leftMotorFront.setPower(multiplier * (gamepad1.left_stick_y));
                leftMotorBack.setPower(multiplier * (gamepad1.left_stick_y));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            //Reverse Right Turn
            else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x > 0 && reversed) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(multiplier * (gamepad1.left_stick_y));
                rightMotorBack.setPower(multiplier * (gamepad1.left_stick_y));
            }
            //Don't move if no joysticks moved
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            if ((clawPosition >= .15 && clawPosition <= .9) || (armPosition >= .15 && armPosition <= .9)) {
                clawMoving = true;
            } else {
                clawMoving = false;
            }
            // Double Press, does an emergency reset
            if (gamepad1.left_bumper) {
                countLeftButton++;
            }
            if (reset.milliseconds() > 100) {
                reset.reset();
                countLeftButton = 0;
            }
            if (countLeftButton == 2) {
                armServo.setPosition(.1);
                clawServo.setPosition(.1);
                countLeftButton = 0;
            } else {

                //Claw closing and arm upping
                //If right bumper is pressed, left bumper is not pressed, and arm isn't running
                if (gamepad1.right_bumper && !gamepad1.left_bumper && clawMoving == false && armPosition <= .9) {
                    clawServo.setPosition(1);
                    targetPositionClaw = 1;
                    positionSetUp = false;
                }
                if (clawPosition >= .9 && positionSetUp == false) {
                    armServo.setPosition(1);
                    targetPositionArm = 1;
                    positionSetUp = true;
                }
                //grabs block
                //Claw opening and arm downing
                if (!gamepad1.right_bumper && gamepad1.left_bumper && armPosition >= .15 && targetPositionClaw != 1) {
                    clawServo.setPosition(.1);
                    targetPositionClaw = .1;
                    positionSetDown = false;
                }
                if (clawPosition <= .15 && positionSetDown == false) {
                    armServo.setPosition(.1);
                    targetPositionArm = .1;
                    positionSetDown = true;
                }
            }
        }
        //If opMode is turned off, instantly power off motors
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
}