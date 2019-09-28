package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="TeleOp", group="Linear Opmode")
public class TeleOpTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime reversedCooldown = new ElapsedTime();
    private ElapsedTime halfSpeedCooldown = new ElapsedTime();

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
        halfSpeedCooldown.reset();
        reversedCooldown.reset();
        while (opModeIsActive()) {

            telemetry.addData("Left Front Power: ", leftMotorFront.getPower());
            telemetry.addData("Left Back Power: ", leftMotorBack.getPower());
            telemetry.addData("Right Front Power: ", rightMotorFront.getPower());
            telemetry.addData("Right Back Power: ", rightMotorBack.getPower());
            telemetry.addData("Reversed: ", reversed);
            telemetry.update();

            if(gamepad1.b && reversedCooldown.seconds() > .5) {
                reversed = !reversed;
                multiplier *= -1;
                reversedCooldown.reset();
            }

            //Moving Forward
            if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(multiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(multiplier * gamepad1.left_stick_y);
                rightMotorFront.setPower(multiplier * gamepad1.left_stick_y);
                rightMotorBack.setPower(multiplier * gamepad1.left_stick_y);
            }

            //Point Turn
            else if (gamepad1.right_stick_x != 0 && gamepad1.left_stick_y == 0) {
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