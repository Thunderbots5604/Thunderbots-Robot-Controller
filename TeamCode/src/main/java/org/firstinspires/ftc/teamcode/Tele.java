package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.lang.Math;

@TeleOp(name="TeleOp", group="Linear Opmode")
public class Tele extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");
        leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            //Stuff to display for Telemetry
            telemetry.addData("Left Motor Power", leftMotorFront.getPower());
            telemetry.addData("Right Motor Power", rightMotorFront.getPower());
            telemetry.update();

            if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y);
                rightMotorFront.setPower(gamepad1.left_stick_y);
                rightMotorBack.setPower(gamepad1.left_stick_y);
            }
            if (gamepad1.right_stick_x != 0 && gamepad1.left_stick_y == 0) {
                leftMotorFront.setPower(-gamepad1.right_stick_x);
                leftMotorBack.setPower(-gamepad1.right_stick_x);
                rightMotorFront.setPower(gamepad1.right_stick_x);
                rightMotorBack.setPower(gamepad1.right_stick_x);
            }
            if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
                rightMotorBack.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
            }
            if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
                leftMotorBack.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
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
