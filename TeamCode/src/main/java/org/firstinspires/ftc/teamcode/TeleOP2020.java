package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp(name="Encoder Values", group="Linear Opmode")
public class EncoderValues extends LinearOpMode {

    // Declare OpMode members.

    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;

    private boolean reversed = false;
    private double multiplier = 1;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");

        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("Left Front Power: ", leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Back Power: ", leftMotorBack.getCurrentPosition());
            telemetry.addData("Right Front Power: ", rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Back Power: ", rightMotorBack.getCurrentPosition());
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
            else if (gamepad1.right_stick_x != 0 && gamepad1.left_stick_y == 0 & reversed == false) {
                leftMotorFront.setPower(-multiplier * gamepad1.right_stick_x);
                leftMotorBack.setPower(-multiplier * gamepad1.right_stick_x);
                rightMotorFront.setPower(multiplier * gamepad1.right_stick_x);
                rightMotorBack.setPower(multiplier * gamepad1.right_stick_x);
            }
            else if (gamepad1.right_stick_x != 0 && gamepad1.left_stick_y == 0 & reversed) {
                leftMotorFront.setPower(multiplier * gamepad1.right_stick_x);
                leftMotorBack.setPower(multiplier * gamepad1.right_stick_x);
                rightMotorFront.setPower(-multiplier * gamepad1.right_stick_x);
                rightMotorBack.setPower(-multiplier * gamepad1.right_stick_x);
            }

            //Swing Turn without Reverse
            else if (gamepad1.left_stick_y < 0 && gamepad1.right_stick_x > 0 && reversed == false) {
                leftMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                leftMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x > 0 && reversed == false) {
                leftMotorFront.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                leftMotorBack.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x < 0 && reversed == false) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorBack.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
            }
            else if (gamepad1.left_stick_y < 0 && gamepad1.right_stick_x < 0 && reversed == false) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
            }

            //Swing Turn with Reverse
            else if (gamepad1.left_stick_y < 0 && gamepad1.right_stick_x > 0 && reversed) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorBack.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
            }
            else if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x > 0 && reversed) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
            }
            else if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x < 0 && reversed) {
                leftMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                leftMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if (gamepad1.left_stick_y < 0 && gamepad1.right_stick_x < 0 && reversed) {
                leftMotorFront.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                leftMotorBack.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
        }
    }
}