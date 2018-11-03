package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@Autonomous(name="Time", group="Autonomous Encoder")
public class AutoTime extends LinearOpMode {

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

        //Stuff to display for Telemetry


        //crane.setPower(1);
        runtime.reset();
        while (runtime.milliseconds() < 3000) {
            telemetry.addLine("Phase: Lowering");
            telemetry.update();
            crane.setPower(1);
        }
        crane.setPower(0);

        sleep(100);
        runtime.reset();
        while(runtime.milliseconds() < 250) {
            telemetry.addLine("Readjusting");
            telemetry.update();
            crane.setPower(-1);
        }
        crane.setPower(0);

        sleep(10000);

        runtime.reset();
        while(runtime.milliseconds() < 250) {
            telemetry.addLine("Backing up");
            telemetry.update();
            leftMotorFront.setPower(.25);
            leftMotorBack.setPower(.25);
            rightMotorFront.setPower(.25);
            rightMotorBack.setPower(.25);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);
        runtime.reset();
        while(runtime.milliseconds() < 500) {
            telemetry.addLine("Turning");
            telemetry.update();
            leftMotorFront.setPower(-.25);
            leftMotorBack.setPower(-.25);
            rightMotorFront.setPower(.25);
            rightMotorBack.setPower(.25);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);
        runtime.reset();
        while(runtime.milliseconds() < 1000) {
            telemetry.addLine("Backing up");
            telemetry.update();
            leftMotorFront.setPower(.25);
            leftMotorBack.setPower(.25);
            rightMotorFront.setPower(.25);
            rightMotorBack.setPower(.25);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);
        runtime.reset();
        while(runtime.milliseconds() < 500) {
            telemetry.addLine("Turning Back");
            telemetry.update();
            leftMotorFront.setPower(.25);
            leftMotorBack.setPower(.25);
            rightMotorFront.setPower(-.25);
            rightMotorBack.setPower(-.25);
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);
        runtime.reset();
        while(runtime.milliseconds() < 10000) {
            telemetry.addLine("Going for crater");
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
    }
}
