package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import java.util.List;

/*This is the encoder values program made by Jack. It should be run after setting the TICKS_MULTIPLIER
to 0 in GodFatherOfAllAutonomous. Then, change the value to 20F / How far it actually went. This means
that the ticks will be multiplied such that they go to 20 inches when they are told to go to 20 inches.
 */

@Autonomous(name="EncoderValueTest_Jack", group="Test")
public class EncoderValuesbyJack extends GodFatherOfAllAutonomous {
    private ElapsedTime time = new ElapsedTime();

    @Override
    public void runOpMode() {
        initialization();

        waitForStart();
        //Runs for what it thinks is 20 inches using the non changed tick multiplier. This is also
        //where the 20 in the TICKS_MULTIPLIER comes from.
        runTo(20, allPower, slowPower);
        //make sure everything is stopped, just in case
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        telemetry.addData("Ran Supposedly 20 inches. Measure.", "Change encoder multiplier in GodFather for");
        telemetry.addData("Left Motor Front Position", leftMotorFront.getCurrentPosition());
        telemetry.addData("Left Motor Back Position", leftMotorBack.getCurrentPosition());
        telemetry.addData("Right Motor Front Position", rightMotorFront.getCurrentPosition());
        telemetry.addData("Right Motor Back Position", rightMotorBack.getCurrentPosition());
        telemetry.update();
        sleep(10000);
    }
}