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

@Autonomous(name="Autonomous", group="Autonomous Competition")
public class Auto extends GodFatherOfAllAutonomous {

    @Override
    public void runOpMode() {
        initialization();
        int[] skystoneLocation = objectDetect();
        int skystone1 = skystoneLocation[0];
        int skystone2 = skystoneLocation[1];

        waitForStart();

        picSkystone(skystone1);
        runTo(-40,allPower);
        turnRight(70, allPower)
        picSkystone(skystone2);
        runTo(-20, allPower);
    }
    public void pickSkystone(int skystone) {
        turnRight((skystone - 2) * 15, allPower);
        runTo(skystone + 6, allPower);
        armUp();
        runTo( -6 - skystone, allPower);
        turnLeft((skystone - 2) * 15, allPower);
        turnLeft(70, allPower);
        runTo(40, allPower);
        armDown();
    }
}