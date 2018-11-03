package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.lang.Math;
@Disabled
@Autonomous(name="AutoTest", group="Autonomous Encoder")
public class AutoTest extends LinearOpMode {

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
        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            //Stuff to display for Telemetry
            telemetry.addData("Left Motor Position", leftMotorFront.getCurrentPosition());
            telemetry.addData("Right Motor Position", rightMotorBack.getCurrentPosition());
            telemetry.addData("Left Motor Target", leftMotorFront.getCurrentPosition());
            telemetry.addData("Right Motor Target", rightMotorBack.getTargetPosition());
            telemetry.update();

            sleep(1000);
            leftMotorFront.setTargetPosition(1000);
            leftMotorBack.setTargetPosition(1000);
            rightMotorFront.setTargetPosition(1000);
            rightMotorBack.setTargetPosition(1000);
            leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (leftMotorFront.isBusy() && rightMotorFront.isBusy()) {
                telemetry.addData("Left Motor Position", leftMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Position", rightMotorBack.getCurrentPosition());
                telemetry.addData("Left Motor Target", leftMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Target", rightMotorBack.getTargetPosition());
                telemetry.update();
                leftMotorFront.setPower(1);
                leftMotorBack.setPower(1);
                rightMotorFront.setPower(1);
                rightMotorBack.setPower(1);
            }
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);
            sleep(10000);

            leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            sleep(5000);
            leftMotorFront.setTargetPosition(-1000);
            leftMotorBack.setTargetPosition(-1000);
            rightMotorFront.setTargetPosition(-1000);
            rightMotorBack.setTargetPosition(-1000);
            leftMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotorBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (leftMotorFront.isBusy() && rightMotorFront.isBusy()) {
                telemetry.addData("Left Motor Position", leftMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Position", rightMotorBack.getCurrentPosition());
                telemetry.addData("Left Motor Target", leftMotorFront.getCurrentPosition());
                telemetry.addData("Right Motor Target", rightMotorBack.getTargetPosition());
                telemetry.update();
                leftMotorFront.setPower(-1);
                leftMotorBack.setPower(-1);
                rightMotorFront.setPower(-1);
                rightMotorBack.setPower(-1);
            }
            leftMotorFront.setPower(0);
            leftMotorBack.setPower(0);
            rightMotorFront.setPower(0);
            rightMotorBack.setPower(0);
        }
    }
}
