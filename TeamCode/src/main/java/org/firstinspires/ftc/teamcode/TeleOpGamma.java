package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



@TeleOp(name="TeleOpGamma", group="Linear Opmode")
public class TeleOpGamma extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime rotTime = new ElapsedTime();

    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;

    private boolean reversed = false;
    private boolean halfSpeed = false;
    private double multiplier = -1;

    private double x = 0;
    private double y = 0;
    private double absx = 0;
    private double absy = 0;

    private int rots = 0;
    private double power = 0;
    private int bumps = 0;

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

        while (opModeIsActive()) {
            x = gamepad1.left_stick_x;
            y = gamepad1.left_stick_y;
            absx = Math.abs(gamepad1.left_stick_x);
            absy = Math.abs(gamepad1.left_stick_y);

            if (x > 0.1 && absy < 0.2  && rots == 0) {
                rots += 1;
            }
            else if (absx < 0.2 && absy > 0.1 && (rots == 1 || rots == 3)) {
                rots += 1;
            }
            else if (x < -0.1 && absy < 0.2 && rots == 2) {
                rots += 1;
            }
            if (rots == 4) {
                power += .2;
                rots = 0;
            }
            if (rotTime.milliseconds() > 600) {
                rotTime.reset();
                power -= 0.1;
            }


            if (gamepad1.right_bumper && bumps % 2 == 0) {
                bumps += 1;
            }
            if (gamepad1.left_bumper && bumps % 2 == 1) {
                bumps += 1;
            }
            if (bumps >= 3) {
                power -= .2;
                bumps -= 3;
            }
            if (gamepad1.b) {
                power = 0;
            }
            if (power > 1) {
                power = 1;
            }
            if (power < -1) {
                power = -1;
            }
            if (power < 0.1 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if (power > 0.1 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(power);
                leftMotorBack.setPower(power);
                rightMotorFront.setPower(power);
                rightMotorBack.setPower(power);
            }
            else if (gamepad1.right_stick_x != 0 && power < 0.1) {
                leftMotorFront.setPower(gamepad1.right_stick_x);
                leftMotorBack.setPower(gamepad1.right_stick_x);
                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
            }
            else if (gamepad1.right_stick_x > 0 && power > 0){
                leftMotorFront.setPower(gamepad1.right_stick_x);
                leftMotorBack.setPower(gamepad1.right_stick_x);
                rightMotorFront.setPower(-gamepad1.right_stick_x + power);
                rightMotorBack.setPower(-gamepad1.right_stick_x + power);
            }
            else if (gamepad1.right_stick_x < 0 & power > 0) {
                leftMotorFront.setPower(gamepad1.right_stick_x + power);
                leftMotorBack.setPower(gamepad1.right_stick_x + power);
                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
            }
            else if (gamepad1.right_stick_x > 0 && power < 0) {
                leftMotorFront.setPower(gamepad1.right_stick_x);
                leftMotorBack.setPower(gamepad1.right_stick_x);
                rightMotorFront.setPower(-gamepad1.right_stick_x - power);
                rightMotorBack.setPower(-gamepad1.right_stick_x - power);
            }
            else if (gamepad1.right_stick_x < 0 & power < 0) {
                leftMotorFront.setPower(gamepad1.right_stick_x - power);
                leftMotorBack.setPower(gamepad1.right_stick_x - power);
                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
            }
        }
    }
}