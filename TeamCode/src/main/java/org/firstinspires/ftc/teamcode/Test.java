package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@Disabled
@TeleOp(name="Distance", group="Autonomous Encoder")
public class Test extends LinearOpMode {

    private DistanceSensor distance = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        distance = hardwareMap.get(DistanceSensor.class, "distance");

        waitForStart();

        while (opModeIsActive()) {
            //Stuff to display for Telemetry
            telemetry.addData("Distance: ", distance.getDistance(DistanceUnit.MM));
            telemetry.update();
        }
    }
}
