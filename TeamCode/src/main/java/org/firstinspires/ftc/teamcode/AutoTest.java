package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.lang.Math;

@Autonomous(name="AutoTest", group="Autonomous Encoder")
public class AutoTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor test = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        test = hardwareMap.get(DcMotor.class, "test");
        test.setDirection(DcMotor.Direction.REVERSE);
        test.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        test.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int phase = 0;

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            //Stuff to display for Telemetry
            telemetry.addData("Motor Power", test.getPower());
            telemetry.addData("Motor Direction", test.getDirection());
            telemetry.addData("Motor Position", test.getCurrentPosition());
            telemetry.addData("Motor Target", test.getTargetPosition());
            telemetry.addData("Phase: ", phase);
            telemetry.update();

            sleep(1000);
            phase = 1;
            test.setTargetPosition(10000);
            test.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (test.isBusy()) {
                telemetry.addData("Motor Power", test.getPower());
                telemetry.addData("Motor Direction", test.getDirection());
                telemetry.addData("Motor Position", test.getCurrentPosition());
                telemetry.addData("Motor Target", test.getTargetPosition());
                telemetry.addData("Phase: ", phase);
                telemetry.update();
                test.setPower(1);
            }
            test.setPower(0);
            sleep(1000);

            phase = 2;
            test.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            sleep(1000);
            test.setTargetPosition(-10000);
            test.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            while (test.isBusy()) {
                telemetry.addData("Motor Power", test.getPower());
                telemetry.addData("Motor Direction", test.getDirection());
                telemetry.addData("Motor Position", test.getCurrentPosition());
                telemetry.addData("Motor Target", test.getTargetPosition());
                telemetry.addData("Phase: ", phase);
                telemetry.update();
                test.setPower(-1);
            }
            test.setPower(0);

            sleep(1000);
            phase = 3;
            sleep(1000);
        }
    }
}
