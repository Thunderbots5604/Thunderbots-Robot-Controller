package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TeleOpNegative", group="Linear Opmode")
public class TeleNegative extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftMotorFront = null;
    private DcMotor leftMotorBack = null;
    private DcMotor rightMotorFront = null;
    private DcMotor rightMotorBack = null;
    private DcMotor crane = null;

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
            telemetry.addData("Left Motor Power", leftMotorFront.getCurrentPosition());
            telemetry.addData("Right Motor Power", rightMotorFront.getPower());
            telemetry.update();

            if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(gamepad1.left_stick_y);
                //leftMotorFront no change
                leftMotorBack.setPower(-gamepad1.left_stick_y);
                //leftMotorBack power negative
                rightMotorFront.setPower(gamepad1.left_stick_y);
                rightMotorBack.setPower(gamepad1.left_stick_y);
                //rightMotorBack no change
            }
            if (gamepad1.right_stick_x != 0 && gamepad1.left_stick_y == 0) {
                leftMotorFront.setPower(gamepad1.right_stick_x);
                //switched leftMotorFront no change
                leftMotorBack.setPower(-gamepad1.right_stick_x);
                //leftMotorBack power negative
                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
                //rightMotorBack no change
            }
            if (gamepad1.left_stick_y < 0 && gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2));
                leftMotorBack.setPower(-(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2)));
                //leftMotorBack power negative
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
                rightMotorBack.setPower((Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2));
                //rightMotorBack no change
            }
            if (gamepad1.left_stick_y < 0 && gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2));
                rightMotorBack.setPower((-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2)));
                //rightMotorBack no change
            }
            if (gamepad1.left_stick_y > 0 && gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2);
                //switched leftMotorFront no change
                leftMotorBack.setPower(-(Math.abs(gamepad1.right_stick_x) + Math.abs(gamepad1.left_stick_y)/2));
                //leftMotorBack power negative
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            if(gamepad1.dpad_down || gamepad2.dpad_down){
                crane.setPower(-1);
            }
            if(gamepad1.dpad_up || gamepad2.dpad_up) {
                crane.setPower(1);
            }
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
