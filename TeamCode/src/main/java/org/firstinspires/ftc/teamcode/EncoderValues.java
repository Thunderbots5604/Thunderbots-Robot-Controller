
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Encoder Values", group="Linear Opmode")
public class EncoderValues extends LinearOpMode {

    // Declare OpMode members.

    private ElapsedTime cooldown = new ElapsedTime();
    private ElapsedTime armCooldown = new ElapsedTime();

    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;
    private Servo clawServo = null;
    private Servo armServo = null;

    private boolean reversed = false;
    private boolean halfSpeed = false;
    private double multiplier = 1;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");
        clawServo = hardwareMap.get(Servo.class, "claw_servo");
        armServo = hardwareMap.get(Servo.class, "arm_servo");


        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Claw Servo", clawServo.getPosition());
            telemetry.addData("Arm Servo", armServo.getPosition());
            telemetry.addData("Left Front Position: ", leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Back Position: ", leftMotorBack.getCurrentPosition());
            telemetry.addData("Right Front Position: ", rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Back Position: ", rightMotorBack.getCurrentPosition());
            telemetry.addData("Left Motor Front Power", leftMotorFront.getPower());
            telemetry.addData("Left Motor Back Power", leftMotorBack.getPower());
            telemetry.addData("Right Motor Front Power", rightMotorFront.getPower());
            telemetry.addData("Right Motor Back Power", rightMotorBack.getPower());
            telemetry.update();

            if(gamepad1.b) {
                leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
                leftMotorFront.setPower(multiplier * gamepad1.right_stick_x);
                leftMotorBack.setPower(multiplier * gamepad1.right_stick_x);
                rightMotorFront.setPower(multiplier * gamepad1.right_stick_x);
                rightMotorBack.setPower(multiplier * gamepad1.right_stick_x);
            }

            //Swerve Turn
            //Left Turn
            else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x < 0 && !reversed) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(multiplier * gamepad1.right_stick_x / 2 + gamepad1.left_stick_y / 2);
                rightMotorBack.setPower(multiplier * gamepad1.right_stick_x / 2 + gamepad1.left_stick_y / 2);
            }
            //Right Turn
            else if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x < 0 && !reversed) {
                leftMotorFront.setPower(multiplier * gamepad1.right_stick_x / 2 + gamepad1.right_stick_x / 2);
                leftMotorBack.setPower(multiplier * gamepad1.right_stick_x / 2 + gamepad1.right_stick_y / 2);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            //Reverse Left Turn
            else if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x < 0 && reversed) {
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
                leftMotorFront.setPower(multiplier * gamepad1.right_stick_x / 2 + gamepad1.left_stick_y / 2);
                leftMotorBack.setPower(multiplier * gamepad1.right_stick_x / 2 + gamepad1.left_stick_y / 2);
            }
            //Reverse Right Turn
            else if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x < 0 && reversed) {
                rightMotorFront.setPower(multiplier * gamepad1.right_stick_x / 2 + gamepad1.right_stick_y / 2);
                rightMotorBack.setPower(multiplier * gamepad1.right_stick_x / 2+ gamepad1.right_stick_y / 2);
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
            }
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            //Claw closing and arm upping
            if (gamepad1.right_bumper && !gamepad1.left_bumper && armCooldown.seconds() > 2) {
                armCooldown.reset();
                armServo.setPosition(90);
                sleep(500);
                clawServo.setPosition(90);
                sleep(500);
                armServo.setPosition(0);
            }
            //Claw opening and arm downing
            else if (!gamepad1.right_bumper && gamepad1.left_bumper && armCooldown.seconds() > 2) {
                armCooldown.reset();
                armServo.setPosition(90);
                sleep(500);
                clawServo.setPosition(0);
                sleep(500);
                armServo.setPosition(0);
            }
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
    }
}
