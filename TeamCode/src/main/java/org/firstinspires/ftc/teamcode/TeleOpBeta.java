package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



@TeleOp(name="TeleOpBeta", group="Linear Opmode")
public class TeleOpBeta extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime cooldown = new ElapsedTime();

    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;
    private Servo clawServo = null;
    private Servo armServo = null;

    private boolean reversed = false;
    private boolean halfSpeed = false;
    private double multiplier = -1;

    private double x = 0;
    private double y = 0;
    private double absx = 0;
    private double absy = 0;

    @Override
    public void runOpMode() {

        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");

        leftMotorFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        cooldown.reset();

        while (opModeIsActive()) {
            telemetry.addData("Left Motors", leftMotorFront.getPower());
            telemetry.addData("Right Motors", rightMotorFront.getPower());
            telemetry.addData("Half Speed" , halfSpeed);
            telemetry.update();

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
            x = gamepad1.left_stick_x;
            y = gamepad1.left_stick_y;
            absx = Math.abs(gamepad1.left_stick_x);
            absy = Math.abs(gamepad1.left_stick_y);

            //Forward & Backward
            if (absy >= .1 && absx <= .1) {
                leftMotorFront.setPower(multiplier * y);
                leftMotorBack.setPower(multiplier * y);
                rightMotorFront.setPower(multiplier * y);
                rightMotorBack.setPower(multiplier * y);
            }
            //Point Turns
            if (absy <= .1 && absx >= .1) {
                leftMotorFront.setPower(multiplier * x);
                leftMotorBack.setPower(multiplier * x);
                rightMotorFront.setPower(-multiplier * x);
                rightMotorBack.setPower(-multiplier * x);
            }
            //Q1 Bottom
            else if (y > 0 && x > 0 && y < x) {
                leftMotorFront.setPower(multiplier * x);
                leftMotorBack.setPower(multiplier * x);
                rightMotorFront.setPower(multiplier * y / 2);
                rightMotorBack.setPower(multiplier * y / 2);
            }
            //Q1 Top
            else if (y > 0 && x > 0 && y > x) {
                leftMotorFront.setPower(multiplier * x);
                leftMotorBack.setPower(multiplier * x);
                rightMotorFront.setPower(multiplier * (x / y) * .5);
                rightMotorBack.setPower(multiplier * (x / y) * .5);
            }
            //Q2 Top
            else if (y > 0 && x < 0 && y > absx) {
                leftMotorFront.setPower(multiplier * (absx / y) * .5);
                leftMotorBack.setPower(multiplier * (absx / y) * .5);
                rightMotorFront.setPower(multiplier * absx);
                rightMotorBack.setPower(multiplier * absx);
            }
            //Q2 Bottom
            else if (y > 0 && x < 0 && y < absx) {
                leftMotorFront.setPower(multiplier * y / 2);
                leftMotorBack.setPower(multiplier * y / 2);
                rightMotorFront.setPower(multiplier * absx);
                rightMotorBack.setPower(multiplier * absx);
            }
            //Q3 Top
            else if (y < 0 && x < 0 && absy < absx) {
                leftMotorFront.setPower(multiplier * y / 2);
                leftMotorBack.setPower(multiplier * y / 2);
                rightMotorFront.setPower(multiplier * x);
                rightMotorBack.setPower(multiplier * x);
            }
            //Q3 Bottom
            else if (y < 0 && x < 0 && absy > absx) {
                leftMotorFront.setPower(multiplier * (absx / y) * .5);
                leftMotorBack.setPower(multiplier * (absx / y) * .5);
                rightMotorFront.setPower(multiplier * x);
                rightMotorBack.setPower(multiplier * x);
            }
            //Q4 bottom
            else if (y < 0 && x > 0 && absy > absx) {
                leftMotorFront.setPower(multiplier * (x / y) * .5);
                leftMotorBack.setPower(multiplier * (x / y) * .5);
                rightMotorFront.setPower(multiplier * x);
                rightMotorBack.setPower(multiplier * x);
            }
            //Q4 Top
            else if (y < 0 && x > 0 && absy < absx) {
                leftMotorFront.setPower(multiplier * x);
                leftMotorBack.setPower(multiplier * x);
                rightMotorFront.setPower(multiplier * y / 2);
                rightMotorBack.setPower(multiplier * y / 2);
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