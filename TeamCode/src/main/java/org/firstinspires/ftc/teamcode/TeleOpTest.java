package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TeleOpTest", group="Linear Opmode")
public class TeleOpTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;
    private DcMotor crane = null;
    private boolean reversed = false;
    private int multiplier = 1;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        leftMotorFront = hardwareMap.get(DcMotor.class, "left_motor_front");
        leftMotorBack = hardwareMap.get(DcMotor.class, "left_motor_back");
        rightMotorFront = hardwareMap.get(DcMotor.class, "right_motor_front");
        rightMotorBack = hardwareMap.get(DcMotor.class, "right_motor_back");
        crane = hardwareMap.get(DcMotor.class, "crane");

        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            //Stuff to display for Telemetry
            telemetry.addData("Left Motor Power", leftMotorFront.getPower());
            telemetry.addData("Right Motor Power", rightMotorFront.getPower());
            telemetry.addData("Reversed: ", reversed);
            telemetry.update();

            if(gamepad1.x && runtime.seconds() > 1) {
                if(reversed == true) {
                    multiplier = 1;
                    reversed = false;
                    runtime.reset();
                }
                else if(reversed == false) {
                    reversed = true;
                    multiplier = -1;
                    runtime.reset();
                }
            }

            if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(multiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(multiplier * gamepad1.left_stick_y);
                rightMotorFront.setPower(multiplier * gamepad1.left_stick_y);
                rightMotorBack.setPower(multiplier * gamepad1.left_stick_y);
            }
            if (gamepad1.right_stick_x != 0 && gamepad1.left_stick_y == 0) {
                leftMotorFront.setPower(gamepad1.right_stick_x);
                leftMotorBack.setPower(gamepad1.right_stick_x);
                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
            }
            if (reversed == false && gamepad1.left_stick_y < 0 && gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                leftMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y) / 2));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            if (reversed == true && gamepad1.left_stick_y < 0 && gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
                rightMotorBack.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
            }
            if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
                rightMotorBack.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
            }
            if (reversed == false && gamepad1.left_stick_y < 0 && gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2));
                rightMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2));
            }
            if (reversed == true && gamepad1.left_stick_y < 0 && gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2));
                leftMotorBack.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2));
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
                leftMotorBack.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            if(gamepad1.dpad_down || gamepad2.dpad_down){
                crane.setPower(-1);
            }
            if(gamepad1.dpad_up || gamepad2.dpad_up)
                crane.setPower(1);
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
                crane.setPower(0);
            }
        }
    }
}
