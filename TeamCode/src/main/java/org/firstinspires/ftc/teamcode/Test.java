package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.lang.Math;

@TeleOp(name="Test", group="Linear Opmode")
public class Test extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor test = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        test = hardwareMap.get(DcMotor.class, "test");
        test.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            //Stuff to display for Telemetry
            telemetry.addData("Motor Power", test.getPower());
            telemetry.addData("Motor Direction", test.getDirection());
            telemetry.addData("Motor Position", test.getCurrentPosition());
            telemetry.update();

            if (gamepad1.left_stick_y != 0) {
                test.setPower(gamepad1.left_stick_y);
            } else {
                test.setPower(0);
            }
        }
    }
}
