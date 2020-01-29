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

@Autonomous(name="Autonomous_Foundation Red", group="Foundation")
public class AutoFoundationRed extends GodFatherOfAllAutonomous {

    private boolean red = true;

    @Override
    public void runOpMode() {
        
        initialization();
        
        waitForStart();
        
        autoTime.reset();
        
        verticalUp();
        spinnyBoyUp();

        strafeRight(8, allPower, slowPower);
        runTo(15, allPower, slowPower);
        runUntil(70, allPower * .7);
        runTo(3, allPower * .6, slowPower * .9);
        moveFoundation(red);
        runTo(10, allPower, slowPower);
        runTo(-10, allPower, slowPower);
        
        verticalDown();
        //15 inches Left for far, 12 inches Right for wall (Red)
        strafeLeft(15, allPower, slowPower);
        
        runTo(-35, allPower, slowPower);
        //Go to far side
        strafeLeft(8, allPower, slowPower);
    }
}