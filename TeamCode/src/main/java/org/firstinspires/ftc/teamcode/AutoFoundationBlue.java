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

@Autonomous(name="Autonomous_Foundation Blue", group="Autonomous Competition")
public class AutoFoundationBlue extends GodFatherOfAllAutonomous {

    @Override
    public void runOpMode() {
        initialization();

        waitForStart();

        runTo(60, allPower);
        sleep(2000);
        turnRight(45, allPower);
        sleep(1000);
        runTo(-30, allPower);
        turnLeft(90, allPower);
        runTo(16, allPower);
        turnRight(55, allPower);
        runTo(-25, allPower);
        sleep(1000);
        runTo(-50, allPower / 2);
        sleep(1000);
        runTo(-25, allPower / 2);
        turnRight(80, allPower);
        runTo(20, allPower);
        turnRight(30, allPower);
        runTo(10, allPower);
        //turnRight(50, allPower);
        //runTo(10, allPower / 2);
        //turnLeft(90, allPower);
    }
}
